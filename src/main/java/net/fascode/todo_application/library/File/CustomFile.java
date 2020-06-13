package net.fascode.todo_application.library.File;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomFile {

    public static File get_File(String file_path, String Branch){
        File fkun= new File(".todo" + File.separator + "branches" + File.separator + Branch + File.separator + file_path);
        //Path returnkun= Paths.get(fkun.getPath()).relativize(Paths.get(file_path));
        //System.out.println(fkun.getAbsolutePath());
        return fkun;
    }
}
