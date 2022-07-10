package bb.roborally.server.game.activation;

import bb.roborally.protocol.game_events.Movement;
import bb.roborally.server.ClientList;
import bb.roborally.server.Server;
import bb.roborally.server.game.*;

import java.io.IOException;
import java.util.ArrayList;

public class Move1Handler {

    Server server;
    Game game;
    User user;

    public Move1Handler(Server server, Game game, User user) {
        this.server = server;
        this.game = game;
        this.user = user;
    }

    public static void handle(User user) {
        Robot robot = user.getRobot();
        Position position = robot.getPosition();
        try{
            MovementCheck.wallOnSameFieldForwardCheck(user)== true;
        }
        catch(Exception wallAhead){
            System.out.println("road is blocked by wall");
        }
        ArrayList<User> users = PlayerQueue.getUsers();
        for (User otherUser : users) {
            if(MovementCheck.robotForwardCheck(user, otherUser)){
                Move1Handler.handle(otherUser);
            }
        }
        if (robot.getRobotOrientation() == Orientation.LEFT) {
            Position nextPosition = new Position(position.getX() - 1, position.getY());
            robot.setPosition(nextPosition);
            Movement movement = new Movement(user.getClientID(), nextPosition.getX(), nextPosition.getY());
            try {
                server.broadcast(movement);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            } else if (robot.getRobotOrientation() == Orientation.RIGHT) {
                Position nextPosition = new Position(position.getX() + 1, position.getY());
                robot.setPosition(nextPosition);
                Movement movement = new Movement(user.getClientID(), nextPosition.getX(), nextPosition.getY());
                try {
                    server.broadcast(movement);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else if (robot.getRobotOrientation() == Orientation.TOP) {
                Position nextPosition = new Position(position.getX(), position.getY() + 1);
                robot.setPosition(nextPosition);
                Movement movement = new Movement(user.getClientID(), nextPosition.getX(), nextPosition.getY());
                try {
                    server.broadcast(movement);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else if (robot.getRobotOrientation() == Orientation.BOTTOM) {
                Position nextPosition = new Position(position.getX(), position.getY() - 1);
                robot.setPosition(nextPosition);
                Movement movement = new Movement(user.getClientID(), nextPosition.getX(), nextPosition.getY());
                try {
                    server.broadcast(movement);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }
}
