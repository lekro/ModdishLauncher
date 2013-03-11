package lekro.moddish.launcher;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;

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
	private JButton vanillaButton;
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
		JButton vanillaButton = new JButton("<html><h1>Play Vanilla</h1></html>", vanillaIcon);
		vanillaButton.setMinimumSize(new Dimension(440,96));
		vanillaButton.setPreferredSize(new Dimension(440,96));
		vanillaButton.setMaximumSize(new Dimension(440,96));
		JButton editModdish = new JButton("Edit");
		updateButton.addActionListener(this);
		ImageIcon moddishIcon = new ImageIcon(getClass().getResource("/res/Oddish.png"));
		JButton moddishButton = new JButton("<html><h1>Play Moddish</h1></html>", moddishIcon);
		moddishButton.setMinimumSize(new Dimension(440,96));
		moddishButton.setPreferredSize(new Dimension(440,96));
		moddishButton.setMaximumSize(new Dimension(440,96));
		JButton editOther = new JButton("Edit");
		updateButton.addActionListener(this);
		ImageIcon otherIcon = new ImageIcon(getClass().getResource("/res/Question.png"));
		JButton otherButton = new JButton("<html><h1>Play Other</h1></html>", otherIcon);
		otherButton.setMinimumSize(new Dimension(440,96));
		otherButton.setPreferredSize(new Dimension(440,96));
		otherButton.setMaximumSize(new Dimension(440,96));
		JButton aboutButton = new JButton("About");
		updateButton.addActionListener(this);
		JButton helpButton = new JButton("Help");
		updateButton.addActionListener(this);
		JButton creditsButton = new JButton("Credits");
		updateButton.addActionListener(this);
		JButton modlistButton = new JButton("Mod-list");
		updateButton.addActionListener(this);
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
		System.out.println(getClass().getResource("/res/Oddish.png").toString().replace("/res/Oddish.png", ""));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//if (e.getSource().equals(vanillaButton)) {
			//try {
				System.out.println(getClass().getClassLoader().getResource("ModdishGUI").getPath());
				//ModdishDownload.getMinecraft(new URL(this.));
			//} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			//}
		//}
	}
}
