package BOproject.Main;

import java.sql.SQLException;

import BOproject.model.ArticleVO;
import BOproject.service.ArticleService;
import BOproject.service.impl.ArticleServiceImpl;

public class ArticleTestMain {

	
	public static void main(String[] args) {
		
		try {
			
			ArticleService articleService = new ArticleServiceImpl();
			
			articleService.listArticle().stream().forEach(System.out::println);
			//System.out.println(articleService.getArticle(3));
			//articleService.registArticle(new ArticleVO(0, "gmldnjs1616@hanmail.net", "두번째 게시물", "두번째 게시물 내용입니다.", 0, null, "101,201,301", null));
			//articleService.modifyArticle(new ArticleVO(2, "gmldnjs1616@hanmail.net","수정 두번째 게시물", "수정 두번째 게시물 내용입니다.", 1, null, "101,201,301", null));
			//articleService.removeArticle(4);
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
	} // main
} // class












