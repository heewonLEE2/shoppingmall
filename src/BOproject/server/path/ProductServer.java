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

public class ProductServer implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Gson gson = new Gson();
		String response = null;
		JsonDataVO requestData = null;
		// CORS 정책때문에 사용
		if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
			exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
			exchange.sendResponseHeaders(204, -1); // No Content, 바디 없음
			return; // OPTIONS 요청 처리 종료
		}

	    // POST 요청 처리
	    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
	        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
	        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

	        // 요청 본문 읽기
	        InputStream inputStream = exchange.getRequestBody();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
	        StringBuilder body = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            body.append(line);
	        }
	        
	       requestData = gson.fromJson(body.toString(), JsonDataVO.class);
	     // JSON 변환
			
	    }
	    
	    //int listSize = JDBCMain.getProductList(requestData.getData()).size();
	    
	    //int randomNumber = (int) (Math.random() * listSize);
	    //response = gson.toJson(JDBCMain.getProductList(requestData.getData()).get(randomNumber).getPimgurl());
	    
		// String gptRespone = AiChat.getChatGPTResponse(null, str);

		exchange.sendResponseHeaders(200, response.getBytes().length);
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(response.getBytes(StandardCharsets.UTF_8));
		}
	}
}
