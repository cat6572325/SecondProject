package com.example.zqq.myapplication.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by cat6572325 on 16-11-16.
 */
public class File_with_ {
    static File fi;

    public File_with_() {

    }

    public File TestFile(File file) {
        try {
            if (new File("/sdcard/RoundVideo").exists()) {//如果文件夹存在，则不创建
            } else {
                new File("/sdcard/RoundVideo").mkdirs();
                //创建文件夹
            }
            if (file.exists()) {//如果文件存在，则追加编号
                String[] Path = file.getPath().split("\\.");
                String path = Path[0] + ((int) (1 + Math.random() * (10 - 1 + 1))) + ".3gp";
                fi = new File(path);
                fi.createNewFile();
                //给真正的file附上路径
            } else {
                fi = file;
                fi.createNewFile();
            }
        } catch (IOException e) {

        }
        return fi;
    }

    public File GetFile() {
        return fi;
    }

    public void DeleteFile() {
        fi.delete();
    }
}