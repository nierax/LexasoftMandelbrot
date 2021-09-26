/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nierax
 *
 */
public class MandelbrotCalculationProperties {

	private MandelbrotPointPosition topLeft;
	private MandelbrotPointPosition bottomRight;
	private int maximumIterations;
	private int imageWidth;
	private int imageHeight;
	private String imageFilename;
	private PaletteVariant paletteVariant;
	private List<Color> customColorPalette;
	private MandelbrotColorGrading colorGrading;
	private Color mandelbrotColor;
	private AspectRatioHandle aspectRatioHandle;
	private MandelbrotUtilityAPI utility;

	/**
	 * 
	 */
	MandelbrotCalculationProperties() {
		utility = MandelbrotUtilityAPI.of();
	}

	public MandelbrotPointPosition getTopLeft() {
		return topLeft;
	}

	public void setTopLeft(MandelbrotPointPosition topLeft) {
		this.topLeft = topLeft;
	}

	public MandelbrotPointPosition getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(MandelbrotPointPosition bottomRight) {
		this.bottomRight = bottomRight;
	}

	public int getMaximumIterations() {
		return maximumIterations;
	}

	public void setMaximumIterations(int maximumIterations) {
		this.maximumIterations = maximumIterations;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
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

	private double difference(double v0, double v1) {
		return Math.abs(v0 - v1);
	}

	private void calculateAspectRatioForCalculation() {
		// Check the number of omitted parameters?
		int count = countNaN();
		// All parameters given
		if (count == 0) {
			return;
		}
		// More than one parameter omitted.
		if (count > 1) {
			throw new IllegalArgumentException(
			    String.format("Just one calculation parameter can be omitted, but it were %s", count));
		}
		double ratioXtoY = (double) imageWidth / (double) imageHeight;
		// width is given
		if (!Double.isNaN(topLeft.cx()) && !Double.isNaN(bottomRight.cx())) {
			double height = difference(topLeft.cx(), bottomRight.cx()) / ratioXtoY;
			if (Double.isNaN(bottomRight.cy())) {
				bottomRight.setCy(topLeft.cy() - height);
			} else {
				topLeft.setCy(bottomRight.cy() + height);
			}
			return;
		}
		// height is given
		double width = difference(topLeft.cy(), bottomRight.cy()) * ratioXtoY;
		if (Double.isNaN(bottomRight.cx())) {
			bottomRight.setCx(topLeft.cx() + width);
		} else {
			topLeft.setCx(bottomRight.cx() - width);
		}
	}

	private int countNaN() {
		int count = 0;
		if (Double.isNaN(topLeft.cx())) {
			count++;
		}
		if (Double.isNaN(topLeft.cy())) {
			count++;
		}
		if (Double.isNaN(bottomRight.cx())) {
			count++;
		}
		if (Double.isNaN(bottomRight.cy())) {
			count++;
		}
		return count;
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
		if (Double.isNaN(topLeft.cx())) {
			throw new IllegalArgumentException(String.format(msg, "topLeft.cx"));
		}
		if (Double.isNaN(topLeft.cy())) {
			throw new IllegalArgumentException(String.format(msg, "topLeft.cy"));
		}
		if (Double.isNaN(bottomRight.cx())) {
			throw new IllegalArgumentException(String.format(msg, "bottomRight.cx"));
		}
		if (Double.isNaN(bottomRight.cy())) {
			throw new IllegalArgumentException(String.format(msg, "bottomRight.cy"));
		}
	}

	private void assertWidthAndHeightGiven() {
		String msg = "Image width and height must be given. %s missing.";
		if (imageWidth == 0) {
			throw new IllegalArgumentException(String.format(msg, "imageWidth"));
		}
		if (imageHeight == 0) {
			throw new IllegalArgumentException(String.format(msg, "imageHeight"));
		}
	}

	private void assertWidthOrHeightGiven() {
		if ((imageHeight == 0) && (imageWidth == 0)) {
			throw new IllegalArgumentException("Either image height oder image width must be given");
		}
	}

	/**
	 * Handle the aspect ratio fit in strategy.
	 */
	private void calculateAspectRatioFitIn() {
		double widthCalc0 = Math.abs(bottomRight.cx() - topLeft.cx());
		double heightCalc0 = Math.abs(topLeft.cy() - bottomRight.cy());
		double aspectRatioImage = (double) imageWidth / (double) imageHeight;
		double aspectRatioCalc = widthCalc0 / heightCalc0;
		int relation = Double.compare(aspectRatioImage, aspectRatioCalc);
		// aspect ratio of image and calculation are identical
		if (relation == 0) {
			// Nothing to do here
			return;
		}
		// aspect ratio of image is wider than aspect ratio of calculation
		if (relation > 0) {
			double widthCalc1 = heightCalc0 * aspectRatioImage;
			bottomRight.setCx(bottomRight.cx() - (widthCalc0 / 2) + (widthCalc1 / 2));
			topLeft.setCx(topLeft.cx() + (widthCalc0 / 2) - (widthCalc1 / 2));
		} else {
			// aspect ratio of image is higher than aspect ratio of calculation
			double heightCalc1 = widthCalc0 / aspectRatioImage;
			topLeft.setCy(topLeft.cy() - (heightCalc0 / 2) + (heightCalc1 / 2));
			bottomRight.setCy(bottomRight.cy() + (heightCalc0 / 2) - (heightCalc1 / 2));
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
			if (countNaN() == 0) {
				bottomRight.setCy(Double.NaN);
			}
			calculateAspectRatioForCalculation();
			return;
		case FOLLOW_CALCULATION:
			assertCalculationCompletelyGiven();
			assertWidthOrHeightGiven();
			Dimension result = utility.calculateAspectRatioForImage(topLeft, bottomRight,
			    new Dimension(imageWidth, imageHeight));
			imageWidth = (int) result.getWidth();
			imageHeight = (int) result.getHeight();
			return;
		case FITIN:
			assertAllParametersGiven();
			calculateAspectRatioFitIn();
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
		if (topLeft != null) {
			newProps.setTopLeft(MandelbrotPointPosition.of(topLeft.cx(), topLeft.cy()));
		}
		if (bottomRight != null) {
			newProps.setBottomRight(MandelbrotPointPosition.of(bottomRight.cx(), bottomRight.cy()));
		}
		newProps.setMaximumIterations(maximumIterations);
		newProps.setImageWidth(imageWidth);
		newProps.setImageHeight(imageHeight);
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
		props.setTopLeft(MandelbrotPointPosition.of(-2.02d, 1.2d));
		props.setBottomRight(MandelbrotPointPosition.of(0.8d, -1.2d));
		props.setPaletteVariant(PaletteVariant.BLUEWHITE);
		props.setColorGrading(MandelbrotColorGrading.of(ColorGradingStyle.LINE, 6));
		props.setImageHeight(405);
		props.setImageWidth(459);
		props.setAspectRatio(AspectRatioHandle.FITIN);
		props.setMaximumIterations(25);
		return props;
	}

}
