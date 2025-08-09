package BOproject.server.path.user;

import java.io.IOException;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.service.OrderService;
import BOproject.service.impl.OrderServiceImpl;
import BOproject.util.CorsHeaderUtil;

public class OrderDeleteServer implements HttpHandler{
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
			String path = exchange.getRequestURI().getPath(); // 전체 경로 예: "/articlelist/21"
		    String[] pathParts = path.split("/");

			// CORS 헤더는 모든 응답에 공통으로 설정
			CorsHeaderUtil.getResponseHeaders(exchange);
		    
		    String orderId = null;
		    if (pathParts.length > 2) {
		    	orderId = pathParts[2];
		    }
		    
			Gson gson = new Gson();
			String response = null;
			OrderService orderService = new OrderServiceImpl();
			
			try {
				orderService.removeOrder(Integer.parseInt(orderId));
				
			} catch(SQLException sqle) {
				sqle.printStackTrace();
			}
			
			response = "서버에서의 요청";
			

			exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response.getBytes(StandardCharsets.UTF_8));
			}

	} // handle
} // class
