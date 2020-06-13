package net.fascode.todo_application.library;

import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;
public class core_test {
    @Test
    public void testJson(){
        System.out.println(core.GenJsonTree(Paths.get(System.getProperty("user.dir") + File.separator +  "src")));
        assertEquals(1,1);
    }
}
