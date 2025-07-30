package BOproject.Main;

import java.sql.SQLException;

import BOproject.service.UserService;
import BOproject.service.impl.UserServiceImpl;

public class Main {

	public static void main(String[] args) {
		
		UserService userService = new UserServiceImpl();	
		
		try {
			
		userService.listUser().stream().forEach(System.out::println);
			
		
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
