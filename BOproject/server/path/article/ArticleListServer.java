package BOproject.server.path.article;

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
import BOproject.util.CorsHeaderUtil;

public class ArticleListServer implements HttpHandler {

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


		exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(response.getBytes(StandardCharsets.UTF_8));
		}

	} // handle
} // class
