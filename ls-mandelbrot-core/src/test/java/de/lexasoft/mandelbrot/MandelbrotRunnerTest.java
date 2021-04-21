/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author nierax
 *
 */
class MandelbrotRunnerTest {

	private MandelbrotCalculationProperties props;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		props = new MandelbrotCalculationProperties();
		props.setTopLeft(MandelbrotPointPosition.of(-2.02d, -1.02d));
		props.setBottomRight(MandelbrotPointPosition.of(0.7d, 1.02d));
		props.setMaximumIterations(50);
		props.setImageWidth(459);
		props.setImageHeight(405);
		props.setImageFilename("C:/Users/axeln/Pictures/mandelbrot-test.tiff");
		props.setColorVariant(ColorVariant.GRADIENT2);
		List<Color> colors = new ArrayList<>();
		colors.add(Color.BLUE);
		colors.add(Color.WHITE);
		props.setColors(colors);
		props.setColorInterval(5);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotRunner#of(de.lexasoft.mandelbrot.MandelbrotCalculationProperties)}.
	 */
	@Test
	void testOfOk() {
		MandelbrotRunner cut = MandelbrotRunner.of(props);
		assertNotSame(props.getTopLeft(), cut.getTopLeft(), "Not the same object, just the same values.");
		assertEquals(-2.02d, cut.getTopLeft().cx());
		assertEquals(-1.02d, cut.getTopLeft().cy());
		assertNotSame(props.getTopLeft(), cut.getTopLeft(), "Not the same object, just the same values.");
		assertEquals(0.7d, cut.getBottomRight().cx());
		assertEquals(1.02d, cut.getBottomRight().cy());
		assertEquals(50, cut.getMaximumIterations());
		assertEquals(459, cut.getImageWidth());
		assertEquals(405, cut.getImageHeight());
		assertEquals("C:/Users/axeln/Pictures/mandelbrot-test.tiff", cut.getImageFilename());
		assertNotNull(cut.getColorize());
		assertTrue(cut.getColorize() instanceof MandelbrotColorPalette);
		List<Color> palette = ((MandelbrotColorPalette) cut.getColorize()).getPalette();
		assertNotNull(palette);
		assertEquals(5, palette.size());
		assertEquals(Color.BLUE.getRed(), palette.get(0).getRed());
		assertEquals(Color.BLUE.getGreen(), palette.get(0).getGreen());
		assertEquals(Color.BLUE.getBlue(), palette.get(0).getBlue());
		assertEquals(Color.WHITE.getRed(), palette.get(4).getRed());
		assertEquals(Color.WHITE.getGreen(), palette.get(4).getGreen());
		assertEquals(Color.WHITE.getBlue(), palette.get(4).getBlue());
	}

	/**
	 * Check for Black and White
	 */
	@Test
	void testOfBlackAndWhite() {
		props.setColorVariant(ColorVariant.BLACK_WHITE);
		MandelbrotRunner cut = MandelbrotRunner.of(props);
		assertNotNull(cut.getColorize());
		assertTrue(cut.getColorize() instanceof MandelbrotBlackWhite);
	}

	/**
	 * Check for Rainbow in 29 steps
	 */
	@Test
	void testOfRainbow29() {
		props.setColorVariant(ColorVariant.RAINBOW29);
		MandelbrotRunner cut = MandelbrotRunner.of(props);
		assertNotNull(cut.getColorize());
		assertTrue(cut.getColorize() instanceof MandelbrotColorPalette);
	}

	/**
	 * Check for Color grading with three colors
	 */
	@Test
	void testOfColorGrading3() {
		props.setColorVariant(ColorVariant.GRADIENT3);
		props.getColors().add(Color.BLUE);
		props.setColorInterval(9);
		MandelbrotRunner cut = MandelbrotRunner.of(props);
		assertNotNull(cut.getColorize());
		assertTrue(cut.getColorize() instanceof MandelbrotColorPalette);
		List<Color> palette = ((MandelbrotColorPalette) cut.getColorize()).getPalette();
		assertEquals(9, palette.size());
	}

}
