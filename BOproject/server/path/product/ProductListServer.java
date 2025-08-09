package BOproject.server.path.product;

import java.io.IOException;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.service.ProductService;
import BOproject.service.impl.ProductServiceImpl;
import BOproject.util.CorsHeaderUtil;

public class ProductListServer implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
        // CORS 헤더는 모든 응답에 공통으로 설정
        CorsHeaderUtil.getResponseHeaders(exchange);

		Gson gson = new Gson();
		String response = null;
		ProductService productService = new ProductServiceImpl();
		
		try {
			response = gson.toJson(productService.listProduct());
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}

		exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(response.getBytes(StandardCharsets.UTF_8));
		}

	}
}










