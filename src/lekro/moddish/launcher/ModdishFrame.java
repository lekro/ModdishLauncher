package lekro.moddish.launcher;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("serial")
public class ModdishFrame extends Frame implements WindowListener {
	private ModdishApplet myApplet = null;
	public ModdishFrame(String title) {
		super(title);
		this.addWindowListener(this);
	}
	public void startMe(Applet applet, String user, String session, Dimension size, boolean maximize) {
		try {
			myApplet = new ModdishApplet(applet, new URL("http://www.minecraft.net/game"));
		} catch (MalformedURLException e) {}
		myApplet.setParameter("username", user);
		myApplet.setParameter("se  ssionid", session);
		myApplet.setParameter("stand-alone", "true");
		applet.setStub(myApplet);
		this.add(myApplet);
		myApplet.setPreferredSize(size);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		if (maximize) {
			this.setExtendedState(MAXIMIZED_BOTH);
		}
		validate();
		myApplet.init();
		myApplet.start();
		setVisible(true);
	}
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(30000L);
				} catch (InterruptedException e)	{
					e.printStackTrace();
				}
			System.exit(0);
			}
		}.start();

		if (myApplet != null) {
		myApplet.stop();
		myApplet.destroy();
		}
		System.exit(0);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
}
