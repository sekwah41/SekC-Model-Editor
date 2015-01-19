package com.sekwah.modeleditor.files;

import java.io.*;

public class CopyFile implements Runnable{
	
	String jarFile;
	String destDir;
	
	public CopyFile(String var1, String var2){
		jarFile = var1;
		destDir = var2;
	}
	
	public void run() {
		try {		
			
            InputStream in = new FileInputStream(jarFile);
            OutputStream out = new FileOutputStream(destDir);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}