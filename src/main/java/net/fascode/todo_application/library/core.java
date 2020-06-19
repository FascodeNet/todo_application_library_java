package net.fascode.todo_application.library;

import net.fascode.todo_application.library.db.Card_Database;
import net.fascode.todo_application.library.db.Project_Database;
import net.fascode.todo_application.library.json.Proj_list;
import net.fascode.todo_application.library.json.Project_DB_JSON;
import net.fascode.todo_application.library.json.Project_List_JSON_OnBranch;
import org.apache.commons.codec.digest.DigestUtils;

import javax.smartcardio.Card;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class core {
    /**
     * 知らぬ
     */
    private Project_Database pdb;
    /**
     * JSON管理システム
     */
    private Project_DB_JSON pdj;
    private String project_name;
    private Project_List_JSON_OnBranch pljo;
    public core(){
        pdb=new Project_Database();
    }
    public String get_proj_name(){
        return project_name;
    }
    /**
     * プロジェクトを読み込みます。
     * @param Project_ID プロジェクトid
     * @param Branch_Name ブランチ名
     */
    public void load_project(String Project_ID,String Branch_Name){
        pdj=new Project_DB_JSON(Project_ID,Branch_Name);
        project_name=pdj.pdb.get_project_title();
        pdb=pdj.pdb;
    }

    /**
     * プロジェクトを作成します
     * @param Project_Name プロジェクト名
     * @param Branch_Name ブランチ名
     */
    public void create_project(String Project_Name,String Branch_Name){
        project_name=Project_Name;
        String project_id= DigestUtils.md5Hex(Project_Name + Long.toString(System.currentTimeMillis()));
        pljo=new Project_List_JSON_OnBranch(Branch_Name);
        Proj_list plkun=new Proj_list();
        plkun.proj_name=Project_Name;
        plkun.proj_id=project_id;
        pljo.proj_list.add(plkun);
        pljo.Write_List();
        pdb.set_project_title(project_name);
        pdb.project_id=project_id;
        pdj=new Project_DB_JSON(pdb,Branch_Name);
    }

    /**
     * プロジェクトを読み込みます(名前で)
     * @param Project_Name プロジェクト名
     * @param Branch_Name ブランチ名
     */
    public void load_project_Name(String Project_Name,String Branch_Name){
        project_name = Project_Name;
        pljo=new Project_List_JSON_OnBranch(Branch_Name);
        for(Proj_list pl:pljo.proj_list){
            if(pl.proj_name.equals(Project_Name)){
                load_project(pl.proj_id,Branch_Name);
                return;
            }
        }
    }

    /**
     * プロジェクトを保存します
     */
    public void save_project()  {
        try {
            pdj.Write_JsonString();
        }catch (IOException e){
            //nothing to do
        }
    }

    /**
     * カードを取得します
     * @param card_id カードid
     * @return カード
     */
    public Card_Database get_card(String card_id){
        return pdj.pdb.get_Card_db(card_id);
    }

    /**
     * カードを作成します。
     * @param cdb 追加するカード
     * @return カードid
     */
    public String Create_Card(Card_Database cdb){

        String card_id=DigestUtils.md5Hex(cdb.Card_name + Long.toString(System.currentTimeMillis()));
        pdj.pdb.put_card(card_id,cdb);
        return card_id;
    }

    /**
     * JSONを返します。
     * @return JSON
     */
    public String to_JSONString(){
        return pdj.To_JsonString();
    }

    /**
     * カードをいじります。
     * @param cdb 変更後
     * @param Card_id カードid
     */
    public void edit_Card(Card_Database cdb,String Card_id){

        pdj.pdb.replace_Card_db(Card_id,new Card_Database(cdb)
        );
    }


    /**
     * カードが消えますを
     * @param Card_id カードid
     */
    public void delete_Card(String Card_id){
        Card_Database cdb2=new Card_Database(pdj.pdb.get_Card_db(Card_id));
        cdb2.isremoved=true;
        pdj.pdb.replace_Card_db(Card_id,cdb2);
    }
    public Date get_latesttime(){
        return pdj.pdb.Latest_Date;
    }
    public ArrayList<String> Card_update_ids(Date dt){
        return pdj.pdb.Card_update_ids(dt);
    }

    /**
     * プロジェクトリストを返しますを
     * @param Branch_name ブランチ名
     * @return プロジェクトリスト
     */
    public static ArrayList<Proj_list> Project_List(String Branch_name){

        Project_List_JSON_OnBranch pljo2=new Project_List_JSON_OnBranch(Branch_name);
        return (ArrayList<Proj_list>)pljo2.proj_list.clone();
    }


}
