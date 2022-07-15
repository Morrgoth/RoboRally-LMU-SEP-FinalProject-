import bb.roborally.server.Server;
import bb.roborally.server.game.Game;
import bb.roborally.server.game.Orientation;
import bb.roborally.server.game.Position;
import bb.roborally.server.game.User;
import bb.roborally.server.game.activation.BlueConveyorBeltActivator;
import bb.roborally.server.game.activation.GreenConveyorBeltActivator;
import bb.roborally.server.game.board.Board;
import bb.roborally.server.game.map.DizzyHighway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConveyorBeltActivatorsTests {
    private static Server server;
    private static Game game;

    @BeforeAll
    public static void init(){
        server = new Server();
        game = server.getGame();
        game.setBoard(new Board(DizzyHighway.buildDizzyHighway()));
    }

    @Test
    public void testGreenConveyorBelt() throws IOException {
        User user1 = new User(0);
        user1.setRobot(game.getRobotList().getRobotByFigureId(1));
        user1.getRobot().setPosition(new Position(2, 9));
        user1.getRobot().setRobotOrientation(Orientation.RIGHT);
        game.getPlayerQueue().add(user1);
        GreenConveyorBeltActivator greenConveyorBeltActivator = new GreenConveyorBeltActivator(server, game, new ArrayList<>());
        greenConveyorBeltActivator.activate();
        assertEquals(3, user1.getRobot().getPosition().getX());
        assertEquals(9, user1.getRobot().getPosition().getY());
    }

    @Test
    public void testBlueConveyorBeltsSameOrientation() throws IOException{
        User user1 = new User(0);
        user1.setRobot(game.getRobotList().getRobotByFigureId(1));
        user1.getRobot().setPosition(new Position(3, 8));
        user1.getRobot().setRobotOrientation(Orientation.RIGHT);
        game.getPlayerQueue().add(user1);
        BlueConveyorBeltActivator blueConveyorBeltActivator = new BlueConveyorBeltActivator(server, game, new ArrayList<>());
        blueConveyorBeltActivator.activate();
        assertEquals(5, user1.getRobot().getPosition().getX());
        assertEquals(8, user1.getRobot().getPosition().getY());
    }

    @Test
    public void testBlueConveyorBeltsNoTurningStraight() throws IOException{
        User user1 = new User(0);
        user1.setRobot(game.getRobotList().getRobotByFigureId(1));
        user1.getRobot().setPosition(new Position(3, 8));
        user1.getRobot().setRobotOrientation(Orientation.BOTTOM);
        game.getPlayerQueue().add(user1);
        BlueConveyorBeltActivator blueConveyorBeltActivator = new BlueConveyorBeltActivator(server, game, new ArrayList<>());
        blueConveyorBeltActivator.activate();
        assertEquals(5, user1.getRobot().getPosition().getX());
        assertEquals(8, user1.getRobot().getPosition().getY());
        assertEquals(Orientation.BOTTOM, user1.getRobot().getRobotOrientation());
    }

    @Test
    public void testBlueConveyorBeltsWithTurning() throws IOException{
        User user1 = new User(0);
        user1.setRobot(game.getRobotList().getRobotByFigureId(1));
        user1.getRobot().setPosition(new Position(4, 8));
        user1.getRobot().setRobotOrientation(Orientation.BOTTOM);
        game.getPlayerQueue().add(user1);
        ArrayList<User> alreadyOnBelts = new ArrayList<>();
        alreadyOnBelts.add(user1);
        BlueConveyorBeltActivator blueConveyorBeltActivator = new BlueConveyorBeltActivator(server, game, alreadyOnBelts);
        blueConveyorBeltActivator.activate();
        assertEquals(6, user1.getRobot().getPosition().getX());
        assertEquals(8, user1.getRobot().getPosition().getY());
        assertEquals(Orientation.RIGHT, user1.getRobot().getRobotOrientation());
    }

    @Test
    public void testBlueConveyorBeltsNoTurning() throws IOException{
        User user1 = new User(0);
        user1.setRobot(game.getRobotList().getRobotByFigureId(1));
        user1.getRobot().setPosition(new Position(4, 8));
        user1.getRobot().setRobotOrientation(Orientation.TOP);
        game.getPlayerQueue().add(user1);
        ArrayList<User> alreadyOnBelts = new ArrayList<>();
        BlueConveyorBeltActivator blueConveyorBeltActivator = new BlueConveyorBeltActivator(server, game, alreadyOnBelts);
        blueConveyorBeltActivator.activate();
        assertEquals(6, user1.getRobot().getPosition().getX());
        assertEquals(8, user1.getRobot().getPosition().getY());
        assertEquals(Orientation.TOP, user1.getRobot().getRobotOrientation());
    }

    @Test
    public void testOutOfBlueConveyorBeltsNoTurning() throws IOException{
        User user1 = new User(0);
        user1.setRobot(game.getRobotList().getRobotByFigureId(1));
        user1.getRobot().setPosition(new Position(3, 7));
        user1.getRobot().setRobotOrientation(Orientation.RIGHT);
        game.getPlayerQueue().add(user1);
        ArrayList<User> alreadyOnBelts = new ArrayList<>();
        BlueConveyorBeltActivator blueConveyorBeltActivator = new BlueConveyorBeltActivator(server, game, alreadyOnBelts);
        blueConveyorBeltActivator.activate();
        assertEquals(4, user1.getRobot().getPosition().getX());
        assertEquals(8, user1.getRobot().getPosition().getY());
        assertEquals(Orientation.RIGHT, user1.getRobot().getRobotOrientation());
    }
}