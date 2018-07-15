package com.jdqm.androidunittest;

import android.os.Environment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Jdqm on 2018/7/15.
 */

public class FileDao {

    public void write(String name, String content) {
        File file = new File(getDirectory(), name);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read(String name) {
        File file = new File(getDirectory(), name);

        if (!file.exists()) {
            return "";
        }

        try {
            FileReader reader = new FileReader(file);

            StringBuilder sb = new StringBuilder();

            char[] buffer = new char[1024];
            int    hasRead;

            while ((hasRead = reader.read(buffer, 0, buffer.length)) > -1) {
                sb.append(new String(buffer, 0, hasRead));
            }
            reader.close();

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void delete(String name) {
        File file = new File(getDirectory(), name);

        if (file.exists()) {
            file.delete();
        }
    }

    protected File getDirectory() {
        // return context.getCacheDir();
        return Environment.getExternalStorageDirectory();
    }
}
