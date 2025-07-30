package BOproject.dao;

import java.sql.SQLException;
import java.util.List;

import BOproject.model.UserVO;

public interface UserDao {

	public abstract List<UserVO> listUser() throws SQLException;
	
	
}
