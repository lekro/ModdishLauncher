package lekro.moddish.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.List;
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
		List<String> saveAs = new LinkedList<String>();
		List<String> loadFrom = new LinkedList<String>();
		saveAs.add("lwjgl.jar");
		saveAs.add("lwjgl_util.jar");
		saveAs.add("jinput.jar");
		saveAs.add("lzma.jar");
		loadFrom.add("lwjgl.jar");
		loadFrom.add("lwjgl_util.jar");
		loadFrom.add("jinput.jar");
		loadFrom.add("lzma.jar");
		if (ModdishUtils.getOS().contains("Windows")) {
			saveAs.add("/natives/jinput-dx8_64.dll");
			saveAs.add("/natives/jinput-dx8.dll");
			saveAs.add("/natives/jinput-raw.dll");
			saveAs.add("/natives/lwjgl.dll");
			saveAs.add("/natives/lwjgl64.dll");
			saveAs.add("/natives/OpenAL32.dll");
			saveAs.add("/natives/OpenAL64.dll");
			loadFrom.add("windows/jinput-dx8_64.dll");
			loadFrom.add("windows/jinput-dx8.dll");
			loadFrom.add("windows/jinput-raw.dll");
			loadFrom.add("windows/lwjgl.dll");
			loadFrom.add("windows/lwjgl64.dll");
			loadFrom.add("windows/OpenAL32.dll");
			loadFrom.add("windows/OpenAL64.dll");
		}
		if (ModdishUtils.getOS().contains("Linux")) {
			saveAs.add("/natives/libjinput-linux64.so");
			saveAs.add("/natives/libjinput-linux.so");
			saveAs.add("/natives/liblwjgl.so");
			saveAs.add("/natives/liblwjgl64.so");
			saveAs.add("/natives/libopenal.so");
			saveAs.add("/natives/libopenal64.so");
			loadFrom.add("linux/libjinput-linux64.so");
			loadFrom.add("linux/libjinput-linux.so");
			loadFrom.add("linux/liblwjgl.so");
			loadFrom.add("linux/liblwjgl64.so");
			loadFrom.add("linux/libopenal.so");
			loadFrom.add("linux/libopenal64.so");
		}
		if (ModdishUtils.getOS().contains("Solaris")) {
			saveAs.add("/natives/libjinput-linux64.so");
			saveAs.add("/natives/libjinput-linux.so");
			saveAs.add("/natives/liblwjgl.so");
			saveAs.add("/natives/liblwjgl64.so");
			saveAs.add("/natives/libopenal.so");
			saveAs.add("/natives/libopenal64.so");
			loadFrom.add("linux/libjinput-linux64.so");
			loadFrom.add("linux/libjinput-linux.so");
			loadFrom.add("solaris/liblwjgl.so");
			loadFrom.add("solaris/liblwjgl64.so");
			loadFrom.add("solaris/libopenal.so");
			loadFrom.add("solaris/libopenal64.so");
		}
		if (ModdishUtils.getOS().contains("Mac OS X")) {
			saveAs.add("/natives/openal.dylib");
			saveAs.add("/natives/liblwjgl.jnilib");
			saveAs.add("/natives/libjinput-osx.jnilib");
			loadFrom.add("macosx/openal.dylib");
			loadFrom.add("macosx/liblwjgl.jnilib");
			loadFrom.add("macosx/libjinput-osx.jnilib");
		}
		for(int i = 0; i < saveAs.size(); i++) {
			if (!new File(binDir + saveAs.get(i)).exists()) {
				getLWJGLFile(binDir + saveAs.get(i), loadFrom.get(i));
			}
		}
	}
}
