package BOproject.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BOproject.dao.ArticleDao;
import BOproject.model.ArticleVO;
import BOproject.model.ProductVO;
import BOproject.util.ConnectionUtil;

public class ArticleDaoimpl implements ArticleDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public ArticleDaoimpl() {
	}

	@Override
	public List<ArticleVO> listArticle() throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " SELECT aid, user_id, atitle, acontent, alikecount, aimgfile, cid, adate FROM bo.ARTICLE ";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<ArticleVO> articleList = new ArrayList<ArticleVO>();
		if (rs != null) {
			while (rs.next()) {
				ArticleVO article = new ArticleVO();
				article.setAid(rs.getInt("aid"));
				article.setUser_id(rs.getString("user_id"));
				article.setAtitle(rs.getString("atitle"));
				article.setAcontent(rs.getString("acontent"));
				article.setAlikeCount(rs.getInt("alikecount"));
				article.setAimgFile(rs.getBytes("aimgfile"));
				article.setCid(rs.getString("cid"));
				article.setAdate(rs.getTimestamp("adate"));
				articleList.add(article);
			}
		}
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return articleList;
	}

	@Override
	public ArticleVO getArticle(int aid) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " SELECT aid, user_id, atitle, acontent, alikecount, aimgfile, cid, adate FROM bo.ARTICLE where aid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, aid);
		rs = pstmt.executeQuery();
		ArticleVO article = new ArticleVO();
		if (rs != null) {
			if (rs.next()) {
				article.setAid(rs.getInt("aid"));
				article.setUser_id(rs.getString("user_id"));
				article.setAtitle(rs.getString("atitle"));
				article.setAcontent(rs.getString("acontent"));
				article.setAlikeCount(rs.getInt("alikecount"));
				article.setAimgFile(rs.getBytes("aimgfile"));
				article.setCid(rs.getString("cid"));
				article.setAdate(rs.getTimestamp("adate"));
			}
		}
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return article;
	}

	@Override
	public int registArticle(ArticleVO article) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " insert into bo.article values(seq_article.nextval, ?, ?, ?, 0, Empty_blob(), ?, sysdate) ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, article.getUser_id());
		pstmt.setString(2, article.getAtitle());
		pstmt.setString(3, article.getAcontent());
		pstmt.setString(4, article.getCid());
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}

	@Override
	public int modifyArticle(ArticleVO article) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " update bo.article SET atitle=?, acontent=?, alikecount=?, adate=sysdate where aid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, article.getAtitle());
		pstmt.setString(2, article.getAcontent());
		pstmt.setInt(3, article.getAlikeCount());
		pstmt.setInt(4, article.getAid());
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}

	@Override
	public int removeArticle(int aid) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " delete bo.article where aid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, aid);
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}
}
