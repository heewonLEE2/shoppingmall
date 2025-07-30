package BOproject.service;

import java.sql.SQLException;
import java.util.List;

import BOproject.model.UserVO;

public interface UserService {

	public abstract List<UserVO> listUser() throws SQLException;
	
	
}
