/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.EventQueue;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;

/**
 * The app, starting the Mandelbrot Swing Application
 * 
 * @author nierax
 *
 */
public class MandelbrotApp {

	/**
	 * 
	 */
	private MandelbrotApp() {
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MandelbrotAttributesDTO model = MandelbrotAttributesDTO.ofDefaults();
					MandelbrotSwingView view = new MandelbrotSwingView();
					MandelbrotUIController ctrl = new MandelbrotUIController(model, view);
					ctrl.initController();
					String laf = UIManager.getSystemLookAndFeelClassName();
					System.out.println("Using system look and feel " + laf);
					UIManager.setLookAndFeel(laf);
					SwingUtilities.updateComponentTreeUI(view.getFrmLexasoftMandelbrotApplication());
					view.getFrmLexasoftMandelbrotApplication().pack();
					view.getFrmLexasoftMandelbrotApplication().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
