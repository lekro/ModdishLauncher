package lekro.moddish.launcher;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class ModdishGUI implements ActionListener {
	private JFrame moddishWindow;
	private JButton vanillaButton, moddishButton, otherButton;
	public ModdishGUI() {
		
        try {    
        	for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    System.out.println("Found Nimbus!");
                    break;
                } else {
                	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                	System.out.println("Found something that works :P.");
                }
            }
        } catch (UnsupportedLookAndFeelException e) {
        	System.err.println("Cannot find a Look and Feel for your system! Will attempt to use default!");
        	e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JButton updateButton = new JButton("Check for updates");
		updateButton.addActionListener(this);
		JButton viewButton = new JButton("View Moddish Install Folder");
		viewButton.addActionListener(this);
		JButton importButton = new JButton("Import...");
		importButton.addActionListener(this);
		JButton exportButton = new JButton("Export...");
		exportButton.addActionListener(this);
		JButton editVanilla = new JButton("Edit");
		editVanilla.addActionListener(this);
		ImageIcon vanillaIcon = new ImageIcon(getClass().getResource("/res/Grass.png"));
		vanillaButton = new JButton("<html><h1>Play Vanilla</h1></html>", vanillaIcon);
		vanillaButton.addActionListener(this);
		vanillaButton.setMinimumSize(new Dimension(440,96));
		vanillaButton.setPreferredSize(new Dimension(440,96));
		vanillaButton.setMaximumSize(new Dimension(440,96));
		JButton editModdish = new JButton("Edit");
		editModdish.addActionListener(this);
		ImageIcon moddishIcon = new ImageIcon(getClass().getResource("/res/Oddish.png"));
		moddishButton = new JButton("<html><h1>Play Moddish</h1></html>", moddishIcon);
		moddishButton.addActionListener(this);
		moddishButton.setMinimumSize(new Dimension(440,96));
		moddishButton.setPreferredSize(new Dimension(440,96));
		moddishButton.setMaximumSize(new Dimension(440,96));
		JButton editOther = new JButton("Edit");
		editOther.addActionListener(this);
		ImageIcon otherIcon = new ImageIcon(getClass().getResource("/res/Question.png"));
		otherButton = new JButton("<html><h1>Play Other</h1></html>", otherIcon);
		otherButton.addActionListener(this);
		otherButton.setMinimumSize(new Dimension(440,96));
		otherButton.setPreferredSize(new Dimension(440,96));
		otherButton.setMaximumSize(new Dimension(440,96));
		JButton aboutButton = new JButton("About");
		aboutButton.addActionListener(this);
		JButton helpButton = new JButton("Help");
		helpButton.addActionListener(this);
		JButton creditsButton = new JButton("Credits");
		creditsButton.addActionListener(this);
		JButton modlistButton = new JButton("Mod-list");
		modlistButton.addActionListener(this);
		moddishWindow = new JFrame("Moddish Launcher Version Test 0.0.1");
		moddishWindow.setDefaultCloseOperation(0);
        moddishWindow.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent aWindowEventThatClosesTheWindow) {
        	    ModdishLauncher.quit();
        	}
        });
		JPanel topPanel = new JPanel();
		JPanel vanillaPanel = new JPanel();
		JPanel moddishPanel = new JPanel();
		JPanel otherPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		JPanel allPanel = new JPanel();
		topPanel.add(updateButton);
		topPanel.add(Box.createRigidArea(new Dimension(10,1)));
		topPanel.add(viewButton);
		topPanel.add(Box.createRigidArea(new Dimension(10,1)));
		topPanel.add(importButton);
		topPanel.add(Box.createRigidArea(new Dimension(10,1)));
		topPanel.add(exportButton);
		vanillaPanel.add(editVanilla);
		vanillaPanel.add(vanillaButton);
		moddishPanel.add(editModdish);
		moddishPanel.add(moddishButton);
		otherPanel.add(editOther);
		otherPanel.add(otherButton);
		bottomPanel.add(creditsButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(48,1)));
		bottomPanel.add(helpButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(48,1)));
		bottomPanel.add(modlistButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(48,1)));
		bottomPanel.add(aboutButton);
		allPanel.setLayout(new BoxLayout(allPanel, BoxLayout.PAGE_AXIS));
		allPanel.add(topPanel);
		allPanel.add(vanillaPanel);
		allPanel.add(moddishPanel);
		allPanel.add(otherPanel);
		allPanel.add(bottomPanel);
		moddishWindow.add(allPanel);
		moddishWindow.setSize(560,440);
		moddishWindow.setResizable(false);
		moddishWindow.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(((JButton) e.getSource()).getText());
		
		if (e.getSource().equals(vanillaButton)) {
			String binDir = "minecrafts/vanilla/.minecraft/bin/";
			if (!new File(binDir + "minecraft.jar").exists()) {
				System.out.println("Grabbing minecraft.jar from Mojang, you do not appear to have it!");
				ModdishDownload.getFile("http://assets.minecraft.net/1_4_7/minecraft.jar", binDir + "minecraft.jar");
			}
			if (!new File(binDir + "lwjgl.jar").exists()) {
				System.out.println("Grabbing lwjgl.jar, you do not appear to have it!");
				ModdishDownload.getFile("http://dl.dropbox.com/u/42740061/moddish/lwjgl-2.8.5/jar/lwjgl.jar", binDir + "lwjgl.jar");
			}
			if (!new File(binDir + "lwjgl_util.jar").exists()) {
				System.out.println("Grabbing lwjgl_util.jar, you do not appear to have it!");
				ModdishDownload.getFile("http://dl.dropbox.com/u/42740061/moddish/lwjgl-2.8.5/jar/lwjgl_util.jar", binDir + "lwjgl_util.jar");
			}
			if (!new File(binDir + "jinput.jar").exists()) {
				System.out.println("Grabbing jinput.jar, you do not appear to have it!");
				ModdishDownload.getFile("http://dl.dropbox.com/u/42740061/moddish/lwjgl-2.8.5/jar/jinput.jar", binDir + "jinput.jar");
			}
			if (!new File(binDir + "natives/jinput-dx8_64.dll").exists()) {
				System.out.println("Grabbing jinput.jar, you do not appear to have it!");
				ModdishDownload.getFile("http://dl.dropbox.com/u/42740061/moddish/lwjgl-2.8.5/native/windows/jinput-dx8_64.dll", binDir + "natives/jinput-dx8_64.dll");
			}
			if (!new File(binDir + "natives/jinput-dx8.dll").exists()) {
				System.out.println("Grabbing jinput.jar, you do not appear to have it!");
				ModdishDownload.getFile("http://dl.dropbox.com/u/42740061/moddish/lwjgl-2.8.5/native/windows/jinput-dx8.dll", binDir + "natives/jinput-dx8.dll");
			}
			if (!new File(binDir + "natives/jinput-raw_64.dll").exists()) {
				System.out.println("Grabbing jinput.jar, you do not appear to have it!");
				ModdishDownload.getFile("http://dl.dropbox.com/u/42740061/moddish/lwjgl-2.8.5/native/windows/jinput-raw_64.dll", binDir + "natives/jinput-raw_64.dll");
			}
			if (!new File(binDir + "natives/jinput-raw.dll").exists()) {
				System.out.println("Grabbing jinput.jar, you do not appear to have it!");
				ModdishDownload.getFile("http://dl.dropbox.com/u/42740061/moddish/lwjgl-2.8.5/native/windows/jinput-raw.dll", binDir + "natives/jinput-raw.dll");
			}
			if (!new File(binDir + "natives/lwjgl.dll").exists()) {
				System.out.println("Grabbing jinput.jar, you do not appear to have it!");
				ModdishDownload.getFile("http://dl.dropbox.com/u/42740061/moddish/lwjgl-2.8.5/native/windows/lwjgl.dll", binDir + "natives/lwjgl.dll");
			}
			if (!new File(binDir + "natives/lwjgl64.dll").exists()) {
				System.out.println("Grabbing jinput.jar, you do not appear to have it!");
				ModdishDownload.getFile("http://dl.dropbox.com/u/42740061/moddish/lwjgl-2.8.5/native/windows/lwjgl64.dll", binDir + "natives/lwjgl64.dll");
			}
			if (!new File(binDir + "natives/OpenAL32.dll").exists()) {
				System.out.println("Grabbing jinput.jar, you do not appear to have it!");
				ModdishDownload.getFile("http://dl.dropbox.com/u/42740061/moddish/lwjgl-2.8.5/native/windows/OpenAL32.dll", binDir + "natives/OpenAL32.dll");
			}
			if (!new File(binDir + "natives/OpenAL64.dll").exists()) {
				System.out.println("Grabbing jinput.jar, you do not appear to have it!");
				ModdishDownload.getFile("http://dl.dropbox.com/u/42740061/moddish/lwjgl-2.8.5/native/windows/OpenAL64.dll", binDir + "natives/OpenAL64.dll");
			}
			URL minecraftJar = null, lwjglJar = null, lwjgl_utilJar = null, jinputJar = null;
			try {
				minecraftJar = new URL("jar:file:" + binDir + "minecraft.jar" + "!/");
				lwjglJar = new URL("jar:file:" + binDir + "lwjgl.jar" + "!/");
				lwjgl_utilJar = new URL("jar:file:" + binDir + "lwjgl_util.jar" + "!/");
				jinputJar = new URL("jar:file:" + binDir + "jinput.jar" + "!/");
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}  
			String nativesDir = new File(binDir, "natives").getAbsolutePath().toString();

			System.setProperty("org.lwjgl.librarypath", nativesDir);
			System.setProperty("net.java.games.input.librarypath", nativesDir);
			URL[] urls = new URL[]{minecraftJar, lwjglJar, lwjgl_utilJar, jinputJar};
			ClassLoader cl = new URLClassLoader(urls, ModdishLauncher.class.getClassLoader());
			Class<?> cls = null;
			try {
				cls = cl.loadClass("net.minecraft.client.Minecraft");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Method[] allMethods = cls.getDeclaredMethods();
			
			String[] args2 = {"hi", "hi", "hi"};
			Object[] args = {args2};
		    for (Method m : allMethods) {
		    	String mname = m.getName();
		    	System.out.println(mname);
		    	if (mname.equalsIgnoreCase("main")) {
		    		try {
						m.invoke(this, args);
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						e1.printStackTrace();
					}
		    	}
		    	/*
		    	System.out.format("invoking %s()%n", mname);
		    	try {
		    		m.setAccessible(true);
		    		o = m.invoke(o);
			    	System.out.format("%s() returned %b%n", mname, (Boolean) o);
			    
			    	// Handle any exceptions thrown by method to be invoked.
		    	} catch (InvocationTargetException x) {
		    		Throwable cause = x.getCause();
		    		System.err.format("invocation of %s failed: %s%n",
		    		mname, cause.getMessage());
		    	} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
		    }
		}
	}
}
