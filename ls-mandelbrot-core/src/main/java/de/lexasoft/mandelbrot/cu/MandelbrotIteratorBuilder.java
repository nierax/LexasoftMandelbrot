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

import java.util.Optional;
import java.util.OptionalInt;

import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;

/**
 * Builder for Mandelbrot iterator, which calculates the Mandelbrot image.
 * 
 * @see MandelbrotIterator
 * @author nierax
 *
 */
public class MandelbrotIteratorBuilder {

	private Optional<MandelbrotColorize> colorize = Optional.empty();
	private Optional<CalculationArea> calculationArea = Optional.empty();
	private OptionalInt maxIterations = OptionalInt.empty();
	private Optional<ImageArea> imageArea = Optional.empty();

	private Optional<MandelbrotIterator> iterator = Optional.empty();

	/**
	 * Create only with the of() Method.
	 */
	private MandelbrotIteratorBuilder() {
	}

	public static MandelbrotIteratorBuilder of() {
		return new MandelbrotIteratorBuilder();
	}

	/**
	 * Which colorize object to use.
	 * 
	 * @param colorize
	 * @return MandelbrotIteratorBuilder to use.
	 */
	public MandelbrotIteratorBuilder withColorize(MandelbrotColorize colorize) {
		this.colorize = Optional.ofNullable(colorize);
		return this;
	}

	/**
	 * Which number of maximum iterations to use.
	 * 
	 * @param maxIterations
	 * @return MandelbrotIteratorBuilder to use.
	 */
	public MandelbrotIteratorBuilder withMaxIterations(int maxIterations) {
		this.maxIterations = OptionalInt.of(maxIterations);
		return this;
	}

	/**
	 * Which calculation area to use?
	 * 
	 * @param calculationArea
	 * @return MandelbrotIteratorBuilder to use.
	 */
	public MandelbrotIteratorBuilder withCalculationArea(CalculationArea calculationArea) {
		this.calculationArea = Optional.ofNullable(calculationArea);
		return this;
	}

	/**
	 * Which dimensions should the image have?
	 * 
	 * @param imageArea
	 * @return
	 */
	public MandelbrotIteratorBuilder withImageArea(ImageArea imageArea) {
		this.imageArea = Optional.ofNullable(imageArea);
		return this;
	}

	/**
	 * Allows to set a custom iterator. Can be omitted.
	 * 
	 * @param iterator the iterator to set
	 */
	public MandelbrotIteratorBuilder withIterator(MandelbrotIterator iterator) {
		this.iterator = Optional.ofNullable(iterator);
		return this;
	}

	/**
	 * Creates a custom iterator, if there isn't a custom one set.
	 * 
	 * @return A new Iterator, using the colorize object, if it is given.
	 */
	private MandelbrotIterator createIterator() {
		if (colorize.isPresent()) {
			return MandelbrotIterator.of(colorize.get());
		}
		return MandelbrotIterator.of();
	}

	/**
	 * Check, whether all pre conditions are fulfilled to do the calculation.
	 * 
	 * @return True, if the calculation may run, false otherwise.
	 */
	private boolean checkPreConditions() {
		return calculationArea.isPresent() && imageArea.isPresent() && maxIterations.isPresent();
	}

	/**
	 * Calculate
	 * 
	 * @return
	 */
	public Optional<MandelbrotImage> calculate() {
		Optional<MandelbrotImage> result = Optional.empty();
		if (checkPreConditions()) {
			MandelbrotIterator iterator = (this.iterator.isPresent()) ? this.iterator.get() : createIterator();
			result = Optional.ofNullable( //
			    iterator.drawMandelbrot( //
			        this.calculationArea.get(), //
			        this.maxIterations.getAsInt(), //
			        this.imageArea.get()));
		}
		return result;
	}
}
