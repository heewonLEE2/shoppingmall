package BOproject.server.path.user;

import java.io.IOException;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.service.OrderService;
import BOproject.service.ProductService;
import BOproject.service.impl.OrderServiceImpl;
import BOproject.service.impl.ProductServiceImpl;
import BOproject.util.CorsHeaderUtil;

public class OrderListServer implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String path = exchange.getRequestURI().getPath(); // 전체 경로 예: "/articlelist/21"
		String[] pathParts = path.split("/");

		// CORS 헤더는 모든 응답에 공통으로 설정
		CorsHeaderUtil.getResponseHeaders(exchange);

		String articleId = null;
		if (pathParts.length > 2) {
			articleId = pathParts[2];
		}


		Gson gson = new Gson();
		String response = null;
		Map<String, Object> responseMap = new HashMap<>();
		OrderService orderService = new OrderServiceImpl();
		ProductService productService = new ProductServiceImpl();
		
		try {
			responseMap.put("order", orderService.userListOrder(articleId));
			responseMap.put("product",productService.listProduct());
			
			response = gson.toJson(responseMap);
			
			exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response.getBytes(StandardCharsets.UTF_8));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
