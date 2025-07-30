package BOproject.model;

import java.io.Serializable;

public class AiVO implements Serializable{

	private static final long serialVersionUID = 12354426268L;
	
	private String user_id;
	private String aicontent;
	private int aid;
	
	public AiVO() {
	}

	public AiVO(String user_id, String aicontent, int aid) {
		this.user_id = user_id;
		this.aicontent = aicontent;
		this.aid = aid;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAicontent() {
		return aicontent;
	}

	public void setAicontent(String aicontent) {
		this.aicontent = aicontent;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	@Override
	public String toString() {
		return "AiVO [user_id=" + user_id + ", aicontent=" + aicontent + ", aid=" + aid + "]";
	}
	
}
