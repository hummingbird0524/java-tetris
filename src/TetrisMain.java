/* **************************************************************************************************************
 * オープンキャンパス
 * 　
 * 　Javaでテトリスを作ろう！
 * 　
 * **************************************************************************************************************/
import action.TetrisKey;
import gui.TetrisBlock;
import gui.TetrisField;
import gui.TetrisGui;
import logic.TetrisLogic;

/**
 * TetrisメインClass
 * 
 * @author	y_nishikawa
 */
public class TetrisMain{
	/** フィールド関連Class */
	private TetrisField		fld;
	/** テトリミノ関連Class */
	private TetrisBlock		blk;
	/** キー操作関連Class   */
	private TetrisKey			key;
	/** GUI関連Class        */
	private TetrisGui			gui;
	/** 内部処理関連Class   */
	private TetrisLogic		log;
	
	// ==============================================================================================================
	
	/**
	 * 初期化処理
	 */
	private void initWindow(){
		// 関連Class生成
		fld = new TetrisField();
		blk = new TetrisBlock();
		key = new TetrisKey();
		gui = new TetrisGui(fld, blk, key);
		log = new TetrisLogic(fld, blk, key, gui);
		
		// ウィンドウ表示
		gui.windowShow();
	}
	
	/**
	 * main処理
	 * 
	 * @param args	(String[])引数
	 */
	public static void main(String[] args){
		try{
			TetrisMain	tm = new TetrisMain();
			
			// 画面初期化
			tm.initWindow();
			
			try{
				// 500msecお休み
				Thread.sleep(500);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			while(true){
				tm.log.execute();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
