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
 * @author Team Awesome
 */
public final class Application
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	public static final Rectangle	DEFAULT_BOUNDS =
		new Rectangle(50, 50, 500, 500);

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
