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
class DTO2PropertiesMapperTest {

	private DTO2PropertiesMapper cut;
	private CalculationPropertiesDTO dto;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new DTO2PropertiesMapper();
		dto = CalculationPropertiesDTO.of("src/test/resources/mandelbrot-test.yaml");
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.DTO2PropertiesMapper#mapDTO2Properties(CalculationPropertiesDTO)}.
	 */
	@Test
	void testMapDTO2Properties() {
		List<MandelbrotCalculationProperties> listOfProps = cut.mapDTO2Properties(dto);
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
		assertEquals("./junit-tmp/mandelbrot-test.tiff", props.getImageFilename());

		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());

		assertEquals(25, props.getCustomColorPalette().get(0).getRed());
		assertEquals(140, props.getCustomColorPalette().get(0).getGreen());
		assertEquals(255, props.getCustomColorPalette().get(0).getBlue());

		assertEquals(255, props.getCustomColorPalette().get(1).getRed());
		assertEquals(255, props.getCustomColorPalette().get(1).getGreen());
		assertEquals(255, props.getCustomColorPalette().get(1).getBlue());

		assertEquals(5, props.getColorGrading());

		assertEquals(Color.BLACK, props.getMandelbrotColor());
	}

}
