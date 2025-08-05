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
import BOproject.service.ReplyService;
import BOproject.service.UserService;
import BOproject.service.impl.ArticleServiceImpl;
import BOproject.service.impl.ReplyServiceImpl;
import BOproject.service.impl.UserServiceImpl;

public class ArticleListServer2 implements HttpHandler {

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
	    
		Gson gson = new Gson();
		String response = null;
		Map<String, Object> responseMap = new HashMap<>();
		ArticleService articleService = new ArticleServiceImpl();
		UserService userService = new UserServiceImpl();
		ReplyService replyService = new ReplyServiceImpl();
		
		try {
		    if (articleId != null) {
		        // articleId가 있으면 해당 게시물만 조회
		        responseMap.put("article", articleService.getArticle(Integer.parseInt(articleId)));
		        responseMap.put("reply", replyService.selectArticleReply(Integer.parseInt(articleId)));
		    } else {
		        // 없으면 전체 리스트 조회
		        responseMap.put("article", articleService.listArticle());
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

		// CORS 헤더는 모든 응답에 공통으로 설정
		exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
		exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
		exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
		exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(response.getBytes(StandardCharsets.UTF_8));
		}

	} // handle
} // class
