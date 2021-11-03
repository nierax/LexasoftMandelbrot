/**
 * 
 */
package de.lexasoft.mandelbrot.cli;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotController;
import de.lexasoft.mandelbrot.ctrl.MandelbrotControllerException;

/**
 * CLI application for MandelbrotIterator calculation.
 * <p>
 * Call with a yaml file, containing the attributes for a MandelbrotIterator
 * calculation
 * 
 * @author nierax
 *
 */
public class MandelbrotCLI {

	private static final Logger LOGGER = LoggerFactory.getLogger(MandelbrotCLI.class);

	static {
		InputStream stream = MandelbrotCLI.class.getClassLoader().getResourceAsStream("logging-cli.properties");
		try {
			LogManager.getLogManager().readConfiguration(stream);
		} catch (SecurityException | IOException e) {
			LOGGER.error("Could not initialize logger", e);
		}
	}

	private void doRun(String yamlFilename) throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("Starting to calculate...");
		MandelbrotAttributesDTO propDTO = MandelbrotAttributesDTO.of(yamlFilename);
		MandelbrotController.of().executeMultiCalculation(propDTO);
		LOGGER.info("Done.");
	}

	/**
	 * Main method for the CLI application.
	 * 
	 * @param args Array of arguments. The first argument (args[0]) is used to read
	 *             the file name to a yaml file, containing the calculation
	 *             properties.
	 */
	public static void main(String[] args) {
		MandelbrotCLI cli = new MandelbrotCLI();
		if ((args == null) || args.length < 1) {
			LOGGER.error("File name to yaml file must be given.");
			System.exit(99);
		}
		try {
			cli.doRun(args[0]);
			System.exit(0);
		} catch (IOException e) {
			LOGGER.error("Unknown IO error occured. Check console for details. (" + e.getLocalizedMessage() + ")", e);
		} catch (MandelbrotControllerException e) {
			LOGGER.error("Could not run the calculation. Check console for details. (" + e.getLocalizedMessage() + ")", e);
		}
		System.exit(99);
	}

}
