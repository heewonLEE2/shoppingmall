package BOproject.server.path;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.ReplyVO;
import BOproject.service.ReplyService;
import BOproject.service.impl.ReplyServiceImpl;

public class ReplyRegisterServer implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) {
		try {
			// CORS 헤더
			exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
			exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
			exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

			// OPTIONS 요청은 응답만 보내고 종료
			if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
				exchange.sendResponseHeaders(204, -1);
				return;
			}

			ReplyService service = new ReplyServiceImpl();

			String method = exchange.getRequestMethod();

			if ("GET".equalsIgnoreCase(method)) {
				// 댓글 목록 조회
				String query = exchange.getRequestURI().getQuery(); // aid=1 형태 예상
				int aid = 0;
				if (query != null && query.startsWith("aid=")) {
					try {
						aid = Integer.parseInt(query.substring(4));
					} catch (NumberFormatException ignored) {
					}
				}

				List<ReplyVO> list = service.selectArticleReply(aid);

				// JSON 생성 (라이브러리 없이 간단히)
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				for (int i = 0; i < list.size(); i++) {
					ReplyVO r = list.get(i);
					sb.append("{");
					sb.append("\"rid\":").append(r.getRid()).append(",");
					sb.append("\"user_id\":\"").append(escapeJson(r.getUser_id())).append("\",");
					sb.append("\"aid\":").append(r.getAid()).append(",");
					sb.append("\"rcontent\":\"").append(escapeJson(r.getRcontent())).append("\",");
					sb.append("\"rdate\":\"").append(r.getRdate()).append("\"");
					sb.append("}");
					if (i < list.size() - 1)
						sb.append(",");
				}
				sb.append("]");

				byte[] responseBytes = sb.toString().getBytes(StandardCharsets.UTF_8);
				exchange.sendResponseHeaders(200, responseBytes.length);
				try (OutputStream os = exchange.getResponseBody()) {
					os.write(responseBytes);
				}

			} else if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)
					|| "DELETE".equalsIgnoreCase(method)) {
				// POST/PUT/DELETE 모두 JSON 본문 받음
				BufferedReader br = new BufferedReader(
						new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				String json = sb.toString();

				// JSON 간단 파싱
				String action = null;
				String userId = null;
				String aidStr = null;
				String ridStr = null;
				String rcontent = null;

				// POST만 action이 있을 수 있음, PUT은 수정, DELETE는 삭제으로 분기
				if ("POST".equalsIgnoreCase(method)) {
					action = extractValue(json, "action");
				}
				userId = extractValue(json, "user_id");
				aidStr = extractValue(json, "aid");
				ridStr = extractValue(json, "rid");
				rcontent = extractValue(json, "rcontent");

				boolean result = false;

				if ("POST".equalsIgnoreCase(method)) {
					if ("insert".equalsIgnoreCase(action)) {
						if (userId == null || aidStr == null || rcontent == null) {
							exchange.sendResponseHeaders(400, -1);
							return;
						}
						ReplyVO insertVo = new ReplyVO();
						insertVo.setUser_id(userId);
						insertVo.setAid(Integer.parseInt(aidStr));
						insertVo.setRcontent(rcontent);
						result = service.registReply(insertVo) > 0;

					} else if ("update".equalsIgnoreCase(action)) {
						if (ridStr == null || rcontent == null) {
							exchange.sendResponseHeaders(400, -1);
							return;
						}
						ReplyVO updateVo = new ReplyVO();
						updateVo.setRid(Integer.parseInt(ridStr));
						updateVo.setRcontent(rcontent);
						result = service.modifyReply(updateVo) > 0;

					} else if ("delete".equalsIgnoreCase(action)) {
						if (ridStr == null) {
							exchange.sendResponseHeaders(400, -1);
							return;
						}
						result = service.removeReply(Integer.parseInt(ridStr)) > 0;

					} else {
						// 알 수 없는 action
						exchange.sendResponseHeaders(400, -1);
						return;
					}
				} else if ("PUT".equalsIgnoreCase(method)) {
					// PUT => 수정
					if (ridStr == null || rcontent == null) {
						exchange.sendResponseHeaders(400, -1);
						return;
					}
					ReplyVO updateVo = new ReplyVO();
					updateVo.setRid(Integer.parseInt(ridStr));
					updateVo.setRcontent(rcontent);
					result = service.modifyReply(updateVo) > 0;

				} else if ("DELETE".equalsIgnoreCase(method)) {
					// DELETE => 삭제 (rid 필수)
					if (ridStr == null) {
						exchange.sendResponseHeaders(400, -1);
						return;
					}
					result = service.removeReply(Integer.parseInt(ridStr)) > 0;

				}

				String response = "{\"result\": " + (result ? 1 : 0) + "}";
				byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
				exchange.sendResponseHeaders(200, responseBytes.length);
				try (OutputStream os = exchange.getResponseBody()) {
					os.write(responseBytes);
				}
			} else {
				exchange.sendResponseHeaders(405, -1); // Method Not Allowed
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				exchange.sendResponseHeaders(500, -1); // Internal Server Error
			} catch (Exception ignore) {
			}
		}
	}

	// JSON 특수문자 escape 처리
	private String escapeJson(String s) {
		if (s == null)
			return "";
		return s.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
	}

	// 간단 JSON 키-값 추출 함수
	private String extractValue(String json, String key) {
		try {
			String pattern = "\"" + key + "\"\\s*:\\s*\"?([^\",}]*)\"?";
			java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
			java.util.regex.Matcher m = p.matcher(json);
			if (m.find()) {
				return m.group(1);
			}
		} catch (Exception ignored) {
		}
		return null;
	}
}