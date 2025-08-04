package BOproject.server.path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.ArticleVO;
import BOproject.service.ArticleService;
import BOproject.service.UserService;
import BOproject.service.impl.ArticleServiceImpl;
import BOproject.service.impl.UserServiceImpl;

public class ArticleRegisterServer implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// CORS 헤더는 모든 응답에 공통으로 설정
		exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
		exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
		exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

		if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
			exchange.sendResponseHeaders(204, -1); // 204 No Content 응답
			return;
		}
		
		// POST 요청 처리
		if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
			Gson gson = new Gson();
			String response;
			ArticleService articleService = new ArticleServiceImpl();
			UserService userService =new UserServiceImpl();
			
			// 요청 본문 읽기
			InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(isr);
			String requestData = br.readLine();

			
			//System.out.println(requestData);
			ArticleVO articleVO = gson.fromJson(requestData, ArticleVO.class);
			
			try {
				//System.out.println(userService.getUser(articleVO.getUser_id()));
				if (userService.getUser(articleVO.getUser_id()).getUser_id() != null) {
					articleService.registArticle(articleVO);
					response = "게시판등록을 성공하였습니다.";
					System.out.println("게시판 등록 성공");
				} else {
					response = "등록된 회원이 아닙니다.! 회원가입후 이용해주세요.";
					System.out.println("실패실패실패");
				}

			} catch (Exception e) {
				e.printStackTrace();
				response = "서버 오류가 발생했습니다.";
				exchange.sendResponseHeaders(500, response.getBytes(StandardCharsets.UTF_8).length);
			}
			// 응답 전송 로직을 if 블록 안으로 이동
			exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response.getBytes(StandardCharsets.UTF_8));
			}
		} else {
			// POST가 아닌 다른 요청(GET 등)에 대한 처리
			String response = "잘못된 요청입니다.";
			exchange.sendResponseHeaders(405, response.getBytes(StandardCharsets.UTF_8).length); // 405 Method Not
																									// Allowed
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response.getBytes(StandardCharsets.UTF_8));
			}
		}

	} // handle
} // class
