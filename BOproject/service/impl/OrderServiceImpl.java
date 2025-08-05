package BOproject.service.impl;

import java.sql.SQLException;

import java.util.List;

import BOproject.dao.OrderDao;
import BOproject.dao.impl.OrderDaoImpl;
import BOproject.model.OrderVO;
import BOproject.service.OrderService;

public class OrderServiceImpl implements OrderService{

	OrderDao orderDao;
	
	public OrderServiceImpl() {
		orderDao = new OrderDaoImpl();
	}
	
	@Override
	public List<OrderVO> listOrder() throws SQLException {
		return orderDao.listOrder();
	}
	
	@Override
	public OrderVO getOrder(int oid) throws SQLException {
		return orderDao.getOrder(oid);
	}
	
	@Override
	public int registOrder(OrderVO order) throws SQLException {
		return orderDao.registOrder(order);
	}
	
	@Override
	public int modifyOrder(OrderVO order) throws SQLException {
		return orderDao.modifyOrder(order);
	}
	
	@Override
	public int removeOrder(int oid) throws SQLException {
		return orderDao.removeOrder(oid);
	}
	
}
