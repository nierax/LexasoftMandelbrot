/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.text.ParseException;

import de.lexasoft.common.model.Message;
import de.lexasoft.common.model.MessageController;
import de.lexasoft.common.model.MessageId;
import de.lexasoft.common.model.MessageSeverity;
import de.lexasoft.common.model.MessageText;
import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.PaletteVariant;
import de.lexasoft.mandelbrot.api.ValidationAPI;
import de.lexasoft.mandelbrot.ctrl.ColorAttributesDTO;
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
	private MessageController msgCtrl;

	/**
	 * 
	 */
	public ColorController(ColorAttributesDTO colorModel, ColorControlPanel view) {
		initModel(colorModel);
		this.view = view;
		initView();
	}

	void initModel(ColorAttributesDTO colorModel) {
		this.paletteVariant = colorModel.getPaletteVariant();
		this.colorGradingStyle = colorModel.getColorGrading().getStyle();
		this.totalNrOfColors = colorModel.getColorGrading().getColorsTotal();
	}

	/**
	 * Connects the input with the model.
	 */
	void initView() {
		view.getPaletteVariant().setSelectedItem(paletteVariant());
		view.getColorGradingStyle().setSelectedItem(gradingStyle());
		try {
			view.getTotalColors().setBasicValue(totalNrOfColors());
		} catch (ParseException e) {
			throw new MandelbrotException("Error parsing total number of colors", e);
		}
		msgCtrl = new MessageController(view.getMessagePanel());
	}

	/**
	 * Registers the listeners to promote changes of the attributes.
	 */
	public void initController() {
		view.getPaletteVariant().addItemListener(this::changePalettVariant);
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
		if (variant == PaletteVariant.CUSTOM) {
			// BLUEWHITE is the default, when no custom palette is given.
			return PaletteVariant.BLUEWHITE.nrOfColorsUngraded();
		}
		return variant.nrOfColorsUngraded();
	}

	private void pushColorErrorMsg2GUI(String key, String message) {
		Message msg = Message.of(MessageId.of(key), MessageText.of(message), MessageSeverity.ERROR);
		msgCtrl.displayMessage(msg);
	}

	private void removeColorErrorMsgFromGUI(String key) {
		msgCtrl.unDisplayMessage(MessageId.of(key));
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
			try {
				view.getTotalColors().setBasicValue(nrOfC);
			} catch (ParseException e) {
				pushColorErrorMsg2GUI("color-1", String.format("Error parsing value for total number of colors \"%s\"", nrOfC));
			}
			pushColorErrorMsg2GUI("nrOfC", String.format("Minimum number of colors set to minimum value %s.", minNrOfC));
		} else {
			removeColorErrorMsgFromGUI("nrOfC");
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
			if (value == PaletteVariant.CUSTOM) {
				pushColorErrorMsg2GUI("customPalette", "Custom palettes are not supported yet in swing gui.");
			} else {
				removeColorErrorMsgFromGUI("customPalette");
			}
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
		int nrOfC = -1;
		try {
			nrOfC = view.getTotalColors().getBasicValue();
			nrOfC = handleTotalNrOfColorsCorrection(nrOfC);
			totalNrOfColors(nrOfC);
			fireEvent();
		} catch (ParseException e) {
			throw new MandelbrotException(String.format("Error parsing value \"%s\" for total number of colors.", nrOfC));
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
	public void replaceModel(ColorAttributesDTO newModel) {
		initModel(newModel);
		initView();
	}
}
