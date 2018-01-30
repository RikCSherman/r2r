package eddb.system;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.*;

public class SystemCsvToJson {

    public static void main(String[] args) throws IOException {
        List<AllSystem> allSystems = new ArrayList<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(r2r.Main.class.getResourceAsStream("/systems.csv")));
        String line;
        in.readLine();
        while((line = in.readLine()) != null) {
            AllSystem system = new AllSystem(line);
            allSystems.add(system);
        }
        ObjectMapper mapper = new ObjectMapper();
        OpenOption[] options = new OpenOption[] { CREATE, WRITE, TRUNCATE_EXISTING };
        OutputStream out = Files.newOutputStream(Paths.get("systems.json"), options);
        mapper.writer().writeValue(out, allSystems);
    }
}
