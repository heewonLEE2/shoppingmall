package BOproject.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BOproject.dao.AiContentDao;
import BOproject.model.AiContentVO;
import BOproject.model.ProductVO;
import BOproject.util.ConnectionUtil;

public class AiContentDaoImpl implements AiContentDao{

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public AiContentDaoImpl() {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
	}
	
	@Override
	public List<AiContentVO> listAiContent() throws SQLException {
		String sql = " select user_id, aicontent, aid, aicontentdate from bo.aicontent ";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<AiContentVO> aiContentList = new ArrayList<AiContentVO>();
		if(rs!=null) {
			while(rs.next()) {
				AiContentVO aiContent = new AiContentVO();
				aiContent.setUser_id(rs.getString("user_id"));
				aiContent.setAicontent(rs.getString("aicontent"));
				aiContent.setAid(rs.getInt("aid"));
				aiContent.setAiContentDate(rs.getTimestamp("aicontentdate"));
			}
		}
		return aiContentList;
	}
	
	@Override
	public AiContentVO getAiContent(String user_id) throws SQLException {
		return null;
	}
	
	@Override
	public int registAiContent(AiContentVO ai) throws SQLException {
		return 0;
	}

	@Override
	public int modifyAiContent(AiContentVO ai) throws SQLException {
		return 0;
	}

	@Override
	public int removeAiContent(String user_id) throws SQLException {
		return 0;
	}
	
}






















