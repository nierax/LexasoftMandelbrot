/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.PaletteVariant;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * @author nierax
 *
 */
class TransitionDTO2PropertiesMapperTest {

	private TransitionDTO2PropertiesMapper cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = createCUT("src/test/resources/mandelbrot-test-transition-0.yaml");
	}

	/**
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private TransitionDTO2PropertiesMapper createCUT(String yamlFile)
	    throws JsonParseException, JsonMappingException, IOException {
		return TransitionDTO2PropertiesMapper.of(CalculationPropertiesDTO.of(yamlFile));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.AbstractDTO2PropertiesMapper#mapDTO2Properties()}.
	 */
	@Test
	void testMapDTO2Properties2Entries() {
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

	/**
	 * Check, whether the second entry is used as the base entry, when 3 entries are
	 * in the list.
	 * <p>
	 * The third element is the same as the first one, transition steps are both
	 * two. In this case the two steps between two and three must be the same as
	 * between one and two, but in reverse order:
	 * <p>
	 * [0] -> [1] -> [0] => <br/>
	 * [0] -> [01a] -> [01b] -> [1] -> [01b] -> [01a] -> [0]
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	void testMapDTO2Properties3Entries() throws JsonParseException, JsonMappingException, IOException {
		cut = createCUT("src/test/resources/mandelbrot-test-transition-0-3elements.yaml");
		List<MandelbrotCalculationProperties> listOfProps = cut.mapDTO2Properties();
		assertNotNull(listOfProps);
		assertEquals(7, listOfProps.size());

		// 5th entry should be [01b]
		MandelbrotCalculationProperties props = listOfProps.get(4);
		assertNotNull(props);
		assertEquals(-1.42d, props.getTopLeft().cx(), 0.001);
		assertEquals(0.6d, props.getTopLeft().cy(), 0.001);
		assertEquals(0.1d, props.getBottomRight().cx(), 0.001);
		assertEquals(-0.6d, props.getBottomRight().cy(), 0.001);
		assertEquals(30, props.getMaximumIterations());
		assertEquals(459, props.getImageWidth());
		assertEquals(405, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-ctrl-trans-test-01_05.tiff", props.getImageFilename());

		// 6th entry should be [01a]
		props = listOfProps.get(5);
		assertNotNull(props);
		assertEquals(-1.72d, props.getTopLeft().cx(), 0.001);
		assertEquals(0.9d, props.getTopLeft().cy(), 0.001);
		assertEquals(0.4d, props.getBottomRight().cx(), 0.001);
		assertEquals(-0.9d, props.getBottomRight().cy(), 0.001);
		assertEquals(20, props.getMaximumIterations(), 0.001);
		assertEquals(459, props.getImageWidth());
		assertEquals(405, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-ctrl-trans-test-01_06.tiff", props.getImageFilename());

	}

}
