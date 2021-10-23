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
package de.lexasoft.mandelbrot.swing;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class StatusbarControllerTest {

	private StatusbarController cut;
	@Mock
	private StatusbarView view;
	@Mock
	private JTextField duration;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new StatusbarController(view);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.StatusbarController#updateDuration(double)}.
	 */
	@Test
	final void testUpdateDuration() {
		// Prepare
		when(view.getDuration()).thenReturn(duration);
		// Run
		cut.updateDuration(55);
		// Check
		verify(duration).setText("55 ms");
	}

}
