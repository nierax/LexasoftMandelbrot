package de.lexasoft.mandelbrot.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import de.lexasoft.mandelbrot.swing.model.AspectRatio;

@SuppressWarnings("serial")
public class CalculationPanel extends JPanel {
	private JTextField tlcx;
	private JTextField tlcy;
	private JTextField brcx;
	private JTextField brcy;
	private JTextField maxIter;
	private JComboBox<AspectRatio> aspectRatio;

	/**
	 * Create the panel.
	 */
	public CalculationPanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Calculation");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Top Left Corner");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		add(lblNewLabel_1, gbc_lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("cx");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		add(lblNewLabel_2, gbc_lblNewLabel_2);

		tlcx = new JTextField();
		GridBagConstraints gbc_tlcx = new GridBagConstraints();
		gbc_tlcx.insets = new Insets(0, 0, 5, 5);
		gbc_tlcx.fill = GridBagConstraints.HORIZONTAL;
		gbc_tlcx.gridx = 1;
		gbc_tlcx.gridy = 2;
		add(tlcx, gbc_tlcx);
		tlcx.setColumns(5);

		JLabel LabelTlcy = new JLabel("cy");
		GridBagConstraints gbc_LabelTlcy = new GridBagConstraints();
		gbc_LabelTlcy.anchor = GridBagConstraints.EAST;
		gbc_LabelTlcy.insets = new Insets(0, 0, 5, 5);
		gbc_LabelTlcy.gridx = 2;
		gbc_LabelTlcy.gridy = 2;
		add(LabelTlcy, gbc_LabelTlcy);

		tlcy = new JTextField();
		GridBagConstraints gbc_tlcy = new GridBagConstraints();
		gbc_tlcy.insets = new Insets(0, 0, 5, 0);
		gbc_tlcy.fill = GridBagConstraints.HORIZONTAL;
		gbc_tlcy.gridx = 3;
		gbc_tlcy.gridy = 2;
		add(tlcy, gbc_tlcy);
		tlcy.setColumns(5);

		JLabel lblNewLabel_3 = new JLabel("Bottom Right Corner");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.gridwidth = 2;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		add(lblNewLabel_3, gbc_lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("cx");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 4;
		add(lblNewLabel_4, gbc_lblNewLabel_4);

		brcx = new JTextField();
		GridBagConstraints gbc_brcx = new GridBagConstraints();
		gbc_brcx.insets = new Insets(0, 0, 5, 5);
		gbc_brcx.fill = GridBagConstraints.HORIZONTAL;
		gbc_brcx.gridx = 1;
		gbc_brcx.gridy = 4;
		add(brcx, gbc_brcx);
		brcx.setColumns(5);

		JLabel lblNewLabel_6 = new JLabel("cy");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.gridx = 2;
		gbc_lblNewLabel_6.gridy = 4;
		add(lblNewLabel_6, gbc_lblNewLabel_6);

		brcy = new JTextField();
		GridBagConstraints gbc_brcy = new GridBagConstraints();
		gbc_brcy.insets = new Insets(0, 0, 5, 0);
		gbc_brcy.fill = GridBagConstraints.HORIZONTAL;
		gbc_brcy.gridx = 3;
		gbc_brcy.gridy = 4;
		add(brcy, gbc_brcy);
		brcy.setColumns(5);

		JLabel lblNewLabel_7 = new JLabel("Aspect Ratio");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 1;
		gbc_lblNewLabel_7.gridy = 7;
		add(lblNewLabel_7, gbc_lblNewLabel_7);

		aspectRatio = new JComboBox<AspectRatio>();
		aspectRatio.setModel(new DefaultComboBoxModel<AspectRatio>(AspectRatio.values()));
		GridBagConstraints gbc_aspectRatio = new GridBagConstraints();
		gbc_aspectRatio.insets = new Insets(0, 0, 5, 0);
		gbc_aspectRatio.fill = GridBagConstraints.HORIZONTAL;
		gbc_aspectRatio.gridx = 3;
		gbc_aspectRatio.gridy = 7;
		add(aspectRatio, gbc_aspectRatio);

		JLabel lblNewLabel_5 = new JLabel("Max. iterations");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.gridwidth = 2;
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_5.gridx = 1;
		gbc_lblNewLabel_5.gridy = 8;
		add(lblNewLabel_5, gbc_lblNewLabel_5);

		maxIter = new JTextField();
		GridBagConstraints gbc_maxIter = new GridBagConstraints();
		gbc_maxIter.fill = GridBagConstraints.HORIZONTAL;
		gbc_maxIter.gridx = 3;
		gbc_maxIter.gridy = 8;
		add(maxIter, gbc_maxIter);
		maxIter.setColumns(5);

	}

	public JTextField getTlcx() {
		return tlcx;
	}

	public JTextField getTlcy() {
		return tlcy;
	}

	public JTextField getBrcx() {
		return brcx;
	}

	public JTextField getBrcy() {
		return brcy;
	}

	public JTextField getMaxIter() {
		return maxIter;
	}

	public JComboBox<AspectRatio> getAspectRatio() {
		return aspectRatio;
	}

}
