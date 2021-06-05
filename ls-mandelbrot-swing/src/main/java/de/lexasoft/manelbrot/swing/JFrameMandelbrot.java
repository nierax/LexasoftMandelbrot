/**
 * 
 */
package de.lexasoft.manelbrot.swing;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * This is the overall window frame, the application is running in.
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class JFrameMandelbrot extends JFrame {

	private int imageWidth;
	private int imageHeight;
	private int frameWidth;
	private int frameHeight;
	private JPanel contentPane;
	private MandelbrotCanvas canvas;

	/**
	 * @throws HeadlessException
	 */
	public JFrameMandelbrot(int imageWidth, int imageHeight) throws HeadlessException {
		super("Lexasoft Mandelbrot");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		setLocationRelativeTo(null);
		this.frameWidth = this.imageWidth;
		this.frameHeight = this.imageHeight;
		setSize(frameWidth, frameHeight);
		setBounds(300, 350, frameWidth, frameHeight);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.add(new JLabel("Hello Mandelbrot"));
		canvas = new MandelbrotCanvas(imageWidth, imageHeight);
		contentPane.add(canvas);
		setMinimumSize(new Dimension(frameWidth, frameHeight));
		setPreferredSize(new Dimension(frameWidth, frameHeight));
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JFrameMandelbrot(459, 405);
			}
		});
	}

};