package jp.co.internous.ecsite.model.form;

import java.io.Serializable;

public class GoodsForm implements Serializable {
	
	//ユーザがブラウザで入力した新規商品情報を受け取るためのフィールド
	private int id;
	private String goodsName;
	private int price;
	
	public void setId(int id) {
		this.id= id;
	}
	public int getId() {
		return id;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPrice() {
		return price;
	}

}
