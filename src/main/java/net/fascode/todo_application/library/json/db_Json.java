package net.fascode.todo_application.library.json;
import net.fascode.todo_application.library.db.Tree_DataBase;
import org.json.JSONObject;
import org.json.JSONArray;

public class db_Json {
    private Tree_DataBase dbcls;
    public db_Json(Tree_DataBase dnc){
        dbcls=dnc;
    }
    public String to_Json(){
        JSONObject rootobj=new JSONObject();
        rootobj.put("name",dbcls.name);
        rootobj.put("packid",dbcls.packid);
        rootobj.put("hash",dbcls.hash);
        rootobj.put("size",dbcls.size);
        rootobj.put("isFile",dbcls.isFile);
        if(!dbcls.isFile) {
            JSONArray children_jarray = new JSONArray();
            for (Tree_DataBase dcls : dbcls.children) {
                to_json_refl(children_jarray, dcls);
            }
            rootobj.put("child", children_jarray);
        }
        return rootobj.toString();
    }
    private void to_json_refl(JSONArray jarraykun, Tree_DataBase dclass){
        JSONObject jobject=new JSONObject();
        jobject.put("name",dclass.name);
        jobject.put("packid",dclass.packid);
        jobject.put("hash",dclass.hash);
        jobject.put("size",dclass.size);
        jobject.put("isFile",dclass.isFile);
        if(!dclass.isFile) {
            JSONArray child_jarray = new JSONArray();
            for (Tree_DataBase dcls : dclass.children) {
                to_json_refl(child_jarray, dcls);
            }
            jobject.put("child", child_jarray);
        }
        jarraykun.put(jobject);
    }
}
