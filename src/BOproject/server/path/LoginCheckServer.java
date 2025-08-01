package BOproject.server.path;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.JsonDataVO;
import BOproject.server.model.LoginDTO;
import BOproject.service.UserService;
import BOproject.service.impl.UserServiceImpl;

public class LoginCheckServer implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// CORS 헤더는 모든 응답에 공통으로 설정
		exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
		exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
		exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

		// CORS Preflight 요청 처리 (Axios는 POST 전에 OPTIONS 요청을 먼저 보냄)
		if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
			exchange.sendResponseHeaders(204, -1); // 204 No Content 응답
			return;
		}

		// POST 요청 처리
		if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
			Gson gson = new Gson();
			String response;
			UserService userService = new UserServiceImpl();
			
			// 요청 본문 읽기
			InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(isr);
			String requestData = br.readLine();
			
			System.out.println(requestData);
			
			LoginDTO loginDTO = gson.fromJson(requestData, LoginDTO.class);

			try {
				// 로그인 로직 수정: 사용자가 존재하면 성공
				if (userService.getUser(loginDTO.getUser_id()).getUser_id() != null) {
					System.out.println(userService.getUser(loginDTO.getUser_id()));
					response = "로그인을 성공하였습니다.";
				} else {
					System.out.println(userService.getUser(loginDTO.getUser_id()));
					response = "로그인을 실패하였습니다.";
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
			exchange.sendResponseHeaders(405, response.getBytes(StandardCharsets.UTF_8).length); // 405 Method Not Allowed
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response.getBytes(StandardCharsets.UTF_8));
			}
		}
	}
}
