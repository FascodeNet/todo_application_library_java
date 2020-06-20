package net.fascode.todo_application.library.main;

import net.fascode.todo_application.library.core;
import net.fascode.todo_application.library.db.Card_Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Main {
    public static void main(String[] args){


        core cr=new core();

        cr.create_project("testkun621","hoge");
        /*cr.Create_Card(new Card_Database("Testkun","# hogehoge",new ArrayList<Card_Database>(Arrays.asList(
                new Card_Database("testhoge","# TEST",
                        new ArrayList<>(Arrays.asList(
                                new Card_Database("testkun","T E S T")
                        ))),
                new Card_Database("tets_TDN","やりますねぇ")))));*/
        Card_Database cdkun=new Card_Database("aiueo","# 野獣","HOMO");
        String card_idkunw=cr.Create_Card(cdkun);
        Card_Database cdkun2=new Card_Database("hogehofe","#HHAH",card_idkunw);
        cr.Create_Card(cdkun2);

        //cr.Create_Card(new Card_Database("TEST25t","HOGEAIUEO"),cdkun);
        //cr.edit_Card(new Card_Database("TEST25t","AIUEO810"),cdkun.children.get(0).Card_id,cdkun);
        Date dt24=cr.get_latesttime();

        cr.save_project();


        System.out.println(cr.to_JSONString());
        core cr2=new core();
        cr2.load_project_Name("testkun621","hoge");

        cr2.Create_Card(new Card_Database("hoge","TDN","HAGE"));
        cr2.Create_Card(new Card_Database("hogre","TfDN","HOMO"));
        cr2.Create_Card(new Card_Database("hogree","TDNa","SEXUAL"));

        cr2.save_project();
        ArrayList<String> strkun=cr2.Card_update_ids(dt24);
        for(String strniki:strkun){
            System.out.println(strniki);
        }
        System.out.println("dest");
        System.out.println(cr2.to_JSONString());
    }
}
