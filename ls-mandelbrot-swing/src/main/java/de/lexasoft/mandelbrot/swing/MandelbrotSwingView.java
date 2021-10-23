package de.lexasoft.mandelbrot.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

public class MandelbrotSwingView {

	private JFrame frmLexasoftMandelbrotApplication;
	private ImagePanel imagePanel;
	private ColorControlPanel colorControlPanel;
	private CalculationPanel calculationPanel;
	private JMenuBar menuBar;
	private FileMenuView mnFile;
	private DrawCalculationAreaPanel drawCalculationAreaPanel;
	private StatusbarView statusBar;

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
		frmLexasoftMandelbrotApplication.getContentPane().setLayout(new BorderLayout(0, 0));

		imagePanel = new ImagePanel();
		frmLexasoftMandelbrotApplication.getContentPane().add(imagePanel, BorderLayout.WEST);
		imagePanel.setPreferredSize(new Dimension(450, 405));
		imagePanel.setLayout(new BorderLayout(0, 0));

		drawCalculationAreaPanel = new DrawCalculationAreaPanel();
		drawCalculationAreaPanel.setBackground(new Color(0, 0, 0, 0));
		imagePanel.add(drawCalculationAreaPanel);
		drawCalculationAreaPanel.setLayout(new BorderLayout(0, 0));

		JPanel rightPanel = new JPanel();
		rightPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		rightPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmLexasoftMandelbrotApplication.getContentPane().add(rightPanel, BorderLayout.EAST);
		GridBagLayout gbl_rightPanel = new GridBagLayout();
		gbl_rightPanel.columnWidths = new int[] { 25 };
		gbl_rightPanel.rowHeights = new int[] { 0, 0 };
		gbl_rightPanel.columnWeights = new double[] { 1.0 };
		gbl_rightPanel.rowWeights = new double[] { 1.0, 1.0 };
		rightPanel.setLayout(gbl_rightPanel);
		rightPanel.setPreferredSize(new Dimension(200, 0));

		calculationPanel = new CalculationPanel();
		GridBagConstraints gbc_calculationPanel = new GridBagConstraints();
		gbc_calculationPanel.insets = new Insets(0, 0, 5, 0);
		gbc_calculationPanel.fill = GridBagConstraints.BOTH;
		gbc_calculationPanel.gridx = 0;
		gbc_calculationPanel.gridy = 0;
		rightPanel.add(calculationPanel, gbc_calculationPanel);

		colorControlPanel = new ColorControlPanel();
		GridBagConstraints gbc_colorControlPanel = new GridBagConstraints();
		gbc_colorControlPanel.fill = GridBagConstraints.BOTH;
		gbc_colorControlPanel.gridx = 0;
		gbc_colorControlPanel.gridy = 1;
		rightPanel.add(colorControlPanel, gbc_colorControlPanel);

		statusBar = new StatusbarView();
		frmLexasoftMandelbrotApplication.getContentPane().add(statusBar, BorderLayout.SOUTH);

		menuBar = new JMenuBar();
		frmLexasoftMandelbrotApplication.setJMenuBar(menuBar);

		mnFile = new FileMenuView();
		menuBar.add(mnFile);
	}

	protected ImagePanel getImagePanel() {
		return imagePanel;
	}

	public JFrame getFrmLexasoftMandelbrotApplication() {
		return frmLexasoftMandelbrotApplication;
	}

	public ColorControlPanel getColorControlPanel() {
		return colorControlPanel;
	}

	public CalculationPanel getCalculationPanel() {
		return calculationPanel;
	}

	public FileMenuView getMnFile() {
		return mnFile;
	}

	public void repaint() {
		SwingUtilities.updateComponentTreeUI(frmLexasoftMandelbrotApplication);
	}

	public void setEnabled(boolean b) {
		frmLexasoftMandelbrotApplication.setEnabled(b);
		enableAllChilds(frmLexasoftMandelbrotApplication, b);
	}

	/**
	 * @param b
	 */
	private void enableAllChilds(Container root, boolean b) {
		for (Component c : root.getComponents()) {
			c.setEnabled(b);
			if (c instanceof Container) {
				enableAllChilds((Container) c, b);
			}
		}
	}

	public DrawCalculationAreaPanel getDrawCalculationAreaPanel() {
		return drawCalculationAreaPanel;
	}

	StatusbarView getStatusBar() {
		return statusBar;
	}
}
