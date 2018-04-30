package sheffield.mbg.pojo;

import org.springframework.stereotype.Component;

public class PaginationStatus {

	public PaginationStatus() {
		// TODO Auto-generated constructor stub
	}

	private String FirstPage = "0";
	private String PrevPage = "1";
	private String NextPage = "2";
	private String LastPage = "3";

	public String getFirstPage() {
		return FirstPage;
	}

	public void setFirstPage(String firstPage) {
		FirstPage = firstPage;
	}

	public String getPrevPage() {
		return PrevPage;
	}

	public void setPrevPage(String prevPage) {
		PrevPage = prevPage;
	}

	public String getNextPage() {
		return NextPage;
	}

	public void setNextPage(String nextPage) {
		NextPage = nextPage;
	}

	public String getLastPage() {
		return LastPage;
	}

	public void setLastPage(String lastPage) {
		LastPage = lastPage;
	}

}
