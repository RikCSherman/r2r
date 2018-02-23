package r2r;

import com.fasterxml.jackson.databind.ObjectMapper;
import r2r.model.RtoRSystem;

import java.io.File;
import java.util.Collection;

public class DataConverter {

    public static void main(String[] args) throws Exception {
        R2r r = new R2r();
        Collection<RtoRSystem> systems = r.loadR2RSystems().values();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("r2rsystems.json"), systems);
    }
}
