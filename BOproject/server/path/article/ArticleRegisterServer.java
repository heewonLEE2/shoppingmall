// 실제 ArticleRegisterServer에 적용할 S3 업로드 코드
package BOproject.server.path.article;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import BOproject.model.ArticleVO;
import BOproject.service.ArticleService;
import BOproject.service.UserService;
import BOproject.service.impl.ArticleServiceImpl;
import BOproject.service.impl.UserServiceImpl;
import BOproject.util.CorsHeaderUtil;

public class ArticleRegisterServer implements HttpHandler {

	private final String BUCKET_NAME = "heewonlee2"; // 실제 버킷명 사용
	private AmazonS3 s3Client;

	public ArticleRegisterServer() {
		this.s3Client = AmazonS3ClientBuilder.defaultClient();
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// CORS 헤더는 모든 응답에 공통으로 설정
		CorsHeaderUtil.getResponseHeaders(exchange);

		if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
			exchange.sendResponseHeaders(204, -1);
			return;
		}

		if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
			handleFileUploadToS3(exchange);
		} else {
			String response = "잘못된 요청입니다.";
			exchange.sendResponseHeaders(405, response.getBytes(StandardCharsets.UTF_8).length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response.getBytes(StandardCharsets.UTF_8));
			}
		}
	}

	private void handleFileUploadToS3(HttpExchange exchange) throws IOException {
		String response;
		ArticleService articleService = new ArticleServiceImpl();
		UserService userService = new UserServiceImpl();

		try {
			String contentType = exchange.getRequestHeaders().getFirst("Content-Type");

			if (contentType != null && contentType.startsWith("multipart/form-data")) {
				ArticleVO articleVO = processMultipartDataWithS3(exchange);

				if (userService.getUser(articleVO.getUser_id()).getUser_id() != null) {
					articleService.registArticle(articleVO);
					response = "게시판등록을 성공하였습니다.";
					System.out.println("게시판 등록 성공 - S3 URL: " + articleVO.getAimgfile());
				} else {
					response = "등록된 회원이 아닙니다.! 회원가입후 이용해주세요.";
					System.out.println("사용자 확인 실패");
				}
			} else {
				response = "잘못된 Content-Type입니다.";
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = "서버 오류가 발생했습니다: " + e.getMessage();
		}

		exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(response.getBytes(StandardCharsets.UTF_8));
		}
	}

	private ArticleVO processMultipartDataWithS3(HttpExchange exchange) throws Exception {
		ArticleVO articleVO = new ArticleVO();

		String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
		String boundary = extractBoundary(contentType);

		byte[] requestBody = exchange.getRequestBody().readAllBytes();
		byte[] boundaryBytes = ("--" + boundary).getBytes(StandardCharsets.UTF_8);
		byte[] endBoundaryBytes = ("\r\n--" + boundary).getBytes(StandardCharsets.UTF_8);

		parseMultipartDataWithS3(requestBody, boundaryBytes, endBoundaryBytes, articleVO);

		return articleVO;
	}

	private void parseMultipartDataWithS3(byte[] requestBody, byte[] boundaryBytes, byte[] endBoundaryBytes,
			ArticleVO articleVO) throws Exception {
		int startIndex = 0;

		while (startIndex < requestBody.length) {
			int boundaryIndex = findBytes(requestBody, boundaryBytes, startIndex);
			if (boundaryIndex == -1)
				break;

			int partStart = boundaryIndex + boundaryBytes.length;
			if (partStart >= requestBody.length)
				break;

			int nextBoundaryIndex = findBytes(requestBody, endBoundaryBytes, partStart);
			if (nextBoundaryIndex == -1) {
				nextBoundaryIndex = requestBody.length;
			}

			byte[] partData = Arrays.copyOfRange(requestBody, partStart, nextBoundaryIndex);
			processPartWithS3(partData, articleVO);

			startIndex = nextBoundaryIndex;
		}
	}

	private void processPartWithS3(byte[] partData, ArticleVO articleVO) throws Exception {
		byte[] headerSeparator = "\r\n\r\n".getBytes(StandardCharsets.UTF_8);
		int separatorIndex = findBytes(partData, headerSeparator, 0);

		if (separatorIndex == -1)
			return;

		String header = new String(Arrays.copyOfRange(partData, 0, separatorIndex), StandardCharsets.UTF_8);
		byte[] bodyData = Arrays.copyOfRange(partData, separatorIndex + headerSeparator.length, partData.length);

		if (header.contains("name=\"file\"") && header.contains("filename=")) {
			// 파일을 S3에 업로드
			String fileName = extractFileNameFromHeader(header);
			String contentType = extractContentTypeFromHeader(header);

			if (fileName != null && !fileName.isEmpty()) {
				// 마지막 \r\n 제거
				if (bodyData.length >= 2 && bodyData[bodyData.length - 2] == '\r'
						&& bodyData[bodyData.length - 1] == '\n') {
					bodyData = Arrays.copyOfRange(bodyData, 0, bodyData.length - 2);
				}

				String s3Url = uploadToS3(fileName, bodyData, contentType);
				articleVO.setAimgfile(s3Url); // S3 URL을 aimgfile에 저장
				System.out.println("S3 업로드 완료: " + s3Url);
			}
		} else {
			// 폼 데이터 처리
			String fieldName = extractFieldNameFromHeader(header);
			String fieldValue = new String(bodyData, StandardCharsets.UTF_8).trim();

			if (fieldValue.endsWith("\r\n")) {
				fieldValue = fieldValue.substring(0, fieldValue.length() - 2);
			}

			setArticleField(articleVO, fieldName, fieldValue);
		}
	}

	private String uploadToS3(String fileName, byte[] fileData, String contentType) throws Exception {
		try {
			// 파일 크기 제한 (10MB)
			if (fileData.length > 10 * 1024 * 1024) {
				throw new RuntimeException("파일 크기가 10MB를 초과합니다.");
			}

			// 파일 타입 검증
			if (contentType == null || !isImageFile(contentType)) {
				throw new RuntimeException("이미지 파일만 업로드 가능합니다.");
			}

			// 고유한 키 생성
			String key = "articles/" + System.currentTimeMillis() + "_" + fileName;

			// 메타데이터 설정
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(fileData.length);
			metadata.setContentType(contentType);

			// S3에 업로드 (ACL 없이)
			ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
			PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, key, inputStream, metadata);

			s3Client.putObject(request);

			// 공개 URL 반환
			String publicUrl = "https://" + BUCKET_NAME + ".s3.ap-northeast-2.amazonaws.com/" + key;
			return publicUrl;

		} catch (Exception e) {
			System.err.println("S3 업로드 실패: " + e.getMessage());
			throw new RuntimeException("S3 업로드 실패: " + e.getMessage(), e);
		}
	}

	private boolean isImageFile(String contentType) {
		return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/jpg")
				|| contentType.equals("image/png") || contentType.equals("image/gif")
				|| contentType.equals("image/webp"));
	}

	// 기존 유틸리티 메서드들...
	private int findBytes(byte[] source, byte[] target, int startIndex) {
		for (int i = startIndex; i <= source.length - target.length; i++) {
			boolean found = true;
			for (int j = 0; j < target.length; j++) {
				if (source[i + j] != target[j]) {
					found = false;
					break;
				}
			}
			if (found)
				return i;
		}
		return -1;
	}

	private String extractBoundary(String contentType) {
		String[] parts = contentType.split(";");
		for (String part : parts) {
			if (part.trim().startsWith("boundary=")) {
				return part.trim().substring(9);
			}
		}
		return null;
	}

	private String extractFileNameFromHeader(String header) {
		String[] lines = header.split("\r\n");
		for (String line : lines) {
			if (line.contains("filename=")) {
				int start = line.indexOf("filename=\"") + 10;
				int end = line.indexOf("\"", start);
				if (start > 9 && end > start) {
					return line.substring(start, end);
				}
			}
		}
		return null;
	}

	private String extractContentTypeFromHeader(String header) {
		String[] lines = header.split("\r\n");
		for (String line : lines) {
			if (line.startsWith("Content-Type: ")) {
				return line.substring(14).trim();
			}
		}
		return "application/octet-stream";
	}

	private String extractFieldNameFromHeader(String header) {
		String[] lines = header.split("\r\n");
		for (String line : lines) {
			if (line.contains("name=\"")) {
				int start = line.indexOf("name=\"") + 6;
				int end = line.indexOf("\"", start);
				if (start > 5 && end > start) {
					return line.substring(start, end);
				}
			}
		}
		return null;
	}

	private void setArticleField(ArticleVO articleVO, String fieldName, String fieldValue) {
		if (fieldName != null && fieldValue != null) {
			switch (fieldName) {
			case "user_id":
				articleVO.setUser_id(fieldValue);
				break;
			case "atitle":
				articleVO.setAtitle(fieldValue);
				break;
			case "acontent":
				articleVO.setAcontent(fieldValue);
				break;
			case "cid":
				articleVO.setCid(fieldValue);
				break;
			case "alikeCount":
				try {
					articleVO.setAlikeCount(Integer.parseInt(fieldValue));
				} catch (NumberFormatException e) {
					articleVO.setAlikeCount(0);
				}
				break;
			}
		}
	}
}