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
package de.lexasoft.mandelbrot;

import java.awt.Dimension;

import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.api.MandelbrotUtilityAPI;

/**
 * @author nierax
 *
 */
public class MandelbrotCoreUtilities implements MandelbrotUtilityAPI {

	@Override
	public Dimension calculateAspectRatioForImage(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight,
	    Dimension bound) {
		double imageHeight = ((bound.getHeight() > 0) && (bound.getWidth() > 0)) ? 0 : bound.getHeight();
		double imageWidth = bound.getWidth();
		double ratioXtoY = Math.abs(topLeft.cx() - bottomRight.cx()) / Math.abs(topLeft.cy() - bottomRight.cy());
		if (imageHeight == 0) {
			imageHeight = bound.getWidth() / ratioXtoY;
		} else {
			imageWidth = bound.getHeight() * ratioXtoY;
		}
		return new Dimension((int) imageWidth, (int) imageHeight);
	}

}
