package util;

import java.io.*;
import java.util.*;

public class FileUtil {

    public static List<String> readFile(String path) {
        List<String> data = new ArrayList<>();
        try {
            File file = new File(path);
            if (!file.exists()) return data;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                data.add(line);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void writeFile(String path, List<String> data) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}