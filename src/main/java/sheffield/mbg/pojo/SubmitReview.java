package sheffield.mbg.pojo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SubmitReview {

	public SubmitReview() {
		// TODO Auto-generated constructor stub
	}

	private Long reviewcommodityid;
	@NotBlank
	private String reviewname;
	@Email
	private String reviewemail;
	@NotBlank
	@Size(min = 1, max = 140)
	private String reviewtext;
	@NotBlank
	private String reviewrate;

	public String getReviewname() {
		return reviewname;
	}

	public void setReviewname(String reviewname) {
		this.reviewname = reviewname;
	}

	public String getReviewemail() {
		return reviewemail;
	}

	public void setReviewemail(String reviewemail) {
		this.reviewemail = reviewemail;
	}

	public String getReviewtext() {
		return reviewtext;
	}

	public void setReviewtext(String reviewtext) {
		this.reviewtext = reviewtext;
	}

	public Long getReviewcommodityid() {
		return reviewcommodityid;
	}

	public void setReviewcommodityid(Long reviewcommodityid) {
		this.reviewcommodityid = reviewcommodityid;
	}

	public String getReviewrate() {
		return reviewrate;
	}

	public void setReviewrate(String reviewrate) {
		this.reviewrate = reviewrate;
	}

}
