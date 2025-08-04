package BOproject.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BOproject.dao.UserDao;
import BOproject.model.UserVO;
import BOproject.util.ConnectionUtil;

public class UserDaoImpl implements UserDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public UserDaoImpl() {
	}

	@Override
	public List<UserVO> listUser() throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " select user_id, uname, upass, uaddress, uphone " + " from bo.user_tb ";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<UserVO> userList = new ArrayList<UserVO>();
		if (rs != null) {
			while (rs.next()) {
				UserVO user = new UserVO();
				user.setUser_id(rs.getString("user_id"));
				user.setUname(rs.getString("uname"));
				user.setUpass(rs.getString("upass"));
				user.setUaddress(rs.getString("uaddress"));
				user.setUphone(rs.getString("uphone"));
				userList.add(user);
			}
		}
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return userList;
	}

	@Override
	public UserVO getUser(String user_id) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " select user_id, uname, upass, uaddress, uphone " + " from bo.user_tb where user_id=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_id);
		rs = pstmt.executeQuery();
		UserVO user = new UserVO();
		if (rs != null) {
			if (rs.next()) {
				user.setUser_id(rs.getString("user_id"));
				user.setUname(rs.getString("uname"));
				user.setUpass(rs.getString("upass"));
				user.setUaddress(rs.getString("uaddress"));
				user.setUphone(rs.getString("uphone"));
			}
		}
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return user;
	}

	@Override
	public int registUser(UserVO user) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " insert into bo.user_tb values(?, ?, ?, ?, ?) ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUser_id());
		pstmt.setString(2, user.getUname());
		pstmt.setString(3, user.getUpass());
		pstmt.setString(4, user.getUaddress());
		pstmt.setString(5, user.getUphone());
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}

	@Override
	public int modifyUser(UserVO user) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " update bo.user_tb set uname=?, upass=?, uaddress=?, uphone=? where user_id=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUname());
		pstmt.setString(2, user.getUpass());
		pstmt.setString(3, user.getUaddress());
		pstmt.setString(4, user.getUphone());
		pstmt.setString(5, user.getUser_id());
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}

	@Override
	public int removeUser(String user_id) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " delete bo.user_tb where user_id=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_id);
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}
}
