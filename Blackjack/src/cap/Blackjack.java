package cap;

import java.util.ArrayList;
import java.util.List;

public class Blackjack {
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private Deck deck;
    private static int points = 100;
    private int bet = 0;

    public Blackjack() {
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        deck = new Deck();
    }

    public void startGame() {
        playerHand.add(deck.deal());
        playerHand.add(deck.deal());
        dealerHand.add(deck.deal());
        dealerHand.add(deck.deal());
    }

    public void playerHit() {
        playerHand.add(deck.deal());
    }

    public void dealerHit() {
        dealerHand.add(deck.deal());
    }

    public int calculatePlayerHandValue() {
        return calculateHandValue(playerHand);
    }

    public int calculateDealerHandValue() {
        return calculateHandValue(dealerHand);
    }

    private int calculateHandValue(List<Card> hand) {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            value += card.getValue();
            if ("Ace".equals(card.getRank())) {
                aceCount++;
            }
        }

        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }

        return value;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public List<Card> getDealerHand() {
        return dealerHand;
    }

    public static int getPoints() {
        return points;
    }

    public void setBet(int bet) {
        if (bet > 0 && bet <= points) {
            this.bet = bet;
            points -= bet;
        }
    }

    public void updatePoints(String result) {
        if ("Player wins!".equals(result)) {
            points += bet * 2;
        } else if ("It's a tie!".equals(result)) {
            points += bet;
        }
    }

    public boolean canPlay() {
        return points > 0;
    }

    public static boolean isGameOver(int playerValue, int dealerValue) {
        return playerValue > 21 || dealerValue > 21;
    }
}