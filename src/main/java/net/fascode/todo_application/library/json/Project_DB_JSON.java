package net.fascode.todo_application.library.json;

import net.fascode.todo_application.library.Date.JSON_Date;
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
import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Project_DB_JSON {
    private JSONObject root_object;
    public Project_Database pdb;
    private String Branch_Name;
    /**
     *
     * @param pdb_ Project_Databaseオブジェクト
     * @param Branch_name ブランチ名
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
            pdb.set_project_title(root_JO.getString("project_title"));
            pdb.Latest_Date=JSON_Date.String_to_Date(root_JO.getString("latest_date"));
            /*JSONObject cardskun=root_JO.getJSONObject("cards");
            for(String key:cardskun.keySet()){
                JSONObject jo2=cardskun.getJSONObject(key);
                Card_Database cdbkun=new Card_Database();
                cdbkun.parent_id=jo2.getString("parent");
                cdbkun.Card_name=jo2.getString("name");
                cdbkun.mark_data=jo2.getString("mark_data");
                pdb.cards.put(key,cdbkun);
            }*/
            JSONArray cardsfile=root_JO.getJSONArray("cards_file");

            cardsfile.forEach(strkun->{
                File fkunniki=CustomFile.get_File(Project_ID + File.separator + "cards" + File.separator + strkun + ".json",Branch_Name);
                try{
                    String jsonniki=readAll(fkunniki);
                    JSONObject jo22=new JSONObject(jsonniki);
                    JSONObject jo_cards=jo22.getJSONObject("cards");
                    Map<String,Card_Database> cdb4_map=new HashMap<>();
                    for(String jofv_key:jo_cards.keySet()){
                        JSONObject jofdf=jo_cards.getJSONObject(jofv_key);
                        Card_Database cdbniki=new Card_Database();
                        cdbniki.isremoved=jofdf.getBoolean("isremoved");
                        cdbniki.mark_data=jofdf.getString("mark_data");
                        cdbniki.Card_name=jofdf.getString("name");
                        cdbniki.parent_id=jofdf.getString("parent");
                        cdbniki.timestamp= JSON_Date.String_to_Date(jofdf.getString("timestamp"));
                        cdbniki.owner=jofdf.getString("owner");
                        cdb4_map.put(jofv_key,cdbniki);
                    }
                    pdb.set_cards_map(new HashMap<>(cdb4_map));
                }catch (IOException | ParseException e){
                    //nothing
                    System.err.println(e.toString());
                }
            });
        }catch (IOException | ParseException e){
            //nothing
            System.err.println(e.toString());
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
        pdb.set_project_title(jo.getString("project_title"));
        try {
            pdb.Latest_Date=JSON_Date.String_to_Date(jo.getString("latest_date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject cardskun=jo.getJSONObject("cards");
        for(String key:cardskun.keySet()){
            JSONObject jo2=cardskun.getJSONObject(key);
            Card_Database cdbkun=new Card_Database();
            cdbkun.parent_id=jo2.getString("parent");
            cdbkun.Card_name=jo2.getString("name");
            cdbkun.mark_data=jo2.getString("mark_data");
            cdbkun.owner=jo2.getString("owner");
            try {
                cdbkun.timestamp=JSON_Date.String_to_Date(jo2.getString("timestamp"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            pdb.put_card(key,cdbkun);
        }

    }
    public String To_JsonString(){

        root_object=new JSONObject();
        root_object.put("project_title",pdb.get_project_title());
        root_object.put("project_id",pdb.project_id);
        root_object.put("latest_date",JSON_Date.Date_to_String(pdb.Latest_Date));
        Card_Database cdkun;
        Map<String,JSONObject> jo_map=new HashMap<>();
        for(String key:pdb.get_cards_keyset()){
            JSONObject jokun=new JSONObject();
            cdkun=pdb.get_Card_db(key);
            jokun.put("name",cdkun.Card_name);
            jokun.put("mark_data",cdkun.mark_data);
            jokun.put("parent",cdkun.parent_id);
            jokun.put("timestamp",JSON_Date.Date_to_String(cdkun.timestamp));
            jokun.put("owner",cdkun.owner);
            jo_map.put(key,jokun);
        }
        root_object.put("cards",jo_map);
        return root_object.toString();
    }
    public void  Write_JsonString() throws IOException {
        root_object=new JSONObject();
        root_object.put("project_title",pdb.get_project_title());
        root_object.put("latest_date",JSON_Date.Date_to_String(pdb.Latest_Date));
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
        for(String key:pdb.get_cards_keyset()){
            JSONObject joude=new JSONObject();
            Card_Database cdb2f=pdb.get_Card_db(key);
            joude.put("name",cdb2f.Card_name);
            joude.put("parent",cdb2f.parent_id);
            joude.put("mark_data",cdb2f.mark_data);
            joude.put("isremoved",cdb2f.isremoved);
            joude.put("timestamp",JSON_Date.Date_to_String(cdb2f.timestamp));
            joude.put("owner",cdb2f.owner);
            jomap.put(key,joude);
        }
        //root_object.put("cards",jomap);
        ArrayList<String> cards_datafile=new ArrayList<>();
        int jomap_maxsize=500;
        ArrayList<Map<String,JSONObject>> jomap_array=new ArrayList<>();
        int count_niki=0;
        int count_2=0;
        /*System.out.println(jomap.size());
        System.out.println("Start");*/
        Map<String,JSONObject> tempkun=new HashMap<>();

        for(String keykun:jomap.keySet()){

            if(count_niki < jomap_maxsize ){
                tempkun.put(keykun,jomap.get(keykun));
                count_niki++;
            }else{
                jomap_array.add(new HashMap<>(tempkun));
                tempkun=new HashMap<>();
                tempkun.put(keykun,jomap.get(keykun));
                count_2++;
                count_niki=1;
            }
//            /System.out.println("niki:" + count_niki + "\n2:" + count_2);
        }
        /*System.out.println("End");
        System.out.println(jomap_array.size());*/

        jomap_array.add(new HashMap<>(tempkun));

        //System.out.println(jomap_array.size());
        File cards_dir=CustomFile.get_File(pdb.project_id + File.separator + "cards",Branch_Name);
        if(!cards_dir.exists()) Files.createDirectories(cards_dir.toPath());
        File card_filekun;
        for(int i=0;i<jomap_array.size();i++){
            card_filekun=CustomFile.get_File(pdb.project_id + File.separator + "cards" + File.separator + "card-" + Integer.toString(i) + ".json",Branch_Name);
            JSONObject jonikihad=new JSONObject();
            jonikihad.put("cards",jomap_array.get(i));
            write_str(card_filekun,jonikihad.toString());
            cards_datafile.add("card-" + i);
        }
        root_object.put("cards_file",new JSONArray(cards_datafile));
        File proj_file=CustomFile.get_File(pdb.project_id + File.separator + "project_meta.json",Branch_Name);
        if(!proj_file.getParentFile().exists()){
            Files.createDirectories(proj_file.getParentFile().toPath());
        }
        write_str(proj_file,root_object.toString());
    }
    public ArrayList<String> Card_update_ids(Date dt){
        return pdb.Card_update_ids(dt);
    }
}
