package net.fascode.todo_application.library.json;

import net.fascode.todo_application.library.File.CustomFile;
import net.fascode.todo_application.library.db.Card_Database;
import net.fascode.todo_application.library.db.Project_Database;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.smartcardio.Card;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Project_DB_JSON {
    private JSONObject root_object;
    public Project_Database pdb;
    private String Branch_Name;
    /**
     *
     * @param pdb_ Project_Databaseオブジェクト
     */
    public Project_DB_JSON(Project_Database pdb_,String Branch_name) {
        pdb = pdb_;
        Branch_Name = Branch_name;

    }
    public Project_DB_JSON(String Project_ID,String Branch_Name){
        pdb=new Project_Database();
        this.Branch_Name=Branch_Name;
        File Project_meta_Json= CustomFile.get_File(Project_ID + File.separator + "project_meta.json",Branch_Name);
        try {
            JSONObject root_JO = new JSONObject(readAll(Project_meta_Json));
            pdb.project_id=root_JO.getString("project_id");
            pdb.project_title=root_JO.getString("project_title");
            JSONArray jarray_S=root_JO.getJSONArray("cards");
            jarray_S.forEach(cardname_o->{
                String CardName_Str=(String)cardname_o;
                File Card_kun= CustomFile.get_File(Project_ID + File.separator + "cards" + File.separator + CardName_Str + ".json",Branch_Name);
                try {
                    String Card_JS=readAll(Card_kun);
                    JSONObject card_jo=new JSONObject(Card_JS);
                    JSONArray jakun2=card_jo.getJSONArray("cards");
                    jakun2.forEach(jo2->{
                        Card_Database cdbkun=new Card_Database();
                        JSONObject cokun=(JSONObject)jo2;
                        parse_card_js(cokun,cdbkun);
                        pdb.cards.add(cdbkun);
                    });
                } catch (IOException e) {
                    //nothing do
                }

            });
        }catch (IOException e){
            //nothing
        }
    }
    private void parse_card_js(JSONObject jsonObject,Card_Database cdb){
        cdb.Card_id=jsonObject.getString("card_id");
        cdb.Card_name=jsonObject.getString("name");
        cdb.mark_data=jsonObject.getString("mark_data");
        JSONArray jakun=jsonObject.getJSONArray("children");
        jakun.forEach(jokun->{
            JSONObject jobj=(JSONObject)jokun;
            Card_Database cdkun=new Card_Database();
            parse_card_js(jobj,cdkun);
            cdb.children.add(cdkun);
        });
    }
    private static String readAll(File fkun)throws IOException{
        FileInputStream fileInputStream=new FileInputStream(fkun);
        BufferedInputStream bufferedInputStream=new BufferedInputStream(fileInputStream);
        ByteArrayOutputStream bokun=new ByteArrayOutputStream();
        byte[] buf=new byte[4096];
        int n=bufferedInputStream.read(buf);
        while(n!=-1){
            bokun.write(buf,0,n);
            n=bufferedInputStream.read(buf);
        }
        return bokun.toString();
    }
    /**
     *
     * @param json_data JSONデータ
     */
    public Project_DB_JSON(String json_data){
        pdb=new Project_Database();
        JSONObject jo=new JSONObject(json_data);
        pdb.project_id=jo.getString("project_id");
        pdb.project_title=jo.getString("project_title");
        JSONArray jarray=jo.getJSONArray("cards");
        jarray.forEach(joObj -> {
            JSONObject jokun=(JSONObject)joObj;
            Card_Database cdb=new Card_Database();
            Json_parse_card(jokun,cdb);
            pdb.cards.add(cdb);
        });

    }
    private void Json_parse_card(JSONObject jobject,Card_Database cdb){
        cdb.mark_data=jobject.getString("mark_data");
        cdb.Card_name=jobject.getString("name");
        cdb.Card_id=jobject.getString("card_id");
        JSONArray jarray=jobject.getJSONArray("children");
        jarray.forEach(joObj->{

            JSONObject jokun=(JSONObject)joObj;
            Card_Database cdbkun=new Card_Database();
            Json_parse_card(jokun,cdbkun);
            cdb.children.add(cdbkun);
        });
    }
    public String To_JsonString(){

        root_object=new JSONObject();
        root_object.put("project_title",pdb.project_title);
        root_object.put("project_id",pdb.project_id);
        JSONArray jsonArray=new JSONArray();
        for(Card_Database cdb:pdb.cards){
            recursive_list_to_json(jsonArray,cdb);
        }
        root_object.put("cards",jsonArray);
        return root_object.toString();
    }
    public void  Write_JsonString() throws IOException {
        root_object=new JSONObject();
        root_object.put("project_title",pdb.project_title);
        root_object.put("project_id",pdb.project_id);
        if(!CustomFile.get_File(pdb.project_id,Branch_Name).exists()){
            Files.createDirectories(Paths.get(CustomFile.get_File(pdb.project_id,Branch_Name).toURI()));
        }

        JSONArray jsonArray=new JSONArray();
        if(!CustomFile.get_File(pdb.project_id + File.separator + "cards",Branch_Name).exists()){
            Files.createDirectories(Paths.get(CustomFile.get_File(pdb.project_id+ File.separator + "cards",Branch_Name).toURI()));
        }
        for(Card_Database cdb:pdb.cards){
            cards_to_file(cdb,jsonArray);
        }
        root_object.put("cards",jsonArray);
        File proj_file=CustomFile.get_File(pdb.project_id + File.separator + "project_meta.json",Branch_Name);
        try{
            FileWriter fwkun=new FileWriter(proj_file);
            fwkun.write(root_object.toString());
            fwkun.close();
        }catch (IOException e){

        }
    }
    private void cards_to_file(Card_Database cdb,JSONArray jsonArray){
        jsonArray.put(cdb.Card_id);
        JSONArray cards=new JSONArray();
        recursive_list_to_json(cards,cdb);
        JSONObject jo=new JSONObject();
        jo.put("cards",cards);
        File cardFile= CustomFile.get_File(pdb.project_id + File.separator + "cards" + File.separator + cdb.Card_id + ".json",Branch_Name);
        try {
            FileWriter fwkun = new FileWriter(cardFile);
            fwkun.write(jo.toString());
            fwkun.close();
        }catch (IOException e){
        }
    }
    private void recursive_list_to_json(JSONArray jarray, Card_Database cdb){
        JSONObject card_obj_j=new JSONObject();
        card_obj_j.put("name",cdb.Card_name);
        card_obj_j.put("card_id",cdb.Card_id);
        card_obj_j.put("mark_data",cdb.mark_data);
        JSONArray child_array=new JSONArray();
        for(Card_Database Carddb:cdb.children){
            recursive_list_to_json(child_array,Carddb);
        }
        card_obj_j.put("children",child_array);
        jarray.put(card_obj_j);
    }
}
