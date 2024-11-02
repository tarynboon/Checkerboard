import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;


public class CheckerRunner extends GraphicsProgram {
	private Board B;
	private GCanvas theBoardCanvas;
	private int squareSize = 30;
	private int piecesRadius = 25;
	private int offset = 2;
	private ArrayList<Moves> clickedPieceMoves;
	private Pieces clickedPiece;
	public static final int APPLICATION_WIDTH = 300;
	public static final int APPLICATION_HEIGHT = 300;
	
	public void run() {
		gameInstructions();
		addMouseListeners();
		setUpBoard();
		playTurn();
		//drawEmptyBoard
		
	}
	
	public void gameInstructions() {
		System.out.println("GAME INSTRUCTIONS: ");
		System.out.println("P1 is RED. P2 is GREEN.");
		System.out.println("The white outlined circles are all your possible moves."
				+ "Once you click on a piece, you cannot change it.");
		System.out.println("Yellow circles will show the possible moves for just the clicked piece");
		System.out.println();
		System.out.println( "Click once to make your move. Player will switch after a move");
		System.out.println();
		System.out.println("If your piece gets to the end of the board, it becomes "
				+ "a queen and can move forwards and backwards diagonally");
		System.out.println("The game ends when either player's pieces are "
				+ "all captured or if a player cannot make anymore moves");
	}
	
	public void setUpBoard(){
		B = new Board();
		theBoardCanvas = this.getGCanvas();
	}
	
	public void playTurn() {
		if(!checkerGameOver()) {
			drawSquares();
			drawPieces();
			drawAllPossibleMoves();
		}	
	}
	
	public boolean checkerGameOver() {
		if(B.getPossibleMoves(B.getPlayerTurn()).size() <= 0) {
			System.out.println("Game over.");
			System.out.println(B.getPlayerTurn() + " loses.");
			return true;
		}
		return false;
	}
	
	public void drawEmptyBoard(){
		for(int row = 0 ; row < B.getWidth(); row++)
			for(int col=0; col < B.getHeight(); col++){
				GRect r = new GRect(row*squareSize, col*squareSize, squareSize, squareSize);
				r.setFillColor(Color.WHITE);
				r.setFilled(true);
				theBoardCanvas.add(r);
			}
	}
	
	public void drawSquares(){
		for(Squares x: B.getcheckersquaresList()){
			GRect r = new GRect (x.getLocation().getX()*squareSize, x.getLocation().getY()*squareSize,squareSize,squareSize);
			r.setFillColor(x.getColor());
			r.setFilled(true);
			theBoardCanvas.add(r);
		}
	}
	
	public void drawPieces() {
		for(Pieces x: B.getPiecesList()) {
			GOval r = new GOval (x.getLocation().getX() * squareSize + offset, x.getLocation().getY() * squareSize + offset, piecesRadius, piecesRadius);
			r.setFillColor(x.getColor());
			r.setFilled(true);
			theBoardCanvas.add(r);
		}
	}
	
	public void redrawBoard() {
		drawSquares();
		drawPieces();
	}
	
	public void drawAllPossibleMoves() {
		for(Moves x: B.getPossibleMovesList()) {
			GOval r = new GOval (x.getTargetSquare().getLocation().getX() * squareSize + offset, x.getTargetSquare().getLocation().getY() * squareSize + offset, piecesRadius, piecesRadius);
			r.setColor(Color.white);
			r.setFilled(false);
			theBoardCanvas.add(r);
		}
	}
	
	public void drawPossibleMovesClickedPiece(Pieces clickedPiece) {
		for(Moves x: B.getClickedPiecePossibleMovesList(clickedPiece)) {
			GOval r = new GOval (x.getTargetSquare().getLocation().getX() * squareSize + offset, x.getTargetSquare().getLocation().getY() * squareSize + offset, piecesRadius, piecesRadius);
			r.setColor(Color.yellow);
			r.setFilled(false);
			theBoardCanvas.add(r);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		Location mouseLocation = convertMouseIntoBoard(e.getX(), e.getY());
		if(clickedPiece == null) {
			clickedPiece = B.getPieceAt(mouseLocation);
			//draw possible moves for clicked piece if the color matches the turn
			if(clickedPiece != null && clickedPiece.getColor().equals(B.getPlayerTurn().getColor())) {
				drawPossibleMovesClickedPiece(clickedPiece);
				clickedPieceMoves = B.clickedPiecePossibleMoves(B.getPlayerTurn(), clickedPiece);
			} else {
				clickedPiece = null;
			}
		} else {
			for(Moves x : clickedPieceMoves) {
				Location target = x.getTargetSquare().getLocation();
				
				if(mouseLocation.equals(target)) {
					B.movePiece(clickedPiece, target);
					redrawBoard();
					clickedPiece = null;
					B.switchPlayerTurn();
					playTurn();
				}
			}
		}
	}
	
	//takes mouse position and converts it into board grid location
	public Location convertMouseIntoBoard(int mouseX, int mouseY) {
		Location boardPosition = new Location(mouseX/squareSize, mouseY/squareSize);
		return boardPosition;
	}
}
