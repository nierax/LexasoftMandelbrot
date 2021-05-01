/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import de.lexasoft.mandelbrot.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * Maps from the properties DTO to the properties.
 * 
 * @author nierax
 *
 */
public class DTO2PropertiesMapper {

	private Double mapDoubleFromString(String val) {
		return ((val == null) || ("".equals(val) || ("auto".equals(val))) ? Double.NaN : Double.parseDouble(val));
	}

	private MandelbrotPointPosition mapPoint(PointDTO point) {
		double cx = mapDoubleFromString(point.getCx());
		double cy = mapDoubleFromString(point.getCy());
		return MandelbrotPointPosition.of(cx, cy);
	}

	private List<Color> mapListOfColorDTO2(List<ColorDTO> listOfColorDTO) {
		List<Color> colors = new ArrayList<>();
		for (ColorDTO colorDTO : listOfColorDTO) {
			colors.add(colorDTO.getColor());
		}
		return colors;
	}

	/**
	 * The method to map from dto to properties.
	 * 
	 * @param dto
	 * @return
	 */
	public List<MandelbrotCalculationProperties> mapDTO2Properties(CalculationPropertiesDTO dto) {
		List<MandelbrotCalculationProperties> listOfProps = new ArrayList<>();
		MandelbrotCalculationProperties props = MandelbrotCalculationProperties.of();
		props.setTopLeft(mapPoint(dto.getTopLeft()));
		props.setBottomRight(mapPoint(dto.getBottomRight()));
		props.setMaximumIterations(dto.getMaximumIterations());
		props.setImageWidth(dto.getImageWidth());
		props.setImageHeight(dto.getImageHeight());
		props.setImageFilename(dto.getImageFilename());
		props.setPaletteVariant(dto.getPaletteVariant());
		props.setCustomColorPalette(mapListOfColorDTO2(dto.getCustomColorPalette()));
		props.setColorGrading(dto.getColorGrading());
		props.setMandelbrotColor(dto.getMandelbrotColor());
		props.normalize();
		listOfProps.add(props);
		return listOfProps;
	}

	public static DTO2PropertiesMapper of() {
		return new DTO2PropertiesMapper();
	}
}
