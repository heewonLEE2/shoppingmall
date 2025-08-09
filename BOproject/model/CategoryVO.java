package BOproject.model;

public class CategoryVO {

	private static final long serialVersionUID = 12336363928L;

	private int cid;
	private String ctype;
	private String cname;

	public CategoryVO() {
	}

	public CategoryVO(int cid, String ctype, String cname) {
		this.cid = cid;
		this.ctype = ctype;
		this.cname = cname;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "CategoryVO [cid=" + cid + ", ctype=" + ctype + ", cname=" + cname + "]";
	}
}
