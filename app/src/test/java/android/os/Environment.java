package android.os;

import java.io.File;

/**
 * Created by Jdqm on 2018/7/15.
 */

public class Environment {
    public static File getExternalStorageDirectory() {
        return new File("本地文件系统目录");
    }
}