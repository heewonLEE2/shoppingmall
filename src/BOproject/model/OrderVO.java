package BOproject.model;

import java.io.Serializable;

public class OrderVO implements Serializable{

	private static final long serialVersionUID = 1232533528L;
	
	private int oid;
	private int pid;
	private String user_id;
	private int oamount;
	private int ototal;
	private String oaddress;
	private String cid;
	
	public OrderVO() {
	}

	public OrderVO(int oid, int pid, String user_id, int oamount, int ototal, String oaddress, String cid) {
		this.oid = oid;
		this.pid = pid;
		this.user_id = user_id;
		this.oamount = oamount;
		this.ototal = ototal;
		this.oaddress = oaddress;
		this.cid = cid;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getOamount() {
		return oamount;
	}

	public void setOamount(int oamount) {
		this.oamount = oamount;
	}

	public int getOtotal() {
		return ototal;
	}

	public void setOtotal(int ototal) {
		this.ototal = ototal;
	}

	public String getOaddress() {
		return oaddress;
	}

	public void setOaddress(String oaddress) {
		this.oaddress = oaddress;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {
		return "OrderVO [oid=" + oid + ", pid=" + pid + ", user_id=" + user_id + ", oamount=" + oamount + ", ototal="
				+ ototal + ", oaddress=" + oaddress + ", cid=" + cid + "]";
	}
	
}
