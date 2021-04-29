/**
 * 
 */
package de.lexasoft.mandelbrot.dto;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.lexasoft.mandelbrot.PaletteVariant;

/**
 * @author admin
 *
 */
public class CalculationPropertiesDTO {

	@JsonProperty
	private PointDTO topLeft;
	@JsonProperty
	private PointDTO bottomRight;
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
	private List<ColorDTO> customColorPalette;
	@JsonProperty
	private int colorGrading;
	@JsonProperty
	private ColorDTO mandelbrotColor;

	public PointDTO getTopLeft() {
		return topLeft;
	}

	public void setTopLeft(PointDTO topLeft) {
		this.topLeft = topLeft;
	}

	public PointDTO getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(PointDTO bottomRight) {
		this.bottomRight = bottomRight;
	}

	public int getMaximumIterations() {
		return maximumIterations;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public String getImageFilename() {
		return imageFilename;
	}

	public PaletteVariant getPaletteVariant() {
		return paletteVariant;
	}

	public List<ColorDTO> getCustomColorPalette() {
		return customColorPalette;
	}

	public int getColorGrading() {
		return colorGrading;
	}

	public Color getMandelbrotColor() {
		return mandelbrotColor.getColor();
	}

	public static CalculationPropertiesDTO of(String yamlFilename)
	    throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.findAndRegisterModules();
		CalculationPropertiesDTO props = mapper.readValue(new File(yamlFilename), CalculationPropertiesDTO.class);
		return props;
	}

}
