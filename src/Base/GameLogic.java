/**
 * 
 */
package Base;

import java.awt.Color;
import java.awt.Font;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.awt.TextRenderer;

/**
 * @author TreMcP
 *
 */
public class GameLogic {

	protected View 	view;//pointer to the view
	
	protected int	playerOneScore;
	protected int	playerTwoScore;
	
	GameLogic(View view) {
		this.view = view;
		this.playerOneScore = 2;
		this.playerTwoScore = 3;
	}
	
	public void update(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();

		
		
	}
	
	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();
		renderScores(drawable, playerOneScore, playerTwoScore);
	}
	
	private void renderScores(GLAutoDrawable drawable, int p1score, int p2score) {
		
		int height = drawable.getSurfaceHeight();
		int width = drawable.getSurfaceWidth();
		
		TextRenderer renderer = new TextRenderer(new Font("Verdana", Font.BOLD, 12));
		renderer.beginRendering(width, height);
		
		if(view.neonMode)
		{
			renderer.setColor(Color.YELLOW);
		}
		else
		{
			renderer.setColor(Color.BLACK);
		}
		//Have to subtract 85 from player one's x point for some reason
		renderer.draw("Player One: " + Integer.toString(p1score), (int) ((1 * width/4) - .5*renderer.getBounds(Integer.toString(p1score)).getWidth() - 85), height - 15);
		renderer.draw("Player Two: " + Integer.toString(p2score), (int) ((3 * width/4) - .5*renderer.getBounds(Integer.toString(p2score)).getWidth()), height - 15);
		renderer.endRendering();
	}
	
	public void increasePlayerOneScore() {
		this.playerOneScore++;
	}
	
	public void increasePlayerTwoScore() {
		this.playerTwoScore++;
	}
	
}
