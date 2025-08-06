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
import BOproject.server.path.article.ArticleListServer2;
import BOproject.server.path.article.ArticleRegisterServer;
import BOproject.server.path.article.ReplyRegisterServer;
import BOproject.server.path.product.ProductLikeCountServer;
import BOproject.server.path.product.ProductListServer;
import BOproject.server.path.product.ProductOneServer;
import BOproject.server.path.product.ProductOrderBuyServer;
import BOproject.server.path.product.ProductOrderDeleteServer;
import BOproject.server.path.user.UserLoginCheckServer;
import BOproject.server.path.user.UserRegisterServer;

public class Router {

	public Router() throws Exception{
		serverRun();
	}	
	
	private void serverRun() throws Exception{
		
		HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
		server.createContext("/logincheck", new UserLoginCheckServer());
		server.createContext("/register", new UserRegisterServer());
		server.createContext("/productlist", new ProductListServer());
		server.createContext("/product", new ProductOneServer());      // 단일 상품
		server.createContext("/productlikecount", new ProductLikeCountServer());
		server.createContext("/articlelist", new ArticleListServer2());
		server.createContext("/articleregist", new ArticleRegisterServer());
		server.createContext("/articledelete",new ArticleDeleteServer());
		server.createContext("/articlelikecount", new ArticleLikeCountServer());
		server.createContext("/airecommend", new AiRecommendServer());
		server.createContext("/airecsys", new AiRecsysServer());
		server.createContext("/aicontentregist", new AiContentRegister());
		server.createContext("/aicontentlist", new AiContentListServer());
		server.createContext("/aicontentdelete", new AicontentDeleteServer());
		server.createContext("/replylist", new ReplyRegisterServer());
		server.createContext("/orderbuy", new ProductOrderBuyServer());
		server.createContext("/orderdelete", new ProductOrderDeleteServer());

		
		server.setExecutor(null);
		server.start();
		System.out.println("서버 시작됨: http://localhost:8888/");
	};
		
} // class










