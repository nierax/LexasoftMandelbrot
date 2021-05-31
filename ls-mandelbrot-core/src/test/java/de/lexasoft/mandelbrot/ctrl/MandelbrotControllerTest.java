/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.api.MandelbrotRunnerException;

/**
 * Integration test for the controller to check the correctness of the
 * calculation.
 * 
 * @author nierax
 */
class MandelbrotControllerTest {

	private MandelbrotController cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.MandelbrotController#flowCalculation()}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws MandelbrotRunnerException
	 */
	@Test
	void testFlowCalculationOneCalculation()
	    throws JsonParseException, JsonMappingException, IOException, MandelbrotRunnerException {
		CalculationPropertiesDTO propsDTO = CalculationPropertiesDTO.of("src/test/resources/mandelbrot-ctrl-test.yaml");
		File imageFile = new File(propsDTO.getImageFilename());
		if (imageFile.exists()) {
			imageFile.delete();
		}
		cut = MandelbrotController.of(propsDTO);
		cut.flowCalculation();

		assertTrue(imageFile.exists());
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.MandelbrotController#flowCalculation()}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws MandelbrotRunnerException
	 */
	@Test
	void testFlowCalculationFollowingCalculation()
	    throws JsonParseException, JsonMappingException, IOException, MandelbrotRunnerException {
		CalculationPropertiesDTO propsDTO = CalculationPropertiesDTO
		    .of("src/test/resources/mandelbrot-ctrl-list-test.yaml");
		// Get all files in the list and delete them before the test
		List<File> files = new ArrayList<>();
		files.add(new File(propsDTO.getImageFilename()));
		propsDTO.getFollowing().stream().forEach((p) -> files.add(new File(p.getImageFilename())));
		files.stream().forEach((f) -> f.delete());

		cut = MandelbrotController.of(propsDTO);
		cut.flowCalculation();

		// Check, whether all files exists
		files.stream().forEach((f) -> assertTrue(f.exists()));
	}

}
