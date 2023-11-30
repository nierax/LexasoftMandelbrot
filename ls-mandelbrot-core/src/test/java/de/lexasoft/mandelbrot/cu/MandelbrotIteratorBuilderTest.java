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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.common.model.MessageSeverity;
import de.lexasoft.common.model.Result;
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

	private static Logger LOGGER = LoggerFactory.getLogger(MandelbrotIteratorBuilderTest.class);

	private MandelbrotIteratorBuilder cut;
	@Mock
	private MandelbrotIteratorFast iterator;
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
		Result<MandelbrotImage> result = cut.calculate();
		assertNull(result.get());
		assertTrue(result.isErroneous());
		assertEquals(3, result.getMessages().countMessagesWithSeverity(MessageSeverity.ERROR));
	}

	/**
	 * Calculation should return no result.
	 */
	@Test
	final void testCalculateJustColorizeParams() {
		Result<MandelbrotImage> result = //
		    cut.withColorize(colorize) //
		        .calculate();
		assertNull(result.get());
		assertTrue(result.isErroneous());
		assertEquals(3, result.getMessages().countMessagesWithSeverity(MessageSeverity.ERROR));
		LOGGER.info(result.getMessages()::toString);
	}

	/**
	 * Calculation should return no result.
	 */
	@Test
	final void testCalculateColorizeCalculationParams() {
		Result<MandelbrotImage> result = //
		    cut.withColorize(colorize) //
		        .withCalculationArea(calculation) //
		        .calculate();
		assertNull(result.get());
		assertTrue(result.isErroneous());
		assertEquals(2, result.getMessages().countMessagesWithSeverity(MessageSeverity.ERROR));
		LOGGER.info(result.getMessages()::toString);
	}

	/**
	 * Calculation should return no result.
	 */
	@Test
	final void testCalculateColorizeCalculationImageAreaParams() {
		Result<MandelbrotImage> result = //
		    cut.withColorize(colorize) //
		        .withCalculationArea(calculation) //
		        .withImageArea(imageArea) //
		        .calculate();
		assertNull(result.get());
		assertTrue(result.isErroneous());
		assertEquals(1, result.getMessages().countMessagesWithSeverity(MessageSeverity.ERROR));
		LOGGER.info(result.getMessages()::toString);
	}

	/**
	 * Calculation should return no result.
	 */
	@Test
	final void testCalculateAll() {
		when(iterator.drawMandelbrot(calculation, 25, imageArea)).thenReturn(result);
		Result<MandelbrotImage> image = //
		    cut.withColorize(colorize) //
		        .withCalculationArea(calculation) //
		        .withImageArea(imageArea) //
		        .withMaxIterations(25) //
		        .calculate();
		assertNotNull(image.get());
		assertFalse(image.isErroneous());
		verify(iterator).drawMandelbrot(calculation, 25, imageArea);
	}

	/**
	 * Calculation should return no result.
	 */
	@Test
	final void testCalculateAllButNoResult() {
		when(iterator.drawMandelbrot(calculation, 25, imageArea)).thenReturn(null);
		Result<MandelbrotImage> image = //
		    cut.withColorize(colorize) //
		        .withCalculationArea(calculation) //
		        .withImageArea(imageArea) //
		        .withMaxIterations(25) //
		        .calculate();
		assertNull(image.get());
		assertTrue(image.isErroneous());
		assertEquals(1, image.getMessages().countMessagesWithSeverity(MessageSeverity.ERROR));
		verify(iterator).drawMandelbrot(calculation, 25, imageArea);
		LOGGER.info(image.getMessages()::toString);
	}
}
