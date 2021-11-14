/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mandelbrot.api.AspectRatioHandle;

/**
 * @author admin
 *
 */
class ImageAttributesDTOTest {

	private ImageAttributesDTO cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = ImageAttributesDTO.ofDefault();
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.ImageAttributesDTO#ofDefault()}.
	 */
	@Test
	final void testOfDefault() {
		assertNotNull(cut);
		assertEquals(459, cut.getImageWidth());
		assertEquals(405, cut.getImageHeight());
		assertNull(cut.getImageFilename());
		assertEquals(AspectRatioHandle.FITIN, cut.getAspectRatioHandle());
	}

	private static Stream<Arguments> _is_width_set_() {
		return Stream.of(//
		    Arguments.of(0, false), //
		    Arguments.of(-1, false), //
		    Arguments.of(1, true), //
		    Arguments.of(450, true));
	}

	/**
	 * Width should be set, when its value is above 0.
	 * 
	 * @param width
	 * @param expected
	 */
	@ParameterizedTest
	@MethodSource
	final void _is_width_set_(int width, boolean expected) {
		// Prepare
		cut.setImageWidth(width);
		// Run and check
		assertEquals(expected, cut.isImageWidthSet());
	}

	private static Stream<Arguments> _is_height_set_() {
		return Stream.of(//
		    Arguments.of(0, false), //
		    Arguments.of(-1, false), //
		    Arguments.of(1, true), //
		    Arguments.of(400, true));
	}

	/**
	 * Width should be set, when its value is above 0.
	 * 
	 * @param height
	 * @param expected
	 */
	@ParameterizedTest
	@MethodSource
	final void _is_height_set_(int height, boolean expected) {
		// Prepare
		cut.setImageHeight(height);
		// Run and check
		assertEquals(expected, cut.isImageHeightSet());
	}

	private final static Stream<Arguments> _is_dimension_set_() {
		return Stream.of(//
		    Arguments.of(0, 0, false), //
		    Arguments.of(0, 1, false), //
		    Arguments.of(1, 0, false), //
		    Arguments.of(-1, 0, false), //
		    Arguments.of(-1, -1, false), //
		    Arguments.of(0, -1, false), //
		    Arguments.of(1, 1, true), //
		    Arguments.of(450, 400, true) //
		);
	}

	@ParameterizedTest
	@MethodSource
	final void _is_dimension_set_(int width, int height, boolean expected) {
		// Prepare
		cut.setImageWidth(width);
		cut.setImageHeight(height);
		// Run and check
		assertEquals(expected, cut.isImageDimensionSet());
	}

}
