import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

public class App {
    public static void main(String[] args) throws Exception {

        // System.out.println("hello world");

        // Get path to Coverage Data zip file
        String filePath = "CoverageData.tar.gz";
        File file = new File(filePath);
        String path = file.getAbsolutePath();
        Path source = Paths.get(path);
        // Get target path
        filePath = "CoverageData";
        file = new File(filePath);
        path = file.getAbsolutePath();
        Path target = Paths.get(path);
        // Decompress tar.gz file
        decompressTar(source, target);

        // Get path to CoverageData directory
        filePath = "CoverageData\\CoverageData";
        file = new File(filePath);
        path = file.getAbsolutePath();
        source = Paths.get(path);
        // List files in CoverageData directory
        File sourceDir = source.toFile();
        String[] sourceFiles = sourceDir.list();
        for (int i = 0; i < sourceFiles.length; i++) {
            filePath = "CoverageData\\CoverageData\\" + sourceFiles[i];
            file = new File(filePath);
            path = file.getAbsolutePath();
            source = Paths.get(path);
            // System.out.println(source);
            filePath = "Test " + i;
            file = new File(filePath);
            path = file.getAbsolutePath();
            target = Paths.get(path);
            // System.out.println(target);
            decompressGzip(source, target);
            // System.out.println(sourceFiles[i]);
        }
        // filePath = "CoverageData2";
        // file = new File(filePath);
        // path = file.getAbsolutePath();
        // target = Paths.get(path);
        // decompressGzip(source, target);
        // System.out.println(source);
        // System.out.println(target);

        // // File[] directoryListing = target.listFiles();
        // // if (directoryListing != null) {
        // // for (File child : directoryListing) {
        // // System.out.println(child);
        // // }
        // // } else {
        // // Handle the case where dir is not really a directory.
        // // Checking dir.isDirectory() above would not be sufficient
        // // to avoid race conditions with another process that deletes
        // // directories.
        // // System.out.println("null");
        // // }
        // // System.out.println(path);
        // InputStream in = new FileInputStream(target);
        // // InputStream zip = new GZIPInputStream(in);
        // InputStreamReader r = new InputStreamReader(in, "UTF8");
        // // String encoding = r.getEncoding();
        // BufferedReader br = new BufferedReader(r);

        // // int i;
        // // int count = 0;
        // // while (count < 300) {
        // // // while ((i = br.read()) != -1) {
        // // String data = br.readLine();
        // // System.out.println(data);
        // // count++;
        // // }

        // br.close();
        // r.close();
        // System.out.println(count);

    }

    public static void decompressTar(Path source, Path target) throws IOException {

        InputStream in = Files.newInputStream(source);
        InputStream zip = new GZIPInputStream(in);
        TarArchiveInputStream tar = new TarArchiveInputStream(zip);

        ArchiveEntry entry;

        int count = 1;
        while ((entry = tar.getNextEntry()) != null) {
            Path resolveEntryPath = target.resolve(entry.getName());
            Path normalizeEntryPath = resolveEntryPath.normalize();
            // System.out.println(normalizeEntryPath);
            if (entry.isDirectory()) {
                Files.createDirectories(normalizeEntryPath);
            } else {

                // Check parent folder
                Path parent = normalizeEntryPath.getParent();
                if (parent != null) {
                    if (Files.notExists(parent)) {
                        Files.createDirectories(parent);
                    }
                }

                // String filePath = "CoverageData\\CoverageData\\Test " + count;
                // File file = new File(filePath);
                // String path = file.getAbsolutePath();
                // Path testTarget = Paths.get(path);
                // System.out.println(source);
                // count++;
                // Copy TarArchiveInputStream to Path normalizeEntryPath
                Files.copy(tar, normalizeEntryPath, StandardCopyOption.REPLACE_EXISTING);

                // decompressGzip(normalizeEntryPath, target);
            }
        }
    }

    public static void decompressGzip(Path source, Path target) throws IOException {

        FileInputStream in = new FileInputStream(source.toFile());
        GZIPInputStream zip = new GZIPInputStream(in);
        // OutputStream out = Files.newOutputStream(target);

        // int file;
        // while ((file = zip.read()) > 0) {
        Files.copy(zip, target);
        // byte[] buffer = new byte[1024];
        // int len;
        // while ((len = zip.read(buffer)) > 0) {
        // out.write(buffer, 0, len);
        // }
        // }
    }
}
