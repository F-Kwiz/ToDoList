package workspacefinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class WorkspaceFinder {
	final static String file_type = ".task";
    public static Map<String, String> parseTaskFiles(String folderPath) {
        Map<String, String> workspaceInfoMap = new HashMap<>();
        try {
            // search through given directory path and subfolders
            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(file_type))
                    .forEach(taskFile -> {
                        try {
                            // Read file
                            Stream<String> lines = Files.lines(taskFile);
                            String workspacePath = "";
                            String workspaceName = "";

                            for (String line : (Iterable<String>) lines::iterator) {
                                if (line.startsWith("workspace_path:")) {
                                    workspacePath = line.substring("workspace_path:".length()).trim();
                                } else if (line.startsWith("name:")) {
                                    workspaceName = line.substring("name:".length()).trim();
                                }
                            }

                            // add information to map
                            if (!workspacePath.isEmpty() && !workspaceName.isEmpty()) {
                                workspaceInfoMap.put(workspaceName, workspacePath);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workspaceInfoMap;
    }
}