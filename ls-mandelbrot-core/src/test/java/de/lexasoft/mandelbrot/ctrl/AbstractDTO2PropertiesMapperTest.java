/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
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

	private static Stream<Arguments> testOf_TransitionCalculation()
	    throws JsonParseException, JsonMappingException, IOException {
		return Stream.of(//
		    Arguments.of(MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-test-transition-1.yaml")), //
		    Arguments.of(MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-test-transition-2.yaml")),
		    Arguments.of(MandelbrotAttributesDTO.of("sample-yaml/mandelbrot-sample-03.yaml")), //
		    Arguments.of(MandelbrotAttributesDTO.of("sample-yaml/mandelbrot-sample-04.yaml")), //
		    Arguments.of(MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-test-transition-3.yaml"))//
		);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.AbstractDTO2PropertiesMapper#of(de.lexasoft.mandelbrot.ctrl.CalculationPropertiesDTO)}.
	 */
	@ParameterizedTest
	@MethodSource
	void testOf_TransitionCalculation(MandelbrotAttributesDTO props) {
		AbstractDTO2PropertiesMapper cut = CUT.of(props);
		assertNotNull(cut);
		assertEquals(TransitionDTO2PropertiesMapper.class, cut.getClass());
		assertNotNull(props.getFollowing());
		for (MandelbrotAttributesDTO dto : props.getFollowing()) {
			assertEquals(TransitionAttributesDTO.class, dto.getClass());
		}
	}

	static Stream<Arguments> testOf_SingleCalculation() throws JsonParseException, JsonMappingException, IOException {
		return Stream.of(//
		    Arguments.of(MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-test.yaml")));
	}

	@ParameterizedTest
	@MethodSource
	void testOf_SingleCalculation(MandelbrotAttributesDTO props) {
		AbstractDTO2PropertiesMapper cut = CUT.of(props);
		assertNotNull(cut);
		assertEquals(SingleDTO2PropertiesMapper.class, cut.getClass());
		assertEquals(BigDecimal.valueOf(-2.02), props.getCalculation().getTopLeft().cx());
	}

	private static Stream<Arguments> testOf_VariantsCalculation()
	    throws JsonParseException, JsonMappingException, IOException {
		return Stream.of(//
		    Arguments.of(MandelbrotAttributesDTO.of("src/test/resources/mandelbrot-test-list.yaml"))//
		);
	}

	@ParameterizedTest
	@MethodSource
	void testOf_VariantsCalculation(MandelbrotAttributesDTO props) {
		AbstractDTO2PropertiesMapper cut = CUT.of(props);
		assertNotNull(cut);
		assertEquals(VariantsDTO2PropertiesMapper.class, cut.getClass());
		assertNotNull(props.getFollowing());
		assertEquals(2, props.getFollowing().size());

	}

}
