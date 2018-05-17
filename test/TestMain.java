
import br.com.hantunes.services.ServicesIO;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author henrique
 */
public class TestMain {

    private static final String PATTERN = ".dat";

    public static void main(String[] args) {
        String basePath = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");
        String pathInput = basePath + fileSeparator + "data/in/";
        String pathBackup = basePath + fileSeparator + "data/bkp/";
        String pathOutput = basePath + fileSeparator + "data/out/";
        File file = new File(pathInput);
        File[] files = file.listFiles();
        ArrayList<String> lines;
        ArrayList<String> dataFiles;
        for (File file1 : files) {
            if (file1.getName().contains(PATTERN)) {
                //process files
                System.out.println("Processing file: " + file1.getName());
                lines = new ServicesIO().readFile(pathInput + file1.getName());
                dataFiles = new ServicesIO().processFile(lines);
                String outputName = file1.getName().replace(".dat", "") + ".done.dat";
                new ServicesIO().writeFile(pathOutput + outputName, dataFiles);
                continue;
            }
        }
    }
}
