package uz.smd.dictionary.edu.cmu.cs.speech.tts.flite;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Utility {
    public static boolean pathExists(String str) {
        return new File(str).exists();
    }

    public static ArrayList<String> readLines(String str) throws IOException {
        ArrayList arrayList = new ArrayList();
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(str));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream), 1024);
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                arrayList.add(readLine);
            } else {
                dataInputStream.close();
                return arrayList;
            }
        }
    }
}
