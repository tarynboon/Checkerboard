import java.awt.*;

public class Pieces {

	protected Board myBoard;
	protected Location myLocation;
	protected Color color;
	protected boolean queen;
	
	//upgraded pieces, when they get to the end they get to move forward and backward
	public boolean isQueen() {
		return queen;
	}

	public void setQueen(boolean queen) {
		this.queen = queen;
	}

	public Pieces(Board myBoard, Location myLocation, Color color) {
		this.myBoard = myBoard;
		this.myLocation = myLocation;
		this.color = color;
		this.queen = false;
	}
	
	public Location getLocation() {
		return myLocation;
	}
	
	public Location getMyLocation() {
		return myLocation;
	}

	public void setMyLocation(Location myLocation) {
		this.myLocation = myLocation;
	}

	public void setMyBoard(Board myBoard) {
		this.myBoard = myBoard;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return "Pieces [myLocation=" + myLocation + ", color=" + color + "]";
	}

}