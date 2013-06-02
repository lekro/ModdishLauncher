package lekro.moddish.launcher;

import java.applet.Applet;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ModdishLogin {
	public static void doLogin(String username, String password) {
		URL loginURL = null;
		String response = null;
		try {
			loginURL = new URL("http://login.minecraft.net/?user=" + username + "&password=" + password + "&version=13");
		} catch (MalformedURLException e) {}
		try {
			InputStream is = loginURL.openStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			response = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Response from login server: "+response);
		String[] loginInfo = response.split(":");
		startGame(loginInfo[2], loginInfo[3]);
	}
	public static void startGame(String username, String sessionid) {
		String myDir = System.getProperty("user.dir");
		String binDir = "minecrafts/vanilla/bin/";
		if (!new File(binDir + "minecraft.jar").exists()) {
			System.out.println("Grabbing minecraft.jar from Mojang, you do not appear to have it!");
			ModdishDownload.getFile("http://assets.minecraft.net/1_5_2/minecraft.jar", binDir + "minecraft.jar");
		}
		ModdishDownload.getLWJGL(binDir);
		URL minecraftJar = null, lwjglJar = null, lwjgl_utilJar = null, jinputJar = null;
		try {
			minecraftJar = new URL("jar:file:" + binDir + "minecraft.jar" + "!/");
			lwjglJar = new URL("jar:file:" + binDir + "lwjgl.jar" + "!/");
			lwjgl_utilJar = new URL("jar:file:" + binDir + "lwjgl_util.jar" + "!/");
			jinputJar = new URL("jar:file:" + binDir + "jinput.jar" + "!/");
		} catch (MalformedURLException e2) {}  
		String nativesDir = new File(binDir, "natives").getAbsolutePath();

		System.setProperty("org.lwjgl.librarypath", nativesDir);
		System.setProperty("net.java.games.input.librarypath", nativesDir);
		System.setProperty("minecraft.applet.TargetDirectory", myDir + "/minecrafts/vanilla");
		URL[] urls = new URL[]{minecraftJar, lwjglJar, lwjgl_utilJar, jinputJar};
		ClassLoader cl = new URLClassLoader(urls, ModdishLauncher.class.getClassLoader());
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
			mcPathField.set(null, new File(myDir + "/minecrafts/vanilla/"));
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
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
}
