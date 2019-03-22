package gui;

/**
 * テトリミノ関連Class
 * 
 * @author	y_nishikawa
 */
public class TetrisBlock{
	/** 座標(X軸) */
	public int			x;
	/** 座標(Y軸) */
	public int			y;
	/** 形状      */
	public int[][]		block;
	
	// ==============================================================================================================
	
	/**
	 * コンストラクター
	 */
	public TetrisBlock(){
		// nop.
	}
	
	/**
	 * コンストラクター
	 * 
	 * @param	srcBlock	(TetrisBlock)コピー元ブロック
	 */
	public TetrisBlock(TetrisBlock srcBlock){
		this.x = srcBlock.x;
		this.y = srcBlock.y;
		
		this.block = new int[srcBlock.block.length][srcBlock.block.length];
		
		this.block = srcBlock.copy();
	}
	
	/**
	 * 新規ブロック生成
	 */
	public void newBlock(){
		// ブロック決定用乱数生成
		int	n = (int)(7 * Math.random() + 1.0);
		
		// ブロック生成
		switch(n){
			case 1:
				// Block(1)
				this.block = new int[][]{
					{0, n, 0}
				  , {n, n, n}
				  , {0, 0, 0}
				};
				
				break;
			
			case 2:
				// Block(2)
				this.block = new int[][]{
					{n, n, 0}
				  , {0, n, n}
				  , {0, 0, 0}
				};
				
				break;
			
			case 3:
				// Block(3)
				this.block = new int[][]{
					{0, n, n}
				  , {n, n, 0}
				  , {0, 0, 0}
				};
				
				break;
			
			case 4:
				// Block(4)
				this.block = new int[][]{
					{0, 0, n}
				  , {n, n, n}
				  , {0, 0, 0}
				};
				
				break;
			
			case 5:
				// Block(5)
				this.block = new int[][]{
					{n, 0, 0}
				  , {n, n, n}
				  , {0, 0, 0}
				};
				
				break;
			
			case 6:
				// Block(6)
				this.block = new int[][]{
					{n, n}
				  , {n, n}
				};
				
				break;
			
			case 7:
				// Block(7)
				this.block = new int[][]{
					{0, 0, 0, 0}
				  , {n, n, n, n}
				  , {0, 0, 0, 0}
				  , {0, 0, 0, 0}
				};
				
				break;
				
			default:
				break;
		}
		
		// ブロック出現位置設定
		this.x = (int)((TetrisMaster.FIELD_COLS - block.length) / 2);
		this.y = 0;
	}
	
	/**
	 * 下移動
	 */
	public void moveDown(){
		// Y軸加算
		this.y++;
	}
	
	/**
	 * 左移動
	 */
	public void moveLeft(){
		// X軸減算
		this.x--;
	}
	
	/**
	 * 右移動
	 */
	public void moveRight(){
		// X軸加算
		this.x++;
	}
	
	/**
	 * ブロックコピー
	 * 
	 * @return		(int[][])コピーされたブロック
	 */
	public int[][] copy(){
		int[][]	copyBlock = new int[this.block.length][this.block.length];
		
		for(int row=0; row<block.length; row++){
			for(int col=0; col<block.length; col++){
				copyBlock[row][col] = block[row][col];
			}
		}
		
		return	copyBlock;
	}
	
	/**
	 * 左回転
	 */
	public void turnLeft(){
		int[][]	turnedBlock = new int[this.block.length][this.block.length];
		
		// ブロックをコピー
		turnedBlock = copy();
		
		for(int row=0; row<this.block.length; row++){
			for(int col=0; col<this.block.length; col++){
				// 行列入替え
				this.block[this.block.length - 1 - col][row] = turnedBlock[row][col];
			}
		}
	}
	
	/**
	 * 右回転
	 */
	public void turnRight(){
		int[][]	turnedBlock = new int[this.block.length][this.block.length];
		
		// ブロックをコピー
		turnedBlock = copy();
		
		for(int row=0; row<this.block.length; row++){
			for(int col=0; col<this.block.length; col++){
				// 行列入替え
				this.block[col][this.block.length - 1 - row] = turnedBlock[row][col];
			}
		}
	}
	
	/**
	 * ブロッククリア
	 */
	public void clear(){
		this.block = new int[][]{{0}};
	}
}