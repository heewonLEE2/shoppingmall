package BOproject.Main;

import java.sql.SQLException;

import BOproject.model.OrderVO;
import BOproject.service.OrderService;
import BOproject.service.impl.OrderServiceImpl;

public class OrderTestMain {

	public static void main(String[] args) {
		
		try {
			OrderService orderService = new OrderServiceImpl();
			
			orderService.listOrder().stream().forEach(System.out::println);
			//System.out.println(orderService.getOrder(2));
			//orderService.registOrder(new OrderVO(0, 1, "gmldnjs1616@hanmail.net", 2,33000,"서울특별시 잠실구", "0601,0702",null));
			//orderService.modifyOrder(new OrderVO(3, 1, "gmldnjs1616@hanmail.net", 4,44000,"수정 서울특별시 잠실구", "0601,0702",null));
			//orderService.removeOrder(2);
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
		
	} // mian
} // class
