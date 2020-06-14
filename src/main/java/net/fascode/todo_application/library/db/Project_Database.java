package net.fascode.todo_application.library.db;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Project_Database {
    public String project_title;
    public String project_id;
    //public ArrayList<Card_Database> cards;
    public Map<String, Card_Database> cards ;
    public Project_Database(){
        project_title="";
        project_id="";
        cards=new HashMap<>();

    }
}
