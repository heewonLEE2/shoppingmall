package BOproject.Main;

import java.sql.SQLException;

import BOproject.model.UserVO;
import BOproject.service.UserService;
import BOproject.service.impl.UserServiceImpl;

public class Main {

	public static void main(String[] args) {
		
		UserService userService = new UserServiceImpl();	
		
		try {
			
		userService.listUser().stream().forEach(System.out::println);
		//userService.registUser(new UserVO("pratice16@hanmail.net", "33희원", "as33333","서울특별시 강남구", "010-1221-1111"));
		//userService.modifyUser(new UserVO("pratice16@hanmail.net", "수정33희원", "수정as33333","서울특별시 강남구", "010-1221-1111"));
		userService.removeUser("pratice16@hanmail.net");
		
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
