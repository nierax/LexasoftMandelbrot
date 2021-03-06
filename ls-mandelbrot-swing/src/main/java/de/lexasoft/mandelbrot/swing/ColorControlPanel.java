package de.lexasoft.mandelbrot.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import de.lexasoft.common.model.MessagePanel;
import de.lexasoft.common.swing.JFormatterFactory;
import de.lexasoft.common.swing.LSJFormattedTextField;
import de.lexasoft.common.swing.MessagePanelTextAreaImpl;
import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.PaletteVariant;

/**
 * The view for the color controls.
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class ColorControlPanel extends JPanel {
	private LSJFormattedTextField<Integer> totalColors;
	private JComboBox<PaletteVariant> paletteVariant;
	private JComboBox<ColorGradingStyle> colorGradingStyle;
	private MessagePanelTextAreaImpl messagePanel;

	/**
	 * Create the panel.
	 */
	public ColorControlPanel() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Color control");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Color Palette");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		add(lblNewLabel_1, gbc_lblNewLabel_1);

		paletteVariant = new JComboBox<PaletteVariant>();
		lblNewLabel_1.setLabelFor(paletteVariant);
		paletteVariant.setModel(new DefaultComboBoxModel<PaletteVariant>(PaletteVariant.values()));
		GridBagConstraints gbc_paletteVariant = new GridBagConstraints();
		gbc_paletteVariant.insets = new Insets(0, 0, 5, 0);
		gbc_paletteVariant.fill = GridBagConstraints.HORIZONTAL;
		gbc_paletteVariant.gridx = 1;
		gbc_paletteVariant.gridy = 1;
		add(paletteVariant, gbc_paletteVariant);

		JLabel lblNewLabel_2 = new JLabel("Color Grading Style");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		add(lblNewLabel_2, gbc_lblNewLabel_2);

		colorGradingStyle = new JComboBox<ColorGradingStyle>();
		lblNewLabel_2.setLabelFor(colorGradingStyle);
		colorGradingStyle.setModel(new DefaultComboBoxModel<ColorGradingStyle>(ColorGradingStyle.values()));
		GridBagConstraints gbc_colorGradingStyle = new GridBagConstraints();
		gbc_colorGradingStyle.insets = new Insets(0, 0, 5, 0);
		gbc_colorGradingStyle.fill = GridBagConstraints.HORIZONTAL;
		gbc_colorGradingStyle.gridx = 1;
		gbc_colorGradingStyle.gridy = 2;
		add(colorGradingStyle, gbc_colorGradingStyle);

		JLabel lblNewLabel_3 = new JLabel("Total number of colors");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		add(lblNewLabel_3, gbc_lblNewLabel_3);

		totalColors = new LSJFormattedTextField<>(JFormatterFactory.createIntegerFormatter());
		lblNewLabel_3.setLabelFor(totalColors);
		GridBagConstraints gbc_totalColors = new GridBagConstraints();
		gbc_totalColors.insets = new Insets(0, 0, 5, 0);
		gbc_totalColors.fill = GridBagConstraints.HORIZONTAL;
		gbc_totalColors.gridx = 1;
		gbc_totalColors.gridy = 3;
		add(totalColors, gbc_totalColors);
		totalColors.setColumns(10);

		messagePanel = new MessagePanelTextAreaImpl();
		GridBagConstraints gbc_messagePanel = new GridBagConstraints();
		gbc_messagePanel.gridwidth = 2;
		gbc_messagePanel.insets = new Insets(0, 0, 0, 5);
		gbc_messagePanel.fill = GridBagConstraints.BOTH;
		gbc_messagePanel.gridx = 0;
		gbc_messagePanel.gridy = 4;
		add(messagePanel, gbc_messagePanel);

	}

	public JComboBox<PaletteVariant> getPaletteVariant() {
		return paletteVariant;
	}

	public JComboBox<ColorGradingStyle> getColorGradingStyle() {
		return colorGradingStyle;
	}

	public LSJFormattedTextField<Integer> getTotalColors() {
		return totalColors;
	}

	public MessagePanel getMessagePanel() {
		return messagePanel;
	}
}
