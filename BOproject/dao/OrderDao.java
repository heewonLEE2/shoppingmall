package BOproject.dao;

import java.sql.SQLException;
import java.util.List;

import BOproject.model.OrderVO;
import BOproject.model.UserVO;

public interface OrderDao {

	public abstract List<OrderVO> listOrder() throws SQLException;
	
	public abstract OrderVO getOrder(int oid) throws SQLException;
	
	public abstract int registOrder(OrderVO order) throws SQLException;
	
	public abstract int modifyOrder(OrderVO order) throws SQLException;
	
	public abstract int removeOrder(int oid) throws SQLException;
	
	public abstract List<OrderVO> userListOrder(String userId) throws SQLException;
	
}















