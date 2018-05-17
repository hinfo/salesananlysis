/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hantunes.server;

import br.com.hantunes.services.ServicesIO;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author henrique
 */
public class Server {

    private static final String PATTERN = ".dat";
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
        File file = new File(pathInput);
        File[] files = file.listFiles();
        ArrayList<String> lines;
        ArrayList<String> dataFiles;
        ServicesIO aux = new ServicesIO();
        for (File file1 : files) {
            if (file1.getName().contains(PATTERN)) {
                //process files
                System.out.print("Processing file: " + file1.getName() + "....");
                lines = aux.readFile(pathInput + file1.getName());
                dataFiles = aux.processFile(lines);
                String outputName = file1.getName().replace(PATTERN, "") + ".done.dat";
                aux.writeFile(pathOutput + outputName, dataFiles);
                System.out.println("OK.");
                continue;
            }
        }
    }
}
