import java.io.*;
import java.util.zip.GZIPInputStream;

public class App {
    public static void main(String[] args) throws Exception {

        String filePath = "CoverageData.tar.gz";
        File file = new File(filePath);
        String path = file.getAbsolutePath();
        System.out.println(path);
        InputStream in = new FileInputStream(path);
        InputStream zip = new GZIPInputStream(in);
        InputStreamReader r = new InputStreamReader(zip, "UTF8");
        // String encoding = r.getEncoding();
        BufferedReader br = new BufferedReader(r);

        int i;
        int count = 0;
        while (count < 300) {
            // while ((i = br.read()) != -1) {
            String data = br.readLine();
            System.out.println(data);
            count++;
        }

        br.close();
        r.close();
        System.out.println(count);

    }
}
