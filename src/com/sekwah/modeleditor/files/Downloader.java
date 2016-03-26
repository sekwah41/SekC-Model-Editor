package com.sekwah.modeleditor.files;

import com.sekwah.modeleditor.windows.SplashScreen;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader implements Runnable{
	private final SplashScreen loadingScreen;
	private String url;
	private String outFile;
	private boolean overwrite;
	public Downloader(String var1, String var2, boolean var3, SplashScreen var4)
	{
		url = var1;
		outFile = var2;
		overwrite = var3;
		loadingScreen = var4;

	}
	public void run()
	{
		try
		{
			File file = new File(outFile);
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			long filesize = connection.getContentLength();
			if(!file.exists() || overwrite || filesize != file.length()){
				float totalDataRead = 0;
				BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
				FileOutputStream out = new FileOutputStream(outFile);
				BufferedOutputStream buf = new BufferedOutputStream(out, 1024);
				System.out.println("[Installer]: Connected to file " + '"' + file.getName() + '"');
				byte[] data = new byte[1024];
				int i = 0;
				while((i = in.read(data, 0, 1024))>=0)
				{
					totalDataRead+=i;
					buf.write(data,0,i);
					float percent = (totalDataRead)/filesize;
					loadingScreen.setDownloadProgress("Downloading: " + file.getName(), (int) (totalDataRead / 1024) + "/" + (filesize / 1024) + "kb" , percent);
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
