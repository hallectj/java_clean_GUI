package gui_example_gridbag;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainGUI app = new MainGUI();
				app.setVisible(true);
				System.out.println("App started");
			}
		});
	}
}
