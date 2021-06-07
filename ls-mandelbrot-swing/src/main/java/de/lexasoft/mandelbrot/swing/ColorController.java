/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.PaletteVariant;
import de.lexasoft.mandelbrot.api.ValidationAPI;

/**
 * The controller for the color section
 * 
 * @author nierax
 *
 */
public class ColorController {

	private MandelbrotCalculationProperties model;
	private ColorControlPanel view;
	private MandelbrotCanvas canvas;

	/**
	 * 
	 */
	public ColorController(MandelbrotCalculationProperties model, ColorControlPanel view, MandelbrotCanvas canvas) {
		this.model = model;
		this.view = view;
		this.canvas = canvas;
		initView();
	}

	/**
	 * Connects the input with the model.
	 */
	public void initView() {
		view.getPaletteVariant().setSelectedItem(model.getPaletteVariant());
		view.getColorGradingStyle().setSelectedItem(model.getColorGrading().getStyle());
		view.getTotalColors().setText(Integer.toString(model.getColorGrading().getColorsTotal()));
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

	private int nrOfColorsUngraded(PaletteVariant variant) {
		if (variant == PaletteVariant.CUSTOM) {
			return model.getCustomColorPalette().size();
		}
		return variant.getNrOfColorsUngraded();
	}

	/**
	 * @param nrOfC
	 * @return
	 */
	private int handleTotalNrOfColorsCorrection(int nrOfC) {
		int nrOfCUngraded = nrOfColorsUngraded(model.getPaletteVariant());
		int minNrOfC = ValidationAPI.of().minimumNrOfColorsForGrading(nrOfCUngraded, model.getColorGrading().getStyle());
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
		int before = model.getColorGrading().getColorsTotal();
		int nrOfC = handleTotalNrOfColorsCorrection(before);
		if (before < nrOfC) {
			model.getColorGrading().setColorsTotal(nrOfC);
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
			model.setPaletteVariant(value);
			view.getColorGradingStyle().setEnabled(isColorGradingStyleEnabled(value));
			view.getTotalColors().setEnabled(isColorGradingNofCEnabled(value, model.getColorGrading().getStyle()));
			if (model.getColorGrading().getStyle() != ColorGradingStyle.NONE) {
				checkAndChangeNrOfColorsCorrection();
			}
			canvas.modelChanged();
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
			model.getColorGrading().setStyle(style);
			view.getTotalColors().setEnabled((isColorGradingNofCEnabled(model.getPaletteVariant(), style)));
			checkAndChangeNrOfColorsCorrection();

			canvas.modelChanged();
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
			model.getColorGrading().setColorsTotal(nrOfC);
			canvas.modelChanged();
		}
	}
}
