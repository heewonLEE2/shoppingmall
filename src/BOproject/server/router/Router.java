package BOproject.server.router;


import java.net.InetSocketAddress;




import com.sun.net.httpserver.HttpServer;

import BOproject.server.path.AiRecomendServer;
import BOproject.server.path.ArticleDeleteServer;
import BOproject.server.path.ArticleLikeCountServer;
import BOproject.server.path.ArticleListServer2;
import BOproject.server.path.ArticleRegisterServer;
import BOproject.server.path.LoginCheckServer;
import BOproject.server.path.ProductListServer;
import BOproject.server.path.UserRegisterServer;

public class Router {

	public Router() throws Exception{
		serverRun();
	}	
	
	private void serverRun() throws Exception{
		HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
		server.createContext("/logincheck", new LoginCheckServer());
		server.createContext("/register", new UserRegisterServer());
		server.createContext("/productlist", new ProductListServer());
		server.createContext("/articlelist", new ArticleListServer2());
		server.createContext("/articleregist", new ArticleRegisterServer());
		server.createContext("/articledelete",new ArticleDeleteServer());
		server.createContext("/articlelikecount", new ArticleLikeCountServer());
		server.createContext("/airecomend", new AiRecomendServer());
		
		server.setExecutor(null);
		server.start();
		System.out.println("서버 시작됨: http://localhost:8888/");
	};
		
} // class
















