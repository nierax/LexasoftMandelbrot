/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * @author nierax
 *
 */
class AbstractDTO2PropertiesMapperTest {

	class CUT extends AbstractDTO2PropertiesMapper {

		public CUT(MandelbrotAttributesDTO propsDTO) {
			super(propsDTO);
		}

		@Override
		protected void mapFollowingCalculations(List<TransitionAttributesDTO> dto,
		    List<MandelbrotCalculationProperties> listOfProps) {
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	private static Stream<Arguments> testOf() throws JsonParseException, JsonMappingException, IOException {
		return Stream.of(
		    Arguments.of(MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-test.yaml"),
		        SingleDTO2PropertiesMapper.class),
		    Arguments.of(MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-test-list.yaml"),
		        VariantsDTO2PropertiesMapper.class),
		    Arguments.of(MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-test-transition-1.yaml"),
		        TransitionDTO2PropertiesMapper.class),
		    Arguments.of(MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-test-transition-2.yaml"),
		        TransitionDTO2PropertiesMapper.class),
		    Arguments.of(MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-test-transition-3.yaml"),
		        TransitionDTO2PropertiesMapper.class));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.AbstractDTO2PropertiesMapper#of(de.lexasoft.mandelbrot.ctrl.CalculationPropertiesDTO)}.
	 */
	@ParameterizedTest
	@MethodSource
	void testOf(MandelbrotAttributesDTO props, Class<AbstractDTO2PropertiesMapper> expectedInstance) {
		AbstractDTO2PropertiesMapper cut = CUT.of(props);
		assertNotNull(cut);
		assertEquals(expectedInstance, cut.getClass());
	}

}
