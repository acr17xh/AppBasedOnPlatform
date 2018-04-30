package sheffield.mbg.pojo;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable {

	public ShoppingCartItem() {
		// TODO Auto-generated constructor stub
	}

	private String itemid;
	private String itemname;
	private String itemquantity;
	private double itemcost;

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemquantity() {
		return itemquantity;
	}

	public void setItemquantity(String itemquantity) {
		this.itemquantity = itemquantity;
	}

	public double getItemcost() {
		return itemcost;
	}

	public void setItemcost(double itemcost) {
		this.itemcost = itemcost;
	}

}
