package de.lexasoft.mandelbrot.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class MandelbrotSwingView {

	private JFrame frmLexasoftMandelbrotApplication;
	private ImagePanel panel;
	private ColorControlPanel colorControlPanel;

	/**
	 * Create the application.
	 */
	public MandelbrotSwingView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLexasoftMandelbrotApplication = new JFrame();
		frmLexasoftMandelbrotApplication.setTitle("Lexasoft Mandelbrot Application");
		frmLexasoftMandelbrotApplication.setBounds(100, 100, 750, 530);
		frmLexasoftMandelbrotApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLexasoftMandelbrotApplication.getContentPane()
		    .setLayout(new BoxLayout(frmLexasoftMandelbrotApplication.getContentPane(), BoxLayout.X_AXIS));

		panel = new ImagePanel();
		frmLexasoftMandelbrotApplication.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_Right = new JPanel();
		panel_Right.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_Right.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmLexasoftMandelbrotApplication.getContentPane().add(panel_Right);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 25, 25 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 1.0 };
		panel_Right.setLayout(gridBagLayout);

		colorControlPanel = new ColorControlPanel();
		GridBagConstraints gbc_colorControlPanel = new GridBagConstraints();
		gbc_colorControlPanel.insets = new Insets(0, 0, 0, 5);
		gbc_colorControlPanel.fill = GridBagConstraints.BOTH;
		gbc_colorControlPanel.gridx = 0;
		gbc_colorControlPanel.gridy = 0;
		panel_Right.add(colorControlPanel, gbc_colorControlPanel);
	}

	protected ImagePanel getImagePanel() {
		return panel;
	}

	public JFrame getFrmLexasoftMandelbrotApplication() {
		return frmLexasoftMandelbrotApplication;
	}

	public ColorControlPanel getColorControlPanel() {
		return colorControlPanel;
	}
}
