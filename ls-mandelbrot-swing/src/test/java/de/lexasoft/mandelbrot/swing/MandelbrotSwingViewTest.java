/**
 * Copyright (C) 2021 admin
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
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author nierax
 *
 */
class MandelbrotSwingViewTest {

	private MandelbrotSwingView cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new MandelbrotSwingView();
		cut.getFrmLexasoftMandelbrotApplication().setVisible(false);
	}

	private void assertComponentsEnabled(boolean enabled) {
		// Application frame
		assertEquals(enabled, cut.getFrmLexasoftMandelbrotApplication().isEnabled());
		// Color panel
		assertEquals(enabled, cut.getColorControlPanel().isEnabled());
		assertEquals(enabled, cut.getColorControlPanel().getColorGradingStyle().isEnabled());
		assertEquals(enabled, cut.getColorControlPanel().getTotalColors().isEnabled());
		assertEquals(enabled, cut.getColorControlPanel().getPaletteVariant().isEnabled());
		// Calculation panel
		assertEquals(enabled, cut.getCalculationPanel().isEnabled());
		assertEquals(enabled, cut.getCalculationPanel().getTlcx().isEnabled());
		assertEquals(enabled, cut.getCalculationPanel().getTlcy().isEnabled());
		assertEquals(enabled, cut.getCalculationPanel().getBrcx().isEnabled());
		assertEquals(enabled, cut.getCalculationPanel().getBrcy().isEnabled());
		assertEquals(enabled, cut.getCalculationPanel().getMaxIter().isEnabled());
		assertEquals(enabled, cut.getCalculationPanel().getAspectRatio().isEnabled());
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.MandelbrotSwingView#setEnabled(boolean)}.
	 */
	@Test
	final void testSetEnabled() {
		// Assert, that the components are initially enabled
		assertComponentsEnabled(true);

		// Now disable everything
		cut.setEnabled(false);

		// Everything should be disabled now.
		assertComponentsEnabled(false);
	}

}
