package BOproject.service.impl;

import java.sql.SQLException;
import java.util.List;

import BOproject.model.ArticleVO;
import BOproject.service.ArticleService;

public class ArticleServiceImpl implements ArticleService{

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
