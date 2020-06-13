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
     * カードを取得します(root)
     * @param card_id カードid
     * @return カード
     */
    public Card_Database get_card(String card_id){
        for(Card_Database cdb:pdj.pdb.cards){
            if(cdb.Card_id.equals(card_id)){
                return cdb;
            }
        }
        Card_Database cdkun=new Card_Database();
        cdkun.error=true;
        return cdkun;
    }

    /**
     * カードを取得します
     * @param card_id カードid
     * @param parent_card 親カード
     * @return
     */
    public Card_Database get_card(String card_id,Card_Database parent_card){

        for(Card_Database cdb:parent_card.children){
            if(cdb.Card_id.equals(card_id)){
                return cdb;
            }
        }

        Card_Database cdkun=new Card_Database();
        cdkun.error=true;
        return cdkun;
    }

    /**
     *
     * @param cdb 追加するカード
     */
    public void Create_Card(Card_Database cdb){

        pdj.pdb.cards.add(cdb);
    }


    /**
     *
     * @param cdb 追加するカード
     * @param parent 親カード
     */
    public void Create_Card(Card_Database cdb,Card_Database parent){
        parent.children.add(cdb);
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
        for(Card_Database cdb2:pdj.pdb.cards){
            if(cdb2.Card_id.equals(Card_id)){
                int index=pdj.pdb.cards.indexOf(cdb2);
                Card_Database cdbdest=new Card_Database();
                cdbdest.Card_id=Card_id;
                cdbdest.children=cdb.children;
                cdbdest.Card_name=cdb.Card_name;
                cdbdest.mark_data=cdb.mark_data;
                pdj.pdb.cards.set(index,cdbdest);
                return;
            }
        }
    }

    /**
     * カードをいじります。
     * @param cdb 変更後
     * @param Card_id カードid
     * @param parent 親カード
     */
    public void edit_Card(Card_Database cdb,String Card_id,Card_Database parent){
        for(Card_Database cdb2:parent.children){
            if(cdb2.Card_id.equals(Card_id)){
                int index=parent.children.indexOf(cdb2);
                Card_Database cdbdest=new Card_Database();
                cdbdest.Card_id=Card_id;
                cdbdest.children=cdb.children;
                cdbdest.Card_name=cdb.Card_name;
                cdbdest.mark_data=cdb.mark_data;
                parent.children.set(index,cdbdest);
                return;
            }
        }
    }

    /**
     * カードが消えますを
     * @param Card_id カードid
     */
    public void delete_Card(String Card_id){
        for(Card_Database cdbkun:pdj.pdb.cards){
            if(cdbkun.Card_id.equals(Card_id)){
                int indexkun=pdj.pdb.cards.indexOf(cdbkun);
                pdj.pdb.cards.remove(indexkun);
            }
        }
    }

    /**
     * カードが消えますを
     * @param Card_id カードid
     * @param parent 親カード
     */
    public void delete_Card(String Card_id,Card_Database parent){
        for(Card_Database cdbkun:parent.children){
            if(cdbkun.Card_id.equals(Card_id)){
                int indexkun=parent.children.indexOf(cdbkun);
                parent.children.remove(indexkun);
            }
        }
    }
}
