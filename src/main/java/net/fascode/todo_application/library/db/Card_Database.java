package net.fascode.todo_application.library.db;

import org.apache.commons.codec.digest.DigestUtils;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.Date;

public class Card_Database {
    public String Card_name;
    public String mark_data;
    public String parent_id;
    public String owner;
    public boolean isremoved;
    public boolean isopened;
    public boolean error;
    public Date timestamp;
    public Card_Database(){
        Card_name="";
        mark_data="";
        parent_id="";
        isremoved=false;
        error=false;
        isopened=false;
        owner="";
        timestamp=new Date();
    }

    /**
     * 複製コンストラクタ
     * @param cdb コピー元
     */
    public Card_Database(Card_Database cdb){
        Card_name=cdb.Card_name;
        mark_data=cdb.mark_data;
        parent_id=cdb.parent_id;
        isremoved=cdb.isremoved;
        timestamp=new Date();
        isopened=cdb.isopened;
        owner=cdb.owner;
    }
    public void change_owner(String owner){
        this.owner=owner;
    }
    /**
     * 派生コンストラクタ
     * @param Card_n カード名
     * @param mark_d カードデータ
     * @param owner 書き込む人
     */
    public Card_Database(String Card_n,String mark_d,String owner){

        Card_name=Card_n;
        mark_data=mark_d;
        parent_id="_root";
        isremoved=false;
        error=false;
        isopened=false;
        timestamp=new Date();
        this.owner=owner;
        new Date();
    }
    /**
     * 派生コンストラクタ
     * @param Card_n カード名
     * @param mark_d カードデータ
     * @param parent_id 親id
     * @param owner 書き込む人
     */
    public Card_Database(String Card_n,String mark_d,String parent_id,String owner){

        Card_name=Card_n;
        mark_data=mark_d;
        this.parent_id=parent_id;
        isremoved=false;
        error=false;
        isopened=false;
        timestamp=new Date();
        this.owner=owner;
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
