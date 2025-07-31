package BOproject.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BOproject.dao.OrderDao;
import BOproject.model.OrderVO;
import BOproject.model.UserVO;
import BOproject.util.ConnectionUtil;

public class OrderDaoImpl implements OrderDao{

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public OrderDaoImpl() {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
	}
	
	@Override
	public List<OrderVO> listOrder() throws SQLException {
		String sql = " select oid, pid, user_id, oamount, ototal, oaddress, cid, odate "
				+ " from bo.ORDER_TB ";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<OrderVO> orderList = new ArrayList<OrderVO>();
		if(rs!=null) {
			while(rs.next()) {
				OrderVO order = new OrderVO();
				order.setOid(rs.getInt("oid"));
				order.setPid(rs.getInt("pid"));
				order.setUser_id(rs.getString("user_id"));
				order.setOamount(rs.getInt("oamount"));
				order.setOtotal(rs.getInt("ototal"));
				order.setOaddress(rs.getString("oaddress"));
				order.setCid(rs.getString("cid"));
				order.setOdate(rs.getTimestamp("odate"));
				orderList.add(order);
			}
		}
		return orderList;
	}
	
	@Override
	public OrderVO getOrder(int oid) throws SQLException {
		String sql = " select oid, pid, user_id, oamount, ototal, oaddress, cid, odate "
				+ " from bo.ORDER_TB where oid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, oid);
		rs = pstmt.executeQuery();
		OrderVO order = new OrderVO();
		if(rs!=null) {
			if(rs.next()) {
				order.setOid(rs.getInt("oid"));
				order.setPid(rs.getInt("pid"));
				order.setUser_id(rs.getString("user_id"));
				order.setOamount(rs.getInt("oamount"));
				order.setOtotal(rs.getInt("ototal"));
				order.setOaddress(rs.getString("oaddress"));
				order.setCid(rs.getString("cid"));
				order.setOdate(rs.getTimestamp("odate"));
			}
		}
		return order;
	}
	
	@Override
	public int registOrder(OrderVO order) throws SQLException {
		String sql = " insert into bo.ORDER_TB values(seq_order_tb.nextval, ?, ?, ?, ?, ?, ?, systimestamp) ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, order.getPid());
		pstmt.setString(2, order.getUser_id());
		pstmt.setInt(3, order.getOamount());
		pstmt.setInt(4, order.getOtotal());
		pstmt.setString(5, order.getOaddress());
		pstmt.setString(6, order.getCid());
		return pstmt.executeUpdate();
	}
	
	@Override
	public int modifyOrder(OrderVO order) throws SQLException {
		String sql = " update bo.ORDER_TB set oamount=?, ototal=?, oaddress=?, cid=?, odate=sysdate where oid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, order.getOamount());
		pstmt.setInt(2, order.getOtotal());
		pstmt.setString(3, order.getOaddress());
		pstmt.setString(4, order.getCid());
		pstmt.setInt(5, order.getOid());
		return pstmt.executeUpdate();
	}

	@Override
	public int removeOrder(int oid) throws SQLException {
		String sql = " delete bo.ORDER_TB where oid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, oid);
		return pstmt.executeUpdate();
	}
	
}














