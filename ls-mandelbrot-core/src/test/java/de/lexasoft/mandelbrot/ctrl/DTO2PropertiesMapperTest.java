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
	private CalculationPropertiesDTO dtoSingle;
	private CalculationPropertiesDTO dtoList;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new DTO2PropertiesMapper();
		dtoSingle = CalculationPropertiesDTO.of("src/test/resources/mandelbrot-test.yaml");
		dtoList = CalculationPropertiesDTO.of("src/test/resources/mandelbrot-test-list.yaml");
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.DTO2PropertiesMapper#mapDTO2Properties(CalculationPropertiesDTO)}.
	 */
	@Test
	void testMapDTO2Properties() {
		List<MandelbrotCalculationProperties> listOfProps = cut.mapDTO2Properties(dtoSingle);
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
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading());

		assertEquals(Color.BLACK, props.getMandelbrotColor());
	}

	@Test
	void testMapDTOList2Properties() {
		List<MandelbrotCalculationProperties> listOfProps = cut.mapDTO2Properties(dtoList);
		assertNotNull(listOfProps);
		assertEquals(3, listOfProps.size());

		// First entry with complete properties
		MandelbrotCalculationProperties props = listOfProps.get(0);
		assertNotNull(props);
		assertEquals(-2.02d, props.getTopLeft().cx());
		assertEquals(1.2d, props.getTopLeft().cy());
		assertEquals(0.7d, props.getBottomRight().cx());
		assertEquals(-1.2d, props.getBottomRight().cy());
		assertEquals(100, props.getMaximumIterations());
		assertEquals(4590, props.getImageWidth());
		assertEquals(4050, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-test-list-01.tiff", props.getImageFilename());
		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading());
		assertEquals(Color.BLACK, props.getMandelbrotColor());

		// Second entry with different maximum iterations
		props = listOfProps.get(1);
		assertNotNull(props);
		assertEquals(-2.02d, props.getTopLeft().cx());
		assertEquals(1.2d, props.getTopLeft().cy());
		assertEquals(0.7d, props.getBottomRight().cx());
		assertEquals(-1.2d, props.getBottomRight().cy());
		assertEquals(500, props.getMaximumIterations());
		assertEquals(4590, props.getImageWidth());
		assertEquals(4050, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-test-list-02.tiff", props.getImageFilename());
		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading());
		assertEquals(Color.BLACK, props.getMandelbrotColor());

		// Third entry with different coordinates and maximum iterations
		props = listOfProps.get(2);
		assertNotNull(props);
		assertEquals(-0.2d, props.getTopLeft().cx());
		assertEquals(0.93d, props.getTopLeft().cy());
		assertEquals(-0.02d, props.getBottomRight().cx());
		assertEquals(0.8d, props.getBottomRight().cy());
		assertEquals(300, props.getMaximumIterations());
		assertEquals(4590, props.getImageWidth());
		assertEquals(4050, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-test-list-03.tiff", props.getImageFilename());
		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading());
		assertEquals(Color.BLACK, props.getMandelbrotColor());
	}

}
