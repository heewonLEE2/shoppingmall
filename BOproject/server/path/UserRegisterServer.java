package BOproject.server.path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BOproject.model.UserVO;
import BOproject.service.UserService;
import BOproject.service.impl.UserServiceImpl;

import org.mindrot.jbcrypt.BCrypt;

public class UserRegisterServer implements HttpHandler {

   // 서버와 동일한 개인키 PEM 문자열
    private static final String PRIVATE_KEY_PEM = 
            "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQDIWexNe5s0fNZW\n" +
            "mpQ164DELtDW4sRsiAXideDHWCLzGXG1H6H2eHkIZfYoHrqdSC7EWbQT6gVeatXQ\n" +
            "VLX/cFudSzkMnb7+MORHHjq/gsydiPC2uWUMmdufmnjPpbfF4FW+ldNa2C5DfDOY\n" +
            "ST4dYCIWKKFZ2nVPvMx6Y6aq5bi5cfBpLm72TAqGPCeAzKIbN3ROqkovqzqiSleh\n" +
            "b19Zf3ff3D22f63hPAAVC6g0J38D4V+83b9aHK8VySb1gODXTeUkyFtsjLVIGQ55\n" +
            "N3hq6FNDutB3hu8sdR+lxyNA5a/8uk0uGjs5qvUhs5q4XpdFufC66GEaY3HxHszm\n" +
            "yEzITyj9AgMBAAECgf9I7FfOA2QRgjDQgtWJnL7YnD7Jw6YJzbpjuaTpK4c+Sfd1\n" +
            "I333gD39H1Qdh+PUVQ8m0bQ2kfDR6afpZ1TxYxThz8eetbG7PK4Bg4KJRgv41OXC\n" +
            "H6u7vHktXPH+onpBZF91WxNROJl21fK2juuWvwr+dVVL1VjQjEOFAHGpjfPI03Ms\n" +
            "fFiqu158fFiiMIGZ3zQMdVCYQPe7+jp0u+4q4jXQtYb4pbNWZ2RwnAHxbTSx34Yi\n" +
            "foFkz934Tsh7JsqHSSbDMnXPxQSDmsZJ3JkDa5QZ2/CCErrN1XZxnyotmV8WzpVS\n" +
            "IZqBJzO69P2RgIpMwKNDDmqgubjZm1BfZ7iWFYECgYEA/ByjlDsoAXZiZAMcJGGj\n" +
            "Dc/kufqI35QNOmGP+NchGHVwE3oozy6cUHUdmebw2WxJdHorvkO7GCFH+nQ3YgHY\n" +
            "bTmwm5av7n18i2M8tyTUl7Y7/hbPppOARxiKvSlvSCgbrpyaYvmPYz3JNZMdnfdL\n" +
            "V/268XA6Av//3scZgWb9C3ECgYEAy3Dtq3cWIQxN910UohY70usPluUqdiO8BPPS\n" +
            "7UK0H8j4m9O9tOUO0RfS5wZZh/QDPakS3phTsxk4PINMVyLhALuACAS2JfWQhKXF\n" +
            "vXXuUe5siRdq5qm5XII5z5sR6103iqIiSgjpp7JOs7qDdUWGyksM7aTpNjOiWqq9\n" +
            "VKHSOE0CgYB8y8xpXtMRzkEsEqIGnFIzTxqAuTSxv77AjHTRD+SxD8j3yzuSOxdj\n" +
            "eGW7viaw6bp+rs8icrKVYjwYDRkxKYqxnqH+kpvp6TISaYAvhEq8Apg10aLeaRtR\n" +
            "liNvOah9RmVVjO2bWr+Qt4/3Niit/jDMkKvOQehlfcVxyIqmlI1j8QKBgEPxJ/6u\n" +
            "m75XvKYS+PQU1s/e3ur3B9JCpuVEhLnmH//Jf3WTJnUGVodwHGg5T6c+z+JE199t\n" +
            "Ie7bSlOzSciEBn7XH7AN2sMcmrIiJsLaYsgMB0VVg8cbAaOqfiIeWMKw8/6hnS/6\n" +
            "gAYYX35YmNSObrCaleu7uoL2hY8gHHDqRim1AoGARrviIrQS6dQ4NeSYerP+w23e\n" +
            "q32APsJewmdW7Mg0BaeWOsB2piNiPnBnggFP/oBRj9FiB1XJrnk4U/tsdOaG58rR\n" +
            "ONmJF3x6mjjN28TiZOkXrlP4dHHEZt6dRUhm0V9Qqvcng7jlX6UfOklq/qgIBUKU\n" +
            "t6LOeGmOHt9sLilhyqI=\n" +
            "-----END PRIVATE KEY-----";

    // PEM 문자열에서 헤더/푸터 제거 후 PrivateKey 객체 생성
    private PrivateKey getPrivateKeyFromPEM(String pem) throws Exception {
        String privateKeyPEM = pem
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    // RSA 복호화 함수
    private String decryptRSA(String encryptedBase64) throws Exception {
        PrivateKey privateKey = getPrivateKeyFromPEM(PRIVATE_KEY_PEM);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBase64);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }


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

            UserVO userVO = gson.fromJson(requestData, UserVO.class);

            try {
                // bcrypt 부분만 안전하게 처리
                if (userVO.getUpass() != null) {
                    // 암호화된 비밀번호를 복호화해서 평문 얻기
                    String decryptedPassword = decryptRSA(userVO.getUpass());

                    // 복호화 결과 유효성 검사
                    if (decryptedPassword != null && !decryptedPassword.isEmpty()) {
                        // bcrypt 해시 적용
                        String hashed = BCrypt.hashpw(decryptedPassword, BCrypt.gensalt());
                        userVO.setUpass(hashed);
                    } else {
                        throw new IllegalArgumentException("복호화된 비밀번호가 유효하지 않습니다.");
                    }
                }

                if (userService.getUser(userVO.getUser_id()).getUser_id() != null) {
                    response = "중복된 아이디 입니다.";
                } else {
                    if (userService.registUser(userVO) == 0) {
                        response = "false";
                        System.out.println("회원가입 실패");
                    } else {
                        response = "회원가입을 성공하였습니다.";
                        System.out.println("회원가입 성공");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                response = "서버 오류가 발생했습니다.";
                exchange.sendResponseHeaders(500, response.getBytes(StandardCharsets.UTF_8).length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
                return;
            }
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            String response = "잘못된 요청입니다.";
            exchange.sendResponseHeaders(405, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
