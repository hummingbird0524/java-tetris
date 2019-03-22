package logic;

import action.TetrisKey;
import gui.TetrisBlock;
import gui.TetrisField;
import gui.TetrisGui;
import gui.TetrisMaster;

/**
 * テトリス内部処理Class
 * 
 * @author	y_nishikawa
 */
public class TetrisLogic{
	/* **************************************************
	 * モード
	 * **************************************************/
	private static final int		PLAY  = 1;
	private static final int		DROP  = 2;
	private static final int		ADD   = 3;
	private static final int		DEL   = 4;
	private static final int		NEW   = 5;
	private static final int		PAUSE = 6;
	private static final int		OVER  = 7;
	private static final int		STUP  = 0;
	
	/* **************************************************
	 * 衝突タイプ
	 * **************************************************/
	private static final int		MV_DOWN  = 0;
	private static final int		MV_Right = 1;
	private static final int		MV_Left  = 2;
	private static final int		TN_Right = 3;
	private static final int		TN_Left  = 4;
	private static final int		BL_NEW   = 5;
	
	private static final long	WAIT_TIME = 1000;
	
	private TetrisField	field;
	private TetrisBlock	block;
	private TetrisKey		key;
	private TetrisGui		gui;
	
	private int	mode;
	
	// ==============================================================================================================
	
	/**
	 * コンストラクター
	 * 
	 * @param	field	(TetrisField)
	 * @param	block	(TetrisBlock)
	 * @param	key		(TetrisKey)キー入力関連Class
	 * @param	gui		(TetrisGui)GUI関連Class
	 */
	public TetrisLogic(TetrisField field, TetrisBlock block, TetrisKey key, TetrisGui gui){
		this.field = field;
		this.block = block;
		this.key   = key;
		this.gui   = gui;
		
		// 初期化
		init();
	}
	
	/**
	 * 初期化処理
	 */
	private void init(){
		// フィールド初期化
		this.field.newField();
		
		// ブロック生成
		this.block.newBlock();
		
		// 開始前状態設定
		this.mode = -1;
		
		this.state = 0;
		
		inputMode(PLAY);
		
		timerReset();
	}
	
	/**
	 * 実行処理
	 */
	public void execute(){
		// ゲームモード設定
		this.mode = outputMode();
		
		// キー入力処理
		inputKey(this.mode);
		
		// ゲーム進行処理
		gameRun(this.mode);
		
		// 描画処理
		screenDraw(this.mode);
		
/* ==================================================
 *  FIXME ④落下速度を調整する！
 * ================================================== */
		//スリープ
//		sleep(15);
	}
	
	/********************************************
	キー入力処理
	********************************************/
	int t1, t2, t3, t4, t5, t6;
	
	/**
	 * キー入力関連処理
	 * 
	 * @param	mode	(int)ゲームモード
	 */
	private void inputKey(int mode){
		switch(mode){
			case PLAY:
				// PLAY状態の場合
				
				// ↑キー
				if(key.isUp && !isCollision(TN_Left)){
					// 左回転
					this.block.turnLeft();
					
					sleep(160);
				}
				
				// ↓キー
				if(key.isDown){
					if(t2 == 0 || t2 >= 18){
						if(!isCollision(MV_DOWN)){
							// 未着底
							
							// 加速落下
							this.block.moveDown();
							
							if(t2 >= 18){
								t2 = 16;
							}
							
							timerReset();
							
						}else{
							// 着底
							
							// 新規ブロック生成
							inputMode(ADD);
						}
					}
					
					t2++;
					
				}else{
					t2 = 0;
				}
				
/* ==================================================
 *  FIXME ②→キーの動作を書かないと！
 * ================================================== */
				// →キー
//				if(key.isRight && !isCollision(MV_Right)){
//					// 右移動
//					this.block.moveRight();
//					
//					sleep(120);
//				}
				
/* ==================================================
 *  FIXME ③←キーの動作を書かないと！
 * ================================================== */
				// ←キー
//				if(key.isLeft && !isCollision(MV_Left)){
//					// 左移動
//					this.block.moveLeft();
//					
//					sleep(120);
//				}
				
				// Xキー
				if(key.isTurnR && !isCollision(TN_Right)){
					// 右回転
					this.block.turnRight();
					
					sleep(160);
				}
				
				// Zキー
				if(key.isTurnL && !isCollision(TN_Left)){
					// 左回転
					this.block.turnLeft();
					
					sleep(160);
				}
				
			case DROP:
			case ADD:
			case DEL:
			case NEW:
				// Enter or Space
				if(key.isStart){
					inputMode(PAUSE);
					
					sleep(200);
				}
				
				break;
			
			case PAUSE :
				// Enter or Space
				if(key.isStart){
					inputMode(PAUSE);
					
					sleep(200);
				}
				
				break;
		}
	}
	
