package bb.roborally.game.board;

import bb.roborally.game.Position;
import bb.roborally.game.tiles.Tile;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * author Philipp Keyzman
 */
public class Cell {
	private Position position;
	private ArrayList<Tile> tiles;
	private final StackPane stackPane = new StackPane();
	private Node overlay = null;
	public static final int CELL_WIDHT = 40;
	public static final int CELL_HEIGHT = 40;
	public Cell() {
		this.tiles = new ArrayList<>();
		stackPane.setPrefHeight(CELL_HEIGHT);
		stackPane.setPrefWidth(CELL_WIDHT);
	}
	public Cell(ArrayList<Tile> tiles) {
		this.tiles = tiles;
		stackPane.setPrefHeight(CELL_HEIGHT);
		stackPane.setPrefWidth(CELL_WIDHT);
	}
	public Cell(int x, int y) {
		this.tiles = new ArrayList<>();
		setPosition(x, y);
	}
	public StackPane getStackPane() {
		return stackPane;
	}
	public void clear() {
		stackPane.getChildren().clear();
	}
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	public Tile getTile(int index) {
		return tiles.get(index);
	}
	public Tile getTile(String type) {
		for (Tile tile: tiles) {
			if (tile.getType().equals(type)) {
				return tile;
			}
		}
		return null;
	}
	public void populate() {
		clear();
		for (Tile tile: tiles) {
			Image image = new Image(getClass().getResource(tile.getResource()).toExternalForm());
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(CELL_HEIGHT);
			imageView.setFitWidth(CELL_WIDHT);
			stackPane.getChildren().add(imageView);
		}
	}
	public int getTileCount() {
		return tiles.size();
	}
	public boolean hasTile(String type) {
		for (Tile tile: tiles) {
			if (tile.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}
	public void push(Node node) {
		stackPane.getChildren().add(node);
		overlay = node;
	}
	public void pop() {
		stackPane.getChildren().remove(overlay);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(int x, int y) {
		this.position = new Position(x, y);
	}
}
