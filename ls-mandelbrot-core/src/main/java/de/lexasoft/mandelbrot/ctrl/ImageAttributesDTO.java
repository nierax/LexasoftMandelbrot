/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.lexasoft.mandelbrot.api.AspectRatioHandle;

/**
 * All attributes, describing the image and its properties.
 * 
 * @author nierax
 *
 */
public class ImageAttributesDTO {

	@JsonProperty
	private int imageWidth;
	@JsonProperty
	private int imageHeight;
	@JsonProperty
	private String imageFilename;
	@JsonProperty
	private AspectRatioHandle aspectRatioHandle;

	/**
	 * @return the imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * @param imageWidth the imageWidth to set
	 */
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * @return the imageHeight
	 */
	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * @param imageHeight the imageHeight to set
	 */
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	/**
	 * @return the imageFilename
	 */
	public String getImageFilename() {
		return imageFilename;
	}

	/**
	 * @param imageFilename the imageFilename to set
	 */
	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}

	/**
	 * Checks, whether a width is given (width > 0).
	 * 
	 * @return True, if width is set, false otherwise.
	 */
	@JsonIgnore
	public boolean isImageWidthSet() {
		return imageWidth > 0;
	}

	/**
	 * Checks, whether an height is given (height > 0).
	 * 
	 * @return True, if height is set, false otherwise.
	 */
	@JsonIgnore
	public boolean isImageHeightSet() {
		return imageHeight > 0;
	}

	/**
	 * Checks, whether both height and width are set.
	 * 
	 * @return True, when height and width are given, false otherwise;
	 */
	@JsonIgnore
	public boolean isImageDimensionSet() {
		return isImageWidthSet() && isImageHeightSet();
	}

	/**
	 * @return the aspectRatioHandle
	 */
	public AspectRatioHandle getAspectRatioHandle() {
		return aspectRatioHandle;
	}

	/**
	 * @param aspectRatioHandle the aspectRatioHandle to set
	 */
	public void setAspectRatioHandle(AspectRatioHandle aspectRatioHandle) {
		this.aspectRatioHandle = aspectRatioHandle;
	}

	/**
	 * Creates {@link ImageAttributesDTO} with default values.
	 * 
	 * @return
	 */
	public final static ImageAttributesDTO ofDefault() {
		ImageAttributesDTO image = new ImageAttributesDTO();
		image.setImageWidth(459);
		image.setImageHeight(405);
		image.setAspectRatioHandle(AspectRatioHandle.FITIN);
		return image;
	}

}
