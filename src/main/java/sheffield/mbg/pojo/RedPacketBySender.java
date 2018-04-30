package sheffield.mbg.pojo;

import java.io.Serializable;

public class RedPacketBySender implements Serializable {

	public RedPacketBySender() {
		// TODO Auto-generated constructor stub
	}

	private String sendername;
	private String note;
	private String totalsum;
	private String time;
	private String amount;
	private String times;

	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTotalsum() {
		return totalsum;
	}

	public void setTotalsum(String totalsum) {
		this.totalsum = totalsum;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

}
