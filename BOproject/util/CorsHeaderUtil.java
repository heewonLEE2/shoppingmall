package BOproject.util;

import com.sun.net.httpserver.HttpExchange;

public class CorsHeaderUtil {

	public CorsHeaderUtil() {
	}

	public static void getResponseHeaders(HttpExchange exchange) {
		// CORS 헤더는 모든 응답에 공통으로 설정
		exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
		exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
		exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
	}

} // class
