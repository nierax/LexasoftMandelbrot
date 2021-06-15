/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import java.awt.image.BufferedImage;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class ImageAttributesDTOTest {

	private ImageAttributesDTO cut;
	@Mock
	private BufferedImage image;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new ImageAttributesDTO();
		image = mock(BufferedImage.class);
	}

	enum ExpectedResult {
		NULL, SAME, OTHER
	}

	private static final Stream<Arguments> testNormalize() {
		return Stream.of(
		    // Variants to check
		    Arguments.of("/irgend/ein/file/name.jpg", 459, 450, mock(BufferedImage.class), ExpectedResult.SAME),
		    Arguments.of(null, 459, 450, mock(BufferedImage.class), ExpectedResult.SAME),
		    Arguments.of("", 459, 450, mock(BufferedImage.class), ExpectedResult.SAME),
		    Arguments.of("/irgend/ein/file/name.jpg", 459, 450, null, ExpectedResult.OTHER),
		    Arguments.of("/irgend/ein/file/name.jpg", 459, 0, null, ExpectedResult.NULL),
		    Arguments.of("/irgend/ein/file/name.jpg", 0, 450, null, ExpectedResult.NULL),
		    Arguments.of("/irgend/ein/file/name.jpg", 0, 0, null, ExpectedResult.NULL),
		    Arguments.of(null, 459, 450, null, ExpectedResult.NULL), Arguments.of("", 459, 450, null, ExpectedResult.NULL));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.ImageAttributesDTO#normalize()}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testNormalize(String fileName, int imageWidth, int imageHeight, BufferedImage image,
	    ExpectedResult expected) {
		// Prepare
		cut.setImageFilename(fileName);
		cut.setImage(image);
		cut.setImageWidth(imageWidth);
		cut.setImageHeight(imageHeight);
		// Run
		cut.normalize();
		// Check
		switch (expected) {
		case NULL:
			assertNull(cut.getImage());
			break;
		case SAME:
			assertNotNull(cut.getImage());
			assertSame(image, cut.getImage());
			break;
		case OTHER:
			assertNotNull(cut.getImage());
			assertNotSame(image, cut.getImage());
		}
	}

}
