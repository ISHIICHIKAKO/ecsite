package jp.co.internous.ecsite.model.form;

import java.io.Serializable;
import java.util.List;

public class CartForm implements Serializable {
	
	private int userId;
	
	private List<Cart> cartList;
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserId() {
		return userId;
	}
	public void serCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}
	public List<Cart> getCartList(){
		return cartList;
	}

}
