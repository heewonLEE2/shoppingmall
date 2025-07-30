package BOproject.service.impl;

import java.sql.SQLException;
import java.util.List;

import BOproject.dao.UserDao;
import BOproject.dao.impl.UserDaoImpl;
import BOproject.model.UserVO;
import BOproject.service.UserService;

public class UserServiceImpl implements UserService{

	UserDao userDao;
	
	public UserServiceImpl() {
		userDao = new UserDaoImpl();
	}
	
	@Override
	public List<UserVO> listUser() throws SQLException {
		return userDao.listUser();
	}
	
}
