public class Control
{

    public static int fieldsCountX = 15, fieldsCountY = 10;
    private Color[][] grid;
    private Color currentPlayer = Color.RED;

    private GUI gui;
    private Line winLine = new Line();

    public Control()
    {
        gui = new GUI(this);

        boolean success = false;

        while(!success)
        {
            String[] size = new String[2];

            try
            {
                size = gui.showSettings().split(",");
            }
            catch(NullPointerException e)
            {
                System.exit(0);
            }



            try
            {
                fieldsCountX = Integer.parseInt(size[0]);
                fieldsCountY = Integer.parseInt(size[1]);
                success = true;
            } catch (NumberFormatException e)
            {

            }
        }

        grid = new Color[fieldsCountX][fieldsCountY];
        gui.createGameWindow(grid);
    }


    public int placeCoin(int x, boolean realPlace)
    {
        if (grid[x][0] == null)
        {
            for (int i = 0; i < fieldsCountY; i++)
            {
                if (grid[x][i] != null)
                {
                    if (realPlace)
                    {
                        grid[x][i - 1] = currentPlayer;
                        if (checkWin())
                        {
                            gui.showWinner(currentPlayer);
                            resetGame();
                        }
                        currentPlayer = currentPlayer == Color.RED ? Color.GREEN : Color.RED;
                    }
                    return i - 1;
                } else if (i == fieldsCountY - 1)
                {
                    if (realPlace)
                    {
                        grid[x][i] = currentPlayer;
                        if (checkWin())
                        {
                            gui.showWinner(currentPlayer);
                            resetGame();
                        }
                        currentPlayer = currentPlayer == Color.RED ? Color.GREEN : Color.RED;
                    }
                    return i;
                }
            }
        }

        return -1;
    }

    private void resetGame()
    {
        winLine.reset();
        for(int x = 0; x < fieldsCountX; x++)
        {
            for(int y = 0; y < fieldsCountY; y++)
            {
                grid[x][y] = null;
            }
        }
    }

    private boolean checkWin()
    {
        for (int x = 0; x < fieldsCountX; x++) {
            for (int y = 0; y < fieldsCountY; y++) {
                if (grid[x][y] == currentPlayer) {
                    int countHorizontal = 0;
                    int countVertical = 0;
                    int countDiagonal1 = 0;
                    int countDiagonal2 = 0;

                    for (int i = 1; i <= 3; i++) {
                        if(x + i < fieldsCountX) {
                            if(grid[x + i][y] == currentPlayer)
                            {
                                countHorizontal++;
                                if(countHorizontal == 3)
                                {
                                    winLine.x1 = x;
                                    winLine.x2 = x + i;
                                    winLine.y1 = winLine.y2 = y;
                                    return true;
                                }
                            }

                            if(y + i < fieldsCountY) {
                                if(grid[x + i][y + i] == currentPlayer)
                                {
                                    countDiagonal1++;
                                    if(countDiagonal1 == 3)
                                    {
                                        winLine.x1 = x;
                                        winLine.x2 = x + i;
                                        winLine.y1 = y;
                                        winLine.y2 = y + i;
                                        return true;
                                    }
                                }
                            }
                        }
                        if(y + i < fieldsCountY) {
                            if(grid[x][y + i] == currentPlayer)
                            {
                                countVertical++;
                                if(countVertical == 3)
                                {
                                    winLine.x1 = winLine.x2 = x;
                                    winLine.y1 = y;
                                    winLine.y2 = y + i;
                                    return true;
                                }
                            }

                            if(x - i >= 0) {
                                if(grid[x - i][y + i] == currentPlayer)
                                {
                                    countDiagonal2++;
                                    if(countDiagonal2 == 3)
                                    {
                                        winLine.x1 = x;
                                        winLine.x2 = x - i;
                                        winLine.y1 = y;
                                        winLine.y2 = y + i;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int i = 0; i < fieldsCountX; i++)
        {
            if(grid[i][0] == null)
            {
                return false;
            }
        }

        gui.showWinner(null);
        resetGame();
        return false;
    }

    public Line getWinLine()
    {
        return winLine;
    }

    public Color getCurrentPlayer()
    {
        return currentPlayer;
    }
}
