/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Color;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.PaletteVariant;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * @author nierax
 *
 */
class TransitionDTO2PropertiesMapperTest {

	private TransitionDTO2PropertiesMapper cut;
	private CalculationPropertiesDTO dtoTrans;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		dtoTrans = CalculationPropertiesDTO.of("src/test/resources/mandelbrot-test-transition-0.yaml");
		cut = TransitionDTO2PropertiesMapper.of(dtoTrans);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.AbstractDTO2PropertiesMapper#mapDTO2Properties()}.
	 */
	@Test
	void testMapDTO2Properties() {
		List<MandelbrotCalculationProperties> listOfProps = cut.mapDTO2Properties();
		assertNotNull(listOfProps);
		assertEquals(4, listOfProps.size());

		// First entry with complete properties
		MandelbrotCalculationProperties props = listOfProps.get(0);
		assertNotNull(props);
		assertEquals(-2.02d, props.getTopLeft().cx());
		assertEquals(1.2d, props.getTopLeft().cy());
		assertEquals(0.7d, props.getBottomRight().cx());
		assertEquals(-1.2d, props.getBottomRight().cy());
		assertEquals(10, props.getMaximumIterations());
		assertEquals(459, props.getImageWidth());
		assertEquals(405, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-ctrl-trans-test-01_01.tiff", props.getImageFilename());
		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading());
		assertEquals(Color.BLACK, props.getMandelbrotColor());

		// Second entry with first transition
		props = listOfProps.get(1);
		assertNotNull(props);
		assertEquals(-1.72d, props.getTopLeft().cx(), 0.001);
		assertEquals(0.9d, props.getTopLeft().cy(), 0.001);
		assertEquals(0.4d, props.getBottomRight().cx(), 0.001);
		assertEquals(-0.9d, props.getBottomRight().cy(), 0.001);
		assertEquals(20, props.getMaximumIterations(), 0.001);
		assertEquals(459, props.getImageWidth());
		assertEquals(405, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-ctrl-trans-test-01_02.tiff", props.getImageFilename());
		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading());
		assertEquals(Color.BLACK, props.getMandelbrotColor());

		// Third entry with second transition
		props = listOfProps.get(2);
		assertNotNull(props);
		assertEquals(-1.42d, props.getTopLeft().cx(), 0.001);
		assertEquals(0.6d, props.getTopLeft().cy(), 0.001);
		assertEquals(0.1d, props.getBottomRight().cx(), 0.001);
		assertEquals(-0.6d, props.getBottomRight().cy(), 0.001);
		assertEquals(30, props.getMaximumIterations());
		assertEquals(459, props.getImageWidth());
		assertEquals(405, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-ctrl-trans-test-01_03.tiff", props.getImageFilename());
		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading());
		assertEquals(Color.BLACK, props.getMandelbrotColor());

		// Forth entry is second point given
		props = listOfProps.get(3);
		assertNotNull(props);
		assertEquals(-1.12d, props.getTopLeft().cx());
		assertEquals(0.3d, props.getTopLeft().cy());
		assertEquals(-0.2d, props.getBottomRight().cx());
		assertEquals(-0.3d, props.getBottomRight().cy());
		assertEquals(40, props.getMaximumIterations());
		assertEquals(459, props.getImageWidth());
		assertEquals(405, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-ctrl-trans-test-01_04.tiff", props.getImageFilename());
		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading());
		assertEquals(Color.BLACK, props.getMandelbrotColor());
	}

}
