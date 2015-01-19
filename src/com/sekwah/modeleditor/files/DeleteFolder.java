package com.sekwah.modeleditor.files;
import java.io.*; 

public class DeleteFolder {
	
	public static void main(String target) throws Exception{
		File file = new File(target);
		delete(file);
	} 
	
	public static void delete(File file) { // Check if file is directory/folder 
			if(file.isDirectory()) { // Get all files in the folder 
				File[] files=file.listFiles(); 
				for(int i=0;i<files.length; i++){
					delete(files[i]);
				}
			}
			System.out.println("Deleted: " + file.getPath());
			file.setWritable(true);
			file.delete();
	}
}