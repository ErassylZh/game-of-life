import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {


    private GameOfLifeGrid grid;
    private JLabel stepsLabel;
    private JLabel endLabel;
    private int stepCount;
    private boolean simulationRunning;
    private int sliderValue = 1;

    public MainFrame() {
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        grid = new GameOfLifeGrid();
        stepsLabel = new JLabel("Steps: 0");
        JSlider slider = new JSlider(1, 5, 1);
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        endLabel = new JLabel();
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.addChangeListener(changeEvent -> sliderValue = slider.getValue());

        startButton.addActionListener(actionEvent -> startSimulation());
        stopButton.addActionListener(actionEvent -> stopSimulation());

        JPanel panel = new JPanel();
        panel.add(slider);
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
        simulationRunning = true;
        Thread simulationThread = new Thread(() -> {
            while (true) {
                grid.updateGameState();
                stepCount++;
                stepsLabel.setText("Steps: " + stepCount);
                if (!simulationRunning) return;
                if (!grid.haveAliveCell()) break;
                if (!grid.isChanged()) break;
                try {
                    Thread.sleep(500 / sliderValue); // Adjust the delay between steps as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            endLabel.setText("life end on " + stepCount + " step!");

        });

        simulationThread.start();
    }

    private void stopSimulation() {
        simulationRunning = false;
    }
}
