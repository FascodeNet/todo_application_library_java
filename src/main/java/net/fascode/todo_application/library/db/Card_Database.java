package net.fascode.todo_application.library.db;

import org.apache.commons.codec.digest.DigestUtils;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class Card_Database {
    public String Card_name;
    public String mark_data;
    public String Card_id;
    public ArrayList<Card_Database> children;
    public boolean error;
    public Card_Database(){
        Card_name="";
        mark_data="";
        Card_id="";
        children=new ArrayList<Card_Database>();
        error=false;
    }

    /**
     * 派生コンストラクタ
     * @param Card_n カード名
     * @param mark_d カードデータ
     */
    public Card_Database(String Card_n,String mark_d){

        Card_name=Card_n;
        mark_data=mark_d;
        Card_id=DigestUtils.md5Hex(Card_n + Long.toString(System.currentTimeMillis()));
        children=new ArrayList<Card_Database>();
        error=false;
    }

    /**
     * 派生コンストラクタ
     * @param Card_n カード名
     * @param mark_d カードデータ
     * @param cl カードリスト
     */
    public Card_Database(String Card_n,String mark_d,ArrayList<Card_Database> cl){

        Card_name=Card_n;
        mark_data=mark_d;
        Card_id= DigestUtils.md5Hex(Card_n + Long.toString(System.currentTimeMillis()));
        children=cl;
        error=false;
    }
    public Card_Database get_child(String Card_Name){
        for(Card_Database cdkun: children){
            if(cdkun.Card_name.equals(Card_Name)){
                return cdkun;
            }
        }
        Card_Database cd2=new Card_Database();
        cd2.error=true;
        return cd2;
    }
}
