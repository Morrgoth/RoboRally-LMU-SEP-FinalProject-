package bb.roborally.server.game.board;

import bb.roborally.protocol.Envelope;
import bb.roborally.protocol.Message;
import bb.roborally.server.game.Orientation;
import bb.roborally.server.game.activation.PushPanelActivator;
import bb.roborally.server.game.tiles.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * author Philipp Keyzman
 */
public class Board implements Message {
	private ArrayList<ArrayList<Cell>> gameMap;

	public Board(ArrayList<ArrayList<Cell>> gameMap) {
		this.gameMap = gameMap;
	}

	public ArrayList<ArrayList<Cell>> getGameMap() {
		return gameMap;
	}

	public void setGameMap(ArrayList<ArrayList<Cell>> gameMap) {
		this.gameMap = gameMap;
	}

	public Cell getAntenna(Board gameMap){
		for(ArrayList<Cell> cellsRow: this.gameMap){
			for(Cell cell: cellsRow){
				for(Tile tile: cell.getTiles()){
					if(tile instanceof Antenna){
						return cell;
					}
				}
			}
		}
		return null;
	}

	public ArrayList<Cell> getStartPoints() {
		ArrayList<Cell> startPoints = new ArrayList<>();
		for (ArrayList<Cell> cellsRow: this.gameMap) {
			for (Cell cell: cellsRow) {
				for (Tile tile: cell.getTiles()) {
					if (tile instanceof StartPoint) {
						startPoints.add(cell);
					}
				}
			}
		}
		return startPoints;
	}

	public ArrayList<Cell> getBlueConveyorBelts() {
		ArrayList<Cell> blueConveyorBelts = new ArrayList<>();
		for (ArrayList<Cell> cellsRow: this.gameMap) {
			for (Cell cell: cellsRow) {
				for (Tile tile: cell.getTiles()) {
					if (tile instanceof ConveyorBelt) {
						ConveyorBelt conveyorBelt = (ConveyorBelt) tile;
						if (conveyorBelt.getSpeed() == 2) {
							blueConveyorBelts.add(cell);
						}
					}
				}
			}
		}
		return blueConveyorBelts;
	}

	public ArrayList<Cell> getGreenConveyorBelts(){
		ArrayList<Cell> greenConveyorBelts = new ArrayList<>();
		for(ArrayList<Cell> cellsRow: this.gameMap){
			for(Cell cell: cellsRow){
				for(Tile tile: cell.getTiles()){
					if(tile instanceof ConveyorBelt){
						ConveyorBelt conveyorBelt = (ConveyorBelt) tile;
						if(conveyorBelt.getSpeed() == 1){
							greenConveyorBelts.add(cell);
						}
					}
				}
			}
		}
		return greenConveyorBelts;
	}

	public ArrayList<Cell> getPushPanels(int register){
		ArrayList<Cell> pushPanels = new ArrayList<>();
		for(ArrayList<Cell> cellsRow: this.gameMap) {
			for (Cell cell : cellsRow) {
				for (Tile tile : cell.getTiles()) {
					if (tile instanceof PushPanel) {
						if(((PushPanel) tile).getRegisters().contains(register)){
							pushPanels.add(cell);
						}
					}
				}
			}
		}
		return pushPanels;
	}

	public ArrayList<Cell> getClockwiseGears(){
		ArrayList<Cell> clockwiseGears = new ArrayList<>();
		for(ArrayList<Cell> cellsRow: this.gameMap){
			for(Cell cell: cellsRow){
				for(Tile tile: cell.getTiles()){
					if(tile instanceof Gear){
						Gear gear = (Gear) tile;
						if(gear.getOrientations().get(0) == Orientation.CLOCKWISE){
							clockwiseGears.add(cell);
						}
					}
				}
			}
		}
		return clockwiseGears;
	}

	public ArrayList<Cell> getCounterclockwiseGears(){
		ArrayList<Cell> counterclockwiseGears = new ArrayList<>();
		for(ArrayList<Cell> cellsRow: this.gameMap){
			for(Cell cell: cellsRow){
				for(Tile tile: cell.getTiles()){
					if(tile instanceof Gear){
						Gear gear = (Gear) tile;
						if(gear.getOrientations().get(0) == Orientation.COUNTERCLOCKWISE){
							counterclockwiseGears.add(cell);
						}
					}
				}
			}
		}
		return counterclockwiseGears;
	}

	public Cell get(int x, int y) {
		return gameMap.get(x).get(y);
	}

	@Override
	public String toJson() {
		return toEnvelope().toJson();
	}

	@Override
	public Envelope toEnvelope() {
		return new Envelope(Envelope.MessageType.GAME_STARTED, this);
	}

}



