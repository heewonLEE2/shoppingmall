package BOproject.dao;

import java.sql.SQLException;

import java.util.List;

import BOproject.model.AiContentVO;

public interface AiContentDao {

	public abstract List<AiContentVO> listAiContent() throws SQLException;
	
	public abstract AiContentVO getAiContent(int aiCon_Id) throws SQLException;
	
	public abstract int registAiContent(AiContentVO aiContent) throws SQLException;
	
	public abstract int modifyAiContent(AiContentVO aiContent) throws SQLException;
	
	public abstract int removeAiContent(int aiCon_Id) throws SQLException;
}
