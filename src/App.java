import java.io.*;
import java.util.zip.GZIPInputStream;

public class App {
    public static void main(String[] args) throws Exception {

        // System.out.println("hello world");

        // Get path to Coverage Data zip file
        String filePath = "CoverageData.tar.gz";
        File file = new File(filePath);
        String path = file.getAbsolutePath();
        File source = new File(path);
        filePath = "CoverageData.tar";
        file = new File(filePath);
        path = file.getAbsolutePath();
        File target = new File(path);
        decompress(source, target);
        System.out.println(source);
        System.out.println(target);
        File[] directoryListing = target.listFiles();
        // if (directoryListing != null) {
            for (File child : directoryListing) {
                System.out.println(child);
            }
        //} else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
           // System.out.println("null");
       // }
        // // System.out.println(path);
        // InputStream in = new FileInputStream(path);
        // InputStream zip = new GZIPInputStream(in);
        // InputStreamReader r = new InputStreamReader(zip, "UTF8");
        // // String encoding = r.getEncoding();
        // BufferedReader br = new BufferedReader(r);

        // int i;
        // int count = 0;
        // while (count < 300) {
        // // while ((i = br.read()) != -1) {
        // String data = br.readLine();
        // System.out.println(data);
        // count++;
        // }

        // br.close();
        // r.close();
        // System.out.println(count);

    }

    public static void decompress(File source, File target) throws IOException {

        InputStream in = new FileInputStream(source);
        InputStream zip = new GZIPInputStream(in);
        OutputStream out = new FileOutputStream(target);

        byte[] buffer = new byte[1024];
        int len;
        while ((len = zip.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }
}
