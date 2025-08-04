package BOproject.dao;

import java.sql.SQLException;
import java.util.List;

import BOproject.model.ReplyVO;


public interface ReplyDao {
	
	public abstract List<ReplyVO> listReply() throws SQLException;
	
	public abstract ReplyVO getReply(int rid) throws SQLException;
	
	public abstract int registReply(ReplyVO Reply) throws SQLException;
	
	public abstract int modifyReply(ReplyVO Reply) throws SQLException;
	
	public abstract int removeReply(int rid) throws SQLException;
	
	public abstract List<ReplyVO> selectArticleReply(int aid) throws SQLException;

}
