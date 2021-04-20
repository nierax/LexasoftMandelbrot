/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

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
	private Color color1;
	private Color color2;
	private Color color3;
	private int nrOfColors;

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

	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	public Color getColor3() {
		return color3;
	}

	public void setColor3(Color color3) {
		this.color3 = color3;
	}

	public int getNrOfColors() {
		return nrOfColors;
	}

	public void setNrOfColors(int nrOfColors) {
		this.nrOfColors = nrOfColors;
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
