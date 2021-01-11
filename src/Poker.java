import java.util.*;

public class Poker {

    private final String[] SUITS = { "C", "D", "H", "S" };
    private final String[] RANKS = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K" };

    private final Player player;
    private List<Card> deck;
    private final Scanner in;
    private int chips = 25;
    private int wager;
    private int cardsChosen;
    private int remove = -1;

    public Poker() {
        this.player = new Player();
        this.in = new Scanner(System.in);
    }

    public void play() {
        wager();
        clearHand();
        clearInfo();
        shuffleAndDeal();
        showHand();
        takeTurn();
        endRound();
    }

    private void wager() {
        do {
            System.out.println("Total chips you have right now: " + chips);
            System.out.println("How many chips would you like to wager (Min: 1, Max: 25)?");
            wager = in.nextInt();
            if (wager > chips) {
                System.out.println("Invalid number of chips. Please wager an amount between 1 to 25.");
            }
            if (wager <= 0) {
                System.out.println("Invalid number of chips. Please wager an amount between 1 to 25.");
            }
        } while (wager < 1 || wager > 25 || wager > chips);
        in.nextLine();
    }

    public void shuffleAndDeal() {
        if (deck == null) {
            initializeDeck();
        }
        Collections.shuffle(deck);  // shuffles the deck
        while (player.getHand().size() < 5) {
            player.takeCard(deck.remove(0));    // deal 2 cards to the player
        }
    }

    ////////// PRIVATE METHODS /////////////////////////////////////////////////////
    private void initializeDeck() {
        deck = new ArrayList<>(52);

        for (String suit : SUITS) {
            for (String rank : RANKS) {
                deck.add(new Card(rank, suit));     // adds 52 cards to the deck (13 ranks, 4 suits)
            }
        }
    }

    private void takeTurn() {
        cardsChosen = -1;
        do {
            System.out.println("How many cards would you like to switch (Min: 0, Max: 3)?");
            cardsChosen = in.nextInt();
        } while (cardsChosen <= -1 || cardsChosen > 3);
        for (int k = 0; k < cardsChosen; k++) {
            do {
                System.out.println("Choose the index of the card you would like to remove (First card equals an index of 0).");
                remove = in.nextInt();
            } while (remove <= -1 || remove > player.hand.size());
            player.hand.remove(remove);
            player.handRanks.remove(remove);
            player.handSuits.remove(remove);
            showHand();
        }
        shuffleAndDeal();
        showHand();

    }

    private void endRound() {
        showHand();
        player.sortHand();

        if (player.checkRoyalFlush()) {
            chips += wager * 100;
            System.out.println("Congratulations, you won with a Royal Flush!");
        } else if (player.checkStraightFlush()) {
            chips += wager * 50;
            System.out.println("Congratulations, you won with a Straight Flush!");
        } else if (player.checkFourOfAKind()) {
            chips += wager * 25;
            System.out.println("Congratulations, you won with a Four of a Kind!");
        } else if (player.checkFullHouse()) {
            chips += wager * 15;
            System.out.println("Congratulations, you won with a Full House!");
        } else if (player.checkFlush()) {
            chips += wager * 10;
            System.out.println("Congratulations, you won with a Flush!");
        } else if (player.checkStraight()) {
            chips += wager * 5;
            System.out.println("Congratulations, you won with a Straight!");
        } else if (player.checkThreeOfAKind()) {
            chips += wager * 3;
            System.out.println("Congratulations, you won with a Three of a Kind!");
        } else if (player.checkTwoPair()) {
            chips += wager * 2;
            System.out.println("Congratulations, you won with a Two Pair!");
        } else if (player.checkHighPair()) {
            chips += wager * 1;
            System.out.println("Congratulations, you won with a Pair!");
        } else {
                chips -= wager;
                System.out.println("Unfortunately, you lost this round. Better luck next time.");
        }

        if (chips <= 0) {
            endGame();
        }
        in.nextLine();
        String answer = "";
        do {
            System.out.println("Would you like to end the game here or continue playing? Type E to end the game, or C to continue.");
            answer = in.nextLine().toUpperCase();
        } while (!answer.equals("E") && !answer.equals("C"));
        if (chips <= 0) {
            endGame();
        } else if (answer.equals("E")) {
            endGame();
        } else if (answer.equals("C")) {
            play();
        }

    }

    private void showHand() {
        System.out.println("\nPLAYER hand: " + player.getHand()); //shows player's full hand
    }

    private void clearHand() {
        player.clearHand();
    }

    private void clearInfo() {
        player.clearInfo();
    }

    private void endGame() {
        if (chips <= 0) {
            System.out.println("You lost ... you ran out of chips :(.");
        } else {
            System.out.println("Thanks for playing! You ended up with " + chips + " chips. Good job!");
        }
        System.exit(0);
    }

    ////////// MAIN METHOD /////////////////////////////////////////////////////////

    public static void main(String[] args) {
        System.out.println("##########################################################");
        System.out.println("#                                                        #");
        System.out.println("#  ######   ######    #   #  #####  ######               #");
        System.out.println("#  #     # #      #   #  #   #      #     #              #");
        System.out.println("#  ######  #      #   # #    #####  ######               #");
        System.out.println("#  #       #      #   #  #   #      #   #                #");
        System.out.println("#  #        ######    #   #  #####  #    #               #");
        System.out.println("#                                                        #");
        System.out.println("#  A human rendition of the classic card game            #");
        System.out.println("#  Poker.                                                #");
        System.out.println("#                                                        #");
        System.out.println("##########################################################");

        String option;
        Scanner init = new Scanner(System.in);
        while(true){
            System.out.print("Enter \"S\" to start a game or \"Q\" to quit this menu: ");
            option = init.nextLine().toUpperCase();
            if(option.equals("S")){
                new Poker().play();
                break;
            } else if (option.equals("Q")){
                System.exit(0);
                break;
            }
        }
    }
}