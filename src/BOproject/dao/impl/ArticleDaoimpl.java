package BOproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import BOproject.dao.ArticleDao;
import BOproject.model.ArticleVO;
import BOproject.util.ConnectionUtil;

public class ArticleDaoimpl implements ArticleDao{

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public ArticleDaoimpl() {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
	}
	@Override
	public List<ArticleVO> listArticle() throws SQLException {
		return null;
	}

	@Override
	public ArticleVO getArticle(int aid) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int registArticle(ArticleVO article) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int modifyArticle(ArticleVO article) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeArticle(int aid) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

















