package BOproject.server.path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.OrderVO;
import BOproject.service.OrderService;
import BOproject.service.impl.OrderServiceImpl;

public class OrderBuyServer implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // CORS 설정
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1); // Preflight 처리
            return;
        }

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            Gson gson = new Gson();
            String responseMessage;

            try (
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr)
            ) {
                String requestData = br.readLine();
                System.out.println("📥 주문 요청 데이터: " + requestData);

                // JSON → OrderVO
                OrderVO order = gson.fromJson(requestData, OrderVO.class);

                // 주문 등록 처리
                OrderService service = new OrderServiceImpl();
                int result = service.registOrder(order);

                if (result > 0) {
                    responseMessage = "✅ 주문이 정상적으로 등록되었습니다.";
                    exchange.sendResponseHeaders(200, responseMessage.getBytes(StandardCharsets.UTF_8).length);
                } else {
                    responseMessage = "❌ 주문 등록에 실패했습니다.";
                    exchange.sendResponseHeaders(500, responseMessage.getBytes(StandardCharsets.UTF_8).length);
                }

            } catch (Exception e) {
                e.printStackTrace();
                responseMessage = "❌ 서버 내부 오류가 발생했습니다.";
                exchange.sendResponseHeaders(500, responseMessage.getBytes(StandardCharsets.UTF_8).length);
            }

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseMessage.getBytes(StandardCharsets.UTF_8));
            }

        } else {
            String response = "🚫 잘못된 요청 방식입니다.";
            exchange.sendResponseHeaders(405, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