	/**
	 * 進行処理
	 * 
	 * @param	mode	(int)
	 */
	private void gameRun(int mode){
		switch(mode){
			case PLAY:
/* ==================================================
 *  FIXME ①ブロックを落とす！
 * ================================================== */
				if(timer(WAIT_TIME)){
					// DROP状態へ
					inputMode(DROP);
				}
				
				break;
			
			case DROP:
		 		if(!isCollision(MV_DOWN)){
					// 底面に衝突しない
		 			
		 			// ブロックを落とす
		 			this.block.moveDown();
					
		 			// PLAY状態へ
					inputMode(PLAY);
				
				}else{
					// 底面に衝突
					
					// ADD状態へ
					inputMode(ADD);
		 		}
		 		
				break;
			
			case ADD:
				// 新規ブロック生成
				addBlock();
				
				// 旧ブロッククリア
				this.block.clear();
				
				if(this.field.checkLine() > 0){
					// ライン消去可能
					
					// DEL状態へ
					inputMode(DEL);
					
				}else{
					// NEW状態へ
					inputMode(NEW);
				}
				
				break;
			
			case DEL :
				switch(state){
					case 0:
						// 
						timerReset();
						
						inputMode(STUP);
						break;
						
					case 1 :
						if(timer(300)){
							// ライン消去
							this.field.deleteLine();
							
							timerReset();
							
							// STUP状態へ
							inputMode(STUP);
						}
						
						break;
						
					case 2:
						if(timer(600)){
							// ラインパディング
							this.field.killLine();
							
							// NEW状態へ
							inputMode(NEW);
						}
						
						break;
						
				}
				
				break;
			
			case NEW :
				switch(state){
					case 0:
						timerReset();
						inputMode(STUP);
						break;
						
					case 1 :
						if(timer(800)){
							this.block.newBlock();
							if(isGameOver()){
								inputMode(OVER);
							}else{
								inputMode(PLAY);
								timerReset();
							}
						}
						break;
					}
				
				break;
			
			case PAUSE :
			 	break;
			
			case OVER :
				switch(state){
					case 0 :
						timerReset();
						inputMode(STUP);
						break;
					case 1 :
						if(timer(1000)){
							init();
						}
						break;
				}
				
				break;
		}
	}
	
	/**
	 * スクリーンへの描画
	 * 
	 * @param	mode	(int)
	 */
	public void screenDraw(int mode){
		switch(mode){
			case PLAY :
			case DROP :
			case ADD :
			case DEL :
			case NEW :
				// フィールド描画
				gui.fieldDraw();
				
				// ブロック描画
				gui.blockDraw();
				
				// 裏画面(bg) → 表画面(fg)
				gui.BackToFore();
				
				break;
				
			case PAUSE :
				break;
				
			case OVER :
				break;
		}
	}
	
