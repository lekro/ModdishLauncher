package lekro.moddish.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ModdishModsInstaller {
	public static void installJarMod(String modPath, String binDir) {
		String localMod = null;
		File minecraftJar = new File(binDir + "/minecraft.jar");
		ZipFile jarZip = null;
		try {
			jarZip = new ZipFile(minecraftJar);
			
		} catch (ZipException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// TODO Add support for other mods, e.g. Rei's Minimap and Optifine, they will have to be installed manually for now.
		if (jarZip.getEntry("MinecraftForge-Credits.txt") != null) {
			System.out.println("Forge is already installed!");
		} else {
		try {
			localMod = "temp/" + new URL(modPath).getFile();
		} catch (MalformedURLException e1) {}
		System.out.println("Installing...");
		ModdishDownload.getFile(modPath, localMod);
		
		try {
			File tempFile = File.createTempFile(minecraftJar.getName(), null);
			tempFile.delete();
			minecraftJar.renameTo(tempFile);
			ZipInputStream zisMod = new ZipInputStream(new FileInputStream(localMod));
			ZipInputStream zisMC = new ZipInputStream(new FileInputStream(tempFile));
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(minecraftJar));
			ZipFile modZip = new ZipFile(localMod);
			ZipEntry ze = zisMod.getNextEntry();
			while (ze != null) {
				while (ze.getName().contains("META-INF")) {
					ze = zisMod.getNextEntry();
				}
				System.out.println("Copying... "+ze.getName());
				zos.putNextEntry(ze);
				byte[] buffer = new byte[1024];
				for (int read = zisMod.read(buffer); read != -1; read = zisMod.read(buffer)) {
					zos.write(buffer, 0, read);
				}
				
				zos.closeEntry();
				ze = zisMod.getNextEntry();
			}
			zisMod.close();
			ze = zisMC.getNextEntry();
			while (ze != null) {
				while (ze.getName().contains("META-INF")) {
					ze = zisMC.getNextEntry();
				}
				while (modZip.getEntry(ze.getName()) != null) {
					ze = zisMC.getNextEntry();
				}
				System.out.println("Copying... "+ze.getName());
				
				zos.putNextEntry(ze);
				byte[] buffer = new byte[1024];
				for (int read = zisMC.read(buffer); read != -1; read = zisMC.read(buffer)) {
					zos.write(buffer, 0, read);
				}
				
				zos.closeEntry();
				ze = zisMC.getNextEntry();
			}
			zos.close();
			zisMC.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} 
	}
}
