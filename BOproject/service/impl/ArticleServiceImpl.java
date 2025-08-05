package BOproject.service.impl;

import java.sql.SQLException;

import java.util.List;

import BOproject.dao.ArticleDao;
import BOproject.dao.impl.ArticleDaoimpl;
import BOproject.dao.impl.ProductDaoImpl;
import BOproject.model.ArticleVO;
import BOproject.service.ArticleService;

public class ArticleServiceImpl implements ArticleService{

	ArticleDao articleDao;
	
	public ArticleServiceImpl() {
		articleDao = new ArticleDaoimpl();
	}
	
	@Override
	public List<ArticleVO> listArticle() throws SQLException {
		return articleDao.listArticle();
	}
	
	@Override
	public ArticleVO getArticle(int aid) throws SQLException {
		return articleDao.getArticle(aid);
	}
	@Override
	public int registArticle(ArticleVO article) throws SQLException {
		return articleDao.registArticle(article);
	}
	
	@Override
	public int modifyArticle(ArticleVO article) throws SQLException {
		return articleDao.modifyArticle(article);
	}
	@Override
	public int removeArticle(int aid) throws SQLException {
		return articleDao.removeArticle(aid);
	}
	
}
