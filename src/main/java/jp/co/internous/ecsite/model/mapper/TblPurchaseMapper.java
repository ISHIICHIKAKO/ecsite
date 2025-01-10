package jp.co.internous.ecsite.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.internous.ecsite.model.dto.HistoryDto;


/*Javaとデータベースの間で情報をやりとりするために、
 * ORMapperというフレームワークがよく使われる
 * ORMapper=Object Relatioin Mapper
 * Javaのオブジェクトとデータベーステーブルのカラムをマッピングしてくれる
 * 今回はORMapperとしてMybatisを使用しており、MybatisではJavaファイルとXMLファイル両方にSQLを書くことができる
 * 今回は、以下の場合はXMLファイルにSQL文を書いていく
 * ・SQL文が複数行になる場合
 * ・DBテーブルを結合する必要がある場合*/


@Mapper
public interface TblPurchaseMapper {
	
	//購入履歴を検索するためのメソッド（findHistory）
	List<HistoryDto> findHistory(int userId);
	
	/*メソッドの上にSQL文が書かれていない
	 * MybatisではSQLをMapper.javaだけでなく、Mapper.xmlにも書くことができる*/
	int insert(int userId, int goodsId, String goodsName, int itemCount, int total);

}
