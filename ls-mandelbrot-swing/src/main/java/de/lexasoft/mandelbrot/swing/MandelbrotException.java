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

/**
 * Signalizes an exception in the Mandelbrot application, which can not be
 * handled the place, where it occurs.
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class MandelbrotException extends RuntimeException {

	/**
	 * @param message
	 */
	public MandelbrotException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MandelbrotException(String message, Throwable cause) {
		super(message, cause);
	}

}
