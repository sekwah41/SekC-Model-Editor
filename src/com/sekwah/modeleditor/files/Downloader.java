package com.sekwah.modeleditor.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader implements Runnable{
	private String url;
	private String outFile;
	private boolean overwrite;
	public Downloader(String var1, String var2, boolean var5)
	{
		url = var1;
		outFile = var2;
		overwrite = var5;
		
		
	}
	public void run()
	{
		try
		{
			File file = new File(outFile);
			if(!file.exists() || overwrite){
				URL url1 = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
				int filesize = connection.getContentLength();
				float totalDataRead = 0;
				BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
				FileOutputStream out = new FileOutputStream(outFile);
				BufferedOutputStream buf = new BufferedOutputStream(out, 1024);
				System.out.println("[Installer]: Connected to file " + '"' + file.getName() + '"');
				byte[] data = new byte[1024];
				int i = 0;
				while((i = in.read(data, 0, 1024))>=0)
				{
					totalDataRead=totalDataRead+i;
					buf.write(data,0,i);
					float percent = (totalDataRead*100)/filesize;
				}
				buf.close();
				out.close();
				in.close();
			}
			else{
				System.out.println("File: '" + outFile + "' already exists");
			}
			
		}
		catch(Exception event){
			event.printStackTrace();
			}
		
	}

}
