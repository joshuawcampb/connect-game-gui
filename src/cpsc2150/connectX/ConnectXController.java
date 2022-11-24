package cpsc2150.connectX;

/**
 * The controller class will handle communication between our View and our Model (IGameBoard)
 *
 * This is where you will write code
 *
 * You will need to include your IGameBoard interface
 * and both of the IGameBoard implementations from Homework 3
 * If your code was correct you will not need to make any changes to your IGameBoard implementation class
 */

public class ConnectXController {
    //our current game that is being played
    private IGameBoard curGame;


    //The screen that provides our view
    private ConnectXView screen;



    public static final int MAX_PLAYERS = 10;
    //our play tokens are hard coded. We could make a screen to get those from the user, but


    int numPlayers;
    private boolean win = false;
    private int counter = 0;
    private char player;
    private boolean beginning = true;
    private String message;

    /**
     *
     * @param model the board implementation
     * @param view the screen that is shown
     * @post the controller will respond to actions on the view using the model.
     */
    ConnectXController(IGameBoard model, ConnectXView view, int np){
        this.curGame = model;
        this.screen = view;
        numPlayers = np;

    }

    /**
     *
     *
     * @param col the column of the activated button
     * @post will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button
     */
    public void processButtonClick(int col) {

        boolean error = false;

        if(win){

            newGame();

        }

        else {

            if (beginning) {

                player = 'X';
                beginning = false;
                win = false;

            }

            if (!curGame.checkIfFree(col)) {

                screen.setMessage("Column is full.");
                error = true;

            }

            if (!error) {

                for (int i = 0; i < curGame.getNumRows(); i++) {
                    BoardPosition boardPos = new BoardPosition(i, col);
                    if (curGame.whatsAtPos(boardPos) == ' ') {
                        screen.setMarker(i, col, player);
                        curGame.placeToken(player, col);
                        i = curGame.getNumRows();
                    }
                }

                if (curGame.checkForWin(col) || curGame.checkTie()) {

                    beginning = true;

                    if (curGame.checkForWin(col))
                        message = "Player " + player + " won! ";

                    else
                        message = "The game resulted in a tie. ";

                    message += "Press any button to play again.";

                    win = true;

                }

                if(win) {

                    screen.setMessage(message);

                }

                else {

                    if (counter < numPlayers - 1)
                        counter++;

                    else
                        counter = 0;

                    if (counter == 0) {
                        player = 'X';
                    }

                    else if (counter == 1) {
                        player = 'O';
                    }

                    else if (counter == 2) {
                        player = 'W';
                    }

                    else if (counter == 3) {
                        player = 'Z';
                    }

                    else if (counter == 4) {
                        player = 'V';
                    }

                    else if (counter == 5) {
                        player = 'T';
                    }

                    else if (counter == 6) {
                        player = 'G';
                    }

                    else if (counter == 7) {
                        player = 'F';
                    }

                    else if (counter == 8) {
                        player = 'S';
                    }

                    else {
                        player = 'R';
                    }

                    String message = "It is " + player + "'s turn.";
                    screen.setMessage(message);

                }
            }
        }
    }

    /**
     * This method will start a new game by returning to the setup screen and controller
     */
    private void newGame()
    {
        //close the current screen
        screen.dispose();
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}
