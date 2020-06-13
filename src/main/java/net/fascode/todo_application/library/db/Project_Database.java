package net.fascode.todo_application.library.db;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class Project_Database {
    public String project_title;
    public String project_id;
    public ArrayList<Card_Database> cards;
    public Project_Database(){
        project_title="";
        project_id="";
        cards=new ArrayList<Card_Database>();

    }
}
