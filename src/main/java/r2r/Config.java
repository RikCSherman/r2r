package r2r;

import org.apache.commons.cli.*;

public class Config {

    private String startSystem;
    private int noSystems;
    private int maxDistance;



    Config(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addRequiredOption( "s", "system", true, "System name to start from" );
        options.addRequiredOption( "d", "distance", true, "Max distance from start system" );
        options.addRequiredOption( "n", "number", true, "Number of systems to include in route" );
        CommandLine line = parser.parse( options, args );
        startSystem = line.getOptionValue('s');
        noSystems = Integer.parseInt(line.getOptionValue('n'));
        maxDistance = Integer.parseInt(line.getOptionValue('d'));
    }

    public String getStartSystem() {
        return startSystem;
    }

    public int getNoSystems() {
        return noSystems;
    }

    public int getMaxDistance() {
        return maxDistance;
    }
}
