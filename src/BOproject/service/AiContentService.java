package BOproject.service;

import java.sql.SQLException;

import java.util.List;

import BOproject.model.AiContentVO;

public interface AiContentService {

	
	public abstract List<AiContentVO> listAiContent() throws SQLException;
	
	public abstract AiContentVO getAiContent(String user_id) throws SQLException;
	
	public abstract int registAiContent(AiContentVO ai) throws SQLException;
	
	public abstract int modifyAiContent(AiContentVO ai) throws SQLException;
	
	public abstract int removeAiContent(String user_id) throws SQLException;
	
}
