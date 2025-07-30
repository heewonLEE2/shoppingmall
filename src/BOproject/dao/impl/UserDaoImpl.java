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

public class UserDaoImpl implements UserDao{
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDaoImpl() {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
	}
	
	@Override
	public List<UserVO> listUser() throws SQLException {
		String sql = " select user_id, uname, upass, uaddress, uphone "
				+ " from user_tb ";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<UserVO> userList = new ArrayList<UserVO>();
		if(rs!=null) {
			while(rs.next()) {
				UserVO user = new UserVO();
				user.setUser_id(rs.getString("user_id"));
				user.setUname(rs.getString("uname"));
				user.setUpass(rs.getString("upass"));
				user.setUaddress(rs.getString("uaddress"));
				user.setUphone(rs.getString("uphone"));
				userList.add(user);
			}
		}
		return userList;
	}
	
}
















