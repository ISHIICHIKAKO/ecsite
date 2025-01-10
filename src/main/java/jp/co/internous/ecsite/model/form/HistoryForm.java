package jp.co.internous.ecsite.model.form;

import java.io.Serializable;

public class HistoryForm implements Serializable {
	
	//購入履歴をデータベースに問い合わせるために必要な情報はuser_idのみ
	private int userId;
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserId() {
		return userId;
	}

}
