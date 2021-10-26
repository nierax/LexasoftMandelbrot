/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;

/**
 * The app, starting the Mandelbrot Swing Application
 * 
 * @author nierax
 *
 */
public class MandelbrotApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(MandelbrotApp.class);

	private static final void initializeLogging() {
		InputStream stream = MandelbrotApp.class.getClassLoader().getResourceAsStream("logging.properties");
		initializeLogging(stream);
	}

	/**
	 * @param stream
	 */
	private static void initializeLogging(InputStream stream) {
		try {
			LogManager.getLogManager().readConfiguration(stream);
		} catch (SecurityException | IOException e) {
			LOGGER.error("Could not initialize logger", e);
		}
	}

	private static void initializeLogging(String[] args) {
		if (args.length == 0) {
			initializeLogging();
		} else {
			try {
				initializeLogging(new FileInputStream(args[0]));
			} catch (FileNotFoundException e) {
				initializeLogging();
				LOGGER.error(String.format("Did not find the file provided %s", args[0]), e);
			}
		}
	}

	/**
	 * 
	 */
	private MandelbrotApp() {
	}

	/**
	 * Starts the application.
	 * <p>
	 * Arguments are used as follows:
	 * <ol>
	 * <li>alternative logging properties (as used in java.util.logging)</li>
	 * </ol>
	 * 
	 * @param args Use of arguments described above.
	 */
	public static void main(String[] args) {
		initializeLogging(args);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MandelbrotAttributesDTO model = MandelbrotAttributesDTO.ofDefaults();
					MandelbrotSwingView view = new MandelbrotSwingView();
					MandelbrotUIController ctrl = new MandelbrotUIController(model, view);
					ctrl.initController();
					String laf = UIManager.getSystemLookAndFeelClassName();
					LOGGER.info("Using system look and feel " + laf);
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
