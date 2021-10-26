/**
 * Copyright (C) 2021 nierax
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;

/**
 * Checking the detection of missing parameters.
 * 
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class MandelbrotIteratorBuilderTest {

	private MandelbrotIteratorBuilder cut;
	@Mock
	private MandelbrotIterator iterator;
	@Mock
	private CalculationArea calculation;
	@Mock
	private MandelbrotColorize colorize;
	@Mock
	private ImageArea imageArea;
	@Mock
	private MandelbrotImage result;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = MandelbrotIteratorBuilder.of().withIterator(iterator);
	}

	/**
	 * Calculation should return no result.
	 */
	@Test
	final void testCalculateNoParams() {
		Optional<MandelbrotImage> image = cut.calculate();
		assertTrue(image.isEmpty());
	}

	/**
	 * Calculation should return no result.
	 */
	@Test
	final void testCalculateJustColorizeParams() {
		Optional<MandelbrotImage> image = //
		    cut.withColorize(colorize) //
		        .calculate();
		assertTrue(image.isEmpty());
	}

	/**
	 * Calculation should return no result.
	 */
	@Test
	final void testCalculateColorizeCalculationParams() {
		Optional<MandelbrotImage> image = //
		    cut.withColorize(colorize) //
		        .withCalculationArea(calculation) //
		        .calculate();
		assertTrue(image.isEmpty());
	}

	/**
	 * Calculation should return no result.
	 */
	@Test
	final void testCalculateColorizeCalculationImageAreaParams() {
		Optional<MandelbrotImage> image = //
		    cut.withColorize(colorize) //
		        .withCalculationArea(calculation) //
		        .withImageArea(imageArea) //
		        .calculate();
		assertTrue(image.isEmpty());
	}

	/**
	 * Calculation should return no result.
	 */
	@Test
	final void testCalculateAll() {
		when(iterator.drawMandelbrot(calculation, 25, imageArea)).thenReturn(result);
		Optional<MandelbrotImage> image = //
		    cut.withColorize(colorize) //
		        .withCalculationArea(calculation) //
		        .withImageArea(imageArea) //
		        .withMaxIterations(25) //
		        .calculate();
		assertTrue(image.isPresent());
		verify(iterator).drawMandelbrot(calculation, 25, imageArea);
	}

	/**
	 * Calculation should return no result.
	 */
	@Test
	final void testCalculateAllButNoResult() {
		when(iterator.drawMandelbrot(calculation, 25, imageArea)).thenReturn(null);
		Optional<MandelbrotImage> image = //
		    cut.withColorize(colorize) //
		        .withCalculationArea(calculation) //
		        .withImageArea(imageArea) //
		        .withMaxIterations(25) //
		        .calculate();
		assertTrue(image.isEmpty());
		verify(iterator).drawMandelbrot(calculation, 25, imageArea);
	}
}
