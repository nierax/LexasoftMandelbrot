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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.swing.DragController.DragListener;

/**
 * Test cases for the @DragController and the underlying @DragListener.
 * 
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class DragControllerTest {

	private DragController cut;
	private DragListener lut;
	@Mock
	private JPanel view;
	@Mock
	private MouseEvent ePressed;
	@Mock
	private MouseEvent eDragged;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new DragController(view);
		lut = cut.getDragListener();
	}

	@Test
	final void testMouseDragged() {
		// Prepare
		when(ePressed.getX()).thenReturn(100);
		when(ePressed.getY()).thenReturn(60);
		when(eDragged.getX()).thenReturn(105);
		when(eDragged.getY()).thenReturn(63);

		// 1. Mouse pressed
		lut.mousePressed(ePressed);
		assertEquals(100, lut.getX());
		assertEquals(60, lut.getY());
		// 2. Mouse moved
		lut.mouseDragged(eDragged);
		assertEquals(105, lut.getX());
		assertEquals(63, lut.getY());
		// 3. Mouse moved again
		when(eDragged.getX()).thenReturn(150);
		when(eDragged.getY()).thenReturn(70);
		lut.mouseDragged(eDragged);
		assertEquals(150, lut.getX());
		assertEquals(70, lut.getY());
	}

}
