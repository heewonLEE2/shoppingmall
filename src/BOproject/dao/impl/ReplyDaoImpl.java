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
		conn = conn = ConnectionUtil.getConnectionUtil().getConnection();
	}
	
	@Override
	public List<ReplyVO> listReply() throws SQLException {
		String sql = " select rid, rcontent, user_id, aid, rdate " + " from bo.reply ";
		
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<ReplyVO> replyList = new ArrayList<ReplyVO>();
		if(rs!=null) {
			while(rs.next()) {
				ReplyVO reply = new ReplyVO();
				reply.setRid(rs.getInt("rid"));
				reply.setRcontent(rs.getString("rcontent"));
				reply.setUser_id(rs.getString("user_id"));
				reply.setAid(rs.getInt("aid"));
				reply.setRdate(rs.getTimestamp("rdate"));
				replyList.add(reply);
			}
		}
		return replyList;
	}
	
	@Override
	public ReplyVO getReply(int rid) throws SQLException {
		String sql = " select rid, rcontent, user_id, aid, rdate " 
				+ " from bo.reply where rid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, rid);
		rs = pstmt.executeQuery();
		ReplyVO reply = new ReplyVO();
		if(rs!=null) {
			if(rs.next()) {
				reply.setRid(rs.getInt("rid"));
				reply.setRcontent(rs.getString("rcontent"));
				reply.setUser_id(rs.getString("user_id"));
				reply.setAid(rs.getInt("aid"));
				reply.setRdate(rs.getTimestamp("rdate"));
			}
		}
		return reply;
		
	}
	
	@Override
	public int registReply(ReplyVO reply) throws SQLException {
		String sql = " insert into bo.reply values(seq_reply.nextval, ?, ?, ?, systimestamp) ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,  reply.getRcontent());
		pstmt.setString(2,  reply.getUser_id());
		pstmt.setInt(3, reply.getAid());
		return pstmt.executeUpdate();
	}
	
	@Override
	public int modifyReply(ReplyVO reply) throws SQLException {
		String sql = " update bo.reply set rcontent=?, rdate=sysdate where rid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,  reply.getRcontent());
		pstmt.setInt(2, reply.getRid());
		return pstmt.executeUpdate();
	}
	
	@Override
	public int removeReply(int rid) throws SQLException {
		String sql = " delete bo.reply where rid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, rid);
		return pstmt.executeUpdate();
	}

}
