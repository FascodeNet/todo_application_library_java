package net.fascode.todo_application.library.json;

import net.fascode.todo_application.library.File.CustomFile;
import net.fascode.todo_application.library.db.Card_Database;
import net.fascode.todo_application.library.db.Project_Database;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.smartcardio.Card;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println(pdb_.project_id);
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
            JSONObject cardskun=root_JO.getJSONObject("cards");
            for(String key:cardskun.keySet()){
                JSONObject jo2=cardskun.getJSONObject(key);
                Card_Database cdbkun=new Card_Database();
                cdbkun.parent_id=jo2.getString("parent");
                cdbkun.Card_name=jo2.getString("name");
                cdbkun.mark_data=jo2.getString("mark_data");
                pdb.cards.put(key,cdbkun);
            }
        }catch (IOException e){
            //nothing
        }
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
        return bokun.toString("UTF-8");
    }
    private static void write_str(File wrf,String outstr)  {
        FileOutputStream fos=null;
        BufferedOutputStream bos=null;
        try {
            fos = new FileOutputStream(wrf);
            bos = new BufferedOutputStream(fos);
            bos.write(outstr.getBytes(StandardCharsets.UTF_8));
        }catch (IOException e){
            //nothing todo;
        }finally {
            try {
                bos.close();
            }catch (IOException e){
                //nothing
            }
            try {
                fos.close();
            }catch (IOException e){
                //nothing
            }
        }

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
        JSONObject cardskun=jo.getJSONObject("cards");
        for(String key:cardskun.keySet()){
            JSONObject jo2=cardskun.getJSONObject(key);
            Card_Database cdbkun=new Card_Database();
            cdbkun.parent_id=jo2.getString("parent");
            cdbkun.Card_name=jo2.getString("name");
            cdbkun.mark_data=jo2.getString("mark_data");
            pdb.cards.put(key,cdbkun);
        }

    }
    public String To_JsonString(){

        root_object=new JSONObject();
        root_object.put("project_title",pdb.project_title);
        root_object.put("project_id",pdb.project_id);
        Card_Database cdkun;
        Map<String,JSONObject> jo_map=new HashMap<>();
        for(String key:pdb.cards.keySet()){
            JSONObject jokun=new JSONObject();
            cdkun=pdb.cards.get(key);
            jokun.put("name",cdkun.Card_name);
            jokun.put("mark_data",cdkun.mark_data);
            jokun.put("parent",cdkun.parent_id);
            jo_map.put(key,jokun);
        }
        root_object.put("cards",jo_map);
        return root_object.toString();
    }
    public void  Write_JsonString() throws IOException {
        root_object=new JSONObject();
        root_object.put("project_title",pdb.project_title);
        root_object.put("project_id",pdb.project_id);
        if(!CustomFile.get_File(pdb.project_id,Branch_Name).exists()){
            Files.createDirectories(Paths.get(CustomFile.get_File(pdb.project_id,Branch_Name).toURI()));
        }
        /*
        for(Card_Database cdb:pdb.cards){
            cards_to_file(cdb,jsonArray);
        }
        root_object.put("cards",jsonArray);*/
        Map<String,JSONObject> jomap=new HashMap<>();
        for(String key:pdb.cards.keySet()){
            JSONObject joude=new JSONObject();
            Card_Database cdb2f=pdb.cards.get(key);
            joude.put("name",cdb2f.Card_name);
            joude.put("parent",cdb2f.parent_id);
            joude.put("mark_data",cdb2f.mark_data);
            jomap.put(key,joude);
        }
        root_object.put("cards",jomap);
        File proj_file=CustomFile.get_File(pdb.project_id + File.separator + "project_meta.json",Branch_Name);
        if(!proj_file.getParentFile().exists()){
            Files.createDirectories(proj_file.getParentFile().toPath());
        }
        write_str(proj_file,root_object.toString());
    }
}
