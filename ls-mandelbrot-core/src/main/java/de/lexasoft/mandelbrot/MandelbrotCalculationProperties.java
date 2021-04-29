/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * @author admin
 *
 */
public class MandelbrotCalculationProperties {

	@JsonProperty
	private MandelbrotPointPosition topLeft;
	@JsonProperty
	private MandelbrotPointPosition bottomRight;
	@JsonProperty
	private int maximumIterations;
	@JsonProperty
	private int imageWidth;
	@JsonProperty
	private int imageHeight;
	@JsonProperty
	private String imageFilename;
	@JsonProperty
	private PaletteVariant paletteVariant;
	@JsonProperty
	private List<Color> customColorPalette;
	@JsonProperty
	private int colorGrading;
	@JsonProperty
	private Color mandelbrotColor;

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

	public int getColorGrading() {
		return colorGrading;
	}

	public void setColorGrading(int nrOfColors) {
		this.colorGrading = nrOfColors;
	}

	public String getImageFilename() {
		return imageFilename;
	}

	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}

	public Color getMandelbrotColor() {
		return mandelbrotColor;
	}

	public void setMandelbrotColor(Color mandelbrotColor) {
		this.mandelbrotColor = mandelbrotColor;
	}

	private Color cloneColor(Color color) {
		return new Color(color.getRGB());
	}

	/**
	 * Unfortunately necessary, as color objects directly read from YAML do not work
	 * correctly.
	 */
	private void cloneColors() {
		if (mandelbrotColor != null) {
			mandelbrotColor = cloneColor(mandelbrotColor);
		}
		if (customColorPalette != null) {
			int i = 0;
			for (Color color : customColorPalette) {
				customColorPalette.set(i, cloneColor(color));
				i++;
			}
		}
	}

	private double difference(double v0, double v1) {
		return Math.abs(v0 - v1);
	}

	/**
	 * Determine aspect ratio from either calculation to image or the other way
	 * round.
	 */
	private void calculateAspectRatio() {
		if ((imageHeight == 0) && (imageWidth == 0)) {
			throw new IllegalArgumentException("Either image height oder image width must be given");
		}
		if ((imageHeight > 0) && (imageWidth > 0)) {
			calculateAspectRatioForCalculation();
			return;
		}
		calculateAspectRatioForImage();
	}

	private void calculateAspectRatioForImage() {
		double ratioXtoY = difference(topLeft.cx(), bottomRight.cx()) / difference(topLeft.cy(), bottomRight.cy());
		if (imageHeight == 0) {
			imageHeight = (int) (imageWidth / ratioXtoY);
		} else {
			imageWidth = (int) (imageHeight * ratioXtoY);
		}
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
			} else if (Double.isNaN(topLeft.cy())) {
				topLeft.setCy(bottomRight.cy() + height);
			}
			return;
		}
		// height is given
		double width = difference(topLeft.cy(), bottomRight.cy()) * ratioXtoY;
		if (Double.isNaN(bottomRight.cx())) {
			bottomRight.setCx(topLeft.cx() + width);
		} else if (Double.isNaN(topLeft.cx())) {
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

	/**
	 * 
	 * @param yamlFilename
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static MandelbrotCalculationProperties of(String yamlFilename)
	    throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.findAndRegisterModules();
		MandelbrotCalculationProperties props = mapper.readValue(new File(yamlFilename),
		    MandelbrotCalculationProperties.class);
		props.cloneColors();
		props.calculateAspectRatio();
		return props;
	}

}
