package BOproject.server.path.product;

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

public class ProductLikeCountServer implements HttpHandler{

   
   @Override
   public void handle(HttpExchange exchange) throws IOException {
   
       String path = exchange.getRequestURI().getPath(); // 전체 경로 예: "/articlelist/21"
          String[] pathParts = path.split("/");

          // pathParts[0]는 빈 문자열 ("/"로 시작하기 때문)
          // pathParts[1]는 "articlelist"
          // pathParts[2]는 게시물 ID("21")가 될 것
          
          String productId = null;
          if (pathParts.length > 2) {
             productId = pathParts[2];
          }
          
         Gson gson = new Gson();
         String response = null;
         ProductService productService = new ProductServiceImpl();
         
         System.out.println(productId + "의 요청이 들어왔어요");
         try {
            
            ProductVO selectProduct = productService.getProduct(Integer.parseInt(productId));
            int likeCount = selectProduct.getPlikeCount();
            
            productService.modifyProduct(new ProductVO(
                  selectProduct.getPid(),
                  selectProduct.getPname(),
                  selectProduct.getPprice(),
                  selectProduct.getPcontent(),
                  selectProduct.getPimgUrl(),
                  likeCount +1,
                  selectProduct.getCid()
                  ));
            
         } catch(SQLException sqle) {
            sqle.printStackTrace();
         } 
         
         response = "서버의 요청입니다.";


         // CORS 헤더는 모든 응답에 공통으로 설정
         exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
         exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
         exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
         exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
         exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
         try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
         }

      
   } // handle
} // class
