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
	 * Handle what to do, if the color palette variant changes.
	 * 
	 * @param evt
	 */
	void changePalettVariant(ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			PaletteVariant value = (PaletteVariant) evt.getItem();
			model.setPaletteVariant(value);
			canvas.modelChanged();
		}
	}

	/**
	 * The total number of colors in color grading has changed.
	 * 
	 * @param evt
	 */
	void changeTotalColors(FocusEvent evt) {
		String sNrOfC = view.getTotalColors().getText();
		if (!"".equals(sNrOfC)) {
			int nrOfC = Integer.parseInt(view.getTotalColors().getText());
			int nrOfCUngraded = nrOfColorsUngraded(model.getPaletteVariant());
			int minNrOfC = ValidationAPI.of().minimumNrOfColorsForGrading(nrOfCUngraded, model.getColorGrading().getStyle());
			if (nrOfC < minNrOfC) {
				nrOfC = minNrOfC;
				view.getTotalColors().setText(Integer.toString(nrOfC));
				view.getErrorText().setText(String.format("Minimum number of colors is %s for grading in style %s.", minNrOfC,
				    model.getColorGrading().getStyle()));
			} else {
				view.getErrorText().setText("");
			}
			model.getColorGrading().setColorsTotal(nrOfC);
			canvas.modelChanged();
		}
	}

	void changeColorGradingStyle(ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			ColorGradingStyle style = (ColorGradingStyle) evt.getItem();
			model.getColorGrading().setStyle(style);
			canvas.modelChanged();
		}
	}
}
