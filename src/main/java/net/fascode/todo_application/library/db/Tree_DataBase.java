package net.fascode.todo_application.library.db;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;

public class Tree_DataBase {
    public String name;
    public String packid;
    public String hash;
    public long size;
    public boolean isFile;
    public ArrayList<Tree_DataBase> children;
    public Tree_DataBase(){
        name="";
        packid="";
        hash="";
        size=0;
        isFile=false;
        children=new ArrayList<Tree_DataBase>();

    }
}
