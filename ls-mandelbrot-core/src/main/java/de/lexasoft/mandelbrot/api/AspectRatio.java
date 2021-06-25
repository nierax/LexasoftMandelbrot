/**
 * 
 */
package de.lexasoft.mandelbrot.api;

/**
 * Controls how to react on different aspect ratios between the given
 * calculation area and the image.
 * 
 * @author nierax
 *
 */
public enum AspectRatio {

	/**
	 * Ignore aspect ratio, the image might be stretched or compressed
	 */
	IGNORE("Ignore"),

	/**
	 * Fit the complete calculation area in the image. This will either widen width
	 * or height of the calculation to ensure, that the given calculation area is
	 * completely visible in the image.
	 */
	FITIN("Fit in"),

	/**
	 * The aspect ratio in the image is used to recalculate image width or image
	 * height.
	 * <p>
	 * Whether image height or image width are recalculate depends on, what
	 * parameter is omitted. If both parameters are set, the image height will be
	 * recalculated.
	 */
	FOLLOW_CALCULATION("Follow calculation"),

	/**
	 * The aspect ratio of the image is used to recalculate calculation's
	 * parameters. Thus calculation's image may be cut or extended to fit into the
	 * image parameters.
	 * <p>
	 * By default the bottom line is changed, but You can redefine that by omitting
	 * one arbitrary parameter.
	 */
	FOLLOW_IMAGE("Follow image");

	private String message;

	/**
	 * @param message
	 */
	private AspectRatio(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

}
