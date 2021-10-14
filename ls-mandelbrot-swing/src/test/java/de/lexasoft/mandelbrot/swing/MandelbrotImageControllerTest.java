/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.api.PaletteVariant;
import de.lexasoft.mandelbrot.swing.model.AspectRatio;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;
import de.lexasoft.mandelbrot.swing.model.ColorControllerModel;

/**
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class MandelbrotImageControllerTest {

	private MandelbrotImageController cut;
	@Mock
	private ImagePanel view;
	@Mock
	private ModelChangedEvent<ColorControllerModel> colorEvent;
	@Mock
	private ModelChangedEvent<CalculationControllerModel> calcEvent;
	@Mock
	private CalculationControllerModel calcModel;
	@Mock
	private ColorControllerModel colorModel;

	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		view = mock(ImagePanel.class);
		calcModel = mock(CalculationControllerModel.class);
		Dimension initialSize = new Dimension(450, 405);
		cut = new MandelbrotImageController(calcModel, colorModel, initialSize, view);
		colorEvent = mock(ModelChangedEvent.class);
		calcEvent = mock(ModelChangedEvent.class);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.MandelbrotImageController#initView()}.
	 */
	@Test
	final void testInitView() {
		// initView() is called within the constructor, so we can check immediately.
		// Is the size set correctly?
		verify(view).setPreferredSize(new Dimension(450, 405));
	}

	private ColorControllerModel createColorControlModel(int nrOfC, PaletteVariant paletteVariant,
	    ColorGradingStyle style) {
		return new ColorControllerModel() {

			@Override
			public int totalNrOfColors() {
				return nrOfC;
			}

			@Override
			public PaletteVariant paletteVariant() {
				return paletteVariant;
			}

			@Override
			public ColorGradingStyle gradingStyle() {
				return style;
			}
		};
	}

	private static Stream<Arguments> testColorModelChanged() {
		return Stream.of(Arguments.of(25, PaletteVariant.BLUEWHITE, ColorGradingStyle.LINE),
		    Arguments.of(35, PaletteVariant.RAINBOW7, ColorGradingStyle.CIRCLE),
		    Arguments.of(500, PaletteVariant.RAINBOW29, ColorGradingStyle.NONE));
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	@ParameterizedTest
	@MethodSource
	final void testColorModelChanged(int nrOfC, PaletteVariant variant, ColorGradingStyle style)
	    throws InterruptedException {
		// Prepare
		when(colorEvent.getModel()).thenReturn(createColorControlModel(nrOfC, variant, style));
		when(view.getWidth()).thenReturn(459);
		when(view.getHeight()).thenReturn(405);
		cut.setCalcModel(createCalculationControllerModel(point(-2.2, 1.2), point(0.8, -1.2), AspectRatio.FILL, 25));

		// Run test
		cut.colorModelChanged(colorEvent);
		Thread.sleep(100); // Wait for the executor to finish

		// Check
		ColorControllerModel color = cut.getColorModel();
		assertEquals(nrOfC, color.totalNrOfColors());
		assertEquals(style, color.gradingStyle());
		assertEquals(variant, color.paletteVariant());
	}

	private CalculationControllerModel createCalculationControllerModel(MandelbrotPointPosition tl,
	    MandelbrotPointPosition br, AspectRatio ar, int maxIter) {
		return new CalculationControllerModel() {

			@Override
			public MandelbrotPointPosition topLeft() {
				return tl;
			}

			@Override
			public int maximumIterations() {
				return maxIter;
			}

			@Override
			public MandelbrotPointPosition bottomRight() {
				return br;
			}

			@Override
			public AspectRatio aspectRatio() {
				return ar;
			}
		};
	}

	/**
	 * To write it shorter.
	 * 
	 * @param cx
	 * @param cy
	 * @return
	 */
	private static MandelbrotPointPosition point(double cx, double cy) {
		return MandelbrotPointPosition.of(cx, cy);
	}

	private static Stream<Arguments> testCalculationModelChanged() {
		return Stream.of(Arguments.of(point(1, -1), point(0.5, -0.5), AspectRatio.FILL, 25, point(0.5, -0.5)),
		    Arguments.of(point(1.2, 0), point(0.8, -0.5), AspectRatio.IGNORE, 50, point(0.8, -0.5)));
	}

	@ParameterizedTest
	@MethodSource
	final void testCalculationModelChanged(MandelbrotPointPosition tl, MandelbrotPointPosition br, AspectRatio ar,
	    int maxIter, MandelbrotPointPosition expBr) throws InterruptedException {
		// Prepare
		calcModel = createCalculationControllerModel(tl, br, ar, maxIter);
		cut.setCalcModel(calcModel);
		when(calcEvent.getModel()).thenReturn(calcModel);
		when(view.getWidth()).thenReturn(459);
		when(view.getHeight()).thenReturn(405);
		when(colorModel.gradingStyle()).thenReturn(ColorGradingStyle.LINE);
		when(colorModel.paletteVariant()).thenReturn(PaletteVariant.BLUEWHITE);
		when(colorModel.totalNrOfColors()).thenReturn(7);

		// Run
		cut.calculationModelChanged(calcEvent);
		Thread.sleep(100); // Wait for the executor to finish

		// Check
		assertEquals(tl, cut.getCalcModel().topLeft());
		assertEquals(expBr, cut.getCalcModel().bottomRight());
		assertEquals(maxIter, cut.getCalcModel().maximumIterations());
	}

}
