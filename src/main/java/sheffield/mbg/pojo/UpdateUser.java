package sheffield.mbg.pojo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateUser {

	public UpdateUser() {
		// TODO Auto-generated constructor stub
	}

	@NotBlank
	private String username;
	@Email
	private String useremail;
	@NotBlank
	@Size(min = 6, max = 12)
	private String userpassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

}
