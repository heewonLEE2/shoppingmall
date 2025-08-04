package BOproject.server.path;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.ArticleVO;
import BOproject.service.ArticleService;
import BOproject.service.ReplyService;
import BOproject.service.UserService;
import BOproject.service.impl.ArticleServiceImpl;
import BOproject.service.impl.ReplyServiceImpl;
import BOproject.service.impl.UserServiceImpl;

public class AiRecomendServer implements HttpHandler{

	
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
			ArticleService articleService = new ArticleServiceImpl();
			UserService userService = new UserServiceImpl();
			ReplyService replyService = new ReplyServiceImpl();
			
			System.out.println(articleId + "의 요청이 들어왔어요");
			//response = "서버의 요청입니다.";
			try {
				ArticleVO articleVO = articleService.getArticle(Integer.parseInt(articleId));
				int articleLikeCount = articleVO.getAlikeCount();
				System.out.println("해당 게시물의 좋아요 갯수" + articleLikeCount);
				articleService.modifyArticle(
						new ArticleVO(
								articleVO.getAid(),
								articleVO.getUser_id(),
								articleVO.getAtitle(),
								articleVO.getAcontent(),
								articleVO.getAlikeCount()+1,
								articleVO.getCid(),
								null,
								articleVO.getAimgfile()
								));
				response = "좋아요 요청이 처리되었습니다.";
			} catch (SQLException sqle) {
			    sqle.printStackTrace();
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
