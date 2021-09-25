/**
 * Copyright (C) 2021 nierax
 * This program is free software; you can redistribute it and/or modify it under the terms of 
 * the GNU General Public License as published by the Free Software Foundation; either version 3 
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * 
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, see <http://www.gnu.org/licenses/>. 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.MandelbrotImage;

/**
 * Integration test for the controller to check the correctness of the
 * calculation.
 * 
 * @author nierax
 */
class MandelbrotControllerTest {

	private MandelbrotController cut;
	private MandelbrotAttributesDTO singleCalc;
	private MandelbrotAttributesDTO multiCalc;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		singleCalc = MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-ctrl-test.yaml");
		multiCalc = MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-ctrl-list-test.yaml");
		cut = MandelbrotController.of();
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.MandelbrotController#executeSingleCalculation()}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	void testExecuteMultiCalculationOneCalculation() throws JsonParseException, JsonMappingException, IOException {
		File imageFile = new File(singleCalc.getImage().getImageFilename());
		if (imageFile.exists()) {
			imageFile.delete();
		}
		cut.executeMultiCalculation(singleCalc);

		assertTrue(imageFile.exists());
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.MandelbrotController#executeSingleCalculation()}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	void testExecuteMultiCalculationFollowingCalculation() throws JsonParseException, JsonMappingException, IOException {
		// Get all files in the list and delete them before the test
		List<File> files = new ArrayList<>();
		files.add(new File(multiCalc.getImage().getImageFilename()));
		multiCalc.getFollowing().stream().forEach((p) -> files.add(new File(p.getImage().getImageFilename())));
		files.stream().forEach((f) -> f.delete());

		cut.executeMultiCalculation(multiCalc);

		// Check, whether all files exists
		files.stream().forEach((f) -> assertTrue(f.exists()));
	}

	/**
	 * The method
	 * {@link MandelbrotController#executeMultiCalculation(MandelbrotAttributesDTO)}
	 * needs a file name with every single calculation. Otherwise a
	 * {@link MandelbrotControllerException} is thrown
	 */
	@Test
	void testExecuteMultiCalculationNoFilenameBasisCalculation() {
		multiCalc.getImage().setImageFilename(null);
		assertThrows(MandelbrotControllerException.class, () -> {
			cut.executeMultiCalculation(multiCalc);
		});
	}

	/**
	 * When the method
	 * {@link MandelbrotController#executeSingleCalculation(MandelbrotAttributesDTO)}
	 * is called with a multiple calculation set, an exception is expected.
	 */
	@Test
	void testExecuteSingleCalculationWithMultiCalc() {
		assertThrows(MandelbrotControllerException.class, () -> {
			cut.executeSingleCalculation(multiCalc);
		});
	}

	/**
	 * The method
	 * {@link MandelbrotController#executeSingleCalculation(MandelbrotAttributesDTO)}
	 * must deliver an image with the calculation in it.
	 */
	@Test
	void testExecuteSingleCalculationWithSingleCalc() {
		MandelbrotImage image = cut.executeSingleCalculation(singleCalc);
		assertNotNull(image);
		// Check dimensions of the image. We can't check more here
		assertEquals(459, image.getImage().getWidth());
		assertEquals(800, image.getImage().getHeight());
	}
}
