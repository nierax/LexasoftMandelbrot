/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.api.AspectRatioHandle;
import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.PaletteVariant;
import de.lexasoft.mandelbrot.api.TransitionVariant;

/**
 * @author nierax
 *
 */
class MandelbrotAttributesDTOTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.CalculationPropertiesDTO#of(java.lang.String)}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	void testOfSingle() throws JsonParseException, JsonMappingException, IOException {
		MandelbrotAttributesDTO cut = MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-dto-test.yaml");
		assertValuesSingleCalculation(cut);
	}

	/**
	 * @param cut
	 */
	private void assertValuesSingleCalculation(MandelbrotAttributesDTO cut) {
		// Calculation
		assertEquals(-2.02, cut.getCalculation().getTopLeft().cx());
		assertEquals(1.2, cut.getCalculation().getTopLeft().cy());
		assertEquals(0.7, cut.getCalculation().getBottomRight().cx());
		assertEquals(-1.2, cut.getCalculation().getBottomRight().cy());
		assertEquals(500, cut.getCalculation().getMaximumIterations());
		// Image
		assertEquals(4590, cut.getImage().getImageWidth());
		assertEquals(4050, cut.getImage().getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-test.tiff", cut.getImage().getImageFilename());
		assertEquals(AspectRatioHandle.FITIN, cut.getImage().getAspectRatioHandle());

		// Color
		assertSame(PaletteVariant.CUSTOM, cut.getColor().getPaletteVariant());
		assertEquals(2, cut.getColor().getCustomColorPalette().size());

		assertEquals(25, cut.getColor().getCustomColorPalette().get(0).getColor().getRed());
		assertEquals(140, cut.getColor().getCustomColorPalette().get(0).getColor().getGreen());
		assertEquals(255, cut.getColor().getCustomColorPalette().get(0).getColor().getBlue());

		assertEquals(255, cut.getColor().getCustomColorPalette().get(1).getColor().getRed());
		assertEquals(255, cut.getColor().getCustomColorPalette().get(1).getColor().getGreen());
		assertEquals(255, cut.getColor().getCustomColorPalette().get(1).getColor().getBlue());

		assertEquals(ColorGradingStyle.LINE, cut.getColor().getColorGrading().getStyle());
		assertEquals(5, cut.getColor().getColorGrading().getColorsTotal());

		assertEquals(Color.BLACK.getRGB(), cut.getColor().getMandelbrotColor().getColor().getRGB());

		// Transitions (following)
		assertNull(cut.getFollowing()); // Not given in this file
	}

	/**
	 * Tests to read a yaml file with transitions in it.
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	void testOfTransitions() throws JsonParseException, JsonMappingException, IOException {
		MandelbrotAttributesDTO cut = MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-dto-transition-test.yaml");
		// We do not care about the single calculations's attributes
		// Check the transitions
		List<TransitionAttributesDTO> following = cut.getFollowing();
		assertNotNull(following);
		assertEquals(1, following.size());
		assertEquals(2, following.get(0).getTransition().steps());
		assertEquals(TransitionVariant.LINEAR, following.get(0).getTransition().variant());
		// Calculation
		CalculationAttributesDTO calculation = following.get(0).getCalculation();
		assertEquals(-1.12, calculation.getTopLeft().cx());
		assertEquals(0.3, calculation.getTopLeft().cy());
		assertEquals(-0.2, calculation.getBottomRight().cx());
		assertEquals(-0.3, calculation.getBottomRight().cy());
		// Other attributes not set, should be null
		assertNull(following.get(0).getColor());
		assertNull(following.get(0).getImage());
	}

	/**
	 * Does the ofDefault() method create all sub objects?
	 */
	@Test
	void testOfDefaults() {
		MandelbrotAttributesDTO cut = MandelbrotAttributesDTO.ofDefaults();
		assertNotNull(cut);
		assertNotNull(cut.getCalculation());
		assertNotNull(cut.getImage());
		assertNotNull(cut.getColor());
		// Following should not be used by default.
		assertNull(cut.getFollowing());
	}

	/**
	 * Is the content of the model written to a yaml file correctly?
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	void testWriteToYamlFile() throws JsonParseException, JsonMappingException, IOException {
		String fileName2Write2 = "./junit-tmp/MandelbrotAttributesDTOTest-01.yaml";
		File file2Write2 = new File(fileName2Write2);
		if (file2Write2.exists()) {
			file2Write2.delete();
		}
		MandelbrotAttributesDTO cut = MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-dto-test.yaml");
		cut.writeToYamlFile(file2Write2);
		assertTrue(file2Write2.exists(), "Yaml file was not created correctly.");

		// Now read the attributes into another model object.
		MandelbrotAttributesDTO read = MandelbrotAttributesDTO.of(file2Write2);
		// Compare values. Should all be the same.
		assertValuesSingleCalculation(read);
	}

}
