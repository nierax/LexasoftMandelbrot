/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author admin
 *
 */
class ColorPaletteFactoryTest {

	private ColorPaletteFactory cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new ColorPaletteFactory();
	}

	/**
	 * Test method for #ColorPaletteFactory::createRainbowPalette29()
	 */
	@Test
	void testCreateRainbowPalette29() {
		List<Color> result = cut.createRainbowPalette29();
		assertEquals(29, result.size(), "Size of list is not correct");
		// First color
		assertEquals(128, result.get(0).getRed(), "First color not correct in red.");
		assertEquals(0, result.get(0).getGreen(), "First color not correct in green.");
		assertEquals(0, result.get(0).getBlue(), "First color not correct in blue.");
		// Last color
		assertEquals(168, result.get(28).getRed(), "Last color not correct in red.");
		assertEquals(0, result.get(28).getGreen(), "Last color not correct in green.");
		assertEquals(185, result.get(28).getBlue(), "Last color not correct in blue.");
	}

	/**
	 * Test method for #ColorPaletteFactory::createRainbowPalette7()
	 */
	@Test
	void testCreateRainbowPalette7() {
		List<Color> result = cut.createRainbowPalette7();
		assertEquals(7, result.size(), "Size of list is not correct");
		assertEquals(Color.RED, result.get(0));
		assertEquals(Color.ORANGE, result.get(1));
		assertEquals(Color.YELLOW, result.get(2));
		assertEquals(Color.GREEN, result.get(3));
		assertEquals(Color.BLUE, result.get(4));
		assertEquals(new Color(75, 0, 130), result.get(5));
		assertEquals(new Color(136, 0, 255), result.get(6));
	}

	@Test
	void testCreateBlueWhitePalette() {
		List<Color> result = cut.createBlueWhitePalette();
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(new Color(25, 140, 255), result.get(0));
		assertEquals(Color.WHITE, result.get(1));
	}

}
