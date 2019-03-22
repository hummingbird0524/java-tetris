package gui;

/**
 * マスター関連Class
 * 
 * @author	y_nishikawa
 */
public class TetrisMaster{
	/** ドットサイズ   */
	public static final int		BLOCK_SIZE = 18;
	/** 列数           */
	public static final int		FIELD_COLS = 10+2;	// フィールド + 左右壁
	/** 行数           */
	public static final int		FIELD_ROWS = 20+1;	// フィールド + 底面
	
	/** 色設定         */
	public static final int[][]	COLORS = {
		{ 90,  90,  90}	// 空白
	  , {  0,   0, 255}	// 壁部
	  , {  0, 255,   0}	// ブロック山部
	  , {255,   0,   0}	// 落下ブロック
	};
	
	/** ウィンドウ幅   */
	public static int		winWidth  = BLOCK_SIZE * FIELD_COLS;
	/** ウィンドウ高さ */
	public static int		winHeight = BLOCK_SIZE * FIELD_ROWS;
	
	// ==============================================================================================================
}
