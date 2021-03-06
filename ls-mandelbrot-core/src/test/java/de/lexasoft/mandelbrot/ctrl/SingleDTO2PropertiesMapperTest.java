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

import de.lexasoft.mandelbrot.api.AspectRatioHandle;
import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.PaletteVariant;

/**
 * @author nierax
 *
 */
class SingleDTO2PropertiesMapperTest {

	private AbstractDTO2PropertiesMapper cut;
	private MandelbrotAttributesDTO dtoSingle;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		dtoSingle = MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-test.yaml");
		cut = SingleDTO2PropertiesMapper.of(dtoSingle);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.SingleDTO2PropertiesMapper#mapDTO2Properties(CalculationPropertiesDTO)}.
	 */
	@Test
	void testMapDTO2Properties() {
		List<MandelbrotCalculationProperties> listOfProps = cut.mapDTO2Properties();
		assertNotNull(listOfProps);
		assertEquals(1, listOfProps.size());
		MandelbrotCalculationProperties props = listOfProps.get(0);
		assertNotNull(props);
		assertEquals(-2.02d, props.getTopLeft().cx());
		assertEquals(1.2d, props.getTopLeft().cy());
		assertEquals(0.7d, props.getBottomRight().cx());
		assertEquals(-1.2d, props.getBottomRight().cy());
		assertEquals(500, props.getMaximumIterations());
		assertEquals(4590, props.getImageWidth());
		assertEquals(4050, props.getImageHeight());
		assertEquals(AspectRatioHandle.IGNORE, props.getAspectRatio());
		assertEquals("./junit-tmp/mandelbrot-test.tiff", props.getImageFilename());

		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(ColorGradingStyle.LINE, props.getColorGrading().getStyle());
		assertEquals(5, props.getColorGrading().getColorsTotal());

		assertEquals(Color.BLACK, props.getMandelbrotColor());
	}

	/**
	 * Default for aspect ratio handler is "FITIN".
	 */
	@Test
	void testMapDTO2PropertiesAspectRatioDefault() {
		// Reset the aspect ratio handle.
		cut.getAttribsDTO().getImage().setAspectRatioHandle(null);
		// Now run the mapping
		List<MandelbrotCalculationProperties> listOfProps = cut.mapDTO2Properties();
		assertNotNull(listOfProps);
		assertEquals(1, listOfProps.size());
		MandelbrotCalculationProperties props = listOfProps.get(0);
		assertNotNull(props);

		assertEquals(AspectRatioHandle.FITIN, props.getAspectRatio());
	}

}
