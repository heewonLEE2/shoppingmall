package BOproject.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BOproject.dao.ReplyDao;
import BOproject.model.ReplyVO;
import BOproject.util.ConnectionUtil;

public class ReplyDaoImpl implements ReplyDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public ReplyDaoImpl() {
	}

	@Override
	public List<ReplyVO> listReply() throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " select rid, user_id, aid, rdate, rcontent " + " from bo.reply ";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<ReplyVO> replyList = new ArrayList<ReplyVO>();
		if (rs != null) {
			while (rs.next()) {
				ReplyVO reply = new ReplyVO();
				reply.setRid(rs.getInt("rid"));
				reply.setUser_id(rs.getString("user_id"));
				reply.setAid(rs.getInt("aid"));
				reply.setRdate(rs.getTimestamp("rdate"));
				reply.setRcontent(rs.getString("rcontent"));
				replyList.add(reply);
			}
		}
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return replyList;
	}

	@Override
	public ReplyVO getReply(int rid) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " select rid, user_id, aid, rdate, rcontent " + " from bo.reply where rid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, rid);
		rs = pstmt.executeQuery();
		ReplyVO reply = new ReplyVO();
		if (rs != null) {
			if (rs.next()) {
				reply.setRid(rs.getInt("rid"));
				reply.setUser_id(rs.getString("user_id"));
				reply.setAid(rs.getInt("aid"));
				reply.setRdate(rs.getTimestamp("rdate"));
				reply.setRcontent(rs.getString("rcontent"));
			}
		}
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return reply;
	}

	@Override
	public int registReply(ReplyVO reply) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " insert into bo.reply values(bo.seq_reply.nextval, ?, ?, systimestamp, ?) ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, reply.getUser_id());
		pstmt.setInt(2, reply.getAid());
		pstmt.setString(3, reply.getRcontent());
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}

	@Override
	public int modifyReply(ReplyVO reply) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " update bo.reply set rcontent=?, rdate=sysdate where rid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, reply.getRcontent());
		pstmt.setInt(2, reply.getRid());
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}

	@Override
	public int removeReply(int rid) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " delete bo.reply where rid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, rid);
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}

	@Override
	public List<ReplyVO> selectArticleReply(int aid) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " select rid, user_id, aid, rdate, rcontent " + " from bo.reply where aid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, aid);
		rs = pstmt.executeQuery();
		List<ReplyVO> replyList = new ArrayList<ReplyVO>();
		if (rs != null) {
			while (rs.next()) {
				ReplyVO reply = new ReplyVO();
				reply.setRid(rs.getInt("rid"));
				reply.setUser_id(rs.getString("user_id"));
				reply.setAid(rs.getInt("aid"));
				reply.setRdate(rs.getTimestamp("rdate"));
				reply.setRcontent(rs.getString("rcontent"));
				replyList.add(reply);
			}
		}
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return replyList;
	}
	
	
} // class







