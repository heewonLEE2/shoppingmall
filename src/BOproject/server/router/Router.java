package BOproject.server.router;


import java.net.InetSocketAddress;


import com.sun.net.httpserver.HttpServer;

import BOproject.server.path.LoginCheckServer;
import BOproject.server.path.ProductListServer;
import BOproject.server.path.RegisterServer;

public class Router {

	public Router() throws Exception{
		serverRun();
	}	
	
	private void serverRun() throws Exception{
		HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
		server.createContext("/logincheck", new LoginCheckServer());
		server.createContext("/register", new RegisterServer());
		server.createContext("/productlist", new ProductListServer());
		
		server.setExecutor(null);
		server.start();
		System.out.println("서버 시작됨: http://localhost:8888/");
	};
		
} // class
















