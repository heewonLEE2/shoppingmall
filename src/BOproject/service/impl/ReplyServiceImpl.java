package BOproject.service.impl;

import java.sql.SQLException;

import java.util.List;

import BOproject.dao.ReplyDao;
import BOproject.dao.impl.ReplyDaoImpl;
import BOproject.model.ReplyVO;
import BOproject.service.ReplyService;

public class ReplyServiceImpl implements ReplyService {
	
	ReplyDao replyDao;
	
	public ReplyServiceImpl() {
		replyDao = new ReplyDaoImpl();
	}
	
	@Override
	public List<ReplyVO> listReply() throws SQLException {
		return replyDao.listReply();
	}
	
	@Override
	public ReplyVO getReply(int rid) throws SQLException {
			return replyDao.getReply(rid);
	}
	
	@Override
	public int registReply(ReplyVO Reply) throws SQLException {
		return replyDao.registReply(Reply);
	}
	
	@Override
	public int modifyReply(ReplyVO Reply) throws SQLException {
		return replyDao.modifyReply(Reply);
	}
	@Override
	public int removeReply(int rid) throws SQLException {
		return replyDao.removeReply(rid);
	}
	
	@Override
	public List<ReplyVO> selectArticleReply(int aid) throws SQLException {
		return replyDao.selectArticleReply(aid);
	}
}
