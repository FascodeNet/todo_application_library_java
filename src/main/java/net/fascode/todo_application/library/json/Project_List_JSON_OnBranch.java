package net.fascode.todo_application.library.json;

import net.fascode.todo_application.library.File.CustomFile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Project_List_JSON_OnBranch {
    public ArrayList<Proj_list> proj_list;
    private String Branch_name;
    private File branch_setting_file;

    /**
     *
     * @param Branch_Name ブランチ名
     */
    public Project_List_JSON_OnBranch(String Branch_Name){
        proj_list=new ArrayList<>();
        Branch_name=Branch_Name;
        branch_setting_file= CustomFile.get_File("branch_info.json",Branch_Name);
        if(!branch_setting_file.exists()){
            if(!branch_setting_file.getParentFile().exists()){
                try{
                    Files.createDirectories(branch_setting_file.getParentFile().toPath());
                }catch (IOException e){
                    //nothing do;
                }
            }
        }else{
            try{
                JSONObject jokun=new JSONObject(readAll(branch_setting_file));

                JSONArray jarraykun=jokun.getJSONArray("projects");
                jarraykun.forEach(j_obkun->{
                    JSONObject jobjkun=(JSONObject)j_obkun;
                    Proj_list plkun=new Proj_list();
                    plkun.proj_name=jobjkun.getString("project_name");
                    plkun.proj_id=jobjkun.getString("project_id");
                    proj_list.add(plkun);
                });
            }catch(IOException e){

            }
        }
    }

    /**
     * Listをファイルに書き込む。
     */
    public void Write_List(){
        JSONObject root_obj=new JSONObject();
        JSONArray jarray=new JSONArray();
        for(Proj_list plkun:proj_list){
            JSONObject ch_jo=new JSONObject();
            ch_jo.put("project_name",plkun.proj_name);
            ch_jo.put("project_id",plkun.proj_id);
            jarray.put(ch_jo);
        }
        root_obj.put("projects",jarray);
        try{
            FileWriter fwkun=new FileWriter(branch_setting_file);
            fwkun.write(root_obj.toString());
            fwkun.close();
        }catch (IOException e){
            //nothing todo
        }
    }

    private static String readAll(File fkun)throws IOException {
        FileInputStream fileInputStream=new FileInputStream(fkun);
        BufferedInputStream bufferedInputStream=new BufferedInputStream(fileInputStream);
        ByteArrayOutputStream bokun=new ByteArrayOutputStream();
        byte[] buf=new byte[4096];
        int n=bufferedInputStream.read(buf);
        while(n!=-1){
            bokun.write(buf,0,n);
            n=bufferedInputStream.read(buf);
        }
        fileInputStream.close();
        return bokun.toString();
    }
}
