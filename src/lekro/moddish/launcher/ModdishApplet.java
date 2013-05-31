package lekro.moddish.launcher;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

public class ModdishApplet extends Applet implements AppletStub {
	private Applet myApplet;
	private URL myURL;
	private boolean isActive = false;
	private final Map<String, String> parameters;
	public ModdishApplet(Applet applet, URL docBase) {
		parameters = new TreeMap<String, String>();
		this.setLayout(new BorderLayout());
		this.add(applet, "Center");
		this.myApplet = applet;
		this.myURL = docBase;
	}
	public void setParameter(String key, String value) {
		parameters.put(key, value);
	}
	public void replace(Applet applet) {
		this.myApplet = applet;
		applet.setStub(this);
		applet.setSize(getWidth(), getHeight());
		this.setLayout(new BorderLayout());
		this.add(applet, "Center");
		applet.init();
		isActive = true;
		applet.start();
		validate();
	}
	@Override
	public String getParameter(String key) {
		String par = parameters.get(key);
		if (par != null) {
			return par;
		} 
		try {
			return super.getParameter(key);
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public boolean isActive() {
		return isActive;
	}
	@Override
	public void appletResize(int width, int height) {
		myApplet.resize(width, height);
	}
	@Override
	public void resize(int width, int height) {
		myApplet.resize(width, height);
	}
	@Override
	public void resize(Dimension d) {
		myApplet.resize(d);
	}
	@Override
	public void init() {
		if (myApplet != null) {
			myApplet.init();
		}
	}
	@Override
	public void start() {
		myApplet.start();
		isActive = true;
	}
	@Override
	public void stop() {
		myApplet.stop();
		isActive = false;
	}
	public void destroy() {
		myApplet.destroy();
	}
	@Override
	public URL getCodeBase() {
		return myApplet.getCodeBase();
	}
	@Override
	public URL getDocumentBase() {
		return myURL;
	}
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		myApplet.setVisible(b);
	}
	public void update(Graphics g) {}
	public void paint(Graphics g) {}
}
