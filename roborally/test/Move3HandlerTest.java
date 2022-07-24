import bb.roborally.server.Server;
import bb.roborally.server.game.Game;
import bb.roborally.server.game.Orientation;
import bb.roborally.server.game.Position;
import bb.roborally.server.game.User;
import bb.roborally.server.game.activation.Move3Handler;
import bb.roborally.server.game.board.ServerBoard;
import bb.roborally.map.ExtraCrispy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Veronika Heckel
 */
public class Move3HandlerTest {
        private static Server server;
        private static Game game;

        @BeforeAll
        public static void init(){
            server = new Server();
            game = server.getGame();
           // game.setBoard(new ServerBoard(board, ExtraCrispy.buildExtraCrispy()));
        }

        @Test
        public void testMove3normalMoveForward() throws IOException {           //Right: erkannt, Bottom: Problem, Left: Problem, Top: Problem
            User user1 = new User(0);
            user1.setName("user1");
            user1.setRobot(game.getRobotList().getRobotByFigureId(1));
            user1.getRobot().setPosition(new Position(6,0));
            user1.getRobot().setRobotOrientation(Orientation.LEFT);

            User user2 = new User(1);
            user2.setName("user2");
            user2.setRobot(game.getRobotList().getRobotByFigureId(2));
            user2.getRobot().setPosition(new Position(0,0));
            user2.getRobot().setRobotOrientation(Orientation.BOTTOM);
            game.getPlayerQueue().add(user1);
            game.getPlayerQueue().add(user2);

            Move3Handler move3Handler = new Move3Handler(server, game, user1);
            move3Handler.handleAlt();
            
            assertEquals(3, user1.getRobot().getPosition().getX());
            assertEquals(0, user1.getRobot().getPosition().getY());

            assertEquals(0, user2.getRobot().getPosition().getX());
            assertEquals(0, user2.getRobot().getPosition().getY());
        }

    @Test
    public void testMove3OffBoard() throws IOException {
        game.setSelectedMap("ExtraCrispy");
        User user1 = new User(0);
        user1.setName("user1");
        user1.setRobot(game.getRobotList().getRobotByFigureId(1));
        user1.setStartingPoint(new Position(0,6));
        user1.getRobot().setPosition(new Position(0,0));
        user1.getRobot().setRobotOrientation(Orientation.TOP);

        User user2 = new User(1);
        user2.setName("user2");
        user2.setRobot(game.getRobotList().getRobotByFigureId(2));
        user2.setStartingPoint(new Position(1,4));
        user2.getRobot().setPosition(new Position(8,4));
        user2.getRobot().setRobotOrientation(Orientation.BOTTOM);

        game.getPlayerQueue().add(user1);
        game.getPlayerQueue().add(user2);

        Move3Handler move3Handler = new Move3Handler(server, game, user1);
        move3Handler.handleAlt();


        assertEquals(0, user1.getRobot().getPosition().getX());
        assertEquals(6, user1.getRobot().getPosition().getY());

        assertEquals(2, user1.getProgrammingDeck().getDiscardPile().size());
        assertEquals("Spam", user1.getProgrammingDeck().getDiscardPile().get(0).getName());

        assertEquals(8, user2.getRobot().getPosition().getX());
        assertEquals(4, user2.getRobot().getPosition().getY());
    }

        @Test
        public void testMove3FallingInPit() throws IOException {

            game.setSelectedMap("ExtraCrispy");
            User user1 = new User(0);
            user1.setName("user1");
            user1.setRobot(game.getRobotList().getRobotByFigureId(1));
            user1.setStartingPoint(new Position(0,6));
            user1.getRobot().setPosition(new Position(4,3));
            user1.getRobot().setRobotOrientation(Orientation.RIGHT);

            User user2 = new User(1);
            user2.setName("user2");
            user2.setRobot(game.getRobotList().getRobotByFigureId(2));
            user2.setStartingPoint(new Position(1,4));
            user2.getRobot().setPosition(new Position(8,4));
            user2.getRobot().setRobotOrientation(Orientation.TOP);


            game.getPlayerQueue().add(user1);
            game.getPlayerQueue().add(user2);
            Move3Handler move3Handler = new Move3Handler(server, game, user1);
            move3Handler.handleAlt();


            assertEquals(0, user1.getRobot().getPosition().getX());
            assertEquals(0, user1.getRobot().getPosition().getY());

            assertEquals(2, user1.getProgrammingDeck().getDiscardPile().size());
            assertEquals("Spam", user1.getProgrammingDeck().getDiscardPile().get(0).getName());

            assertEquals(8, user2.getRobot().getPosition().getX());
            assertEquals(4, user2.getRobot().getPosition().getY());

        }

