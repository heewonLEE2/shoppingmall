package BOproject.service;

import java.sql.SQLException;
import java.util.List;

import BOproject.model.OrderVO;

public interface OrderService {
	
	public abstract List<OrderVO> listOrder() throws SQLException;
	
	public abstract OrderVO getOrder(int oid) throws SQLException;
	
	public abstract int registOrder(OrderVO order) throws SQLException;
	
	public abstract int modifyOrder(OrderVO order) throws SQLException;
	
	public abstract int removeOrder(int oid) throws SQLException;
}
