package BOproject.Main;

import java.sql.SQLException;

import BOproject.model.ReplyVO;
import BOproject.service.ReplyService;
import BOproject.service.impl.ReplyServiceImpl;

public class ReplyTestMain {

	
	public static void main(String[] args) {
	
		try {
			
			ReplyService replyService = new ReplyServiceImpl();
			
			//replyService.listReply().stream().forEach(System.out::println);
			//replyService.registReply(new ReplyVO(0, "gmldnjs1616@hanmail.net", 23,null,"자바에서 첫 댓글입니다."));
			//System.out.println(replyService.getReply(23));
			//replyService.modifyReply(null);
			replyService.selectArticleReply(24).stream().forEach(System.out::println);
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
		
	} // main
} // class


























