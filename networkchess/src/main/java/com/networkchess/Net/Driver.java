package com.networkchess.Net;

import merrimackutil.cli.LongOption;
import merrimackutil.cli.OptionParser;
import merrimackutil.json.types.JSONObject;
import merrimackutil.util.Tuple;

import java.io.File;

import static merrimackutil.json.JsonIO.readObject;

public class Driver {
    /**
     * Config file of our server holds a default file
     */
    private static String configFile = "networkchess/src/main/java/com/networkchess/Net/defaultConfig.json";
    /**
     * addr from config file that server runs on
     */
    private static String addr;
    /**
     * Port server is listening on
     */
    private static int port;
    /**
     * Number of threads our server is allowed to use
     */
    private static int threads;
    /**
     * Prints the usage to the screen and exits.
     *
     * Code is adapted from Prof. Kissel example in networking
     */
    private static void usage() {
        System.out.println("usage:");
        System.out.println("  ChessServer --config <config>");
        System.out.println("  ChessServer --help");
        System.out.println("options:");
        System.out.println("  -c, --config\t\tConfig file to use.");
        System.out.println("  -h, --help\t\tDisplay the help.");
        System.exit(1);
    }

    /**
     * Processes the command line arugments.
     * @param args the command line arguments.
     *
     * Code is adapted from Prof. Kissel example in networking
     */
    public static void main(String[] args){
        OptionParser parser;
        boolean doHelp = false;
        boolean doConfig = false;

        LongOption[] opts = new LongOption[2];
        opts[0] = new LongOption("help", false, 'h');
        opts[1] = new LongOption("config", true, 'c');

        Tuple<Character, String> currOpt;

        parser = new OptionParser(args);
        parser.setLongOpts(opts);
        parser.setOptString("hc:");

        while (parser.getOptIdx() != args.length) {
            currOpt = parser.getLongOpt(false);

            switch (currOpt.getFirst()) {
                case 'h':
                    doHelp = true;
                    break;
                case 'c':
                    doConfig = true;
                    configFile = currOpt.getSecond();
                    break;
                case '?':
                    System.out.println("Unknown option: " + currOpt.getSecond());
                    usage();
                    break;
            }
        }

        if (doHelp && doConfig) {
            usage();
        }

        if (doHelp) {
            usage();
        }

        loadConfig(configFile);

        ChessServer chessServer = new ChessServer(addr,port,threads);
    }

    /**
     * Loads the config file and gets values for chess server
     * @param configFile
     */
    private static void loadConfig(String configFile) {
        try {
            //read in JSON file
            JSONObject config = readObject(new File(configFile));

            if (!(config.containsKey("addr") && config.containsKey("port") && config.containsKey("threads"))) {
                System.err.println("ChessServer: Config is missing a field must have, addr - String, port - int, threads - int");
                System.err.println("Please check you config file for errors");
                System.exit(1);
            }

            //get attributes
            addr = config.getString("addr");
            port = config.getInt("port");
            threads = config.getInt("threads");
        } catch (Exception e) {
            System.err.println("ChessServer: Error reading your config file");
            System.err.println(e);
            System.exit(1);
        }
    }
}
