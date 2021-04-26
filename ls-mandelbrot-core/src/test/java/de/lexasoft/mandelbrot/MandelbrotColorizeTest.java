/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests, whether the factory method in the interface instantiates correctly.
 * 
 * @author nierax
 *
 */
class MandelbrotColorizeTest {

	private List<Color> colors;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		colors = new ArrayList<>();
		colors.add(Color.BLUE);
		colors.add(Color.WHITE);
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotColorize#of()} with
	 * color variant black and white.
	 * <p>
	 * In this case both list of colors and color interval are not needed.
	 */
	@Test
	void testOfBlackAndWhite() {
		MandelbrotColorize cut = MandelbrotColorize.of(PaletteVariant.BLACK_WHITE, null, 0);
		assertNotNull(cut);
		assertTrue(cut instanceof MandelbrotBlackWhite);
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotColorize#of()} with
	 * color variant rainbow in 29 steps.
	 * <p>
	 * The grading is 0, which means, that the original RAINBOW29 list is expected.
	 */
	@Test
	void testOfRainbow29() {
		MandelbrotColorize cut = MandelbrotColorize.of(PaletteVariant.RAINBOW29, null, 0);
		assertNotNull(cut);
		assertTrue(cut instanceof MandelbrotColorPalette);
		List<Color> palette = ((MandelbrotColorPalette) cut).getPalette();
		assertNotNull(palette);
		// Check, whether this is the right palette.
		assertEquals(29, palette.size());
		assertEquals(128, palette.get(0).getRed());
		assertEquals(0, palette.get(0).getGreen());
		assertEquals(0, palette.get(0).getBlue());
		assertEquals(168, palette.get(28).getRed());
		assertEquals(0, palette.get(28).getGreen());
		assertEquals(185, palette.get(28).getBlue());
		assertEquals(29, palette.size());
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotColorize#of()} with
	 * color variant rainbow in 29 steps and a grading of 57 (minimum value).
	 * <p>
	 * A custom (ungraded) palette is not needed, as RAINBOW29 is a predefined list.
	 */
	@Test
	void testOfRainbow29Graded() {
		MandelbrotColorize cut = MandelbrotColorize.of(PaletteVariant.RAINBOW29, null, 57);
		assertNotNull(cut);
		assertTrue(cut instanceof MandelbrotColorPalette);
		List<Color> palette = ((MandelbrotColorPalette) cut).getPalette();
		assertNotNull(palette);
		// Check, whether this is the right palette.
		assertEquals(57, palette.size());
		assertEquals(128, palette.get(0).getRed());
		assertEquals(0, palette.get(0).getGreen());
		assertEquals(0, palette.get(0).getBlue());
		assertEquals(168, palette.get(56).getRed());
		assertEquals(0, palette.get(56).getGreen());
		assertEquals(185, palette.get(56).getBlue());
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotColorize#of()} with
	 * color variant color grading between 2 colors.
	 * <p>
	 * In this case the list must contain the two colors, between the colors are
	 * grading.
	 */
	@Test
	void testOfGrading2() {
		MandelbrotColorize cut = MandelbrotColorize.of(PaletteVariant.GRADIENT2, colors, 5);
		assertNotNull(cut);
		assertTrue(cut instanceof MandelbrotColorPalette);
		List<Color> palette = ((MandelbrotColorPalette) cut).getPalette();
		assertEquals(5, palette.size());
		assertEquals(Color.BLUE.getRed(), palette.get(0).getRed());
		assertEquals(Color.BLUE.getGreen(), palette.get(0).getGreen());
		assertEquals(Color.BLUE.getBlue(), palette.get(0).getBlue());
		assertEquals(Color.WHITE.getRed(), palette.get(4).getRed());
		assertEquals(Color.WHITE.getGreen(), palette.get(4).getGreen());
		assertEquals(Color.WHITE.getBlue(), palette.get(4).getBlue());
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotColorize#of()} with
	 * color variant color grading between 3 colors.
	 * <p>
	 * In this case the list must contain the 3 colors, between the colors are
	 * grading.
	 */
	@Test
	void testOfGrading3() {
		colors.add(Color.GREEN);
		MandelbrotColorize cut = MandelbrotColorize.of(PaletteVariant.GRADIENT3, colors, 9);
		assertNotNull(cut);
		assertTrue(cut instanceof MandelbrotColorPalette);
		List<Color> palette = ((MandelbrotColorPalette) cut).getPalette();
		assertEquals(9, palette.size());
		assertEquals(Color.BLUE.getRed(), palette.get(0).getRed());
		assertEquals(Color.BLUE.getGreen(), palette.get(0).getGreen());
		assertEquals(Color.BLUE.getBlue(), palette.get(0).getBlue());
		assertEquals(Color.GREEN.getRed(), palette.get(8).getRed());
		assertEquals(Color.GREEN.getGreen(), palette.get(8).getGreen());
		assertEquals(Color.GREEN.getBlue(), palette.get(8).getBlue());
	}

	/**
	 * Not enough colors given for gradient 2.
	 */
	@Test
	void testOfGradient2ColorMissing() {
		// New empty list -> No colors defined
		List<Color> colors = new ArrayList<>();
		assertThrows(IllegalArgumentException.class, () -> {
			MandelbrotColorize.of(PaletteVariant.GRADIENT2, colors, 5);
		});
		// Adding just one color is not enough
		colors.add(Color.BLUE);
		assertThrows(IllegalArgumentException.class, () -> {
			MandelbrotColorize.of(PaletteVariant.GRADIENT2, colors, 5);
		});
	}

	/**
	 * Not enough colors given for gradient 3.
	 */
	@Test
	void testOfGradient3ColorMissing() {
		// New empty list -> No colors defined
		List<Color> colors = new ArrayList<>();
		assertThrows(IllegalArgumentException.class, () -> {
			MandelbrotColorize.of(PaletteVariant.GRADIENT3, colors, 9);
		});
		// Adding just one color is not enough
		colors.add(Color.BLUE);
		assertThrows(IllegalArgumentException.class, () -> {
			MandelbrotColorize.of(PaletteVariant.GRADIENT3, colors, 9);
		});
		// Adding a second color is not enough, as well
		colors.add(Color.GREEN);
		assertThrows(IllegalArgumentException.class, () -> {
			MandelbrotColorize.of(PaletteVariant.GRADIENT3, colors, 9);
		});
	}

}
