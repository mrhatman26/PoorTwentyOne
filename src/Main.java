import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main{
    static Scanner uInput = new Scanner(System.in);
    public static void main(String[] args){
        int playerCount = 2;
        while (true){
            System.out.println("How many players? (Between 2 and 6):");
            String input = uInput.nextLine();
            if (!isNumber(input)){
                System.out.println("\nInput must be a number!\n");
            }
            else{
                playerCount = Integer.parseInt(input);
                if (playerCount < 2 || playerCount > 6){
                    System.out.println("\nInput must be between 2 and 6!\n");
                }
                else{
                    System.out.println(input + " players selected.\n");
                    PauseSeconds(1);
                    break;
                }
            }
        }
        Player[] players = new Player[playerCount];
        String[] names = {"PC", "Paul", "Steve", "Nyu", "Tim", "Ona"};
        for (int x = 0; x < playerCount; x++){
            players[x] = new Player(names[x], "//", 0);
        }
        System.out.println("Let the game begin!");
        PauseSeconds(2);
        gameLoop(players);
    }

    public static void gameLoop(Player[] gamePlayers){
        int gameNo = 1;
        int currentPlayer = 0;
        int randomNo = 0;
        Random rand = new Random();
        String[] cardNos = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        Boolean gameLoop = true;
        Boolean firstRound = true;
        Boolean roundFinished = false;
        while (gameLoop){
            currentPlayer = 0;
            if (firstRound){
                System.out.println("Game " + String.valueOf(gameNo) + "!");
                PauseSeconds(1);
            }
            while (currentPlayer < gamePlayers.length){
                gamePlayers[currentPlayer].setHeld(false);
                System.out.println("\n" + gamePlayers[currentPlayer].getName() + "'s turn!");
                if (!gamePlayers[currentPlayer].getBusted() && !gamePlayers[currentPlayer].getWinner()){
                    if (currentPlayer == 0){
                        if (firstRound){
                            System.out.println("The dealer is passing you your first card");
                            PauseSeconds(1);
                            randomNo = rand.nextInt(12 - 0 + 1) + 0;
                            System.out.println("Your card is " + cardNos[randomNo]);
                            PauseSeconds(1);
                            gamePlayers[currentPlayer].addCard(cardNos[randomNo]);
                            gamePlayers[currentPlayer].increaseScore(randomNo + 1);
                            System.out.println("Your score is now " + String.valueOf(gamePlayers[currentPlayer].getScore() + "/21"));
                            PauseSeconds(3);
                        }
                        else{
                            if (gamePlayers[currentPlayer].getScore() != 21){
                                String uChoice = "";
                                while (true){
                                    System.out.println("Hit (H) or Stand(S)?:");
                                    System.out.println("Your score is currently " + String.valueOf(gamePlayers[currentPlayer].getScore() + "/21"));
                                    System.out.println("[Your cards are: " + gamePlayers[currentPlayer].getCards().replaceAll(",", " | ") + "]");
                                    uChoice = uInput.nextLine().toUpperCase();
                                    if (!uChoice.equals("H") && !uChoice.equals("S")){
                                        System.out.println("\nPlease enter H or S!\n");
                                    }
                                    else{
                                        break;
                                    }
                                }
                                if (uChoice.equals("H")){
                                    System.out.println("The dealer is passing you another card");
                                    PauseSeconds(1);
                                    randomNo = rand.nextInt(12 - 0 + 1) + 0;
                                    System.out.println("Your card is " + cardNos[randomNo]);
                                    PauseSeconds(1);
                                    gamePlayers[currentPlayer].addCard(cardNos[randomNo]);
                                    gamePlayers[currentPlayer].increaseScore(randomNo + 1);
                                    System.out.println("Your score is now " + String.valueOf(gamePlayers[currentPlayer].getScore() + "/21"));
                                    PauseSeconds(1);
                                    System.out.println("[Your cards are now: " + gamePlayers[currentPlayer].getCards().replaceAll(",", " | ") + "]");
                                    if (gamePlayers[currentPlayer].getScore() > 21){
                                        gamePlayers[currentPlayer].setBusted(true);
                                        System.out.println("You've gone bust!");
                                    }
                                    if (gamePlayers[currentPlayer].getScore() == 21){
                                        gamePlayers[currentPlayer].setWinner(true);
                                        System.out.println("You score is now 21! You might have won!");
                                    }
                                    PauseSeconds(3);
                                }
                                if (uChoice.equals("S")){
                                    System.out.println("The dealer avoids giving you a card");
                                    PauseSeconds(1);
                                    System.out.println("Your score is still " + String.valueOf(gamePlayers[currentPlayer].getScore() + "/21"));
                                    PauseSeconds(1);
                                    System.out.println("Your cards are still: "+ gamePlayers[currentPlayer].getCards().replaceAll(",", " | ") + "]");
                                    PauseSeconds(1);
                                    gamePlayers[currentPlayer].setHeld(true);
                                }
                            }
                            else{
                                System.out.println("Your score is already 21 so there's no need to hit");
                                PauseSeconds(1);
                                System.out.println("The dealer avoids giving you a card");
                                PauseSeconds(1);
                                System.out.println("Your score is still " + String.valueOf(gamePlayers[currentPlayer].getScore() + "/21"));
                                PauseSeconds(1);
                                System.out.println("Your cards are still: "+ gamePlayers[currentPlayer].getCards().replaceAll(",", " | ") + "]");
                                PauseSeconds(1);
                                gamePlayers[currentPlayer].setHeld(true);
                                //gamePlayers[currentPlayer].setFinished(true);
                            }
                        }
                        //Add "A.I".
                    }
                    else{
                        if (!firstRound) {
                            randomNo = rand.nextInt(100 - 1 + 1) + 1;
                            if (randomNo > 50) {
                                System.out.println(gamePlayers[currentPlayer].getName() + " chose to hit!");
                                PauseSeconds(1);
                                System.out.println("The dealer hands " + gamePlayers[currentPlayer].getName() + " a card.");
                                PauseSeconds(1);
                                randomNo = rand.nextInt(12 - 0 + 1) + 0;
                                gamePlayers[currentPlayer].addCard(cardNos[randomNo]);
                                gamePlayers[currentPlayer].increaseScore(randomNo + 1);
                                if (gamePlayers[currentPlayer].getScore() > 21) {
                                    gamePlayers[currentPlayer].setBusted(true);
                                    //gamePlayers[currentPlayer].setFinished(true);
                                }
                                if (gamePlayers[currentPlayer].getScore() == 21) {
                                    gamePlayers[currentPlayer].setWinner(true);
                                    //gamePlayers[currentPlayer].setFinished(true);
                                }
                            } else {
                                System.out.println(gamePlayers[currentPlayer].getName() + " chose to stand");
                                PauseSeconds(1);
                                System.out.println("The dealer avoids giving them a card");
                                PauseSeconds(1);
                                //gamePlayers[currentPlayer].setFinished(true);
                            }
                        }
                        else{
                            System.out.println("The dealer hands " + gamePlayers[currentPlayer].getName() + " their first card.");
                            PauseSeconds(1);
                            randomNo = rand.nextInt(12 - 0 + 1) + 0;
                            gamePlayers[currentPlayer].addCard(cardNos[randomNo]);
                            gamePlayers[currentPlayer].increaseScore(randomNo + 1);
                            if (gamePlayers[currentPlayer].getScore() > 21) {
                                gamePlayers[currentPlayer].setBusted(true);
                                //gamePlayers[currentPlayer].setFinished(true);
                            }
                            if (gamePlayers[currentPlayer].getScore() == 21) {
                                gamePlayers[currentPlayer].setWinner(true);
                                //gamePlayers[currentPlayer].setFinished(true);
                            }
                        }
                    }
                }
                else{
                    if (gamePlayers[currentPlayer].getBusted()){
                        System.out.println("\nIt would be " + gamePlayers[currentPlayer].getName() + "'s turn, but they've gone bust with a final score of " + gamePlayers[currentPlayer].getScore());
                        PauseSeconds(1);
                        //gameLoop = false;
                    }
                    if (gamePlayers[currentPlayer].getWinner()){
                        if (currentPlayer != 0){
                            System.out.println(gamePlayers[currentPlayer].getName() + " chose to stand");
                            PauseSeconds(1);
                            System.out.println("The dealer avoids giving them a card");
                            PauseSeconds(1);
                        }
                    }
                }
                currentPlayer++;

            }
            if (firstRound){
                firstRound = false;
            }
            //Check if all players are finished
            roundFinished = true;
            for (int x = 0; x < gamePlayers.length; x++){
                if (!gamePlayers[x].inFinishState()){
                    roundFinished = false;
                }
            }
            //If all players are finished, reveal the winners and their cards.
            if (roundFinished){
                System.out.println("\nROUND OVER!\n");
                PauseSeconds(1);
                int winnerAmount = 0;
                int highestScore = 0;
                int highScoreNo = 0;
                String winner = "";
                String uChoice = "";
                String[] winners = new String[gamePlayers.length];
                for (int x = 0; x < gamePlayers.length; x++){
                    if (gamePlayers[x].getWinner()){
                        winnerAmount++;
                        winners[x] = gamePlayers[x].getName() + "+" + gamePlayers[x].getCards() + "+"  + gamePlayers[x].getScore();
                        winner = gamePlayers[x].getName() + "+" + gamePlayers[x].getCards() + "+"  + gamePlayers[x].getScore();
                    }
                    else{
                        if (!gamePlayers[x].getBusted()) {
                            if (gamePlayers[x].getScore() > highestScore) {
                                highestScore = x;
                            }
                        }
                    }
                }
                if (winnerAmount <= 0){
                    System.out.println("Nobody got to 21, but " + gamePlayers[highScoreNo].getName() + " got the closest with a score of" + String.valueOf(highestScore));
                    PauseSeconds(1);
                    System.out.println("Their cards were " + gamePlayers[highScoreNo].getCards());
                    PauseSeconds(3);
                }
                if (winnerAmount == 1){
                    String[] winnerInfo = winner.split("\\+");
                    System.out.println("The winner was " + winnerInfo[0] + "!\nThey had a score of " + winnerInfo[2] + "\nWith the following cards: " + winnerInfo[1]);
                    PauseSeconds(3);
                    System.out.println("\nThe rest of you lost");
                    PauseSeconds(1);
                }
                if (winnerAmount > 1){
                    System.out.print("There was no winner as it was a draw!");
                    PauseSeconds(1);
                    System.out.println("\nThe winners were:\n");
                    PauseSeconds(1);
                    for (int x = 0; x < gamePlayers.length; x++){
                        if (winners[x] != null){
                            String[] winnerInfo = winners[x].split("\\+");
                            System.out.println(winnerInfo[0] + "\nWith a score of " + winnerInfo[2] + "\nAnd the following cards: " + winnerInfo[1]);
                        }
                        PauseSeconds(1);
                    }
                    if (winnerAmount >= gamePlayers.length){
                        System.out.println("\nYou all won, so technically, you're all losers, right?");
                        PauseSeconds(3);
                    }
                    else{
                        System.out.println("\nIt was a draw for the winners. That just makes those who went bust even bigger losers, right?");
                        PauseSeconds(3);
                    }
                }
                //break;
                while (true){
                    System.out.println("Would you like to play again? (Y/N)");
                    uChoice = uInput.nextLine().toUpperCase();
                    if (!uChoice.equals("Y") && !uChoice.equals("N")){
                        System.out.println("\nPlease enter Y or N\n");
                    }
                    else{
                        break;
                    }
                }
                if (uChoice.equals("Y")){
                    System.out.println("\nResetting for another game!\n");
                    PauseSeconds(3);
                    for (int x = 0; x < gamePlayers.length; x++){
                        gamePlayers[x].resetCards();
                        gamePlayers[x].resetScore();
                        gamePlayers[x].setBusted(false);
                        gamePlayers[x].setFinished(false);
                        gamePlayers[x].setHeld(false);
                        gamePlayers[x].setWinner(false);
                        roundFinished = false;
                        firstRound = true;
                        gameNo++;
                    }
                }
                else{
                    System.out.println("\nThank you for playing!\n");
                    PauseSeconds(3);
                    break;
                }
            }
        }
        uInput.close();
    }

    public static Boolean isNumber(String value){
        try{
            Integer.parseInt(value);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }

    public static void PauseSeconds(int seconds){
        try{
            TimeUnit.SECONDS.sleep(seconds);
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}

class Player{
    private String name;
    private String cards;
    private int score;
    private Boolean busted = false;
    private Boolean winner = false;
    private Boolean finished = false;
    private Boolean held = false;
    Player(String name, String cards, int score){
        this.name = name;
        this.cards = cards;
        this.score = score;
    }

    public String getName(){
        return this.name;
    }

    public String getCards(){
        return this.cards;
    }

    public int getScore(){
        return this.score;
    }

    public boolean getBusted(){
        return this.busted;
    }

    public boolean getWinner(){
        return this.winner;
    }

    public boolean getHeld(){
        return this.held;
    }

    public boolean getFinished(){
        return this.finished;
    }

    public void increaseScore(int amount){
        this.score = this.score + amount;
    }

    public void resetScore(){
        this.score = 0;
    }

    public void addCard(String cardLetter){
        if (this.cards.equals("") || this.cards.equals("//")){
            this.cards = cardLetter;
        }
        else{
            this.cards = this.cards + "," + cardLetter;
        }
    }

    public void resetCards(){
        this.cards = "//";
    }

    public void setBusted(Boolean value){
        this.busted = value;
    }

    public void setWinner(Boolean value){
        this.winner = value;
    }

    public void setHeld(Boolean value){
        this.held = value;
    }

    public void setFinished(Boolean value){
        this.finished = value;
    }

    public Boolean inFinishState(){
        if (this.busted || this.winner || this.held){
            //System.out.println(this.name + " is in a finish stated");
            return true;
        }
        else{
            //System.out.println(this.name + " is NOT in a finish stated");
            return false;
        }
    }
}