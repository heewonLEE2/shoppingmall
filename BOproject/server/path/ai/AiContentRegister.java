package BOproject.server.path.ai;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.AiContentVO;
import BOproject.model.ProductVO;
import BOproject.server.model.AiContentDTO;
import BOproject.service.AiContentService;
import BOproject.service.ProductService;
import BOproject.service.UserService;
import BOproject.service.impl.AiContentServiceImpl;
import BOproject.service.impl.ProductServiceImpl;
import BOproject.service.impl.UserServiceImpl;
import BOproject.util.CorsHeaderUtil;

public class AiContentRegister implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// CORS 헤더는 모든 응답에 공통으로 설정
		CorsHeaderUtil.getResponseHeaders(exchange);

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
			ProductService productService = new ProductServiceImpl();
			AiContentService aiContentService = new AiContentServiceImpl();

			// 요청 본문 읽기
			InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(isr);
			String requestData = br.readLine();
			AiContentDTO aiContentDTO = gson.fromJson(requestData, AiContentDTO.class);

			try {
				// 유저 등록 정보 확인
				if (userService.getUser(aiContentDTO.getUserId()).getUser_id() == null) {
					response = "회원정보를 확인해주세요";
				} else {
					ProductVO selectProduct = productService.getProduct(Integer.parseInt(aiContentDTO.getProductId()));
					// aicontent 데이터베이스에 저장
					aiContentService
							.registAiContent(new AiContentVO(0, aiContentDTO.getUserId(), aiContentDTO.getAiComment(),
									selectProduct.getPimgUrl(), null, selectProduct.getCid().substring(0, 7)));
					response = "게시물 등록되었습니다.";
				}

				exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
				try (OutputStream os = exchange.getResponseBody()) {
					os.write(response.getBytes(StandardCharsets.UTF_8));
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}

		} // if (POST)
	} // handle
} // class
