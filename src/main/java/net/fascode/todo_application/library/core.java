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
import java.util.Map;

public class core {
    public Project_Database pdb;
    public Project_DB_JSON pdj;
    private String project_name;
    private Project_List_JSON_OnBranch pljo;
    public core(){
        pdb=new Project_Database();
    }
    public void load_project(String Project_ID,String Branch_Name){
        pdj=new Project_DB_JSON(Project_ID,Branch_Name);
        pdb=pdj.pdb;
    }

    public void create_project(String Project_Name,String Branch_Name){
        project_name=Project_Name;
        String project_id= DigestUtils.md5Hex(Project_Name + Long.toString(System.currentTimeMillis()));
        pljo=new Project_List_JSON_OnBranch(Branch_Name);
        Proj_list plkun=new Proj_list();
        plkun.proj_name=Project_Name;
        plkun.proj_id=project_id;
        pljo.proj_list.add(plkun);
        pljo.Write_List();
        pdb.project_title=project_name;
        pdb.project_id=project_id;
        pdj=new Project_DB_JSON(pdb,Branch_Name);
    }
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
     * @return
     */
    public Card_Database get_card(String card_id){
        return pdj.pdb.cards.get(card_id);
    }

    /**
     *
     * @param cdb 追加するカード
     * @return カードid
     */
    public String Create_Card(Card_Database cdb){

        String card_id=DigestUtils.md5Hex(cdb.Card_name + Long.toString(System.currentTimeMillis()));
        pdj.pdb.cards.put(card_id,cdb);
        return card_id;
    }


    public String to_JSONString(){
        return pdj.To_JsonString();
    }

    /**
     * カードをいじります。
     * @param cdb 変更後
     * @param Card_id カードid
     */
    public void edit_Card(Card_Database cdb,String Card_id){
        pdj.pdb.cards.replace(Card_id,cdb);
    }


    /**
     * カードが消えますを
     * @param Card_id カードid
     */
    public void delete_Card(String Card_id){
        pdj.pdb.cards.remove(Card_id);
    }

}
