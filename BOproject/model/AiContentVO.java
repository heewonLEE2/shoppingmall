package BOproject.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class AiContentVO implements Serializable{

	private static final long serialVersionUID = 12354426268L;
	
	private int aiCon_Id;
	private String aiCon_user_Id;
	private String aiContent;
	private String aiContentUrl;
	private Timestamp aiContentDate;
	private String cid;
	
	public AiContentVO() {
	}

	public AiContentVO(int aiCon_Id, String aiCon_user_Id, String aiContent, String aiContentUrl,
			Timestamp aiContentDate, String cid) {
		this.aiCon_Id = aiCon_Id;
		this.aiCon_user_Id = aiCon_user_Id;
		this.aiContent = aiContent;
		this.aiContentUrl = aiContentUrl;
		this.aiContentDate = aiContentDate;
		this.cid = cid;
	}

	public int getAiCon_Id() {
		return aiCon_Id;
	}

	public void setAiCon_Id(int aiCon_Id) {
		this.aiCon_Id = aiCon_Id;
	}

	public String getAiCon_user_Id() {
		return aiCon_user_Id;
	}

	public void setAiCon_user_Id(String aiCon_user_Id) {
		this.aiCon_user_Id = aiCon_user_Id;
	}

	public String getAiContent() {
		return aiContent;
	}

	public void setAiContent(String aiContent) {
		this.aiContent = aiContent;
	}

	public String getAiContentUrl() {
		return aiContentUrl;
	}

	public void setAiContentUrl(String aiContentUrl) {
		this.aiContentUrl = aiContentUrl;
	}

	public Timestamp getAiContentDate() {
		return aiContentDate;
	}

	public void setAiContentDate(Timestamp aiContentDate) {
		this.aiContentDate = aiContentDate;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {
		return "AiContentVO [aiCon_Id=" + aiCon_Id + ", aiCon_user_Id=" + aiCon_user_Id + ", aiContent=" + aiContent
				+ ", aiContentUrl=" + aiContentUrl + ", aiContentDate=" + aiContentDate + ", cid=" + cid + "]";
	}
	
}
