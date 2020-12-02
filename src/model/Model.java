package model;



import controller.EventListener;

import java.nio.file.Paths;
import java.util.Set;

public class Model {
    public static int FIELD_CELL_SIZE = 20;
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get("src/res/levels.txt"));

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        restart();
    }

    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();
        if (checkWallCollision(player,direction)||checkBoxCollisionAndMoveIfAvailable(direction)){
            return;
        }
        moveObject(player,direction);
        checkCompletion();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        boolean result = false;
        Set<Wall> setWall = gameObjects.getWalls();
        for (Wall wall : setWall) {
            result = gameObject.isCollision(wall, direction);
            if (result) {
                break;
            }
        }
        return result;
    }

    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        Set<Box> setBox = gameObjects.getBoxes();
        Player player = gameObjects.getPlayer();
        Box nextBox = null;

        for (Box box : setBox) {
            if (player.isCollision(box, direction)) {
                nextBox = box;
                break;
            }
        }

        if (nextBox == null) {
            return false;
        }

        if (checkWallCollision(player, direction) || checkWallCollision(nextBox, direction)) {
            return true;
        }

        for (Box box : setBox) {
            if (box == nextBox) {
                continue;
            }
            if (nextBox.isCollision(box, direction)) {
                return true;
            }
        }
        moveObject(nextBox, direction);
        return false;
    }

    private void moveObject(Movable movable, Direction direction) {
        int x = 0;
        int y = 0;

        switch (direction) {
            case LEFT:
                x = -FIELD_CELL_SIZE;
                break;
            case DOWN:
                y = FIELD_CELL_SIZE;
                break;
            case RIGHT:
                x = FIELD_CELL_SIZE;
                break;
            case UP:
                y = -FIELD_CELL_SIZE;
                break;
        }
        movable.move(x, y);
    }

    public void checkCompletion() {
        boolean isCompleted = true;
        for (Home home : gameObjects.getHomes()) {
            boolean hasBox = false;
            for (Box box : gameObjects.getBoxes()) {
                if (home.getX() == box.getX() && home.getY() == box.getY()){
                    hasBox = true;
                    break;
                }
            }
            if (!hasBox){
                isCompleted = false;
            }
        }
        if (isCompleted){
            eventListener.levelCompleted(currentLevel);
        }
    }
}

