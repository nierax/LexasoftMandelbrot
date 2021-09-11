/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.EventQueue;

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
					view.getFrmLexasoftMandelbrotApplication().pack();
					view.getFrmLexasoftMandelbrotApplication().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
