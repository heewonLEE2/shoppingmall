package BOproject.dao;

import java.sql.SQLException;
import java.util.List;

import BOproject.model.ArticleVO;
import BOproject.model.ProductVO;

public interface ArticleDao {
	
	public abstract List<ArticleVO> listArticle() throws SQLException;
	
	public abstract ArticleVO getArticle(int aid) throws SQLException;
	
	public abstract int registArticle(ArticleVO article) throws SQLException;
	
	public abstract int modifyArticle(ArticleVO article) throws SQLException;
	
	public abstract int removeArticle(int aid) throws SQLException;
}
