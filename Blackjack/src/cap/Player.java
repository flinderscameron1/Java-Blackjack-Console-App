package cap;

import java.util.*;

public class Player 
{
    private List<Card> hand;
    private int points;

    public Player(int initialPoints) 
    {
        hand = new ArrayList<>();
        points = initialPoints;
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
            value -= 10;
            aceCount--;
        }

        return value;
    }

    public List<Card> getHand() 
    {
        return hand;
    }

    public int getPoints() 
    {
        return points;
    }

    public void betPoints(int bet) 
    {
        points -= bet;
    }

    public void addPoints(int reward) 
    {
        points += reward;
    }
}
