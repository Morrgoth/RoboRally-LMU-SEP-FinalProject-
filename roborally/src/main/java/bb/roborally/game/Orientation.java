package bb.roborally.game;

public enum Orientation {

	LEFT("left"),
	RIGHT("right"),
	TOP("top"),
	BOTTOM("bottom"),
	TOP_LEFT("topLeft"),
	TOP_RIGHT("topRight"),
	BOTTOM_LEFT("bottomLeft"),
	BOTTOM_RIGHT("bottomRight");

	public final String orientation;
	Orientation(final String orientation){
		this.orientation = orientation;
	}
	@Override
	public String toString(){
		return orientation;
	}
}