package BOproject.service.impl;

import java.sql.SQLException;

import java.util.List;

import BOproject.dao.AiContentDao;
import BOproject.dao.ProductDao;
import BOproject.dao.impl.AiContentDaoImpl;
import BOproject.dao.impl.ProductDaoImpl;
import BOproject.model.AiContentVO;
import BOproject.service.AiContentService;

public class AiContentServiceImpl implements AiContentService{

	
	AiContentDao aiContentDao;
	
	public AiContentServiceImpl() {
		aiContentDao = new AiContentDaoImpl();
	}
	
	@Override
	public List<AiContentVO> listAiContent() throws SQLException {
		return aiContentDao.listAiContent();
	}
	
	@Override
	public AiContentVO getAiContent(String user_id) throws SQLException {
		return aiContentDao.getAiContent(user_id);
	}
	
	@Override
	public int registAiContent(AiContentVO ai) throws SQLException {
		return aiContentDao.registAiContent(ai);
	}
	
	@Override
	public int modifyAiContent(AiContentVO ai) throws SQLException {
		return aiContentDao.modifyAiContent(ai);
	}
	
	@Override
	public int removeAiContent(String user_id) throws SQLException {
		return aiContentDao.removeAiContent(user_id);
	}
	
}
