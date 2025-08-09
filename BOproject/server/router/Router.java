package BOproject.server.router;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import BOproject.server.path.ai.AiContentListServer;
import BOproject.server.path.ai.AiContentRegister;
import BOproject.server.path.ai.AiRecommendServer;
import BOproject.server.path.ai.AiRecsysServer;
import BOproject.server.path.ai.AicontentDeleteServer;
import BOproject.server.path.article.ArticleDeleteServer;
import BOproject.server.path.article.ArticleLikeCountServer;
import BOproject.server.path.article.ArticleListServer;
import BOproject.server.path.article.ArticleRegisterServer;
import BOproject.server.path.article.ReplyRegisterServer;
import BOproject.server.path.product.ProductLikeCountServer;
import BOproject.server.path.product.ProductListServer;
import BOproject.server.path.product.ProductOneServer;
import BOproject.server.path.product.ProductOrderBuyServer;
import BOproject.server.path.user.OrderListServer;
import BOproject.server.path.user.OrderDeleteServer;
import BOproject.server.path.user.UserLoginCheckServer;
import BOproject.server.path.user.UserRegisterServer;

public class Router {

	public Router() throws Exception{
		serverRun();
	}	
	
	private void serverRun() throws Exception{
		
		HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
		server.createContext("/logincheck", new UserLoginCheckServer()); // 유저 정보 확인 (로그인) 요청 처리
		server.createContext("/register", new UserRegisterServer()); // 유저 회원가입 요청 처리
		server.createContext("/productlist", new ProductListServer()); // 상품 리스트 요청 처리
		server.createContext("/product", new ProductOneServer()); // 단일 상품 상세 정보 요청 처리
		server.createContext("/productlikecount", new ProductLikeCountServer()); // 상품 좋아요 증가 요청 처리
		server.createContext("/articlelist", new ArticleListServer()); // 게시물 리스트 요청 처리
		server.createContext("/articleregist", new ArticleRegisterServer()); // 게시물 등록 요청 처리
		server.createContext("/articledelete",new ArticleDeleteServer()); // 게시물 삭제 요청 처리
		server.createContext("/articlelikecount", new ArticleLikeCountServer()); // 게시물 좋아요 증가 요청 처리
		server.createContext("/airecommend", new AiRecommendServer()); // ai 추천상품 요청 처리
		server.createContext("/airecsys", new AiRecsysServer()); // ai 추천한 상품 정보 반환 요청 처리
		server.createContext("/aicontentregist", new AiContentRegister()); // ai 게시물 등록 요청 처리
		server.createContext("/aicontentlist", new AiContentListServer()); // ai 게시물 리스트 요청 처리
		server.createContext("/aicontentdelete", new AicontentDeleteServer()); // ai 게시물 삭제 요청 처리
		server.createContext("/replylist", new ReplyRegisterServer()); // 댓글 리스트 요청 처리
		server.createContext("/orderbuy", new ProductOrderBuyServer()); // 상품 주문 정보 요청 처리
		server.createContext("/orderdelete", new OrderDeleteServer()); // 상품 주문 정보 삭제 요청 처리
		server.createContext("/orderlist", new OrderListServer()); // 주문 배송 현황 요청 처리
		
		server.setExecutor(null);
		server.start();
		System.out.println("서버 시작됨: http://localhost:8888/");
	};
		
} // class










