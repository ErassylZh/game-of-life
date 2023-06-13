import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOfLifeGrid extends JPanel {
    private static final int GRID_SIZE = 50; // Number of cells in each dimension
    private static final int CELL_SIZE = 10; // Size of each cell in pixels
    private static final int GRID_WIDTH = GRID_SIZE * CELL_SIZE;
    private static final int GRID_HEIGHT = GRID_SIZE * CELL_SIZE;

    private int lastRow = -1;
    private int lastCol = -1;
    // Rest of the code...
    private boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
    private boolean[][] prev = new boolean[GRID_SIZE][GRID_SIZE];

    public GameOfLifeGrid() {
        setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
        setBackground(Color.WHITE);

        // Mouse listener to toggle cell state on click
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                updateCellState(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                updateCellState(e);
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }
    private void updateCellState(MouseEvent e) {
        int row = e.getY() / CELL_SIZE;
        int col = e.getX() / CELL_SIZE;

        if (row != lastRow || col != lastCol) {
            if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
                grid[row][col] = !grid[row][col];
                lastRow = row;
                lastCol = col;
                repaint();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);

        // Draw vertical lines
        for (int x = 0; x <= GRID_WIDTH; x += CELL_SIZE) {
            g.drawLine(x, 0, x, GRID_HEIGHT);
        }

        // Draw horizontal lines
        for (int y = 0; y <= GRID_HEIGHT; y += CELL_SIZE) {
            g.drawLine(0, y, GRID_WIDTH, y);
        }

        g.setColor(Color.BLACK);

        // Draw live cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col]) {
                    int x = col * CELL_SIZE;
                    int y = row * CELL_SIZE;
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    private void toggleCellState(int row, int col) {
        if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
            grid[row][col] = !grid[row][col];
        }
    }

    // Method to update the game state according to the rules of Game of Life
    public void updateGameState() {
        boolean[][] newGrid = new boolean[GRID_SIZE][GRID_SIZE];
        copyGrids();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int liveNeighbors = countLiveNeighbors(row, col);
                if (grid[row][col]) {
                    // Any live cell with fewer than two live neighbors dies (underpopulation)
                    // Any live cell with more than three live neighbors dies (overpopulation)
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newGrid[row][col] = false;
                    } else {
                        newGrid[row][col] = true;
                    }
                } else {
                    // Any dead cell with exactly three live neighbors becomes a live cell (reproduction)
                    if (liveNeighbors == 3) {
                        newGrid[row][col] = true;
                    }
                }
            }
        }
        grid = newGrid;
        repaint();
    }

    private int countLiveNeighbors(int row, int col) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborRow = row + i;
                int neighborCol = col + j;

                if (neighborRow >= 0 && neighborRow < GRID_SIZE &&
                        neighborCol >= 0 && neighborCol < GRID_SIZE &&
                        !(i == 0 && j == 0)) {
                    if (grid[neighborRow][neighborCol]) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private void copyGrids() {
        for (int i = 0; i < prev.length;i++){
            for(int j=0;j<prev[i].length;j++){
                prev[i][j]=grid[i][j];
            }
        }
    }

    public boolean haveAliveCell() {
        boolean atLeastOneCellAlive=false;
        for(int i=0;i<prev.length;i++)
            for(int j=0;j<prev[i].length;j++){
                if(grid[i][j]) atLeastOneCellAlive=true;
            }


        return atLeastOneCellAlive;
    }
    public boolean isChanged(){
        for(int i=0;i<prev.length;i++)
            for(int j=0;j<prev[i].length;j++){
                if(grid[i][j]!=prev[i][j]) return true;
            }
        return false;
    }
}