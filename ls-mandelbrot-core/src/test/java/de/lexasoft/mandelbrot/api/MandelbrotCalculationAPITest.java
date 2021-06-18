package de.lexasoft.mandelbrot.api;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.cu.MandelbrotIterator;

/**
 * Checks the MandelbrotCalculationAPI.
 * 
 * @author admin
 *
 */
@ExtendWith(MockitoExtension.class)
class MandelbrotCalculationAPITest {

	private MandelbrotCalculationProperties model;
	private MandelbrotCalculationAPI cut;
	@Mock
	private MandelbrotIterator iterator;
	@Mock
	private MandelbrotImage image;

	@BeforeEach
	void setUp() throws Exception {
		cut = new MandelbrotCalculationAPI();
		model = new MandelbrotCalculationProperties();
		model.setTopLeft(MandelbrotPointPosition.of(-2.02d, -1.02d));
		model.setBottomRight(MandelbrotPointPosition.of(0.7d, 1.02d));
		model.setMaximumIterations(50);
		model.setImageWidth(459);
		model.setImageHeight(405);
		model.setImageFilename("./junit-tmp/mandelbrot-test.tiff");
		model.setImage(MandelbrotImage.of(model.getImageWidth(), model.getImageHeight(), model.getImageFilename()));
		model.setPaletteVariant(PaletteVariant.CUSTOM);
		List<Color> colors = new ArrayList<>();
		colors.add(Color.BLUE);
		colors.add(Color.WHITE);
		model.setCustomColorPalette(colors);
		model.setColorGrading(MandelbrotColorGrading.of(ColorGradingStyle.LINE, 5));
		model.setMandelbrotColor(Color.YELLOW);
		iterator = mock(MandelbrotIterator.class);
		image = mock(MandelbrotImage.class);
		when(iterator.drawMandelbrot(any(MandelbrotPointPosition.class), any(MandelbrotPointPosition.class), anyInt(),
		    anyInt(), anyInt(), any(MandelbrotImage.class))).thenReturn(image);
	}

	private MandelbrotPointPosition point(double cx, double cy) {
		return MandelbrotPointPosition.of(cx, cy);
	}

	/**
	 * Are all values set correctly to the Iterator?
	 */
	@Test
	final void testCalculate() {
		MandelbrotImage image = cut.calculate(model);
		assertSame(this.image, image);
		// Check, whether all parameters are set correctly.
		verify(iterator.drawMandelbrot(point(-2.02d, -1.02d), point(0.7d, 1.02d), 50, 459, 405, this.image));
	}

}
