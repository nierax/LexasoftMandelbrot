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
		// Set first calculation directly
		MandelbrotCalculationProperties props = mapSingleCalculation(dto, MandelbrotCalculationProperties.of());
		listOfProps.add(props);
		// If there are more calculations given
		List<CalculationPropertiesDTO> followingCalcs = dto.getFollowing();
		if (followingCalcs != null && !followingCalcs.isEmpty()) {
			for (CalculationPropertiesDTO calc : followingCalcs) {
				listOfProps.add(mapSingleCalculation(calc, props.cloneValues()));
			}
		}
		// At last: Normalize all properties in list
		listOfProps.stream().forEach((p) -> p.normalize());
		return listOfProps;
	}

	private MandelbrotCalculationProperties mapSingleCalculation(CalculationPropertiesDTO dto,
	    MandelbrotCalculationProperties props) {
		if (dto.getTopLeft() != null) {
			props.setTopLeft(mapPoint(dto.getTopLeft()));
		}
		if (dto.getBottomRight() != null) {
			props.setBottomRight(mapPoint(dto.getBottomRight()));
		}
		if (dto.getMaximumIterations() > 0) {
			props.setMaximumIterations(dto.getMaximumIterations());
		}
		if (dto.getImageWidth() > 0) {
			props.setImageWidth(dto.getImageWidth());
		}
		if (dto.getImageHeight() > 0) {
			props.setImageHeight(dto.getImageHeight());
		}
		if (dto.getImageFilename() != null && !"".equals(dto.getImageFilename())) {
			props.setImageFilename(dto.getImageFilename());
		}
		if (dto.getPaletteVariant() != null) {
			props.setPaletteVariant(dto.getPaletteVariant());
		}
		if (dto.getCustomColorPalette() != null) {
			props.setCustomColorPalette(mapListOfColorDTO2(dto.getCustomColorPalette()));
		}
		if (dto.getColorGrading() > 0) {
			props.setColorGrading(dto.getColorGrading());
		}
		if (dto.getMandelbrotColor() != null) {
			props.setMandelbrotColor(dto.getMandelbrotColor().getColor());
		}
		return props;
	}

	public static DTO2PropertiesMapper of() {
		return new DTO2PropertiesMapper();
	}
}
