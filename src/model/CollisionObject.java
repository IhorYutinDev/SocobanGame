package model;

public abstract class CollisionObject extends GameObject {
    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        boolean result = false;
        switch (direction) {
            case DOWN:
                if (this.getX() == gameObject.getX() && (this.getY() + Model.FIELD_CELL_SIZE) == gameObject.getY()) {
                    result = true;
                }
                break;
            case RIGHT:
                if (this.getY() == gameObject.getY() && (this.getX() + Model.FIELD_CELL_SIZE) == gameObject.getX()) {
                    result = true;
                }
                break;
            case UP:
                if (this.getX() == gameObject.getX() && (this.getY() - Model.FIELD_CELL_SIZE) == gameObject.getY()) {
                result = true;
            }
                break;
            case LEFT:
                if (this.getY() == gameObject.getY() && (this.getX() - Model.FIELD_CELL_SIZE) == gameObject.getX()) {
                    result = true;
                }
                break;
        }
        return result;
    }
}
