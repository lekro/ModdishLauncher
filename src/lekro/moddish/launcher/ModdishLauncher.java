package lekro.moddish.launcher;


public class ModdishLauncher implements Runnable {
	
	
	
	public static void quit() {
		System.exit(0);
	}
	public static void main(String args[]) {
		new ModdishGUI();
	}
	@Override
	public void run() {
		new ModdishGUI();
	}
}
