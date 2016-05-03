/**
 *
 */
package Base;

import java.util.Random;
import java.util.Stack;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

/**
 *
 */
public class GameLogic {

	protected View 	view;//pointer to the view

	public static int	rightPlayerScore;
	public static int	leftPlayerScore;

	public static Stack<Integer> leftPlayerPowerup = new Stack<Integer>();
	public static Stack<Integer> leftPlayerDefense = new Stack<Integer>();
	public static int leftPlayerStreak = 0;

	public static Stack<Integer> rightPlayerPowerup = new Stack<Integer>();
	public static Stack<Integer> rightPlayerDefense = new Stack<Integer>();
	public static int rightPlayerStreak = 0;

	public static int scoreLimit = 10;

	public static boolean winner = false;
	public static String strWin = "";


	GameLogic(View view) {
		this.view = view;
		rightPlayerScore = 0;
		leftPlayerScore = 0;
	}

	public void update(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();

	}

	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();
	}



	public void incrementScore(String which, boolean bonus)
	{
		if(which.equals("right")) increaseRightPlayerScore(bonus);
		else if(which.equals("left")) increaseLeftPlayerScore(bonus);
	}

	private void increaseRightPlayerScore(boolean bonus) {
		if(bonus)
		{
			rightPlayerScore += 2;
			rightPlayerStreak += 2;
		}
		else
		{
			rightPlayerScore++;
			rightPlayerStreak += 1;
		}
		leftPlayerStreak = 0;
		checkRightWin();
		checkRightPow();
	}

	private void increaseLeftPlayerScore(boolean bonus) {
		if(bonus)
		{
			leftPlayerScore += 2;
			leftPlayerStreak += 2;
		}
		else
		{
			leftPlayerScore++;
			leftPlayerStreak += 1;
		}
		rightPlayerStreak = 0;
		checkLeftWin();
		checkLeftPow();
	}

	private void checkRightWin()
	{
		if(rightPlayerScore >= scoreLimit)
		{
			winner = true;
			strWin = "Player Two";
		}
	}

	private void checkRightPow()
	{
		boolean givePowerup = false;
		switch(rightPlayerStreak)
		{
			case 1: givePowerup = Utilities.getChance(20); break;
			case 2: givePowerup = Utilities.getChance(30); break;
			case 3: givePowerup = Utilities.getChance(40); break;
			case 4: givePowerup = Utilities.getChance(50); break;
			default: givePowerup = Utilities.getChance(60); break;
		}
		if(givePowerup)
		{
			int pow = whichPow();
//			System.out.println(pow);
			if(pow == 4)
				rightPlayerDefense.push(pow);
			else
				rightPlayerPowerup.push(pow);
		}
	}

	public static void checkLeftWin()
	{
		if(leftPlayerScore >= scoreLimit)
		{
			winner = true;
			strWin = "Player One";
		}
	}

	private void checkLeftPow()
	{
		boolean givePowerup = false;
		switch(leftPlayerStreak)
		{
			case 1: givePowerup = Utilities.getChance(20); break;
			case 2: givePowerup = Utilities.getChance(30); break;
			case 3: givePowerup = Utilities.getChance(40); break;
			case 4: givePowerup = Utilities.getChance(50); break;
			default: givePowerup = Utilities.getChance(60); break;
		}
		if(givePowerup)
		{
			int pow = whichPow();
			if(pow == 4)
				leftPlayerDefense.push(pow);
			else
				leftPlayerPowerup.push(pow);
		}
	}

	private int whichPow()
	{
		Random rand = new Random();
		return rand.nextInt(4) + 1;
	}

}
