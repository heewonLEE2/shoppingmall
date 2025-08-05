package BOproject.server.path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Random;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.ProductVO;
import BOproject.server.model.AiRecommendDTO;
import BOproject.service.ProductService;
import BOproject.service.impl.ProductServiceImpl;

public class AiRecommendServer implements HttpHandler {

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
			String response = null;
			ProductService productService = new ProductServiceImpl();
	        Random random = new Random();
			InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
			    sb.append(line);
			}
			
			String requestData = sb.toString();

			// ai 추천 정보를 DTO 객체에 맵핑
			AiRecommendDTO aiDTO = gson.fromJson(requestData, AiRecommendDTO.class);

			try {
				if (aiDTO.getBudget().contains("~")) { // ~ 포함되어있으면 ~ 기준으로 잘라서 배열에 저장
					String[] aiDTObudget = aiDTO.getBudget().split("~");
					int listSize = productService.betweenListProduct(aiDTO.getCid(),Integer.parseInt(aiDTObudget[0]),Integer.parseInt(aiDTObudget[1])).size();
					int randomNumber = random.nextInt(listSize); // 랜덤으로 가져온다.
					response = gson.toJson(productService.betweenListProduct(aiDTO.getCid(),Integer.parseInt(aiDTObudget[0]),Integer.parseInt(aiDTObudget[1])).get(randomNumber)); 
				} else if (Integer.parseInt(aiDTO.getBudget()) <= 30000) {
					int listSize = productService.ListProductLowPrice(aiDTO.getCid()).size();
					int randomNumber = random.nextInt(listSize); // 랜덤으로 가져온다.
					response = gson.toJson(productService.ListProductLowPrice(aiDTO.getCid()).get(randomNumber)); 
				} else {
					int listSize = productService.ListProductHighPrice(aiDTO.getCid()).size();
					int randomNumber = random.nextInt(listSize); // 랜덤으로 가져온다.
					response = gson.toJson(productService.ListProductHighPrice(aiDTO.getCid()).get(randomNumber)); 
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			// ~ 이있으면 제거


			exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response.getBytes(StandardCharsets.UTF_8));
			}
		}
	} // handle\\
} // class
