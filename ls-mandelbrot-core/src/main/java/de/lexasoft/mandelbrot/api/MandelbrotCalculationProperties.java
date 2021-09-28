/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nierax
 *
 */
public class MandelbrotCalculationProperties {

	private CalculationArea calculation;
	private int maximumIterations;
	private ImageArea image;
	private String imageFilename;
	private PaletteVariant paletteVariant;
	private List<Color> customColorPalette;
	private MandelbrotColorGrading colorGrading;
	private Color mandelbrotColor;
	private AspectRatioHandle aspectRatioHandle;

	public int getMaximumIterations() {
		return maximumIterations;
	}

	public void setMaximumIterations(int maximumIterations) {
		this.maximumIterations = maximumIterations;
	}

	public PaletteVariant getPaletteVariant() {
		return paletteVariant;
	}

	public void setPaletteVariant(PaletteVariant paletteVariant) {
		this.paletteVariant = paletteVariant;
	}

	public List<Color> getCustomColorPalette() {
		return customColorPalette;
	}

	public void setCustomColorPalette(List<Color> colors) {
		this.customColorPalette = colors;
	}

	public MandelbrotColorGrading getColorGrading() {
		return colorGrading;
	}

	public void setColorGrading(MandelbrotColorGrading colorGrading) {
		this.colorGrading = colorGrading;
	}

