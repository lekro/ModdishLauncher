package lekro.moddish.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ModdishDownload {
	private static final String myDir = System.getProperty("user.dir");
	public static void getFile(String remoteLocale, String localLocale) {
		URL remoteURL = null;
		try {
			remoteURL = new URL(remoteLocale);
		} catch (MalformedURLException e) {}
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
	public static void getLWJGLFile(String saveAs, String loadAs) {
		
		String lwjglTemp = myDir + "/temp/lwjgl.zip";
		if (!(new File(lwjglTemp).exists())) {
			System.out.println("Downloading LWJGL (will be stored in temp directory)...");
			getFile("https://dl.dropboxusercontent.com/u/42740061/moddish/lwjgl-2.9.0.zip", myDir + "/temp/lwjgl.zip");
		}
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(lwjglTemp));
			ZipEntry ze = zis.getNextEntry();
			byte[] buffer = new byte[1024];
			while (ze != null) {
				if (ze.getName().contains(loadAs)) {
					String fileName = ze.getName();
					System.out.println("Extracting: " + fileName);
					File myFile = new File(saveAs);
					myFile.getParentFile().mkdirs();
					myFile.createNewFile();
						
					FileOutputStream fos = new FileOutputStream(myFile);
					int n;
					while ((n = zis.read(buffer, 0, 1024)) > -1) {
						fos.write(buffer, 0, n);
					}
					fos.close();
				}
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			zis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void getLWJGL(String binDir) {
		getLWJGLFile(binDir + "lwjgl.jar", "lwjgl.jar");
		getLWJGLFile(binDir + "lwjgl_util.jar", "lwjgl_util.jar");
		getLWJGLFile(binDir + "jinput.jar", "jinput.jar");
		getLWJGLFile(binDir + File.separator + "natives" + File.separator + "jinput-dx8_64.dll", "windows/jinput-dx8_64.dll");
		getLWJGLFile(binDir + File.separator + "natives" + File.separator + "jinput-dx8.dll", "windows/jinput-dx8.dll");
		getLWJGLFile(binDir + File.separator + "natives" + File.separator + "jinput-raw.dll", "windows/jinput-raw.dll");
		getLWJGLFile(binDir + File.separator + "natives" + File.separator + "lwjgl.dll", "windows/lwjgl.dll");
		getLWJGLFile(binDir + File.separator + "natives" + File.separator + "lwjgl64.dll", "windows/lwjgl64.dll");
		getLWJGLFile(binDir + File.separator + "natives" + File.separator + "OpenAL32.dll", "windows/OpenAL32.dll");
		getLWJGLFile(binDir + File.separator + "natives" + File.separator + "OpenAL64.dll", "windows/OpenAL64.dll");
		
	}
}
