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

import BOproject.service.ArticleService;
import BOproject.service.UserService;
import BOproject.service.impl.ArticleServiceImpl;
import BOproject.service.impl.UserServiceImpl;

public class ArticleListServer implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// CORS 헤더는 모든 응답에 공통으로 설정
		exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
		exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
		exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

		Gson gson = new Gson();
		String response = null;
		Map<String, Object> responseMap = new HashMap<>();
		ArticleService articleService = new ArticleServiceImpl();
		UserService userService = new UserServiceImpl();
		
		
		try {
			// 응답 형식을 "article" : [listArticle], "users" : [listUser] 형식으로 하기위해
			// users 데이터도 필요하기 때문
			responseMap.put("article", articleService.listArticle());
			responseMap.put("users", userService.listUser());
			response = gson.toJson(responseMap);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(response.getBytes(StandardCharsets.UTF_8));
		}

	} // handle
} // class
