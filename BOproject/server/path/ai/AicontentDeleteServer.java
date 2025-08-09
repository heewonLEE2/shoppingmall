package BOproject.server.path.ai;

import java.io.IOException;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.service.AiContentService;
import BOproject.service.impl.AiContentServiceImpl;
import BOproject.util.CorsHeaderUtil;

public class AicontentDeleteServer implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// CORS 헤더는 모든 응답에 공통으로 설정
		CorsHeaderUtil.getResponseHeaders(exchange);

		String path = exchange.getRequestURI().getPath(); // 전체 경로 예: "/articlelist/21"
		String[] pathParts = path.split("/");

		// pathParts[0]는 빈 문자열 ("/"로 시작하기 때문)
		// pathParts[1]는 "articlelist"
		// pathParts[2]는 게시물 ID("21")가 될 것

		String articleId = null;
		if (pathParts.length > 2) {
			articleId = pathParts[2];
		}

		Gson gson = new Gson();
		String response = null;
		AiContentService aiContentService = new AiContentServiceImpl();

		try {
			if (articleId != null) {
				// articleId가 있으면 해당 게시물만 조회
				aiContentService.removeAiContent(Integer.parseInt(articleId));
				response = "삭제 성공";
			} else {
				// 없으면 전체 리스트 조회
				response = "서버에러발생";
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(response.getBytes(StandardCharsets.UTF_8));
		}
	}
} // class
