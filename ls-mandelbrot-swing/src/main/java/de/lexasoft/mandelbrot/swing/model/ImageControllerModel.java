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
package de.lexasoft.mandelbrot.swing.model;

/**
 * The model of the image export dialog
 * 
 * @author nierax
 *
 */
public interface ImageControllerModel {

	/**
	 * 
	 * @return The width of the image
	 */
	int imageWidth();

	/**
	 * 
	 * @return The height of the image
	 */
	int imageHeight();

	/**
	 * The file name of the image file to write to.
	 * 
	 * @return
	 */
	String imageFilename();
}
