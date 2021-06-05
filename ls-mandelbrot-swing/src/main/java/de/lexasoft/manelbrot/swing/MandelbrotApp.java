package de.lexasoft.manelbrot.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.PaletteVariant;

public class MandelbrotApp {

	private JFrame frmLexasoftMandelbrotApplication;
	private MandelbrotCanvas panel;
	private JTextField colorGrading;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MandelbrotApp window = new MandelbrotApp();
					window.frmLexasoftMandelbrotApplication.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MandelbrotApp() {
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

		panel = new MandelbrotCanvas(495, 405);
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
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		panel_Right.setLayout(gridBagLayout);

		JLabel lblNewLabel_1 = new JLabel("Color palette");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel_Right.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JComboBox<PaletteVariant> paletteVariant = new JComboBox<>();
		paletteVariant.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					PaletteVariant value = (PaletteVariant) evt.getItem();
					getMandelbrotCanvas().changePaletteVariant(value);
				}
			}
		});
		paletteVariant.setModel(new DefaultComboBoxModel<PaletteVariant>(PaletteVariant.values()));
		paletteVariant.setSelectedIndex(3);
		GridBagConstraints gbc_paletteVariant = new GridBagConstraints();
		gbc_paletteVariant.insets = new Insets(0, 0, 5, 0);
		gbc_paletteVariant.fill = GridBagConstraints.HORIZONTAL;
		gbc_paletteVariant.gridx = 1;
		gbc_paletteVariant.gridy = 0;
		panel_Right.add(paletteVariant, gbc_paletteVariant);

		JLabel lblNewLabel_3 = new JLabel("Color Grading");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 1;
		panel_Right.add(lblNewLabel_3, gbc_lblNewLabel_3);

		colorGrading = new JTextField();
		GridBagConstraints gbc_colorGrading = new GridBagConstraints();
		gbc_colorGrading.insets = new Insets(0, 0, 5, 0);
		gbc_colorGrading.fill = GridBagConstraints.HORIZONTAL;
		gbc_colorGrading.gridx = 1;
		gbc_colorGrading.gridy = 1;
		panel_Right.add(colorGrading, gbc_colorGrading);
		colorGrading.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Color grading style");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel_Right.add(lblNewLabel_2, gbc_lblNewLabel_2);

		JComboBox colorGradingStyle = new JComboBox();
		colorGradingStyle.setModel(new DefaultComboBoxModel(ColorGradingStyle.values()));
		GridBagConstraints gbc_colorGradingStyle = new GridBagConstraints();
		gbc_colorGradingStyle.fill = GridBagConstraints.HORIZONTAL;
		gbc_colorGradingStyle.gridx = 1;
		gbc_colorGradingStyle.gridy = 2;
		panel_Right.add(colorGradingStyle, gbc_colorGradingStyle);
	}

	protected MandelbrotCanvas getMandelbrotCanvas() {
		return panel;
	}
}
