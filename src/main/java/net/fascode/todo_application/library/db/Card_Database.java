package net.fascode.todo_application.library.db;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class Card_Database {
    public String Card_name;
    public String mark_data;
    public String Card_id;
    public ArrayList<Card_Database> children;
    public Card_Database(){
        Card_name="";
        mark_data="";
        Card_id="";
        children=new ArrayList<Card_Database>();
    }
}
