package de.lexasoft.manelbrot.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import de.lexasoft.mandelbrot.api.PaletteVariant;

public class MandelbrotApp {

	private JFrame frmLexasoftMandelbrotApplication;
	private MandelbrotCanvas panel;

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
		panel_Right.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmLexasoftMandelbrotApplication.getContentPane().add(panel_Right);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 51, 0 };
		gridBagLayout.rowHeights = new int[] { 207, 13, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_Right.setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Mandelbrot");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_Right.add(lblNewLabel, gbc_lblNewLabel);

		JComboBox<PaletteVariant> comboBox = new JComboBox<>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					PaletteVariant value = (PaletteVariant) evt.getItem();
					getMandelbrotCanvas().changePaletteVariant(value);
				}
			}
		});
		comboBox.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
//				if ("model".equals(evt.getPropertyName())) {
//					Object object = evt.getNewValue();
//					DefaultComboBoxModel<PaletteVariant> model = (DefaultComboBoxModel<PaletteVariant>) object;
//					PaletteVariant value = (PaletteVariant) model.getSelectedItem();
//					getMandelbrotCanvas().changePaletteVariant(value);
//				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<PaletteVariant>(PaletteVariant.values()));
		comboBox.setSelectedIndex(3);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		panel_Right.add(comboBox, gbc_comboBox);
	}

	protected MandelbrotCanvas getMandelbrotCanvas() {
		return panel;
	}
}
