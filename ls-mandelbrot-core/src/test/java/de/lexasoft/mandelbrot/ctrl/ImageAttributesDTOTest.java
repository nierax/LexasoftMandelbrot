/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.api.AspectRatio;

/**
 * @author admin
 *
 */
class ImageAttributesDTOTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.ImageAttributesDTO#ofDefault()}.
	 */
	@Test
	final void testOfDefault() {
		ImageAttributesDTO cut = ImageAttributesDTO.ofDefault();
		assertNotNull(cut);
		assertEquals(459, cut.getImageWidth());
		assertEquals(405, cut.getImageHeight());
		assertNull(cut.getImageFilename());
		assertEquals(AspectRatio.FITIN, cut.getAspectRatioHandle());
	}

}
