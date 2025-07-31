package BOproject.Main;

import java.sql.SQLException;

import BOproject.model.AiContentVO;
import BOproject.service.AiContentService;
import BOproject.service.impl.AiContentServiceImpl;

public class AiTestMain {

	public static void main(String[] args) {
		
		try {
			
			AiContentService aiContentService = new AiContentServiceImpl();
			
			aiContentService.listAiContent().stream().forEach(System.out::println);
			//System.out.println(aiContentService.getAiContent(3));
			//aiContentService.registAiContent(new AiContentVO(0, "gmldnjs1616@hanmail.net", "ai222컨텐츠입니다.", "img_url_222", null));
			//aiContentService.modifyAiContent(new AiContentVO(3, "gmldnjs1616@hanmail.net", "수정 컨텐츠입니다.", "수정_img_url_222", null));
			//aiContentService.removeAiContent(7);
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	} // main
} // class
