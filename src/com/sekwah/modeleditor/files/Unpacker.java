package com.sekwah.modeleditor.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Unpacker implements Runnable{
	String jarFile;
	String destDir;
	public Unpacker(String var1, String var2){
		jarFile = var1;
		destDir = var2;
	}
	public void run() {
		try
		{
			File fs = new File(jarFile);
			JarFile jar = new JarFile(jarFile);
			Enumeration enum1 = jar.entries();
			float totalDataRead = 0;
			while(enum1.hasMoreElements())
			{
				try{
				float percent = (++totalDataRead/jar.size())*100;//fix
				JarEntry file = (JarEntry)enum1.nextElement();
				if(!file.isDirectory()){
				File f = new File(File.separator + destDir + File.separator + file.getName());
				System.out.println("Unpacking: " + f.getAbsolutePath() + "(" + file.getName() + ")");
				f.getParentFile().mkdirs();
				InputStream is = jar.getInputStream(file);
				FileOutputStream fos = new FileOutputStream(f);
				
				byte[] buf = new byte[4096];
			    int r;
			    while ((r = is.read(buf)) != -1) {
			      fos.write(buf, 0, r);
			    }
			    
				/**while(is.available() > 0)
				{
					//ProgressBar.setValue(100 - (int) (((float)is.available() / (float)startAvailable) * 100));
					fos.write(is.read());
				}*/
				fos.close();
				is.close();
				}
				}
				catch(Exception e){e.printStackTrace();}
			}
		}
		catch(Exception e){e.printStackTrace();}
		
	}

}
