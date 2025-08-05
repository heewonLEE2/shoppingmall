package BOproject.service.impl;

import java.sql.SQLException;

import java.util.List;

import BOproject.dao.AiContentDao;
import BOproject.dao.impl.AiContentDaoImpl;
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
	public AiContentVO getAiContent(int aiCon_Id) throws SQLException {
		return aiContentDao.getAiContent(aiCon_Id);
	}
	
	@Override
	public int registAiContent(AiContentVO aiContent) throws SQLException {
		return aiContentDao.registAiContent(aiContent);
	}
	
	@Override
	public int modifyAiContent(AiContentVO aiContent) throws SQLException {
		return aiContentDao.modifyAiContent(aiContent);
	}
	
	@Override
	public int removeAiContent(int aiCon_Id) throws SQLException {
		return aiContentDao.removeAiContent(aiCon_Id);
	}
	
}
