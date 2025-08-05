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
	
	@Override
	public UserVO getUser(String user_id) throws SQLException {
		return userDao.getUser(user_id);
	}
	
	@Override
	public int registUser(UserVO user) throws SQLException {
		return userDao.registUser(user);
	}
	
	@Override
	public int modifyUser(UserVO user) throws SQLException {
		return userDao.modifyUser(user);
	}
	
	@Override
	public int removeUser(String user_id) throws SQLException {
		return userDao.removeUser(user_id);
	}
}




















