package Base;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

//******************************************************************************

/**
 *
 * @author  Team Awesome
 */
public final class Utilities
{
	// Use this to load images
	private Image	fullyLoadImage(String filename)
	{
		Image	image = null;

		// Catch some exceptions
		try
		{
			// Load an image file into an image object
			image = Toolkit.getDefaultToolkit().createImage(filename);
		}
		catch (Exception ex)
		{
			System.err.println("that wasn't supposed to happen");
			ex.printStackTrace();
			System.exit(1);
		}

		return image;
	}

	//**********************************************************************
	// Public Class Methods (Event Handling)
	//**********************************************************************

	public static boolean	isAltDown(InputEvent e)
	{
		return ((e.getModifiersEx() & InputEvent.ALT_DOWN_MASK) != 0);
	}

	public static boolean	isAltGraphDown(InputEvent e)
	{
		return ((e.getModifiersEx() & InputEvent.ALT_GRAPH_DOWN_MASK) != 0);
	}

	public static boolean	isControlDown(InputEvent e)
	{
		return ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0);
	}

	public static boolean	isMetaDown(InputEvent e)
	{
		return ((e.getModifiersEx() & InputEvent.META_DOWN_MASK) != 0);
	}

	public static boolean	isShiftDown(InputEvent e)
	{
		return ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0);
	}

	public static boolean	isButton1Down(InputEvent e)
	{
		return (((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) ||
				((e instanceof MouseEvent) &&
				 (((MouseEvent)e).getButton() == 1)));
	}

	public static boolean	isButton2Down(InputEvent e)
	{
		return (((e.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK) != 0) ||
				((e instanceof MouseEvent) &&
				 (((MouseEvent)e).getButton() == 2)));
	}

	public static boolean	isButton3Down(InputEvent e)
	{
		return (((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) ||
				((e instanceof MouseEvent) &&
				 (((MouseEvent)e).getButton() == 3)));
	}

	public static boolean getChance(int chance)
	{
		Random rand = new Random();
		int temp = rand.nextInt(100) + 1;
		if(temp <= chance) return true;
		else return false;
	}
}

//******************************************************************************
