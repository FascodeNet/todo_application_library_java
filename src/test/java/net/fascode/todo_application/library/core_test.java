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
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
public class core_test {
    @Test
    public void testJson(){


        core cr=new core();

        cr.create_project("testkun621","hoge");
        cr.Create_Card(new Card_Database("Testkun","# hogehoge",new ArrayList<Card_Database>(Arrays.asList(
                new Card_Database("testhoge","# TEST",
                        new ArrayList<>(Arrays.asList(
                                new Card_Database("testkun","T E S T")
                        ))),
                new Card_Database("tets_TDN","やりますねぇ")))));
        Card_Database cdkun=new Card_Database("aiueo","# HOGE");
        cr.Create_Card(cdkun);
        cr.Create_Card(new Card_Database("TEST25t","HOGEAIUEO"),cdkun);
        cr.edit_Card(new Card_Database("TEST25t","AIUEO810"),cdkun.children.get(0).Card_id,cdkun);
        cr.save_project();


        System.out.println(cr.to_JSONString());
        core cr2=new core();
        cr2.load_project_Name("testkun621","hoge");
        Card_Database cdfrt = cr2.pdj.pdb.cards.get(0);
        cr2.edit_Card(new Card_Database("AIUE0","TESTGGGG",cdfrt.children),cdfrt.Card_id);
        cr2.save_project();
        System.out.println("dest");
        System.out.println(cr2.to_JSONString());
        assertEquals(1,1);
    }

}
