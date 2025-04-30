package cap;

import java.util.*;

public class Deck 
{
    private List<Card> cards;

    public Deck() 
    {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (String suit : suits) 
        {
            for (int i = 0; i < ranks.length; i++) 
            {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
    }

    public void shuffle() 
    {
        Collections.shuffle(cards);
    }

    public Card deal() 
    {
        return cards.remove(cards.size() - 1);
    }

    public List<Card> getRemainingCards() 
    {
    	List<Card> remainingCards = new ArrayList<Card>(cards);
        return remainingCards;
    }
}