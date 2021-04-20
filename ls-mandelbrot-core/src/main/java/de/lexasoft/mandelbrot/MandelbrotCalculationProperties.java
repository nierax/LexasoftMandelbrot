/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * @author admin
 *
 */
public class MandelbrotCalculationProperties {

	private MandelbrotPointPosition topLeft;
	private MandelbrotPointPosition bottomRight;
	private int maximumIterations;
	private int imageWidth;
	private int imageHeight;
	private ColorVariant colorVariant;
	private List<Color> colors;
	private int colorInterval;

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

	public ColorVariant getColorVariant() {
		return colorVariant;
	}

	public void setColorVariant(ColorVariant colorVariant) {
		this.colorVariant = colorVariant;
	}

	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}

	public int getColorInterval() {
		return colorInterval;
	}

	public void setColorInterval(int nrOfColors) {
		this.colorInterval = nrOfColors;
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
		return props;
	}

}
