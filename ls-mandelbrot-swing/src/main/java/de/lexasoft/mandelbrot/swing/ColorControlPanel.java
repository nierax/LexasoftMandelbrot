package de.lexasoft.mandelbrot.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

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
	private JTextField totalColors;
	private JComboBox paletteVariant;
	private JComboBox colorGradingStyle;
	private JLabel errorText;

	/**
	 * Create the panel.
	 */
	public ColorControlPanel() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Color control");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
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

		paletteVariant = new JComboBox();
		lblNewLabel_1.setLabelFor(paletteVariant);
		paletteVariant.setModel(new DefaultComboBoxModel(PaletteVariant.values()));
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

		colorGradingStyle = new JComboBox();
		lblNewLabel_2.setLabelFor(colorGradingStyle);
		colorGradingStyle.setModel(new DefaultComboBoxModel(ColorGradingStyle.values()));
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

		totalColors = new JTextField();
		lblNewLabel_3.setLabelFor(totalColors);
		GridBagConstraints gbc_totalColors = new GridBagConstraints();
		gbc_totalColors.insets = new Insets(0, 0, 5, 0);
		gbc_totalColors.fill = GridBagConstraints.HORIZONTAL;
		gbc_totalColors.gridx = 1;
		gbc_totalColors.gridy = 3;
		add(totalColors, gbc_totalColors);
		totalColors.setColumns(10);

		errorText = new JLabel("Error");
		errorText.setFont(new Font("Tahoma", Font.PLAIN, 10));
		errorText.setHorizontalAlignment(SwingConstants.LEFT);
		errorText.setForeground(Color.RED);
		GridBagConstraints gbc_errorText = new GridBagConstraints();
		gbc_errorText.gridwidth = 2;
		gbc_errorText.insets = new Insets(0, 0, 0, 5);
		gbc_errorText.anchor = GridBagConstraints.WEST;
		gbc_errorText.gridx = 0;
		gbc_errorText.gridy = 4;
		add(errorText, gbc_errorText);

	}

	public JComboBox getPaletteVariant() {
		return paletteVariant;
	}

	public JComboBox getColorGradingStyle() {
		return colorGradingStyle;
	}

	public JTextField getTotalColors() {
		return totalColors;
	}

	public JLabel getErrorText() {
		return errorText;
	}
}
