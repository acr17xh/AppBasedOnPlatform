package sheffield.mbg.pojo;

import java.io.Serializable;

public class Review2 implements Serializable{

	public Review2() {
		// TODO Auto-generated constructor stub
	}

	private String reviewdate;

	private Integer rate;

	private String customerreview;

	private String username;

	public String getReviewdate() {
		return reviewdate;
	}

	public void setReviewdate(String reviewdate) {
		this.reviewdate = reviewdate;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getCustomerreview() {
		return customerreview;
	}

	public void setCustomerreview(String customerreview) {
		this.customerreview = customerreview;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
