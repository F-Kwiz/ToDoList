package tasks.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {

    static public List<String> findXMLFiles(String directoryPath) {
        List<String> xmlFilePaths = new ArrayList<>();
        File directory = new File(directoryPath);

        // check if path is a directory
        if (directory.isDirectory()) {
            // look for xml files in a recursive way
            searchXMLFiles(directory, xmlFilePaths);
        } else {
            System.out.println("Model-FileFinder: The given path is not a directory");
        }
        return xmlFilePaths;
    }

    private static void searchXMLFiles(File directory, List<String> xmlFilePaths) {
        // List of all files in active directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // search in a subfolder (recursive)
                    searchXMLFiles(file, xmlFilePaths);
                } else {
                    // check if file is xml
                    if (file.getName().toLowerCase().endsWith(".xml")) {
                        xmlFilePaths.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }
}