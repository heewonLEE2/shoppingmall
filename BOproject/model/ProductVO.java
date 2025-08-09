package BOproject.model;

import java.io.Serializable;

public class ProductVO implements Serializable {

	private static final long serialVersionUID = 12354423928L;

	private int pid;
	private String pname;
	private int pprice;
	private String pcontent;
	private String pimgUrl;
	private int plikeCount;
	private String cid;

	public ProductVO() {
	}

	public ProductVO(int pid, String pname, int pprice, String pcontent, String pimgUrl, int plikeCount, String cid) {
		this.pid = pid;
		this.pname = pname;
		this.pprice = pprice;
		this.pcontent = pcontent;
		this.pimgUrl = pimgUrl;
		this.plikeCount = plikeCount;
		this.cid = cid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public int getPprice() {
		return pprice;
	}

	public void setPprice(int pprice) {
		this.pprice = pprice;
	}

	public String getPcontent() {
		return pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	public String getPimgUrl() {
		return pimgUrl;
	}

	public void setPimgUrl(String pimgUrl) {
		this.pimgUrl = pimgUrl;
	}

	public int getPlikeCount() {
		return plikeCount;
	}

	public void setPlikeCount(int plikeCount) {
		this.plikeCount = plikeCount;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {
		return "ProductVO [pid=" + pid + ", pname=" + pname + ", pprice=" + pprice + ", pcontent=" + pcontent
				+ ", pimgUrl=" + pimgUrl + ", plikeCount=" + plikeCount + ", cid=" + cid + "]";
	}

}
