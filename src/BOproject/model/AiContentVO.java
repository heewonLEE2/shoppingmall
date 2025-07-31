package BOproject.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class AiContentVO implements Serializable{

	private static final long serialVersionUID = 12354426268L;
	
	private String user_id;
	private String aicontent;
	private int aid;
	private Timestamp aiContentDate;
	
	public AiContentVO() {
	}

	public AiContentVO(String user_id, String aicontent, int aid, Timestamp aiContentDate) {
		this.user_id = user_id;
		this.aicontent = aicontent;
		this.aid = aid;
		this.aiContentDate = aiContentDate;
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

	public Timestamp getAiContentDate() {
		return aiContentDate;
	}

	public void setAiContentDate(Timestamp aiContentDate) {
		this.aiContentDate = aiContentDate;
	}

	@Override
	public String toString() {
		return "AiContentVO [user_id=" + user_id + ", aicontent=" + aicontent + ", aid=" + aid + ", aiContentDate="
				+ aiContentDate + "]";
	}

}
