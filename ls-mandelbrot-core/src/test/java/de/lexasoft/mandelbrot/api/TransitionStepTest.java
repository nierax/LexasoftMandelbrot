/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author nierax
 *
 */
class TransitionStepTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	private static Stream<Arguments> testOf() {
		return Stream.of(Arguments.of(TransitionVariant.LINEAR, LinearTransitionStep.class));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.TransitionStep#of(de.lexasoft.mandelbrot.api.TransitionVariant)}.
	 */
	@ParameterizedTest
	@MethodSource
	void testOf(TransitionVariant variant, Class<?> expected) {
		TransitionStep created = TransitionStep.of(variant);
		assertNotNull(created);
		assertEquals(expected, created.getClass());
	}

}
