package net.fascode.todo_application.library;

import net.fascode.todo_application.library.db.Dir_Tree;
import net.fascode.todo_application.library.db.Tree_DataBase;
import net.fascode.todo_application.library.json.db_Json;

import java.io.File;
import java.nio.file.Path;

public class core {
    public static String GenJsonTree(Path dirpath){
        Dir_Tree trkun=new Dir_Tree(dirpath.toFile());
        trkun.Gen_Dir();
        Tree_DataBase tdbaniki= trkun.get_tdb();
        db_Json djsonkun=new db_Json(tdbaniki);
        return djsonkun.to_Json();
    }
}
