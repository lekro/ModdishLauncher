package lekro.moddish.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ModdishDownload {
	public static void getFile(String remoteLocale, String localLocale) {
		URL remoteURL = null;
		try {
			remoteURL = new URL(remoteLocale);
		} catch (MalformedURLException e) {
			System.err.println("Failed to create URL, somehow!");
			e.printStackTrace();
		}
		try {
			ReadableByteChannel rbc = Channels.newChannel(remoteURL.openStream());
			File localMC = new File(localLocale);
			localMC.getParentFile().mkdirs();
			localMC.createNewFile();
			FileOutputStream fos = new FileOutputStream(localMC);
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
