import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
// import org.apache.commons.compress.archivers.ArchiveInputStream;
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

        File[] files = target.toFile().listFiles();
        for (File file2 : files) {
            System.out.println(file2);
        }
        // Get all source code methods tested
        // getMethods(target);

    }

    public static void decompressTar(Path source, Path target) throws IOException {

        InputStream in = Files.newInputStream(source);
        InputStream zip = new GZIPInputStream(in);
        TarArchiveInputStream tar = new TarArchiveInputStream(zip);

        ArchiveEntry entry;

        while ((entry = tar.getNextEntry()) != null) {
            Path resolveEntryPath = target.resolve(entry.getName());
            Path normalizeEntryPath = resolveEntryPath.normalize();
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

                // Copy entry from tar to normalizeEntryPath
                Files.copy(tar, normalizeEntryPath, StandardCopyOption.REPLACE_EXISTING);

            }
        }
    }

    public static List<String> getMethods(File[] source) throws IOException {

        List<String> methods = new ArrayList<String>();
        String line;
        int count = 0;

        // BufferedReader br = Files.newBufferedReader(source);

        // while ((line = br.readLine()) != null && count < 50) {
        //     // process the line
        //     System.out.println(line);
        // }
        // // close resources
        // br.close();

        return methods;

    } 
}
