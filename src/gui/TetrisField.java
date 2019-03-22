package gui;

/**
 * フィールド関連Class
 * 
 * @author	y_nishikawa
 */
public class TetrisField{
	/** フィールド     */
	public int[][]		field;
	
	/** 消去ライン番号 */
	private int		delNum;
	/** 消去ライン     */
	private int[]		delRow;
	
	// ==============================================================================================================
	
	/**
	 * フィールド初期化
	 */
	public void newField(){
		// フィールドセル数設定
		this.field = new int[TetrisMaster.FIELD_ROWS][TetrisMaster.FIELD_COLS];
		
		for(int row=0; row<TetrisMaster.FIELD_ROWS-1; row++){
			// 左の壁
			this.field[row][0] = 9;
			
			// プレイエリア
			for(int col=1; col<TetrisMaster.FIELD_COLS-1; col++){
				this.field[row][col] = 0;
			}
			
			// 右の壁
			this.field[row][TetrisMaster.FIELD_COLS-1] = 9;
		}
		
		// 底面
		for(int col=0; col<TetrisMaster.FIELD_COLS; col++){
			this.field[TetrisMaster.FIELD_ROWS-1][col] = 9;
		}
		
		// 消去ライン
		this.delRow = new int[TetrisMaster.FIELD_ROWS];
	}
	
	/**
	 * ライン消去可否チェック
	 * 
	 * @return		(int)消去可能ライン
	 */
	public int checkLine(){
		this.delNum = 0;
		
		int	cells;
		
		for(int row=0; row<TetrisMaster.FIELD_ROWS-1; row++){
			cells = 0;
			
			// ラインブロック数カウント
			for(int col=1; col<TetrisMaster.FIELD_COLS-1; col++){
				if(this.field[row][col] != 0){
					cells++;
				}else{
					break;
				}
			}
			
			if(cells == TetrisMaster.FIELD_COLS-2){
				this.delRow[delNum++] = row;
			}
		}
		
		return	this.delNum;
	}
	
	/**
	 * ライン消去
	 */
	public void deleteLine(){
		for(int n=0; n<this.delNum; n++){
			for(int col=1; col<TetrisMaster.FIELD_COLS-1; col++){
				// 該当ラインをすべて消去
				this.field[this.delRow[n]][col] = 0;
			}
		}
	}
	
	/**
	 * ラインパディング
	 */
	public void killLine(){
		for(int n=0; n<this.delNum; n++){
			// 1ラインずつ下に移動
			for(int row=this.delRow[n]; row>0; row--){
				System.arraycopy(this.field[row-1], 0, field[row], 0, TetrisMaster.FIELD_COLS);
			}
			
			// 最上段クリア
			for(int col=1; col<TetrisMaster.FIELD_COLS-1; col++){
				this.field[0][col] = 0;
			}
		}
	}
}