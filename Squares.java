import java.awt.Color;

public class Squares {
	private Color color;
	private Location location;
	
	public Squares(Color color, Location location) {
		super();
		this.color = color;
		this.location = location;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
}
