import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private Canvas canvas;
    private Control control;

    public GUI(Control control){
        this.control = control;

    }

    public void createGameWindow(Color[][] grid)
    {
        getContentPane().setPreferredSize(new Dimension(Control.fieldsCountX * 50, Control.fieldsCountY * 50));
        pack();

        setLocationRelativeTo(null);
        setTitle("Vier gewinnt");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new Canvas(this.control, grid, this);

        add(canvas);

        setVisible(true);
    }

    public void showWinner(Color color)
    {
        String opt = color == Color.RED ? "Rot" : "Gruen";
        opt = color == null ? "Niemand" : opt;

        JOptionPane.showMessageDialog(new JFrame(), opt + " hat gewonnen");
    }

    public String showSettings()
    {
        return JOptionPane.showInputDialog(new JFrame(), "Wir groß soll das Spiel sein? \n breite,hoehe");
    }

    public int getWidth()
    {
        return getContentPane().getWidth();
    }

    public int getHeight()
    {
        return getContentPane().getHeight();
    }

}
