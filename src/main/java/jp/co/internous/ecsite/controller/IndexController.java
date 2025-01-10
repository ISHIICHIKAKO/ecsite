package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.ecsite.model.domain.MstGoods;
import jp.co.internous.ecsite.model.domain.MstUser;
import jp.co.internous.ecsite.model.dto.HistoryDto;
import jp.co.internous.ecsite.model.form.CartForm;
import jp.co.internous.ecsite.model.form.HistoryForm;
import jp.co.internous.ecsite.model.form.LoginForm;
import jp.co.internous.ecsite.model.mapper.MstGoodsMapper;
import jp.co.internous.ecsite.model.mapper.MstUserMapper;
import jp.co.internous.ecsite.model.mapper.TblPurchaseMapper;

@Controller

//localhost:8080/ecsite/のURLでアクセスできるように設定
@RequestMapping("/ecsite")
public class IndexController {
	
	//MstGoodsを介してmst_goodsテーブルにアクセスするためのmapper（DAO）
	//@Autowiredで自動的にインスタンス化
	@Autowired
	private MstGoodsMapper goodsMapper;
	
	//MstUserを介してmst_userテーブルにアクセスするためのmapper（DAO）
	//@Autowiredにより自動的にインスタンス化
	@Autowired
	private MstUserMapper userMapper;
	
	
	@Autowired
	private TblPurchaseMapper purchaseMapper;
	
	//WebサービスAPIとして作成するため、JSON形式を扱えるようにGsonをインスタンス化
	private Gson gson = new Gson();
	
	/*トップページ（index.html）に遷移するメソッド
	 * goodsテーブルから取得した商品エンティティの一覧をフロントに渡すModelを追加
	 * return"index";によってindex.htmlに遷移*/
	@GetMapping("/")
	public String index(Model model) {
		List<MstGoods> goods = goodsMapper.findAll();
		model.addAttribute("goods", goods);
		
		return "index";
	}
	
	
	//@ReponseBody　文字そのものが返却される
	@ResponseBody
	@PostMapping("/api/login")
	public String loginApi(@RequestBody LoginForm f) {
		
		//mst_userテーブルからユーザ名とパスワードによって検索し、結果を取得
		MstUser user = userMapper.findByUserNameAndPassword(f);
		
		if(user == null) {
			user = new MstUser();
			user.setFullName("ゲスト");
		}
		
		//MstUser型のuserをJSON形式の文字列に変換し、画面側へ返却
		return gson.toJson(user);
	}
	
	
	
	@ResponseBody
	@PostMapping("/api/purchase")
	public int purchaseApi(@RequestBody CartForm f) {
		f.getCartList().forEach((c) -> {
			int total = c.getPrice() * c.getCount();
			purchaseMapper.insert(f.getUserId(), c.getId(), c.getGoodsName(), c.getCount(), total);
		});
		
		return f.getCartList().size();
	}
	
	
	//購入履歴の表示を担うメソッド（historyApi）
	@ResponseBody
	@PostMapping("/api/history")
	public String historyApi(@RequestBody HistoryForm f) {
		int userId = f.getUserId();
		List<HistoryDto> history = purchaseMapper.findHistory(userId);
		
		return gson.toJson(history);
	}

}
