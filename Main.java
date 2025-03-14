package breakoutBall;
import javax.swing.JFrame;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GameControl gamePlay = new GameControl();

        frame.setBounds(200,150, 700, 600);
        frame.setTitle("Breakout Ball");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(gamePlay);
        frame.setVisible(true);

    }}