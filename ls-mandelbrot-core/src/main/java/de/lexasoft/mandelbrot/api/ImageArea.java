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
package de.lexasoft.mandelbrot.api;

/**
 * Represents the area of an image.
 * 
 * @author nierax
 *
 */
public class ImageArea {

	private int width;
	private int height;

	/**
	 * 
	 */
	private ImageArea(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Extends or reduces the dimensions of this image to let it fit in the aspect
	 * ratio of the calculation area, given.
	 * <p>
	 * In case width and height are given, height will be recalculated.
	 * 
	 * @param calculation
	 */
	public void followAspectRatio(CalculationArea calculation) {
		double ratioXtoY = Math.abs(calculation.topLeft().cx() - calculation.bottomRight().cx())
		    / Math.abs(calculation.topLeft().cy() - calculation.bottomRight().cy());
		if (width == 0) {
			width = (int) (height * ratioXtoY);
		} else {
			height = (int) (width / ratioXtoY);
		}
	}

	/**
	 * @return the width
	 */
	public int width() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int height() {
		return height;
	}

	public final static ImageArea of(int width, int height) {
		return new ImageArea(width, height);
	}

}
