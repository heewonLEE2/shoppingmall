package BOproject.server.model;

import java.io.Serializable;

public class AiRecommendDTO implements Serializable{

	private static final long serialVersionUID = 221333528L;
	
	private String cid;
	private String budget;
	private String age;
	private String weight;
	private String height;
	
	public AiRecommendDTO() {
	}

	public AiRecommendDTO(String cid, String budget, String age, String weight, String height) {
		this.cid = cid;
		this.budget = budget;
		this.age = age;
		this.weight = weight;
		this.height = height;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "AiRecommendDTO [cid=" + cid + ", budget=" + budget + ", age=" + age + ", weight=" + weight + ", height="
				+ height + "]";
	}
	
}
