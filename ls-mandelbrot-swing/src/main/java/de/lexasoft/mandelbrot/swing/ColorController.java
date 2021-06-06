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

	/**
	 * Handle what to do, if the color palette variant changes.
	 * 
	 * @param evt
	 */
	private void changePalettVariant(ItemEvent evt) {
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
	private void changeTotalColors(FocusEvent evt) {
		String sNrOfC = view.getTotalColors().getText();
		if (!"".equals(sNrOfC)) {
			model.getColorGrading().setColorsTotal(Integer.parseInt(view.getTotalColors().getText()));
			canvas.modelChanged();
		}
	}

	private void changeColorGradingStyle(ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			ColorGradingStyle style = (ColorGradingStyle) evt.getItem();
			model.getColorGrading().setStyle(style);
			canvas.modelChanged();
		}
	}
}
