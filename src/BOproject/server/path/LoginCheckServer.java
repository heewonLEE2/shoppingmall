package BOproject.server.path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.server.model.LoginDTO;
import BOproject.service.UserService;
import BOproject.service.impl.UserServiceImpl;
import BOproject.model.UserVO;  // UserVO import 추가 (UserVO가 있는 패키지 경로로 수정 필요)

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

            System.out.println("요청 데이터: " + requestData);

            LoginDTO loginDTO = gson.fromJson(requestData, LoginDTO.class);

            try {
                // DB에서 아이디로 사용자 정보 조회
                UserVO user = userService.getUser(loginDTO.getUser_id());

                if (user == null || user.getUser_id() == null) {
                    // 아이디가 DB에 없음
                    response = "아이디가 존재하지 않습니다.";
                } else {
                    // 아이디 존재, 비밀번호 비교
                    if (user.getUpass().equals(loginDTO.getUser_pass())) {
                        response = "로그인을 성공하였습니다.";
                    } else {
                        response = "비밀번호가 틀렸습니다.";
                    }
                }

                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }

            } catch (Exception e) {
                e.printStackTrace();
                response = "서버 오류가 발생했습니다.";
                exchange.sendResponseHeaders(500, response.getBytes(StandardCharsets.UTF_8).length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
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
