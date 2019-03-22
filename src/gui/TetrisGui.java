package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import action.TetrisKey;

/**
 * TetrisGUI関連Class
 * 
 * @author	y_nishikawa
 */
public class TetrisGui{
	/** メインフレーム      */
	private JFrame		frmMain;
	/** メインパネル        */
	private JPanel		pnlMain;
	/** イメージ            */
	private Image			img;
	
	/** フィールド関連Class */
	private TetrisField	field;
	/** テトリミノ関連Class */
	private TetrisBlock	block;
	/** キー操作関連Class   */
	private TetrisKey		key;
	
	/** 色                  */
	private Color[]		color;
	
	/** 前面グラフィック    */
	private Graphics		foreGra;
	/** 背面グラフィック    */
	private Graphics		backGra;
	
	// ==============================================================================================================
	
	/**
	 * コンストラクター
	 * 
	 * @param	field	(TetrisField)フィールド関連Class
	 * @param	block	(TetrisBlock)テトリミノ関連Class
	 * @param	key		(TetrisKey)キー入力関連Class
	 */
	public TetrisGui(TetrisField field, TetrisBlock block, TetrisKey key){
		this.field = field;
		this.block = block;
		this.key   = key;
		
		// 色設定
		this.color = new Color[]{
			new Color(TetrisMaster.COLORS[0][0], TetrisMaster.COLORS[0][1], TetrisMaster.COLORS[0][2])
		  , new Color(TetrisMaster.COLORS[1][0], TetrisMaster.COLORS[1][1], TetrisMaster.COLORS[1][2])
		  , new Color(TetrisMaster.COLORS[2][0], TetrisMaster.COLORS[2][1], TetrisMaster.COLORS[2][2])
		  , new Color(TetrisMaster.COLORS[3][0], TetrisMaster.COLORS[3][1], TetrisMaster.COLORS[3][2])
		};
		
		// フレーム設定
		this.frmMain = new JFrame("Java de Tetris");
		this.frmMain.setBounds(100, 100, TetrisMaster.winWidth, TetrisMaster.winHeight);
		this.frmMain.setResizable(false);
		this.frmMain.addKeyListener(this.key);
		this.frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// コンテンツペイン設定
		Container	ctnr = this.frmMain.getContentPane();
		ctnr.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		// パネル設定
		this.pnlMain = new JPanel();
		this.pnlMain.setPreferredSize(new Dimension(TetrisMaster.winWidth, TetrisMaster.winHeight));
		ctnr.add(this.pnlMain);
		
		// フレーム調整
		this.frmMain.pack();
		
		// 描画イメージ生成
		this.img = this.pnlMain.createImage(TetrisMaster.winWidth, TetrisMaster.winHeight);
		
		// グラフィックコンテキスト取得
		this.foreGra = this.pnlMain.getGraphics();
		this.backGra = this.img.getGraphics();
		
		// 背景設定
		this.backGra.setColor(Color.BLACK);
		this.backGra.fillRect(0, 0, TetrisMaster.winWidth, TetrisMaster.winHeight);
	}
	
	/**
	 * ウィンドウ表示
	 */
	public void windowShow(){
		this.frmMain.setVisible(true);
		
		this.frmMain.requestFocus();
	}
	
	/**
	 * フィールド描画
	 */
	public void fieldDraw(){
		for(int row=0; row<=TetrisMaster.FIELD_ROWS-1; row++){
			for(int col=0; col<=TetrisMaster.FIELD_COLS-1; col++){
				switch(field.field[row][col]){
					case 0:
						// ブロックなし部
						backGra.setColor(color[0]);
						backGra.fillRect(
								TetrisMaster.BLOCK_SIZE*col+1
							  , TetrisMaster.BLOCK_SIZE*row+1
							  , TetrisMaster.BLOCK_SIZE-2
							  , TetrisMaster.BLOCK_SIZE-2
						);
						
						break;
					
					case 9:
						// 壁部
						backGra.setColor(color[1]);
						backGra.fillRect(
								TetrisMaster.BLOCK_SIZE*col+1
							  , TetrisMaster.BLOCK_SIZE*row+1
							  , TetrisMaster.BLOCK_SIZE-2
							  , TetrisMaster.BLOCK_SIZE-2
						);
						
						break;
					
					default:
						// ブロック山部
						backGra.setColor(color[2]);
						backGra.fillRect(
								TetrisMaster.BLOCK_SIZE*col+1
							  , TetrisMaster.BLOCK_SIZE*row+1
							  , TetrisMaster.BLOCK_SIZE-2
							  , TetrisMaster.BLOCK_SIZE-2
						);
						
						break;
				}
			}
		}
	}
	
	/**
	 * ブロック描画(背景)
	 */
	public void blockDraw(){
		for(int row=0; row<=this.block.block.length-1; row++){
			for(int col=0; col<=this.block.block.length-1; col++){
				if(block.block[row][col] != 0){
					// ブロックあり
					backGra.setColor(color[3]);
					backGra.fillRect(
							TetrisMaster.BLOCK_SIZE*(col+block.x)+1
						  , TetrisMaster.BLOCK_SIZE*(row+block.y)+1
						  , TetrisMaster.BLOCK_SIZE-2
						  , TetrisMaster.BLOCK_SIZE-2
					);
				}
			}
		}
	}
	
	/**
	 * 背景→前景
	 */
	public void BackToFore(){
		foreGra.drawImage(img, 0, 0, pnlMain);
	}
}