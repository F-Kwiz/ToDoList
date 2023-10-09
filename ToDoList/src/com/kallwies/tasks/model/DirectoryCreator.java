package com.kallwies.tasks.model;

import java.io.File;

public class DirectoryCreator {
	final static String projectName = "Tasks";
	final static boolean workspace_project = true;
	
    public static void main(String[] args) {
    	// Create standard String to save Files and Directory into users Directory
    	String userHome = System.getProperty("user.home");
    	String programDirectory = userHome + File.separator + projectName;
    	File programDirFile = new File(programDirectory);
    	// Checks if a file for this program already exists. If not creates one
        if (!programDirFile.exists() || !programDirFile.isDirectory()) {
            if (programDirFile.mkdirs()) {
                System.out.println("Directory " + programDirectory + " was created.");
            } else {
                System.out.println("Error during creation of directory " + programDirectory);
                return;
            }
        }
    	
        // Is it a program that needs workspaces?
        if (workspace_project) {
        	// Creates String to determine path of the workspaces folder
	    	String workspaceDirectory = programDirectory + File.separator + "workspaces";
	    	File workspaceDirFile = new File(workspaceDirectory);
	        
	    	// Looks for workspace directory. If it is not found. Creates directory.
	        if (!workspaceDirFile.exists() || !workspaceDirFile.isDirectory()) {
	            if (workspaceDirFile.mkdirs()) {
	                System.out.println("Directory " + workspaceDirFile + " was created.");
	            } else {
	                System.out.println("Error during creation of directory " + workspaceDirFile);
	                return;
	            }
	        }
	        
	        // Checks workspace directory for workspaces. If none is found. Creates one named "default"
	        File[] workspaceFolders = new File(workspaceDirectory).listFiles(File::isDirectory);
	        if (workspaceFolders.length > 0 && workspaceFolders != null) {
	        	
	            return;
	        } else {
	            System.out.println("No workspaces found in the specified directory.");
	        	File defaultDirectory = new File(workspaceDirectory +  File.separator + "default" + File.separator);
	            if (!defaultDirectory.exists() || !defaultDirectory.isDirectory()) {
	                if (defaultDirectory.mkdirs()) {
	                    System.out.println("Directory " + defaultDirectory + " was created.");
	                } else {
	                    System.out.println("Error during Creation of directory " + defaultDirectory);
	                    return;
	                }
	            }
	        }
        }
    }
}

