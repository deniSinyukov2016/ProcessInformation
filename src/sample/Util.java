package sample;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.List;

public class Util{

    // считываем содержимое файла в String с помощью BufferedReader
    public static String readUsingBufferedReader(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader(fileName));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while( ( line = reader.readLine() ) != null ) {
            stringBuilder.append( line );
            stringBuilder.append( ls );
        }

        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    public static File writeUsingFileWriter(String data) throws IOException {
        File file = new File(System.getProperty("user.name") + ".txt");

        System.out.println(file.getPath());
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String setLine(List<String> list){
        String line = StringUtils.join(list, ",");

        return line;
    }
}