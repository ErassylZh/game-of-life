import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {


    private GameOfLifeGrid grid;
    private JLabel stepsLabel;
    private JLabel endLabel;
    private int stepCount;
    private boolean simulationRunning;
    public MainFrame() {
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        grid = new GameOfLifeGrid();
        stepsLabel = new JLabel("Steps: 0");
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        endLabel=new JLabel();

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSimulation();
            }
        });

        JPanel panel = new JPanel();
        panel.add(stepsLabel);
        panel.add(startButton);
        panel.add(stopButton);
        panel.add(endLabel);
        add(grid);
        add(panel, "South");



        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void startSimulation() {
        simulationRunning=true;
        Thread simulationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    grid.updateGameState();
                    stepCount++;
                    stepsLabel.setText("Steps: " + stepCount);
                    if(!simulationRunning) return;
                    if(!grid.haveAliveCell()) break;
                    if(!grid.isChanged()) break;
                    try {
                        Thread.sleep(500); // Adjust the delay between steps as needed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                endLabel.setText("life end on "+stepCount+" step!");
            }
        });

        simulationThread.start();
    }

    private void stopSimulation() {
        simulationRunning = false;
    }
}