        @Test
        public void testMove3BlockedByWallOnSameField() throws IOException{
            User user1 = new User(0);
            user1.setName("user1");
            user1.setRobot(game.getRobotList().getRobotByFigureId(1));
            user1.getRobot().setPosition(new Position(1,4));
            user1.getRobot().setRobotOrientation(Orientation.TOP);

            User user2 = new User(1);
            user2.setName("user2");
            user2.setRobot(game.getRobotList().getRobotByFigureId(2));
            user2.getRobot().setPosition(new Position(8,4));
            user2.getRobot().setRobotOrientation(Orientation.TOP);

            game.getPlayerQueue().add(user1);
            game.getPlayerQueue().add(user2);

            Move3Handler move3Handler = new Move3Handler(server, game, user1);
            move3Handler.handleAlt();

            assertEquals(1, user1.getRobot().getPosition().getX());
            assertEquals(2, user1.getRobot().getPosition().getY());

            assertEquals(8, user2.getRobot().getPosition().getX());
            assertEquals(4, user2.getRobot().getPosition().getY());


        }

        @Test
        public void testMove3BlockedByWallOnNextField() throws IOException{
            User user1 = new User(0);
            user1.setName("user1");
            user1.setRobot(game.getRobotList().getRobotByFigureId(1));
            user1.getRobot().setPosition(new Position(1,9));
            user1.getRobot().setRobotOrientation(Orientation.RIGHT);

            User user2 = new User(1);
            user2.setName("user2");
            user2.setRobot(game.getRobotList().getRobotByFigureId(2));
            user2.getRobot().setPosition(new Position(8,4));
            user2.getRobot().setRobotOrientation(Orientation.TOP);

            game.getPlayerQueue().add(user1);
            game.getPlayerQueue().add(user2);

            Move3Handler move3Handler = new Move3Handler(server, game, user1);
            move3Handler.handleAlt();

            assertEquals(3, user1.getRobot().getPosition().getX());
            assertEquals(9, user1.getRobot().getPosition().getY());

            assertEquals(8, user2.getRobot().getPosition().getX());
            assertEquals(4, user2.getRobot().getPosition().getY());
        }

         @Test
        public void moveThreePushRobot() throws IOException{
            User user1 = new User(0);
            User user2 = new User(1);
            User user3 = new User(2);

            user1.setName("user1");
            user2.setName("user2");

            user1.setRobot(game.getRobotList().getRobotByFigureId(1));
            user1.getRobot().setPosition(new Position(7,0));
            user1.getRobot().setRobotOrientation(Orientation.LEFT);

            user2.setRobot(game.getRobotList().getRobotByFigureId(2));
            user2.getRobot().setPosition(new Position(6,0));
            user2.getRobot().setRobotOrientation(Orientation.RIGHT);

             user3.setRobot(game.getRobotList().getRobotByFigureId(3));
             user3.getRobot().setPosition(new Position(5,0));
             user3.getRobot().setRobotOrientation(Orientation.RIGHT);


            game.getPlayerQueue().add(user1);
            game.getPlayerQueue().add(user2);
            game.getPlayerQueue().add(user3);

            Move3Handler move3Handler = new Move3Handler(server, game, user1);
            move3Handler.handleAlt();

            assertEquals(4, user1.getRobot().getPosition().getX());
            assertEquals(0, user1.getRobot().getPosition().getY());

            assertEquals(3, user2.getRobot().getPosition().getX());
            assertEquals(0, user2.getRobot().getPosition().getY());

            assertEquals(2, user3.getRobot().getPosition().getX());
            assertEquals(0, user3.getRobot().getPosition().getY());
        }

