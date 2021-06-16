/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.awt.image.BufferedImage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonIgnore
	private BufferedImage image;
	@JsonProperty
	private AspectRatioDTO aspectRatioHandle;

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
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * @return the aspectRatioHandle
	 */
	public AspectRatioDTO getAspectRatioHandle() {
		return aspectRatioHandle;
	}

	/**
	 * @param aspectRatioHandle the aspectRatioHandle to set
	 */
	public void setAspectRatioHandle(AspectRatioDTO aspectRatioHandle) {
		this.aspectRatioHandle = aspectRatioHandle;
	}

}
