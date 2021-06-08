/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.EventQueue;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

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
					MandelbrotCalculationProperties model = MandelbrotCalculationProperties.ofDefault();
					MandelbrotSwingView view = new MandelbrotSwingView(model);
					MandelbrotController ctrl = new MandelbrotController(model, view);
					ctrl.initController();
					view.getFrmLexasoftMandelbrotApplication().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}