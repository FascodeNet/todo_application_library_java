package net.fascode.todo_application.library.db;

import javax.smartcardio.Card;
import java.util.*;

public class Project_Database {
    private String project_title;
    public String project_id;
    public Date Latest_Date;
    //public ArrayList<Card_Database> cards;
    private Map<String, Card_Database> cards ;
    public Project_Database(){
        project_title="";
        project_id="";
        cards=new HashMap<>();
        Latest_Date=new Date();

    }
    public void set_cards_map(Map<String, Card_Database> cdb){
        cards=cdb;
    }

    /**
     * カードの追加
     * @param card_id カードid
     * @param cdb カードデータベース
     */
    public void put_card(String card_id,Card_Database cdb){
        if(cdb.timestamp.after(Latest_Date)){
            Latest_Date= (Date) cdb.timestamp.clone();
        }
        cards.put(card_id,cdb);
    }

    /**
     * カードのkeysetの取得
     * @return keyset
     */
    public Set<String> get_cards_keyset(){
        return cards.keySet();
    }
    public Map<String,Card_Database> get_cards(){
        return cards;
    }
    /**
     * カードを取得
     * @param key カードid
     * @return カード
     */
    public Card_Database get_Card_db(String key){
        return cards.get(key);
    }

    /**
     * カードの置き換え。
     * @param card_id カードid
     * @param cdb 置き換え後
     */
    public void replace_Card_db(String card_id,Card_Database cdb){
        if(cdb.timestamp.after(Latest_Date)){
            Latest_Date= (Date) cdb.timestamp.clone();
        }
        cards.replace(card_id,cdb);
    }

    /**
     * カードの更新日時をもとに、更新が必要なカードのidを返します。
     * @param dt 相手側の最終更新日時
     * @return 更新が必要なカードid
     */
    public ArrayList<String> Card_update_ids(Date dt){
        ArrayList<String>  id_collection=new ArrayList<>();
        for(String key:cards.keySet()){
            if(cards.get(key).timestamp.after(dt)){
                id_collection.add(key);
            }
        }
        return id_collection;
    }

    /**
     * project_titleを取得
     * @return project_titleの中身
     */
    public String get_project_title(){
        return project_title;
    }

    /**
     * project_titleをセット
     * @param title セットするオブジェクト
     */
    public void set_project_title(String title){
        project_title=title;
        Latest_Date=new Date();
    }
}
