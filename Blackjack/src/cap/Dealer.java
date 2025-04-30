package cap;

import java.util.*;

public class Dealer 
{
    private List<Card> hand;

    public Dealer() 
    {
        hand = new ArrayList<>();
    }

    public void addCard(Card card) 
    {
        hand.add(card);
    }

    public int calculateHandValue() 
    {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) 
        {
            value += card.getValue();
            if (card.getRank().equals("Ace")) 
            {
                aceCount++;
            }
        }

        while (value > 21 && aceCount > 0) 
        {
            value -= 10; // Count Ace as 1 instead of 11
            aceCount--;
        }

        return value;
    }

    public List<Card> getHand() 
    {
        return hand;
    }

    public boolean shouldHit(List<Card> remainingDeck, double threshold) 
    {
        double safeDrawProbability = calculateSafeDrawProbability(remainingDeck);
        return safeDrawProbability > threshold;
    }

    private double calculateSafeDrawProbability(List<Card> remainingDeck) 
    {
        int currentValue = calculateHandValue();
        int maxSafeValue = 21 - currentValue;

        if (maxSafeValue <= 0) 
        {
            return 0.0;
        }

        int safeCardCount = 0;
        for (Card card : remainingDeck) 
        {
            if (card.getValue() <= maxSafeValue) 
            {
                safeCardCount++;
            }
        }

        return (double) safeCardCount / remainingDeck.size();
    }
}