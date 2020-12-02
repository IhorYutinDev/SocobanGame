package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class LevelLoader {
    private Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        if (level > 60) {
            level = level % 60 == 0 ? 60 : level % 60;
        }

        int x, x0, y, y0;
        x = y = x0 = y0 = Model.FIELD_CELL_SIZE / 2;

        Set<Wall> walls = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Player player = null;

        char[][] matrix = null;
        int arrWidth = 0;
        int arrHeight = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(levels.toFile()));
            while (br.ready()) {
                if (br.readLine().equals("Maze: " + level)) {
                    br.readLine();
                    String stringWidth = br.readLine();
                    arrWidth = Integer.parseInt(stringWidth.substring(stringWidth.indexOf(":") + 2));
                    String stringHeight = br.readLine();
                    arrHeight = Integer.parseInt(stringHeight.substring(stringHeight.indexOf(":") + 2));
                    matrix = new char[arrHeight][arrWidth];
                    br.readLine();
                    br.readLine();
                    br.readLine();
                    for (int i = 0; i < arrHeight; i++) {
                        matrix[i] = br.readLine().toCharArray();
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < arrHeight; i++) {
            for (int j = 0; j < arrWidth; j++) {
                x = x0 + j * Model.FIELD_CELL_SIZE;
                y = y0 + i * Model.FIELD_CELL_SIZE;

                switch (matrix[i][j]) {
                    case 'X':
                        walls.add(new Wall(x, y));
                        break;
                    case '.':
                        homes.add(new Home(x, y));
                        break;
                    case '*':
                        boxes.add(new Box(x, y));
                        break;
                    case '&':
                        homes.add(new Home(x, y));
                        boxes.add(new Box(x, y));
                        break;
                    case '@':
                        player = new Player(x, y);
                        break;
                }
            }
        }
        return new GameObjects(walls, boxes, homes, player);
    }
}
