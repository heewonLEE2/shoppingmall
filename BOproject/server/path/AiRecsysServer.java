package BOproject.server.path;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.ProductVO;
import BOproject.service.ArticleService;
import BOproject.service.ProductService;
import BOproject.service.UserService;
import BOproject.service.impl.ArticleServiceImpl;
import BOproject.service.impl.ProductServiceImpl;
import BOproject.service.impl.UserServiceImpl;
import BOproject.util.AiChatUtil;

public class AiRecsysServer implements HttpHandler{

	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	    String path = exchange.getRequestURI().getPath(); // 전체 경로 예: "/articlelist/21"
	    String[] pathParts = path.split("/");

	    // pathParts[0]는 빈 문자열 ("/"로 시작하기 때문)
	    // pathParts[1]는 "articlelist"
	    // pathParts[2]는 게시물 ID("21")가 될 것
	    
	    String articleId = null;
	    if (pathParts.length > 2) {
	        articleId = pathParts[2];
	    }
		// CORS 헤더는 모든 응답에 공통으로 설정
		exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
		exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
		exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

		Gson gson = new Gson();
		String response = null;
		ProductService productService = new ProductServiceImpl();
		Map<String, Object> responseMap = new HashMap<>();
		
		try {
			ProductVO productVO = productService.getProduct(Integer.parseInt(articleId));
			responseMap.put("product",productVO);
			//responseMap.put("aicomment",AiChatUtil.getChatGPTResponse(productVO.getPimgUrl(), null));
			responseMap.put("aicomment", "ai 추천 코멘트입니다.~");
			response = gson.toJson(responseMap);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(response.getBytes(StandardCharsets.UTF_8));
		}
		
	}
}