	/**
	 * 衝突チェック
	 * 
	 * @param	type	(int)
	 * @return			(boolean)
	 */
	private boolean isCollision(int type){
		TetrisBlock	copyBlk = new TetrisBlock(block);
		
		switch(type){
			case MV_DOWN:
				// ↓
				copyBlk.moveDown();
				break;
				
			case MV_Right:
				// →
				copyBlk.moveRight();
				break;
				
			case MV_Left:
				// ←
				copyBlk.moveLeft();
				break;
				
			case TN_Right:
				// 右回転
				copyBlk.turnRight();
				break;
				
			case TN_Left:
				// 左回転
				copyBlk.turnLeft();
				break;
				
			case BL_NEW:
				// 新規生成
				break;
		}
		
		/* **************************************************
		 * 衝突判定
		 * **************************************************/
		for(int blkRow=0; blkRow<copyBlk.block.length; blkRow++){
			if(copyBlk.y + blkRow < 0) {
				continue;
			}
			
			if(copyBlk.y + blkRow > TetrisMaster.FIELD_ROWS-1){
				break;
			}
			
			for(int blkCol=0; blkCol<copyBlk.block.length; blkCol++){
				if(copyBlk.x + blkCol < 0){
					continue;
				}
				
				if(copyBlk.x + blkCol > TetrisMaster.FIELD_COLS-1){
					break;
				}
				
				if(copyBlk.block[blkRow][blkCol] != 0){
					if(this.field.field[copyBlk.y + blkRow][copyBlk.x + blkCol] != 0){
						return	true;
					}
				}
			}
		}
		
		return	false;
	}
	
	/**
	 * ブロック追加
	 */
	public void addBlock(){
		for(int row=0; row<this.block.block.length; row++){
			for(int col=0; col<this.block.block.length; col++){
				if(this.block.block[row][col] != 0){
					this.field.field[this.block.y + row][this.block.x + col] = this.block.block[row][col];
				}
			}
		}
	}
	
	/**
	 * ゲームオーバー判定
	 * 
	 * @return		(bolean)
	 */
	public boolean isGameOver(){
		if(isCollision(BL_NEW)){
			return	true;
			
		}else{
			return	false;
		}
	}
	
	/**
	 * スリープ処理
	 * 
	 * @param	ms	(long)スリープ時間(msec)
	 */
	private void sleep(long ms){
		try{
			Thread.sleep(ms);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	// ==============================================================================================================
	
	/* ************************************************************
	 * タイマー関連処理
	 * ************************************************************/
	/** 現在時刻         */
	private long	nowTime  = 0;
	/** タイマー開始時刻 */
	private long	preTime  = 0;
	/** 経過時間         */
	private long	pastTime = 0;

	/**
	 * タイマー動作
	 * 
	 * @param	waitTime	(long)wait時間(msec)
	 * @return				(boolean)経過可否(true: 指定時間経過 / false: 指定時間非経過)
	 */
	private boolean timer(long waitTime){
		if(preTime == 0){
			// タイマー非動作
			
			// タイマーﾘｾｯﾄ
			preTime  = System.currentTimeMillis();
			pastTime = 0;
			
			return	false;
		}
		
		// 経過時間測定
		nowTime 	= System.currentTimeMillis();
		pastTime 	= pastTime + (nowTime - preTime);
		preTime 	= nowTime;
		
		if(pastTime > waitTime){
			// 指定時間経過
			preTime = 0;
			return	true;
			
		}else{
			// 指定時間非経過
			return	false;
		}
	}
	
	/**
	 * タイマーリセット
	 */
	private void timerReset(){
		preTime = 0;
	}
	
	// ==============================================================================================================
	
	/* ************************************************************
	 * モード関連処理
	 * ************************************************************/
	private int	modeNum = -1;
	private int[]	MODE    = new int[4];
	private int	state   = 0;
	private int	saveMode;
	private int	saveState;
	
	/**
	 * モード設定
	 * 
	 * @param	mode	(int)モード
	 */
	private void inputMode(int mode){
		if(mode == STUP){
			state++;
			
			return;
		}
		
		// 格納配列が満杯なら、何もしない
		if(modeNum >= MODE.length-1){
			return;
		}
		
		// 次回モードを格納
		MODE[++modeNum] = mode;
	}
	
	/**
	 * モード取得
	 * 
	 * @return		(int)モード
	 */
	private int outputMode(){
		int	mode;
		
		// 次回モードが無ければ、現在モード継続
		if(modeNum < 0){
			return	this.mode;
		}
		
		// モード取得
		mode = MODE[0];
		
		System.arraycopy(MODE, 1, MODE, 0, MODE.length-1);
		
		modeNum--;
		
		// PAUSEの場合は以前のモードを記憶
		if(mode == PAUSE){
			if(this.mode != PAUSE){
				saveMode  = this.mode;
				saveState = state;
				
			}else{
				state = saveState;
				
				return	saveMode;
			}
		}
		
		state = 0;
		
		return	mode;
	}
}