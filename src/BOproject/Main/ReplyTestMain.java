package BOproject.Main;

import java.sql.SQLException;

import BOproject.model.ReplyVO;
import BOproject.service.ReplyService;
import BOproject.service.impl.ReplyServiceImpl;

public class ReplyTestMain {

	
	public static void main(String[] args) {
	
		try {
			
			ReplyService replyService = new ReplyServiceImpl();
			
			replyService.listReply().stream().forEach(System.out::println);
			//replyService.registReply(new ReplyVO(0, "첫 댓글입니다.", "gmldnjs1616@hanmail.net", 3,null));
			//System.out.println(replyService.getReply(1));
			//replyService.modifyReply(null);
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
		
	} // main
} // class


























