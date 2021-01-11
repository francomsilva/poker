import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    public final List<Card> hand;
    public final ArrayList<Integer> handRanks;
    public final ArrayList<Integer> handSuits;
    private boolean checking;
    public String handType = "";

    private int chips = 25;

    public Player() {
        this.hand = new ArrayList<>();
        this.handRanks = new ArrayList<>();
        this.handSuits = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void takeCard(Card card) {
        hand.add(card);
        handRanks.add(Card.getOrderedRank(card.getRank()));
        handSuits.add(Card.getOrderedSuit(card.getSuit()));
        sortHand();
    }

    public void clearHand() {
        hand.clear();
    }

    public void clearInfo() {
        handRanks.clear();
        handSuits.clear();
    }

    public boolean checkRoyalFlush() {
        if (checkStraightFlush() && Card.getOrderedRank(hand.get(4).getRank()) == 14) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkStraightFlush() {
        if (checkStraight() && checkFlush()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkFourOfAKind() {

        if ((handRanks.get(0) == handRanks.get(1) && handRanks.get(0) == handRanks.get(2) && handRanks.get(0) == handRanks.get(3)) || ((handRanks.get(1) == handRanks.get(2)) && (handRanks.get(1) == handRanks.get(3)) && (handRanks.get(1) == handRanks.get(4)))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkFullHouse() {
        if (checkTwoPair() && checkThreeOfAKind()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkFlush() {
        if (handSuits.get(0) == handSuits.get(1) && handSuits.get(0) == handSuits.get(2) && handSuits.get(0) == handSuits.get(3) && handSuits.get(0) == handSuits.get(4)) {
            return true;
        }
        return false;
    }

    public boolean checkStraight() {
        checking = true;
        for (int j = 0; j < hand.size()-1; j++) {
            if (handRanks.get(j) != handRanks.get(j+1) - 1) {
                checking = false;
            }
        }
        return checking;
    }

    public boolean checkThreeOfAKind() {
        if (
                (handRanks.get(0) == handRanks.get(1) && handRanks.get(0) == handRanks.get(2)) ||
                        (handRanks.get(1) == handRanks.get(2) && handRanks.get(1) == handRanks.get(3)) ||
                        (handRanks.get(2) == handRanks.get(3) && handRanks.get(2) == handRanks.get(4))
        ) {
            return true;
        }
        return false;
    }

    public boolean checkTwoPair() {
        if (
                ((handRanks.get(0) == handRanks.get(1)) && (handRanks.get(2) == handRanks.get(3)) ||
                        (handRanks.get(0) == handRanks.get(1)) && (handRanks.get(3) == handRanks.get(4)) ||
                        (handRanks.get(1) == handRanks.get(2)) && (handRanks.get(3) == handRanks.get(4)))
        ) {
            return true;
        }
        return false;
    }

    public boolean checkHighPair() {
        for (int k = 0; k < hand.size()-1; k++) {
            if (handRanks.get(k) == handRanks.get(k+1) && handRanks.get(k) >= 11) {
                return true;
            }
        }
        return false;
    }

    public void sortHand() {
        hand.sort((a, b) -> {
            if (Card.getOrderedRank(a.getRank()) == Card.getOrderedRank(b.getRank())) {
                return Card.getOrderedSuit(a.getSuit()) - Card.getOrderedSuit(b.getSuit());     // order by suit if
            }                                                                                   // ranks are the same
            return Card.getOrderedRank(a.getRank()) - Card.getOrderedRank(b.getRank());         // otherwise, by rank
        });
        Collections.sort(handRanks);
        Collections.sort(handSuits);
    }

}