/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

/**
 * Controls how to react on different aspect ratios between the given
 * calculation area and the image.
 * 
 * @author nierax
 *
 */
public enum AspectRatioDTO {

	/**
	 * Ignore aspect ratio, the image might be stretched or compressed
	 */
	IGNORE,

	/**
	 * Fit the complete calculation area in the image. This will either widen width
	 * or height of the calculation to ensure, that the given calculation area is
	 * completely visible in the image.
	 */
	FILL;

}
