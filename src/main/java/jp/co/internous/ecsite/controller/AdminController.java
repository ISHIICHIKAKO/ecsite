package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.ecsite.model.domain.MstGoods;
import jp.co.internous.ecsite.model.domain.MstUser;
import jp.co.internous.ecsite.model.form.GoodsForm;
import jp.co.internous.ecsite.model.form.LoginForm;
import jp.co.internous.ecsite.model.mapper.MstGoodsMapper;
import jp.co.internous.ecsite.model.mapper.MstUserMapper;

@Controller
@RequestMapping("/ecsite/admin")
public class AdminController {
	
	
	/*作成した2つのMapperをフィールドとして追加*/
	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private MstGoodsMapper goodsMapper;
	
	
	@RequestMapping("/")
	public String index() {
		return "admintop";
	}
	
	
	/*@RequestMappingはpostもgetも許容できる
	 * welcomeメソッド（ログイン時）はブラウザのフォームによって
	 * post送信される場合のみ許容したい
	 * URL直接入力によるget通信ではアクセスできないようにしたい*/
	@PostMapping("/welcome")
	public String welcome(LoginForm form, Model model) {
		
		//MstUserMapperに定義されているメソッド（とそれ付随するSQL文）が実行され、MstUser型の変数userに代入される
		MstUser user = userMapper.findByUserNameAndPassword(form);
		
		//null=ヒットしなかった場合、ブロック内の処理が実行される
		//forward　トップページへ遷移
		if(user == null) {
			model.addAttribute("errMessage","ユーザー名またはパスワードが違います。");
			return "forward:/ecsite/admin/";
		}
		
		if(user.getIsAdmin() == 0) {
			model.addAttribute("errMessage","管理者ではありません。");
			return "forward:/ecsite/admin/";
		}
		
		
		//ここに到達＝ログイン成功・管理者でのログインとなる
		//MstGoodsMapperのfindAllメソッド　商品情報をすべて検索し、HTMLに渡す情報をmodelに登録
		List<MstGoods> goods = goodsMapper.findAll();
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("password", user.getPassword());
		model.addAttribute("goods", goods);
		
		return "welcome";
	}
	
	
	//新規商品の登録機能
	@PostMapping("/goodsMst")
	public String goodsMst(LoginForm f, Model m) {
		m.addAttribute("userName", f.getUserName());
		m.addAttribute("password", f.getPassword());
		
		//以下のgoodsmstは遷移するHTMLファイル名なのですべて小文字
				return "goodsmst";
	}
	
	
	//新規商品情報をデータベースへ登録（addGoodsメソッド）
	@PostMapping("/addGoods")
	public String addGoods(GoodsForm goodsForm, LoginForm loginForm, Model m) {
		m.addAttribute("userName", loginForm.getUserName());
		m.addAttribute("password", loginForm.getPassword());
	
		MstGoods goods = new MstGoods();
		goods.setGoodsName(goodsForm.getGoodsName());
		goods.setPrice(goodsForm.getPrice());
		
		goodsMapper.insert(goods);
		
		return "forward:/ecsite/admin/welcome";
	}
	
	
	@ResponseBody
	@PostMapping("/api/deleteGoods")
	/*例外をキャッチした場合、「処理に失敗した」＝「-1」
	 * 「処理が成功した」場合＝「1」*/
	public String deleteApi(@RequestBody GoodsForm f, Model m) {
		try {
			goodsMapper.deleteById(f.getId());		
		} catch(IllegalArgumentException e) {
			return "-1";
		}
		
		return "1";
	}

}
