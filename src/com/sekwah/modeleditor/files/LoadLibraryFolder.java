package com.sekwah.modeleditor.files;

import java.io.File;

public class LoadLibraryFolder {
	
	public static void main(String target) throws Exception{
		File file = new File(target);
		load(file);
	} 
	
	public static void load(File file) { // Check if file is directory/folder
			if(file.isDirectory()) { // Get all files in the folder 
				File[] files=file.listFiles(); 
				for(int i=0;i<files.length; i++){
					load(files[i]);
				}
			}
			System.out.println("Loaded: " + file.getPath());
		try {
			System.load(file.getPath());
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}
}