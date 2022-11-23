import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Canvas extends JPanel
{
    private int sizeX;
    private int sizeY;
    private Graphics2D g2d;
    private Control control;
    private Color[][] grid;
    private GUI gui;

    private Line winLine;

    public Canvas(Control control, Color[][] grid, GUI gui)
    {
        this.grid = grid;
        this.control = control;
        this.gui = gui;

        winLine = control.getWinLine();

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                if (e.getButton() == 1)
                {
                    int x = e.getX() / sizeX;
                    control.placeCoin(x, true);
                }
            }
        });
    }


    @Override
    public void paintComponent(Graphics g)
    {
        sizeX = gui.getWidth() / Control.fieldsCountX;
        sizeY = gui.getHeight() / Control.fieldsCountY;

        super.paintComponent(g);
        g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setStroke(new BasicStroke(2));

        int yHover = -100;
        int xHover = -100;

        int finalSize = Math.min(sizeX, sizeY);

        if (getMousePosition() != null)
        {
            try
            {
                xHover = (int) (getMousePosition().getX()) / sizeX;
                yHover = control.placeCoin(xHover, false);
            } catch (Exception e)
            {

            }
        }

        for (int x = 0; x < Control.fieldsCountX; x++)
        {
            for (int y = 0; y < Control.fieldsCountY; y++)
            {
                if (grid[x][y] != null)
                {
                    if (grid[x][y] == Color.RED)
                    {
                        g2d.setColor(java.awt.Color.RED);
                    } else if (grid[x][y] == Color.GREEN)
                    {
                        g2d.setColor(java.awt.Color.GREEN);
                    }

                    g2d.fillOval(x * sizeX + 3, y * sizeY + 3, finalSize - 6, finalSize - 6);
                } else
                {
                    g2d.setColor(java.awt.Color.GRAY);
                    g2d.drawOval(x * sizeX + 3, y * sizeY + 3, finalSize - 6, finalSize - 6);
                }
            }
        }


        if (winLine.x1 != winLine.x2 || winLine.y1 != winLine.y2)
        {
            g2d.setColor(java.awt.Color.GRAY);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawLine(winLine.x1 * sizeX + sizeX / 2, winLine.y1 * sizeY + sizeY / 2, winLine.x2 * sizeX + sizeX / 2, winLine.y2 * sizeY + sizeY / 2);
        }

        float transparency = yHover < 0 ? 0 : 0.5f;
        g2d.setColor(control.getCurrentPlayer() == Color.RED ? new java.awt.Color(1, 0, 0, transparency) : new java.awt.Color(0, 1, 0, transparency));
        g2d.fillOval(xHover * sizeX - 2, yHover * sizeY - 2, finalSize + 5, finalSize + 5);

        repaint();
    }
}
