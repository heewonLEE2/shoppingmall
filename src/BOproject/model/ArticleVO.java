package BOproject.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class ArticleVO {

	private static final long serialVersionUID = 12336632238L;
	
	private int aid;
	private String user_id;
	private String atitle;
	private String acontent;
	private int alikeCount;
	private String cid;
	private Timestamp adate;
	private String aimgfile;
	
	public ArticleVO() {
	}

	public ArticleVO(int aid, String user_id, String atitle, String acontent, int alikeCount, String cid,
			Timestamp adate, String aimgfile) {
		this.aid = aid;
		this.user_id = user_id;
		this.atitle = atitle;
		this.acontent = acontent;
		this.alikeCount = alikeCount;
		this.cid = cid;
		this.adate = adate;
		this.aimgfile = aimgfile;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAtitle() {
		return atitle;
	}

	public void setAtitle(String atitle) {
		this.atitle = atitle;
	}

	public String getAcontent() {
		return acontent;
	}

	public void setAcontent(String acontent) {
		this.acontent = acontent;
	}

	public int getAlikeCount() {
		return alikeCount;
	}

	public void setAlikeCount(int alikeCount) {
		this.alikeCount = alikeCount;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Timestamp getAdate() {
		return adate;
	}

	public void setAdate(Timestamp adate) {
		this.adate = adate;
	}

	public String getAimgfile() {
		return aimgfile;
	}

	public void setAimgfile(String aimgfile) {
		this.aimgfile = aimgfile;
	}

	@Override
	public String toString() {
		return "ArticleVO [aid=" + aid + ", user_id=" + user_id + ", atitle=" + atitle + ", acontent=" + acontent
				+ ", alikeCount=" + alikeCount + ", cid=" + cid + ", adate=" + adate + ", aimgfile=" + aimgfile + "]";
	}

	
}
