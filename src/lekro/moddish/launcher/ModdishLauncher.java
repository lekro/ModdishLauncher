package lekro.moddish.launcher;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class ModdishLauncher {
	private static JFrame moddishWindow;
	
	public static void initGUI() {
		
        try {    
        	for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    System.out.println("Found Nimbus!");
                    break;
                } else {
                	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                	System.out.println("Found Metal Theme.");
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
		JButton viewButton = new JButton("View Moddish Install Folder");
		JButton importButton = new JButton("Import...");
		JButton exportButton = new JButton("Export...");
		JButton editVanilla = new JButton("Edit");
		ImageIcon vanillaIcon = new ImageIcon("Grass.png");
		JButton vanillaButton = new JButton("<html><h1>Play Vanilla</h1></html>", vanillaIcon);
		vanillaButton.setMinimumSize(new Dimension(440,80));
		vanillaButton.setPreferredSize(new Dimension(440,80));
		vanillaButton.setMaximumSize(new Dimension(440,80));
		JButton editModdish = new JButton("Edit");
		ImageIcon moddishIcon = new ImageIcon("Oddish.png");
		JButton moddishButton = new JButton("<html><h1>Play Moddish</h1></html>", moddishIcon);
		moddishButton.setMinimumSize(new Dimension(440,80));
		moddishButton.setPreferredSize(new Dimension(440,80));
		moddishButton.setMaximumSize(new Dimension(440,80));
		JButton editOther = new JButton("Edit");
		ImageIcon otherIcon = new ImageIcon("Question.png");
		JButton otherButton = new JButton("<html><h1>Play Other</h1></html>", otherIcon);
		otherButton.setMinimumSize(new Dimension(440,80));
		otherButton.setPreferredSize(new Dimension(440,80));
		otherButton.setMaximumSize(new Dimension(440,80));
		moddishWindow = new JFrame("Moddish Launcher Version Test 0.0.1");
		moddishWindow.setDefaultCloseOperation(0);
        moddishWindow.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent aWindowEventThatClosesTheWindow) {
        	    quit();
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
		allPanel.setLayout(new BoxLayout(allPanel, BoxLayout.PAGE_AXIS));
		allPanel.add(topPanel);
		allPanel.add(vanillaPanel);
		allPanel.add(moddishPanel);
		allPanel.add(otherPanel);
		moddishWindow.add(allPanel);
		moddishWindow.setSize(560,440);
		moddishWindow.setResizable(false);
		moddishWindow.setVisible(true);
	}
	public static void quit() {
		System.exit(0);
	}
	public static void main(String args[]) {
		initGUI();
	}
}
