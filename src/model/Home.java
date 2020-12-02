package model;

import java.awt.*;

public class Home extends GameObject {
    public Home(int x, int y) {
        super(x, y);
        setWidth(2);
        setHeight(2);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.drawOval(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());

    }
}
