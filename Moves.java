
public class Moves {
	private Squares targetSquare;
	private Pieces piece;
	
	public Moves(Squares targetSquare, Pieces piece) {
		super();
		this.targetSquare = targetSquare;
		this.piece = piece;
	}
	
	public Squares getTargetSquare() {
		return targetSquare;
	}
	public void setTargetSquare(Squares targetSquare) {
		this.targetSquare = targetSquare;
	}
	public Pieces getPiece() {
		return piece;
	}
	public void setPiece(Pieces piece) {
		this.piece = piece;
	}
}
