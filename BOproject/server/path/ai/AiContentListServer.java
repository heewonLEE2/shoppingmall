package BOproject.server.path.ai;

import java.io.IOException;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.AiContentVO;
import BOproject.model.ProductVO;
import BOproject.service.AiContentService;
import BOproject.service.ProductService;
import BOproject.service.UserService;
import BOproject.service.impl.AiContentServiceImpl;
import BOproject.service.impl.ProductServiceImpl;
import BOproject.service.impl.UserServiceImpl;
import BOproject.util.CorsHeaderUtil;

public class AiContentListServer implements HttpHandler {

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
		Map<String, Object> responseMap = new HashMap<>();
		UserService userService = new UserServiceImpl();
		AiContentService aiContentService = new AiContentServiceImpl();
		ProductService productService = new ProductServiceImpl();

		try {
			if (articleId != null) {
				// articleId가 있으면 해당 게시물만 조회

				AiContentVO selectAiContent = aiContentService.getAiContent(Integer.parseInt(articleId));
				List<ProductVO> result = productService.listProduct().stream()
						.filter(e -> e.getPimgUrl().equals(selectAiContent.getAiContentUrl()))
						.collect(Collectors.toList());
				responseMap.put("article", selectAiContent);
				responseMap.put("product", result);

			} else {
				// 없으면 전체 리스트 조회
				responseMap.put("article", aiContentService.listAiContent());
			}
			responseMap.put("users", userService.listUser());
			response = gson.toJson(responseMap);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (NumberFormatException nfe) {
			// articleId가 숫자가 아닐 경우 처리
			responseMap.put("error", "Invalid article ID");
			response = gson.toJson(responseMap);
		}

		exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(response.getBytes(StandardCharsets.UTF_8));
		}

	} // handle
} // class
