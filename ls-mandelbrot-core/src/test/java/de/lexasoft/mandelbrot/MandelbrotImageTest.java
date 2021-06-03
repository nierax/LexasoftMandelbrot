/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author nierax
 *
 */
class MandelbrotImageTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotImage#of(java.lang.String)}.
	 */
	@Test
	final void testOfQualifiedFilename() {
		MandelbrotImage result = MandelbrotImage.of(450, 300, "/qualified/file/name");
		assertNotNull(result);
		assertTrue(result instanceof MandelbrotImageFile);
	}

}
