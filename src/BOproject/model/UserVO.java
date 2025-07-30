package BOproject.model;

import java.io.Serializable;

public class UserVO implements Serializable{

	private static final long serialVersionUID = 12354411238L;
	
	private String user_id;
	private String uname;
	private String upass;
	private String uaddress;
	private String uphone;
	
	public UserVO() {
	}

	public UserVO(String user_id, String uname, String upass, String uaddress, String uphone) {
		this.user_id = user_id;
		this.uname = uname;
		this.upass = upass;
		this.uaddress = uaddress;
		this.uphone = uphone;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpass() {
		return upass;
	}

	public void setUpass(String upass) {
		this.upass = upass;
	}

	public String getUaddress() {
		return uaddress;
	}

	public void setUaddress(String uaddress) {
		this.uaddress = uaddress;
	}

	public String getUphone() {
		return uphone;
	}

	public void setUphone(String uphone) {
		this.uphone = uphone;
	}

	@Override
	public String toString() {
		return "UserVO [user_id=" + user_id + ", uname=" + uname + ", upass=" + upass + ", uaddress=" + uaddress
				+ ", uphone=" + uphone + "]";
	}
	
}
