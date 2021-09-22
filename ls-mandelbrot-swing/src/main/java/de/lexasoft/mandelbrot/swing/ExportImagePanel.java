/**
 * Copyright (C) 2021 nierax
 * This program is free software; you can redistribute it and/or modify it under the terms of 
 * the GNU General Public License as published by the Free Software Foundation; either version 3 
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * 
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, see <http://www.gnu.org/licenses/>. 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class ExportImagePanel extends JPanel {
	private JTextField imageWidth;
	private JTextField imageHeight;
	private JLabel lblFileName;
	private JTextField imageFilename;
	private JButton chooseButton;
	private JButton btnExportImage;
	private JSeparator separator;
	private JSeparator separator_1;

	/**
	 * 
	 */
	public ExportImagePanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 0;
		add(separator, gbc_separator);

		JLabel lblImageWidth = new JLabel("Image width");
		GridBagConstraints gbc_lblImageWidth = new GridBagConstraints();
		gbc_lblImageWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblImageWidth.anchor = GridBagConstraints.WEST;
		gbc_lblImageWidth.gridx = 1;
		gbc_lblImageWidth.gridy = 1;
		add(lblImageWidth, gbc_lblImageWidth);

		imageWidth = new JTextField();
		GridBagConstraints gbc_imageWidth = new GridBagConstraints();
		gbc_imageWidth.insets = new Insets(0, 0, 5, 5);
		gbc_imageWidth.fill = GridBagConstraints.HORIZONTAL;
		gbc_imageWidth.gridx = 2;
		gbc_imageWidth.gridy = 1;
		add(imageWidth, gbc_imageWidth);
		imageWidth.setColumns(10);

		JLabel lblImageHeight = new JLabel("Image height");
		GridBagConstraints gbc_lblImageHeight = new GridBagConstraints();
		gbc_lblImageHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblImageHeight.anchor = GridBagConstraints.EAST;
		gbc_lblImageHeight.gridx = 4;
		gbc_lblImageHeight.gridy = 1;
		add(lblImageHeight, gbc_lblImageHeight);

		imageHeight = new JTextField();
		GridBagConstraints gbc_imageHeight = new GridBagConstraints();
		gbc_imageHeight.insets = new Insets(0, 0, 5, 5);
		gbc_imageHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_imageHeight.gridx = 5;
		gbc_imageHeight.gridy = 1;
		add(imageHeight, gbc_imageHeight);
		imageHeight.setColumns(10);

		lblFileName = new JLabel("File name");
		GridBagConstraints gbc_lblFileName = new GridBagConstraints();
		gbc_lblFileName.anchor = GridBagConstraints.EAST;
		gbc_lblFileName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFileName.gridx = 1;
		gbc_lblFileName.gridy = 2;
		add(lblFileName, gbc_lblFileName);

		imageFilename = new JTextField();
		imageFilename.setEnabled(false);
		imageFilename.setEditable(false);
		GridBagConstraints gbc_imageFilename = new GridBagConstraints();
		gbc_imageFilename.gridwidth = 3;
		gbc_imageFilename.insets = new Insets(0, 0, 5, 5);
		gbc_imageFilename.fill = GridBagConstraints.HORIZONTAL;
		gbc_imageFilename.gridx = 2;
		gbc_imageFilename.gridy = 2;
		add(imageFilename, gbc_imageFilename);
		imageFilename.setColumns(10);

		chooseButton = new JButton("Choose...");
		GridBagConstraints gbc_chooseButton = new GridBagConstraints();
		gbc_chooseButton.insets = new Insets(0, 0, 5, 5);
		gbc_chooseButton.anchor = GridBagConstraints.WEST;
		gbc_chooseButton.gridx = 5;
		gbc_chooseButton.gridy = 2;
		add(chooseButton, gbc_chooseButton);

		btnExportImage = new JButton("Export image");
		btnExportImage.setEnabled(false);
		GridBagConstraints gbc_btnExportImage = new GridBagConstraints();
		gbc_btnExportImage.insets = new Insets(0, 0, 5, 5);
		gbc_btnExportImage.anchor = GridBagConstraints.WEST;
		gbc_btnExportImage.gridx = 5;
		gbc_btnExportImage.gridy = 3;
		add(btnExportImage, gbc_btnExportImage);

		separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.insets = new Insets(0, 0, 0, 5);
		gbc_separator_1.gridx = 1;
		gbc_separator_1.gridy = 4;
		add(separator_1, gbc_separator_1);
	}

	public JTextField getImageWidth() {
		return imageWidth;
	}

	public JTextField getImageHeight() {
		return imageHeight;
	}

	public JTextField getImageFilename() {
		return imageFilename;
	}

	public JButton getChooseButton() {
		return chooseButton;
	}

	public JButton getBtnExportImage() {
		return btnExportImage;
	}
}