    @Test
    public void moveThreePushRobotWithWall() throws IOException{
        User user1 = new User(0);
        User user2 = new User(1);
        User user3 = new User(2);

        user1.setName("user1");
        user2.setName("user2");

        user1.setRobot(game.getRobotList().getRobotByFigureId(1));
        user1.getRobot().setPosition(new Position(4,0));
        user1.getRobot().setRobotOrientation(Orientation.RIGHT);

        user2.setRobot(game.getRobotList().getRobotByFigureId(2));
        user2.getRobot().setPosition(new Position(5,0));
        user2.getRobot().setRobotOrientation(Orientation.BOTTOM);

        user3.setRobot(game.getRobotList().getRobotByFigureId(3));
        user3.getRobot().setPosition(new Position(6,0));
        user3.getRobot().setRobotOrientation(Orientation.LEFT);


        game.getPlayerQueue().add(user1);
        game.getPlayerQueue().add(user2);
        game.getPlayerQueue().add(user3);

        Move3Handler move3Handler = new Move3Handler(server, game, user1);
        move3Handler.handleAlt();


        assertEquals(6, user1.getRobot().getPosition().getX());
        assertEquals(0, user1.getRobot().getPosition().getY());

        assertEquals(7, user2.getRobot().getPosition().getX());
        assertEquals(0, user2.getRobot().getPosition().getY());

        assertEquals(8, user3.getRobot().getPosition().getX());
        assertEquals(0, user3.getRobot().getPosition().getY());
    }

    @Test
    public void testBlocktByWallBetweenNeighbors() throws IOException {

        game.setSelectedMap("ExtraCrispy");

        User user1 = new User(0);
        User user2 = new User(1);
        User user3 = new User(2);
        User user4 = new User(3);

        user1.setName("user1");
        user2.setName("user2");
        user3.setName("user3");
        user4.setName("user4");

        user1.setRobot(game.getRobotList().getRobotByFigureId(1));
        user1.setStartingPoint(new Position(1,4));
        user1.getRobot().setPosition(new Position(4,0));
        user1.getRobot().setRobotOrientation(Orientation.RIGHT);

        user2.setRobot(game.getRobotList().getRobotByFigureId(2));
        user2.setStartingPoint(new Position(1,8));
        user2.getRobot().setPosition(new Position(5,0));
        user2.getRobot().setRobotOrientation(Orientation.RIGHT);

        user3.setRobot(game.getRobotList().getRobotByFigureId(3));
        user3.getRobot().setPosition(new Position(6,0));
        user3.setStartingPoint(new Position(1,5));
        user3.getRobot().setRobotOrientation(Orientation.LEFT);

        user4.setRobot(game.getRobotList().getRobotByFigureId(4));
        user4.setStartingPoint(new Position(1,4));
        user4.getRobot().setPosition(new Position(9,0));
        user4.getRobot().setRobotOrientation(Orientation.TOP);

        game.getPlayerQueue().add(user1);
        game.getPlayerQueue().add(user2);
        game.getPlayerQueue().add(user3);
        game.getPlayerQueue().add(user4);

        Move3Handler move3Handler = new Move3Handler(server, game, user1);
        move3Handler.handleAlt();

        assertEquals(6, user1.getRobot().getPosition().getX());
        assertEquals(0, user1.getRobot().getPosition().getY());

        assertEquals(7, user2.getRobot().getPosition().getX());
        assertEquals(0, user2.getRobot().getPosition().getY());

        assertEquals(8, user3.getRobot().getPosition().getX());
        assertEquals(0, user3.getRobot().getPosition().getY());

        assertEquals(9, user4.getRobot().getPosition().getX());
        assertEquals(0, user4.getRobot().getPosition().getY());
    }


}