package BOproject.server.path.article;

import java.io.IOException;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.service.ArticleService;
import BOproject.service.impl.ArticleServiceImpl;
import BOproject.util.CorsHeaderUtil;

public class ArticleDeleteServer implements HttpHandler{
	
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
		ArticleService articleService = new ArticleServiceImpl();
		
		try {
		    if (articleId != null) {
		        // articleId가 있으면 해당 게시물만 조회
		    	articleService.removeArticle(Integer.parseInt(articleId));
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