	public String getImageFilename() {
		return imageFilename;
	}

	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}

	/**
	 * Default value is {@link AspectRatioHandle#FITIN}
	 * 
	 * @return the aspectRatioHandle
	 */
	public AspectRatioHandle getAspectRatio() {
		if (aspectRatioHandle == null) {
			aspectRatioHandle = AspectRatioHandle.FITIN;
		}
		return aspectRatioHandle;
	}

	/**
	 * @param aspectRatioHandle the aspectRatioHandle to set
	 */
	public void setAspectRatio(AspectRatioHandle aspectRatioHandle) {
		this.aspectRatioHandle = aspectRatioHandle;
	}

	/**
	 * The index will be added, divided by "_" before the file type in the filename.
	 * It is filled up with "0" to the length of nrOfDigits digits. If the index has
	 * more than nrOfDigits digits, the index will be added in its original length.
	 * 
	 * @param idx The index number to add
	 * @return The file name with the added index (getImageFilename() will also get
	 *         this file name).
	 */
	public String addIndex2ImageFilename(int nrOfDigits, int idx) {
		int posOfPoint = this.imageFilename.lastIndexOf(".");
		String fileTypeWithPoint = this.imageFilename.substring(posOfPoint);
		String fileName2Point = this.imageFilename.substring(0, posOfPoint);
		String format = (nrOfDigits > 0) ? "%s_%0" + nrOfDigits + "d%s" : "%s_%d%s"; // for nrOfDigits=4: "%s_%04d%s"
		this.imageFilename = String.format(format, fileName2Point, idx, fileTypeWithPoint);
		return this.imageFilename;
	}

	public Color getMandelbrotColor() {
		return mandelbrotColor;
	}

	public void setMandelbrotColor(Color mandelbrotColor) {
		this.mandelbrotColor = mandelbrotColor;
	}

	private void assertAllParametersGiven() {
		assertCalculationCompletelyGiven();
		assertWidthAndHeightGiven();
	}

	/**
	 * 
	 */
	private void assertCalculationCompletelyGiven() {
		String msg = "Not all parameters given. %s missing.";
		if (calculation == null) {
			throw new IllegalArgumentException(String.format(msg, "Calculation"));
		}
		if (Double.isNaN(calculation.topLeft().cx())) {
			throw new IllegalArgumentException(String.format(msg, "topLeft.cx"));
		}
		if (Double.isNaN(calculation.topLeft().cy())) {
			throw new IllegalArgumentException(String.format(msg, "topLeft.cy"));
		}
		if (Double.isNaN(calculation.bottomRight().cx())) {
			throw new IllegalArgumentException(String.format(msg, "bottomRight.cx"));
		}
		if (Double.isNaN(calculation.bottomRight().cy())) {
			throw new IllegalArgumentException(String.format(msg, "bottomRight.cy"));
		}
	}

	private void assertWidthAndHeightGiven() {
		String msg = "Image width and height must be given. %s missing.";
		if (image.width() == 0) {
			throw new IllegalArgumentException(String.format(msg, "imageWidth"));
		}
		if (image.height() == 0) {
			throw new IllegalArgumentException(String.format(msg, "imageHeight"));
		}
	}

	private void assertWidthOrHeightGiven() {
		if ((image.height() == 0) && (image.width() == 0)) {
			throw new IllegalArgumentException("Either image height oder image width must be given");
		}
	}

	/**
	 * Control aspect ratio correction, depending on the aspect ratio handle.
	 * 
	 * @param aspectRatioHandle
	 */
	void handleAspectRatio(AspectRatioHandle aspectRatioHandle) {
		switch (aspectRatioHandle) {
		case IGNORE:
			assertAllParametersGiven();
			// Nothing more to do here, just calculate as provided
			return;
		case FOLLOW_IMAGE:
			assertWidthAndHeightGiven();
			calculation.followAspectRatio(image);
			return;
		case FOLLOW_CALCULATION:
			assertCalculationCompletelyGiven();
			assertWidthOrHeightGiven();
			image.followAspectRatio(calculation);
			return;
		case FITIN:
			assertAllParametersGiven();
			calculation.fitIn(image);
		default:
			break;
		}
	}

	/**
	 * Calculates the properties, that are not given such as aspect ratio.
	 */
	public void normalize() {
		handleAspectRatio(getAspectRatio());
	}

	/**
	 * Clones this object with all values containing new objects with same values.
	 * <p>
	 * Empty values will be kept empty in the clone.
	 * 
	 * @return Cloned object of these properties.
	 */
	public MandelbrotCalculationProperties cloneValues() {
		MandelbrotCalculationProperties newProps = MandelbrotCalculationProperties.of();
		if (calculation != null) {
			newProps.setCalculation(calculation.cloneValues());
		}
		newProps.setMaximumIterations(maximumIterations);
		if (image != null) {
			newProps.setImage(ImageArea.of(image.width(), image.height()));
		}
		newProps.setImageFilename(imageFilename);
		newProps.setAspectRatio(getAspectRatio());
		newProps.setPaletteVariant(paletteVariant);
		if (customColorPalette != null) {
			newProps.setCustomColorPalette(new ArrayList<>());
			for (Color color : customColorPalette) {
				newProps.getCustomColorPalette().add(color);
			}
		}
		newProps.setColorGrading(colorGrading);
		newProps.setMandelbrotColor(mandelbrotColor);
		return newProps;
	}

	/**
	 * Static factory method to build a new object of
	 * {@link MandelbrotCalculationProperties}
	 * 
	 * @return New instance of {@link MandelbrotCalculationProperties}
	 */
	public static MandelbrotCalculationProperties of() {
		return new MandelbrotCalculationProperties();
	}

	public static MandelbrotCalculationProperties ofDefault() {
		MandelbrotCalculationProperties props = of();
		props.setCalculation(
		    CalculationArea.of(MandelbrotPointPosition.of(-2.02d, 1.2d), MandelbrotPointPosition.of(0.8d, -1.2d)));
		props.setPaletteVariant(PaletteVariant.BLUEWHITE);
		props.setColorGrading(MandelbrotColorGrading.of(ColorGradingStyle.LINE, 6));
		props.setImage(ImageArea.of(459, 405));
		props.setAspectRatio(AspectRatioHandle.FITIN);
		props.setMaximumIterations(25);
		return props;
	}

	/**
	 * @return the image
	 */
	public ImageArea getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(ImageArea image) {
		this.image = image;
	}

	/**
	 * @deprecated Use {@link MandelbrotCalculationProperties#getImage#width()}
	 *             instead.
	 * @return
	 */
	@Deprecated
	public int getImageWidth() {
		return (image == null) ? 0 : image.width();
	}

	/**
	 * @deprecated Use {@link MandelbrotCalculationProperties#getImage#height()}
	 *             instead.
	 * @return
	 */
	@Deprecated
	public int getImageHeight() {
		return (image == null) ? 0 : image.height();
	}

	/**
	 * @return the calculation
	 */
	public CalculationArea getCalculation() {
		return calculation;
	}

	/**
	 * @param calculation the calculation to set
	 */
	public void setCalculation(CalculationArea calculation) {
		this.calculation = calculation;
	}

	/**
	 * @deprecated Use {@link #getCalculation()} instead
	 * @return
	 */
	@Deprecated
	public MandelbrotPointPosition getTopLeft() {
		return (calculation == null) ? null : calculation.topLeft();
	}

	/**
	 * @deprecated Use {@link #getCalculation()} instead
	 * @return
	 */
	@Deprecated
	public MandelbrotPointPosition getBottomRight() {
		return (calculation == null) ? null : calculation.bottomRight();
	}

}
