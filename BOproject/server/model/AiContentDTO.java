package BOproject.server.model;

import java.io.Serializable;

public class AiContentDTO implements Serializable {

	private static final long serialVersionUID = 221333528L;

	private String userId;
	private String productId;
	private String aiComment;

	public AiContentDTO() {
	}

	public AiContentDTO(String userId, String productId, String aiComment) {
		this.userId = userId;
		this.productId = productId;
		this.aiComment = aiComment;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAiComment() {
		return aiComment;
	}

	public void setAiComment(String aiComment) {
		this.aiComment = aiComment;
	}

	@Override
	public String toString() {
		return "AiContentDTO [userId=" + userId + ", productId=" + productId + ", aiComment=" + aiComment + "]";
	}

}
