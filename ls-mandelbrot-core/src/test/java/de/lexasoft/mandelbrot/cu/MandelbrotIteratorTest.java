/**
 * Copyright (C) 2023 nierax
 * This program is free software; you can redistribute it and/or modify it under the terms of 
 * the GNU General Public License as published by the Free Software Foundation; either version 3 
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * 
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, see <http://www.gnu.org/licenses/>. 
 */
package de.lexasoft.mandelbrot.cu;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.MandelbrotBlackWhite;
import de.lexasoft.mandelbrot.MandelbrotColorPalette;

/**
 * Test for default methods if interface @MandelbrotIterator
 * 
 * @author nieray
 */
class MandelbrotIteratorTest {

	private MandelbrotColorPalette colors;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		List<Color> colorList = new ArrayList<>();
		colors = MandelbrotColorPalette.of(colorList, new Color(128, 128, 128));
	}

	/**
	 * Test for of method without parameter
	 */
	@Test
	final void of_without_color_will_create_black_and_white_color() {
		MandelbrotIterator cut = MandelbrotIterator.of();
		assertNotNull(cut);
		assertTrue((cut.colorize() instanceof MandelbrotBlackWhite));
	}

	@Test
	final void of_with_parameter_creates_with_given_model() {
		MandelbrotIterator cut = MandelbrotIterator.of(colors);
		assertNotNull(cut);
		assertTrue(cut.colorize().equals(colors));
	}

}
