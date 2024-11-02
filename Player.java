import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private ArrayList<Pieces> myPiecesList;
	private Color color;
	
	public Player(ArrayList<Pieces> myPiecesList, Color color) {
		super();
		this.myPiecesList = myPiecesList;
		this.color = color;
	}
	
	public ArrayList<Pieces> getMyPiecesList() {
		return myPiecesList;
	}
	@Override
	public String toString() {
		return "Player [color=" + color + "]";
	}

	public void setMyPiecesList(ArrayList<Pieces> myPiecesList) {
		this.myPiecesList = myPiecesList;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
