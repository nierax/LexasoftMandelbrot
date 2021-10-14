package de.lexasoft.mandelbrot.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.MandelbrotImage;

/**
 * Checks the MandelbrotCalculationAPI.
 * 
 * @author nierax
 *
 */
class MandelbrotCalculationAPITest {

	private MandelbrotCalculationProperties model;
	private MandelbrotCalculationAPI cut;

	@BeforeEach
	void setUp() throws Exception {
		cut = new MandelbrotCalculationAPI();
		model = new MandelbrotCalculationProperties();
		model.setCalculation(
		    CalculationArea.of(MandelbrotPointPosition.of(-2.02d, -1.02d), MandelbrotPointPosition.of(0.7d, 1.02d)));
		model.setMaximumIterations(50);
		model.setImage(ImageArea.of(459, 405));
		model.setImageFilename("./junit-tmp/mandelbrot-test.tiff");
		model.setPaletteVariant(PaletteVariant.CUSTOM);
		List<Color> colors = new ArrayList<>();
		colors.add(Color.ORANGE);
		colors.add(Color.WHITE);
		model.setCustomColorPalette(colors);
		model.setColorGrading(MandelbrotColorGrading.of(ColorGradingStyle.CIRCLE, 10));
		model.setMandelbrotColor(Color.DARK_GRAY);
	}

	/**
	 * Are all values set correctly to the Iterator?
	 * 
	 * @throws IOException
	 */
	@Test
	final void testCalculate() throws IOException {
		MandelbrotImage image = cut.calculate(model);
		assertNotNull(image);
		assertEquals(459, image.getImage().getWidth());
		assertEquals(405, image.getImage().getHeight());
		image.writeToFile("./junit-tmp/calculationapi-test.png");
	}

}
