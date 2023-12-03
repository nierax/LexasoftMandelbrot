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

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mandelbrot.ColorGradingLine;
import de.lexasoft.mandelbrot.ColorPaletteFactory;
import de.lexasoft.mandelbrot.MandelbrotBlackWhite;
import de.lexasoft.mandelbrot.MandelbrotColorPalette;
import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * @author nierax
 */
class MandelbrotIteratorTest {

	private CalculationArea calculation;
	private ImageArea imageDim;
	private static final int IMAGE_WIDTH = 459;
	private static final int IMAGE_HEIGHT = 405;
	private static final String IMAGE_DIRECTORY = "junit-tmp";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		calculation = CalculationArea.of(MandelbrotPointPosition.of(-2.02, 1.2), MandelbrotPointPosition.of(0.7, -1.2));
		imageDim = ImageArea.of(IMAGE_WIDTH, IMAGE_HEIGHT);
	}

	private static Stream<Arguments> testDrawMandelbrot() {
		ColorPaletteFactory cFactory = new ColorPaletteFactory();
		List<Color> ungraded = new ArrayList<>();
		ungraded.add(Color.BLUE);
		ungraded.add(Color.WHITE);
		ungraded.add(Color.ORANGE);
		ungraded.add(Color.WHITE);
		return Stream.of(
		    // FAST, Black and white
		    Arguments.of(CalcPrecision.FAST, //
		        new MandelbrotBlackWhite(), 500, //
		        IMAGE_DIRECTORY + "/mandelbrot-bw-fast.tiff"),
		    // FAST, Rainbow 29 colors
		    Arguments.of(CalcPrecision.FAST, //
		        MandelbrotColorPalette.of(cFactory.createRainbowPalette29(), Color.BLACK), //
		        580, IMAGE_DIRECTORY + "/mandelbrot-rainbow-fast.tiff"),
		    // FAST, 3 colors in list
		    Arguments.of(CalcPrecision.FAST, //
		        MandelbrotColorPalette.of(ColorGradingLine.of().gradePalette(ungraded, 21), Color.BLACK), 500,
		        IMAGE_DIRECTORY + "/mandelbrot-colorlist-fast.tiff"),
		    // LOW, Black and white
		    Arguments.of(CalcPrecision.LOW, //
		        new MandelbrotBlackWhite(), 50, //
		        IMAGE_DIRECTORY + "/mandelbrot-bw-low.tiff"),
		    // LOW, Rainbow 29 colors
		    Arguments.of(CalcPrecision.LOW, //
		        MandelbrotColorPalette.of(cFactory.createRainbowPalette29(), Color.BLACK), //
		        50, IMAGE_DIRECTORY + "/mandelbrot-rainbow-low.tiff"),
		    // MIDDLE, Black and white
		    Arguments.of(CalcPrecision.MIDDLE, //
		        new MandelbrotBlackWhite(), 50, //
		        IMAGE_DIRECTORY + "/mandelbrot-bw-middle.tiff"),
		    // MIDDLE, Rainbow 29 colors
		    Arguments.of(CalcPrecision.MIDDLE, //
		        MandelbrotColorPalette.of(cFactory.createRainbowPalette29(), Color.BLACK), //
		        50, IMAGE_DIRECTORY + "/mandelbrot-rainbow-middle.tiff"),
		    // EXACT, 3 colors in list
		    Arguments.of(CalcPrecision.EXACT, //
		        MandelbrotColorPalette.of(ColorGradingLine.of().gradePalette(ungraded, 21), Color.BLACK), //
		        50, IMAGE_DIRECTORY + "/mandelbrot-colorlist-exact.tiff"));

	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.cu.MandelbrotIteratorFast#drawMandelbrot(de.lexasoft.mandelbrot.api.MandelbrotPointPosition, de.lexasoft.mandelbrot.api.MandelbrotPointPosition, int, int, int)}.
	 * 
	 * @throws IOException
	 */
	@ParameterizedTest
	@MethodSource
	void testDrawMandelbrot(CalcPrecision version, MandelbrotColorize col, int maxIter, String filename)
	    throws IOException {
		MandelbrotIterator cut = MandelbrotIterator.of(version, col);
		MandelbrotImage image = cut.drawMandelbrot(calculation, maxIter, imageDim);
		assertNotNull(image, "Image could not be created");
		image.writeToFile(filename);
	}

}
