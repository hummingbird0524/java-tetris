package action;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * キー関連Class
 * 
 * @author	y_nishikawa
 */
public class TetrisKey implements KeyListener{
	/* **************************************************
	 * 入力キー判定用
	 * **************************************************/
	/** ↑キー               */
	public boolean	isUp;
	/** ↓キー               */
	public boolean	isDown;
	/** ←キー               */
	public boolean	isLeft;
	/** →キー               */
	public boolean	isRight;
	/** Zキー(左回転)        */
	public boolean	isTurnL;
	/** Xキー(右回転)        */
	public boolean	isTurnR;
	/** Enter or Space(開始) */
	public boolean	isStart;
	
	// ==============================================================================================================
	
	/* (非 Javadoc)
	 * @see	java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e){
		// nop.
	}
	
	/* (非 Javadoc)
	 * @see	java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case 38:
				// ↑キー
				this.isUp = true;
				break;
			
			case 40:
				// ↓キー
				this.isDown = true;
				break;

			case 37:
				// ←キー
				this.isLeft = true;
				break;
				
			case 39:
				// →キー
				this.isRight = true;
				break;
				
			case 10:
			case 32:
				// Enter or Spaceキー
				this.isStart = true;
				break;
		}
		
		switch(e.getKeyChar()){
			case 'z' :
			case 'Z' :
				isTurnL = true;
				break;
				
			case 'x' :
			case 'X' :
				isTurnR = true;
				break;
		}
	}
	
	/* (非 Javadoc)
	 * @see	java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
			case 38:
				// ↑キー
				this.isUp = false;
				break;
			
			case 40:
				// ↓キー
				this.isDown = false;
				break;
	
			case 37:
				// ←キー
				this.isLeft = false;
				break;
				
			case 39:
				// →キー
				this.isRight = false;
				break;
				
			case 10:
			case 32:
				// Enter or Spaceキー
				this.isStart = false;
				break;
		}
		
		switch(e.getKeyChar()){
			case 'z' :
			case 'Z' :
				isTurnL = false;
				break;
				
			case 'x' :
			case 'X' :
				isTurnR = false;
				break;
		}
	}
}