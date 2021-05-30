/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.MandelbrotColorGrading;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.api.MandelbrotRunner;
import de.lexasoft.mandelbrot.api.MandelbrotRunnerException;
import de.lexasoft.mandelbrot.api.PaletteVariant;

/**
 * @author nierax
 *
 */
class MandelbrotSingleRunnerTest {

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
		props.setImageFilename("./junit-tmp/mandelbrot-test.tiff");
		props.setPaletteVariant(PaletteVariant.CUSTOM);
		List<Color> colors = new ArrayList<>();
		colors.add(Color.BLUE);
		colors.add(Color.WHITE);
		props.setCustomColorPalette(colors);
		props.setColorGrading(MandelbrotColorGrading.of(ColorGradingStyle.LINE, 5));
		props.setMandelbrotColor(Color.YELLOW);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotSingleRunner#of(de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties)}.
	 */
	@Test
	void testOfOk() {
		MandelbrotSingleRunner cut = MandelbrotSingleRunner.of(props);
		assertNotSame(props.getTopLeft(), cut.getTopLeft(), "Not the same object, just the same values.");
		assertEquals(-2.02d, cut.getTopLeft().cx());
		assertEquals(-1.02d, cut.getTopLeft().cy());
		assertNotSame(props.getTopLeft(), cut.getTopLeft(), "Not the same object, just the same values.");
		assertEquals(0.7d, cut.getBottomRight().cx());
		assertEquals(1.02d, cut.getBottomRight().cy());
		assertEquals(50, cut.getMaxIterations());
		assertEquals(459, cut.getImageWidth());
		assertEquals(405, cut.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-test.tiff", cut.getImageFilename());
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
		props.setPaletteVariant(PaletteVariant.BLACK_WHITE);
		MandelbrotSingleRunner cut = MandelbrotSingleRunner.of(props);
		assertNotNull(cut.getColorize());
		assertTrue(cut.getColorize() instanceof MandelbrotBlackWhite);
	}

	/**
	 * Check for Rainbow in 29 steps
	 */
	@Test
	void testOfRainbow29() {
		props.setPaletteVariant(PaletteVariant.RAINBOW29);
		props.setColorGrading(null);
		MandelbrotSingleRunner cut = MandelbrotSingleRunner.of(props);
		assertNotNull(cut.getColorize());
		assertTrue(cut.getColorize() instanceof MandelbrotColorPalette);
	}

	/**
	 * Check for Rainbow in 7 steps
	 */
	@Test
	void testOfRainbow7() {
		props.setPaletteVariant(PaletteVariant.RAINBOW7);
		props.setColorGrading(null);
		MandelbrotSingleRunner cut = MandelbrotSingleRunner.of(props);
		assertNotNull(cut.getColorize());
		assertTrue(cut.getColorize() instanceof MandelbrotColorPalette);
	}

	/**
	 * Tests the run method, if the filename is not correct. An
	 * {@link MandelbrotRunnerException} is thrown.
	 */
	@Test
	void testRunOutputFileNotWorking() {
		props.setImageFilename("/anything-that-does-not-work/");
		MandelbrotRunner cut = MandelbrotSingleRunner.of(props);
		assertThrows(MandelbrotRunnerException.class, () -> {
			cut.run();
		});
	}

	/**
	 * Tests the run method, if everything is correct.
	 * 
	 * @throws MandelbrotRunnerException
	 */
	@Test
	void testRunOk() throws MandelbrotRunnerException {
		// First check, whether file exists and delete it.
		File file2Write = new File(props.getImageFilename());
		if (file2Write.exists()) {
			file2Write.delete();
		}
		assertFalse(file2Write.exists());
		MandelbrotRunner cut = MandelbrotSingleRunner.of(props);
		cut.run();
		assertTrue(file2Write.exists());
	}

}
