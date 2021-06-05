package de.lexasoft.manelbrot.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MandelbrotApp {

	private JFrame frmLexasoftMandelbrotApplication;

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
		frmLexasoftMandelbrotApplication.setBounds(100, 100, 520, 450);
		frmLexasoftMandelbrotApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLexasoftMandelbrotApplication.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel = new MandelbrotCanvas(495, 405);
		frmLexasoftMandelbrotApplication.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
	}

}
