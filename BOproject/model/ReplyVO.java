package BOproject.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReplyVO implements Serializable{

	private static final long serialVersionUID = 124588484238L;
	
	private int rid;
	private String user_id;
	private int aid;
	private Timestamp rdate;
	private String rcontent;
	
	public ReplyVO() {
	}

	public ReplyVO(int rid, String user_id, int aid, Timestamp rdate, String rcontent) {
		this.rid = rid;
		this.user_id = user_id;
		this.aid = aid;
		this.rdate = rdate;
		this.rcontent = rcontent;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getRcontent() {
		return rcontent;
	}

	public void setRcontent(String rcontent) {
		this.rcontent = rcontent;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public Timestamp getRdate() {
		return rdate;
	}

	public void setRdate(Timestamp rdate) {
		this.rdate = rdate;
	}

	@Override
	public String toString() {
		return "ReplyVO [rid=" + rid + ", rcontent=" + rcontent + ", user_id=" + user_id + ", aid=" + aid + ", rdate="
				+ rdate + "]";
	}

	
	
}
