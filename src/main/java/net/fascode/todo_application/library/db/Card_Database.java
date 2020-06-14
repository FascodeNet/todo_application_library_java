package net.fascode.todo_application.library.db;

import org.apache.commons.codec.digest.DigestUtils;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class Card_Database {
    public String Card_name;
    public String mark_data;
    public String parent_id;
    public boolean isremoved;
    public boolean isopened;
    public boolean error;
    public Card_Database(){
        Card_name="";
        mark_data="";
        parent_id="";
        isremoved=false;
        error=false;
        isopened=false;
    }

    /**
     * 派生コンストラクタ
     * @param Card_n カード名
     * @param mark_d カードデータ
     */
    public Card_Database(String Card_n,String mark_d){

        Card_name=Card_n;
        mark_data=mark_d;
        parent_id="_root";
        isremoved=false;
        error=false;
        isopened=false;
    }

    /**
     * 派生コンストラクタ
     * @param Card_n カード名
     * @param mark_d カードデータ
     * @param parent_id 親id
     */
    public Card_Database(String Card_n,String mark_d,String parent_id){

        Card_name=Card_n;
        mark_data=mark_d;
        this.parent_id=parent_id;
        isremoved=false;
        error=false;
        isopened=false;
    }
    /*public Card_Database get_child(String Card_Name){
        for(Card_Database cdkun: children){
            if(cdkun.Card_name.equals(Card_Name)){
                return cdkun;
            }
        }
        Card_Database cd2=new Card_Database();
        cd2.error=true;
        return cd2;
    }*/
}
