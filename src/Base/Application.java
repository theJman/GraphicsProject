package Base;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.opengl.glu.*;
import com.jogamp.opengl.util.*;


/**
 * Graphics Project
 * 
 * Air Hockey Simulator
 * 
 * @author Team Awesome
 */
public final class Application
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	
	/**
	 * Change TableHeight to change your window size. 
	 * These vars ensure that the standard dimensions of an air hockey table are kept.
	 * 
	 * @author tremc_000
	 */
	public static final int			TableHeight		= 1000;
	public static final double		TableWidthMult	= 1.8;
	
	public static final Rectangle	DEFAULT_BOUNDS	=
		new Rectangle(50, 50, (int)(TableHeight * TableWidthMult), TableHeight);
	
	public static		int			numCircles		= 0;

	//**********************************************************************
	// Main
	//**********************************************************************

	public static void	main(String[] args)
	{
		GLProfile		profile = GLProfile.getDefault();
		GLCapabilities	capabilities = new GLCapabilities(profile);
		//GLCanvas		canvas = new GLCanvas(capabilities);
		GLJPanel		canvas = new GLJPanel(capabilities);
		JFrame			frame = new JFrame("Application");
		
		System.out.println("Please enter the number of pucks that you want.");
		Scanner reader = new Scanner(System.in);
		numCircles = reader.nextInt();
		reader.close();

		frame.setBounds(DEFAULT_BOUNDS);
		frame.getContentPane().add(canvas);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		
		View			view = new View(canvas);
	}
}

//******************************************************************************
