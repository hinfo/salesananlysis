package br.com.ilegra.server;

import br.com.ilegra.services.ServicesIO;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author henrique
 * Reading and processing files raw data in .dat files
 * This is a daemon service
 */
public class Server extends ServicesIO {

    private static final String PATTERN_INPUT = ".dat";
    private static final String PATTERN_OUTPUT = ".done.dat";
    private static final String PATH_OUT = "data/out/";
    private static final String PATH_IN = "data/in/";
    public static String basePath;
    public static String fileSeparator;

    public static void main(String[] args) {
        Thread tr = new Thread(t1);
        tr.start();
    }
    public static Runnable t1 = new Runnable() {
        @Override
        public void run() {
            System.out.println("Service Running...");
            try {
                while (true) {
                    listenPath();
                    Thread.sleep(1000);
                    System.gc();
                }

            } catch (Exception e) {
                System.out.println("Service stopped!");
                System.out.println("Error: " + e);
            }

        }
    };

    public static void listenPath() {

        basePath = System.getProperty("user.home");
        fileSeparator = System.getProperty("file.separator");
        String pathInput = basePath + fileSeparator + PATH_IN;
        String pathOutput = basePath + fileSeparator + PATH_OUT;
        String outputName = "";
        File file = new File(pathInput);
        File[] files = file.listFiles();
        ArrayList<String> lines;
        ArrayList<String> dataFiles;
        ServicesIO aux = new ServicesIO();
        for (File fileInput : files) {
            if (!fileInput.getName().contains(PATTERN_INPUT)){
                break;
            }
            if (fileInput.getName().contains(PATTERN_INPUT)) {
                //process files
                System.out.print("Processing file: " + fileInput.getName() + "....");
                lines = aux.readFile(pathInput + fileInput.getName());
                showLines(lines);
                dataFiles = aux.processFile(lines);
                outputName = fileInput.getName().replace(PATTERN_INPUT, "") + PATTERN_OUTPUT;
                aux.writeFile(pathOutput + outputName, dataFiles);
                System.out.println("OK.");
            }
        }
    }
    //Debug
    public static void showLines(ArrayList<String> lines){
        System.out.println(lines.size());
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
