package BOproject.server.path;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.ArticleVO;
import BOproject.service.ArticleService;
import BOproject.service.UserService;
import BOproject.service.impl.ArticleServiceImpl;
import BOproject.service.impl.UserServiceImpl;

public class ArticleRegisterServer2 implements HttpHandler {

    private static final String UPLOAD_DIR = "C:\\pub2504\\Mid_termProject_Front_08_04\\Mid_termProject_Front\\css\\assets\\article_img\\";
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // CORS 헤더 설정
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            handleFileUpload(exchange);
        } else {
            String response = "잘못된 요청입니다.";
            exchange.sendResponseHeaders(405, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    private void handleFileUpload(HttpExchange exchange) throws IOException {
        String response;
        ArticleService articleService = new ArticleServiceImpl();
        UserService userService = new UserServiceImpl();
        
        try {
            String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
            
            if (contentType != null && contentType.startsWith("multipart/form-data")) {
                ArticleVO articleVO = processMultipartData(exchange);
                
                if (userService.getUser(articleVO.getUser_id()).getUser_id() != null) {
                    articleService.registArticle(articleVO);
                    response = "게시판등록을 성공하였습니다.";
                    System.out.println("게시판 등록 성공");
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

    private ArticleVO processMultipartData(HttpExchange exchange) throws Exception {
        ArticleVO articleVO = new ArticleVO();
        
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        String boundary = extractBoundary(contentType);
        
        // 바이트 배열로 데이터 읽기
        byte[] requestBody = exchange.getRequestBody().readAllBytes();
        
        // boundary를 바이트 배열로 변환
        byte[] boundaryBytes = ("--" + boundary).getBytes(StandardCharsets.UTF_8);
        byte[] endBoundaryBytes = ("\r\n--" + boundary).getBytes(StandardCharsets.UTF_8);
        
        // multipart 데이터를 바이트 레벨에서 파싱
        parseMultipartData(requestBody, boundaryBytes, endBoundaryBytes, articleVO);
        
        return articleVO;
    }
    
    private void parseMultipartData(byte[] requestBody, byte[] boundaryBytes, byte[] endBoundaryBytes, ArticleVO articleVO) throws Exception {
        int startIndex = 0;
        
        while (startIndex < requestBody.length) {
            // 다음 boundary 찾기
            int boundaryIndex = findBytes(requestBody, boundaryBytes, startIndex);
            if (boundaryIndex == -1) break;
            
            // 다음 부분의 시작점
            int partStart = boundaryIndex + boundaryBytes.length;
            if (partStart >= requestBody.length) break;
            
            // 다음 boundary 찾기
            int nextBoundaryIndex = findBytes(requestBody, endBoundaryBytes, partStart);
            if (nextBoundaryIndex == -1) {
                nextBoundaryIndex = requestBody.length;
            }
            
            // 이 부분의 데이터 추출
            byte[] partData = Arrays.copyOfRange(requestBody, partStart, nextBoundaryIndex);
            processPart(partData, articleVO);
            
            startIndex = nextBoundaryIndex;
        }
    }
    
    private void processPart(byte[] partData, ArticleVO articleVO) throws Exception {
        // 헤더와 본문 분리 (\r\n\r\n으로 구분)
        byte[] headerSeparator = "\r\n\r\n".getBytes(StandardCharsets.UTF_8);
        int separatorIndex = findBytes(partData, headerSeparator, 0);
        
        if (separatorIndex == -1) return;
        
        // 헤더 부분
        String header = new String(Arrays.copyOfRange(partData, 0, separatorIndex), StandardCharsets.UTF_8);
        
        // 본문 부분
        byte[] bodyData = Arrays.copyOfRange(partData, separatorIndex + headerSeparator.length, partData.length);
        
        if (header.contains("name=\"file\"") && header.contains("filename=")) {
            // 파일 처리
            String fileName = extractFileNameFromHeader(header);
            if (fileName != null && !fileName.isEmpty()) {
                // 마지막 \r\n 제거
                if (bodyData.length >= 2 && bodyData[bodyData.length-2] == '\r' && bodyData[bodyData.length-1] == '\n') {
                    bodyData = Arrays.copyOfRange(bodyData, 0, bodyData.length - 2);
                }
                
                String savedFileName = saveFile(fileName, bodyData);
                articleVO.setAimgfile("/css/assets/article_img/" + savedFileName);
                System.out.println("파일 처리 완료: " + savedFileName);
            }
        } else {
            // 폼 데이터 처리
            String fieldName = extractFieldNameFromHeader(header);
            String fieldValue = new String(bodyData, StandardCharsets.UTF_8).trim();
            
            // 마지막 \r\n 제거
            if (fieldValue.endsWith("\r\n")) {
                fieldValue = fieldValue.substring(0, fieldValue.length() - 2);
            }
            
            setArticleField(articleVO, fieldName, fieldValue);
        }
    }
    
    private int findBytes(byte[] source, byte[] target, int startIndex) {
        for (int i = startIndex; i <= source.length - target.length; i++) {
            boolean found = true;
            for (int j = 0; j < target.length; j++) {
                if (source[i + j] != target[j]) {
                    found = false;
                    break;
                }
            }
            if (found) return i;
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
    
    private String saveFile(String originalFileName, byte[] fileData) throws IOException {
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFileName.substring(dotIndex);
        }
        
        String savedFileName = System.currentTimeMillis() + "_" + originalFileName;
        File savedFile = new File(UPLOAD_DIR + savedFileName);
        
        // 바이너리 데이터를 그대로 저장
        Files.write(savedFile.toPath(), fileData);
        
        System.out.println("파일 저장 완료: " + savedFile.getAbsolutePath());
        System.out.println("파일 크기: " + fileData.length + " bytes");
        
        return savedFileName;
    }
}
