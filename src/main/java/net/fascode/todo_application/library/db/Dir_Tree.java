package net.fascode.todo_application.library.db;

import org.apache.commons.codec.digest.DigestUtils;
import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class Dir_Tree {
    private File root_dir;
    private Tree_DataBase tdb;

    public Dir_Tree(File root_dir){
        this.root_dir=root_dir;
        tdb=new Tree_DataBase();
    }
    public Tree_DataBase get_tdb() { return tdb;}
    public void set_tdb(Tree_DataBase tdb){this.tdb=tdb;}
    public void Gen_Dir(){
        readDir(tdb,root_dir);
    }
    private void readDir(Tree_DataBase tdbkun,File dir){
        tdbkun.name=dir.getName();
        String hash_dir=DigestUtils.md5Hex(Long.toString(System.currentTimeMillis()) + dir.getName());
        tdbkun.hash= hash_dir;
        tdbkun.packid= hash_dir;
        tdbkun.size=0;
        tdbkun.isFile=false;
        File[] files = dir.listFiles();
        if( files == null )
            return;
        for( File file : files ) {
            if (!file.exists())
                continue;
            else if (file.isDirectory()){
                Tree_DataBase addkun_buf=new Tree_DataBase();
                readDir(addkun_buf,file);
                tdbkun.children.add(addkun_buf);
            }
            else if (file.isFile()) {
                Tree_DataBase addkun_buf2 = new Tree_DataBase();
                try {
                    file_readkun(file, addkun_buf2);
                    tdbkun.children.add(addkun_buf2);
                }catch(IOException e){
                    System.err.println("IO Exception!\n" + e.getLocalizedMessage());
                }
            }
        }
    }
    private void file_readkun(File fkun,Tree_DataBase tdbkun) throws IOException {
        tdbkun.name=fkun.getName();
        tdbkun.isFile=true;
        String hashkun=DigestUtils.md5Hex(new FileInputStream(fkun));
        tdbkun.packid= hashkun;
        tdbkun.hash=hashkun;
        tdbkun.size=fkun.length();
    }
}
