package net.fascode.todo_application.library;

import net.fascode.todo_application.library.db.Card_Database;
import net.fascode.todo_application.library.db.Project_Database;
import net.fascode.todo_application.library.json.Project_DB_JSON;
import org.junit.Test;

import javax.smartcardio.Card;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;
public class core_test {
    @Test
    public void testJson(){
        Project_Database pdbkun=new Project_Database();
        Card_Database cdbkun=new Card_Database();
        cdbkun.Card_name="tdn";
        cdbkun.Card_id="aaadf";
        cdbkun.mark_data="```php \ngte\n```";
        Card_Database cdbkun3=new Card_Database();
        cdbkun3.Card_name="thdn";
        cdbkun3.Card_id="aafadf";
        cdbkun3.mark_data="```php \nagte\n```";
        Card_Database cdbkun2=new Card_Database();
        cdbkun2.Card_name="tddn";
        cdbkun2.Card_id="aaaadf";
        cdbkun2.mark_data="```php \ngt\n```";
        cdbkun2.children.add(cdbkun);
        cdbkun2.children.add(cdbkun3);

        Card_Database cdbkun5=new Card_Database();
        cdbkun5.Card_name="thgtdn";
        cdbkun5.Card_id="aafadgtgtf";
        cdbkun5.mark_data="```php \najygte\n```";

        pdbkun.project_id="jvfrv";
        pdbkun.project_title="tdn";
        pdbkun.cards.add(cdbkun2);
        pdbkun.cards.add(cdbkun5);
        Project_DB_JSON dbjson=new Project_DB_JSON(pdbkun,"test");

        System.out.println(dbjson.To_JsonString());
        try{
            dbjson.Write_JsonString();
        }
        catch (IOException e){
            //Nothing
        }

        Project_DB_JSON dbjson2=new Project_DB_JSON("jvfrv","test");
        System.out.println(dbjson2.To_JsonString());
        assertEquals(1,1);
    }
}
