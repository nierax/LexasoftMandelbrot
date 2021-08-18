/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.PaletteVariant;
import de.lexasoft.mandelbrot.api.ValidationAPI;
import de.lexasoft.mandelbrot.ctrl.ColorAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.swing.model.ColorControllerModel;

/**
 * The controller for the color section
 * 
 * @author nierax
 *
 */
public class ColorController extends ModelChangingController<ColorControllerModel> implements ColorControllerModel {

	private ColorControlPanel view;

	// Attributes of the color controller model
	private PaletteVariant paletteVariant;
	private ColorGradingStyle colorGradingStyle;
	private int totalNrOfColors;

	/**
	 * 
	 */
	public ColorController(MandelbrotAttributesDTO initialModel, ColorControlPanel view) {
		initModel(initialModel);
		this.view = view;
		initView();
	}

	void initModel(MandelbrotAttributesDTO initialModel) {
		ColorAttributesDTO color = initialModel.getColor();
		this.paletteVariant = color.getPaletteVariant();
		this.colorGradingStyle = color.getColorGrading().getStyle();
		this.totalNrOfColors = color.getColorGrading().getColorsTotal();
	}

	/**
	 * Connects the input with the model.
	 */
	void initView() {
		view.getPaletteVariant().setSelectedItem(paletteVariant());
		view.getColorGradingStyle().setSelectedItem(gradingStyle());
		view.getTotalColors().setText(Integer.toString(totalNrOfColors()));
		view.getErrorText().setText("");
	}

	/**
	 * Registers the listeners to promote changes of the attributes.
	 */
	public void initController() {
		view.getPaletteVariant().addItemListener(e -> changePalettVariant(e));
		view.getTotalColors().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				changeTotalColors(e);
			}

			@Override
			public void focusGained(FocusEvent e) {
				// Nothing to do here
			}
		});
		view.getColorGradingStyle().addItemListener(e -> changeColorGradingStyle(e));
	}

	/**
	 * @TODO Add functionality for custom palette.
	 * @param variant
	 * @return
	 */
	private int nrOfColorsUngraded(PaletteVariant variant) {
		// Custom not supported yet. So let's think about it later
//		if (variant == PaletteVariant.CUSTOM) {
//			return model.getCustomColorPalette().size();
//		}
		return variant.nrOfColorsUngraded();
	}

	/**
	 * @param nrOfC
	 * @return
	 */
	private int handleTotalNrOfColorsCorrection(int nrOfC) {
		int nrOfCUngraded = nrOfColorsUngraded(paletteVariant());
		int minNrOfC = ValidationAPI.of().minimumNrOfColorsForGrading(nrOfCUngraded, gradingStyle());
		if (nrOfC < minNrOfC) {
			nrOfC = minNrOfC;
			view.getTotalColors().setText(Integer.toString(nrOfC));
			view.getErrorText().setText(String.format("Minimum number of colors set to minimum value %s.", minNrOfC));
		} else {
			view.getErrorText().setText("");
		}
		return nrOfC;
	}

	/**
	 * 
	 */
	private void checkAndChangeNrOfColorsCorrection() {
		int before = totalNrOfColors();
		int nrOfC = handleTotalNrOfColorsCorrection(before);
		if (before < nrOfC) {
			totalNrOfColors(nrOfC);
		}
	}

	private boolean isColorGradingStyleEnabled(PaletteVariant variant) {
		return variant != PaletteVariant.BLACK_WHITE;
	}

	private boolean isColorGradingNofCEnabled(PaletteVariant variant, ColorGradingStyle style) {
		return isColorGradingStyleEnabled(variant) && (style != ColorGradingStyle.NONE);
	}

	/**
	 * Handle what to do, if the color palette variant changes.
	 * <p>
	 * In case the value is set to BLACK_WHITE, the color grading section has to be
	 * disabled completely.
	 * <p>
	 * In case the value is set to any other value, the color grading option has to
	 * be opened to choose a color grading style. It should be disabled to enter a
	 * number of colors, if the style is set to NONE.
	 * <p>
	 * If the number of colors is too low for the given color palette and grading
	 * style, the value should be set to the minimum value.
	 * 
	 * @param evt
	 */
	void changePalettVariant(ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			PaletteVariant value = (PaletteVariant) evt.getItem();
			paletteVariant(value);
			view.getColorGradingStyle().setEnabled(isColorGradingStyleEnabled(value));
			view.getTotalColors().setEnabled(isColorGradingNofCEnabled(value, gradingStyle()));
			if (gradingStyle() != ColorGradingStyle.NONE) {
				checkAndChangeNrOfColorsCorrection();
			}
			fireEvent();
		}
	}

	/**
	 * Handle what to do, if the color grading style changes.
	 * <p>
	 * If the color grading is set to NONE, the number of colors has to be disabled.
	 * <p>
	 * If the number of colors is too low for the given color palette and grading
	 * style, the value should be set to the minimum value.
	 * 
	 * @param evt
	 */
	void changeColorGradingStyle(ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			ColorGradingStyle style = (ColorGradingStyle) evt.getItem();
			gradingStyle(style);
			view.getTotalColors().setEnabled((isColorGradingNofCEnabled(paletteVariant(), style)));
			checkAndChangeNrOfColorsCorrection();

			fireEvent();
		}
	}

	/**
	 * The total number of colors in color grading has changed.
	 * <p>
	 * If the number of colors is too low for the given color palette and grading
	 * style, the value should be set to the minimum value.
	 * 
	 * @param evt
	 */
	void changeTotalColors(FocusEvent evt) {
		String sNrOfC = view.getTotalColors().getText();
		if (!"".equals(sNrOfC)) {
			int nrOfC = Integer.parseInt(view.getTotalColors().getText());
			nrOfC = handleTotalNrOfColorsCorrection(nrOfC);
			totalNrOfColors(nrOfC);
			fireEvent();
		}
	}

	private void fireEvent() {
		fireModelChangedEvent(new ModelChangedEvent<ColorControllerModel>(this, this));
	}

	@Override
	public PaletteVariant paletteVariant() {
		return paletteVariant;
	}

	public PaletteVariant paletteVariant(PaletteVariant paletteVariant) {
		return this.paletteVariant = paletteVariant;
	}

	@Override
	public ColorGradingStyle gradingStyle() {
		return colorGradingStyle;
	}

	public ColorGradingStyle gradingStyle(ColorGradingStyle gradingStyle) {
		return colorGradingStyle = gradingStyle;
	}

	@Override
	public int totalNrOfColors() {
		return totalNrOfColors;
	}

	public int totalNrOfColors(int totalNrofColors) {
		return this.totalNrOfColors = totalNrofColors;
	}

	/**
	 * Replace the current model by reinitializing the controller and the
	 * corresponding view.
	 * 
	 * @param newModel
	 */
	public void replaceModel(MandelbrotAttributesDTO newModel) {
		initModel(newModel);
		initView();
	}
}
