package BOproject.server.path;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.ProductVO;
import BOproject.service.ProductService;
import BOproject.service.impl.ProductServiceImpl;

public class ProductOneServer implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath(); // e.g. "/product/123"
        String[] parts = path.split("/");

        if (parts.length < 3) {
            sendError(exchange, 400, "잘못된 요청 경로입니다.");
            return;
        }

        int pid;
        try {
            pid = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            sendError(exchange, 400, "pid 형식 오류");
            return;
        }

        ProductService productService = new ProductServiceImpl();
        ProductVO product;
        try {
            product = productService.getProduct(pid);
        } catch (SQLException e) {
            sendError(exchange, 500, "DB 오류");
            return;
        }

        Gson gson = new Gson();
        String response = gson.toJson(product);

        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void sendError(HttpExchange exchange, int statusCode, String message) throws IOException {
        String response = "{\"error\":\"" + message + "\"}";
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
