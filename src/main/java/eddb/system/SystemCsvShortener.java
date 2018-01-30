package eddb.system;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SystemCsvShortener {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = new PrintWriter(new FileWriter("systems-short.csv"));
        BufferedReader in = new BufferedReader(new FileReader("systems.csv"));
        String line;
        in.readLine();
        while((line = in.readLine()) != null) {
            List<String> words = split(line);
            out.printf("%s,%s,%s,%s,%s,%s\n", words.get(0), words.get(1), words.get(2), words.get(3), words.get(4), words.get(5));
        }
        out.close();
    }

    private static List<String> split(String s)
    {
        List<String> words = new ArrayList<String>();
        boolean notInsideComma = true;
        int start =0, end=0;
        for(int i=0; i<s.length()-1; i++)
        {
            if(s.charAt(i)==',' && notInsideComma)
            {
                words.add(s.substring(start,i));
                start = i+1;
            }
            else if(s.charAt(i)=='"')
                notInsideComma=!notInsideComma;
        }
        words.add(s.substring(start));
        return words;
    }
}
