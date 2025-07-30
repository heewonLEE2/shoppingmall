package BOproject.service;

import java.sql.SQLException;
import java.util.List;

import BOproject.model.UserVO;

public interface UserService {

	public abstract List<UserVO> listUser() throws SQLException;
	
	public abstract UserVO getUser(String user_id) throws SQLException;
	
	public abstract int registUser(UserVO user) throws SQLException;
	
	public abstract int modifyUser(UserVO user) throws SQLException;
	
	public abstract int removeUser(String user_id) throws SQLException;
	
}
