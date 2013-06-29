package lekro.moddish.launcher;

import java.applet.Applet;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JOptionPane;

public class ModdishLogin {
	public static boolean doLogin(String username, String password, String minecraftType) {
		URL loginURL = null;
		String response = null;
		try {
			loginURL = new URL("http://login.minecraft.net/?user=" + username + "&password=" + password + "&version=13");
		} catch (MalformedURLException e) {}
		try {
			response = new BufferedReader(new InputStreamReader(loginURL.openStream())).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Response from login server: "+response);
		
		String[] loginInfo = response.split(":");
		if (loginInfo.length == 5) {
			System.out.println("Username: "+loginInfo[2]+", Session ID: "+loginInfo[3]);
			startGame(loginInfo[2], loginInfo[3], minecraftType);
			return true;
		}
		else {
			JOptionPane.showMessageDialog(ModdishGUI.dialog, response, "Login error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	public static void startGame(String username, String sessionid, String minecraftType) {
		String myDir = "" + System.getProperty("user.dir");
		char[] old = myDir.toCharArray();
		char[] dirchar = new char[old.length];
		for (int i = 0; i < old.length; i++) {
			if (old[i] == '\\') {
				dirchar[i] = '/';
			} else {
				dirchar[i] = old[i];
			}
		}
		myDir = new String(dirchar);
		System.out.println(myDir);
		String binDir = myDir + "/minecrafts/"+minecraftType+"/bin/";
		if (!new File(binDir + "minecraft.jar").exists()) {
			System.out.println("Grabbing minecraft.jar from Mojang, you do not appear to have it!");
			ModdishDownload.getFile("http://assets.minecraft.net/1_5_2/minecraft.jar", binDir + "minecraft.jar");
		}
		ModdishDownload.getLWJGL(binDir);
		if (minecraftType.equals("moddish")) {
			ModdishModsInstaller.installJarMod("http://files.minecraftforge.net/minecraftforge/minecraftforge-universal-1.5.2-7.8.1.737.zip", binDir);
		}
		String nativesDir = binDir + "natives/";
		String[] lwjglJars = new String[] { "lwjgl.jar", "lwjgl_util.jar",
				"jinput.jar" };
		URL[] urls = new URL[4];
		try {
			File f = new File(binDir, "minecraft.jar");
			urls[0] = f.toURI().toURL();

			for (int i = 1; i < urls.length; i++) {
				File jar = new File(binDir, lwjglJars[i - 1]);
				urls[i] = jar.toURI().toURL();
			}
		} catch (MalformedURLException e) {
			System.err.println("MalformedURLException, " + e.toString());
			System.exit(5);
		}
		System.setProperty("org.lwjgl.librarypath", nativesDir);
		System.setProperty("net.java.games.input.librarypath", nativesDir);
		System.setProperty("minecraft.applet.TargetDirectory", myDir + "/minecrafts/" + minecraftType + "/");
		System.out.println(myDir + "/minecrafts/"+minecraftType);
		URLClassLoader cl = new URLClassLoader(urls, ModdishLogin.class.getClassLoader());
		Class<?> mc = null;
		//																								Thanks to Forkk and Orochimarufan for helping me with getting Minecraft to target a different directory :D
		try {
			mc = cl.loadClass("net.minecraft.client.Minecraft");
		} catch (ClassNotFoundException e2) {
			System.err.println("Couldn't find Minecraft main class!");
			e2.printStackTrace();
		}
		Field[] fields = mc.getDeclaredFields();
		Field mcPathField = null;
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			if (f.getType() != File.class) {
				continue;
			}
			if (f.getModifiers() != (Modifier.PRIVATE + Modifier.STATIC)) {
				continue;
			}
			mcPathField = f;
			break;
		}
		mcPathField.setAccessible(true);
		try {
			mcPathField.set(null, new File(myDir + "/minecrafts/" + minecraftType + "/"));
		} catch (IllegalArgumentException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		Class<?> cls = null;
		try {
			cls = cl.loadClass("net.minecraft.client.MinecraftApplet");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Applet applet = (Applet) cls.newInstance();
			ModdishFrame myWindow = new ModdishFrame("Moddish Modpack");
			myWindow.startMe(applet, username, sessionid, new Dimension(854, 480), false);
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
}
