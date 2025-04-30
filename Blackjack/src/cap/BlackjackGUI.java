package cap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BlackjackGUI extends JFrame 
{
    private Blackjack game;
    private JPanel dealerPanel, playerPanel, controlPanel;
    private JLabel dealerCountLabel, playerCountLabel;
    private JButton startButton, hitButton, stayButton;
    private int points = 10; 
    private int currentBet = 0; 
    private JLabel pointsLabel; 

    public BlackjackGUI() 
    {
        game = new Blackjack();
        setTitle("Blackjack");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2)); 

        dealerPanel = new JPanel(new BorderLayout());
        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer's Cards"));
        dealerCountLabel = new JLabel("Total: 0", SwingConstants.CENTER);
        dealerPanel.add(dealerCountLabel, BorderLayout.SOUTH);

        playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createTitledBorder("Your Cards"));
        playerCountLabel = new JLabel("Total: 0", SwingConstants.CENTER);
        pointsLabel = new JLabel("Points: " + points, SwingConstants.CENTER);
        JPanel playerBottomPanel = new JPanel(new GridLayout(2, 1));
        playerBottomPanel.add(playerCountLabel);
        playerBottomPanel.add(pointsLabel);
        playerPanel.add(playerBottomPanel, BorderLayout.SOUTH); 

        add(dealerPanel);
        add(playerPanel);

        controlPanel = new JPanel();
        startButton = new JButton("Start Game");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");

        controlPanel.add(startButton);
        controlPanel.add(hitButton);
        controlPanel.add(stayButton);
        add(controlPanel, BorderLayout.SOUTH); 

        hitButton.setEnabled(false); 
        stayButton.setEnabled(false);

        startButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                startGame();
            }
        });

        hitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                playerHits();
            }
        });

        stayButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                playerStays();
            }
        });
    }

    private void startGame() 
    {
        if (points <= 0) 
        {
            JOptionPane.showMessageDialog(this, "You have no points left!");
            return;
        }

        String input = JOptionPane.showInputDialog(this, "Enter your bet (1 to " + points + "):");
        try 
        {
            currentBet = Integer.parseInt(input);
            if (currentBet < 1 || currentBet > points) 
            {
                JOptionPane.showMessageDialog(this, "Invalid bet amount!");
                return;
            }
        } 
        catch (NumberFormatException ex) 
        {
            JOptionPane.showMessageDialog(this, "Invalid input!");
            return;
        }

        points -= currentBet; 
        updatePointsDisplay();

        game = new Blackjack();
        dealerPanel.removeAll();
        playerPanel.removeAll();

        game.getPlayer().addCard(game.getDeck().deal());
        game.getPlayer().addCard(game.getDeck().deal());
        game.getDealer().addCard(game.getDeck().deal());
        game.getDealer().addCard(game.getDeck().deal());

        updateDisplay();

        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        startButton.setEnabled(false); 
    }

    private void playerHits() 
    {
        game.getPlayer().addCard(game.getDeck().deal());
        updateDisplay();

        int playerValue = game.getPlayer().calculateHandValue();

        if (playerValue > 21) 
        {
            JOptionPane.showMessageDialog(this, "You busted! Dealer wins.");
            endGame(false);
        } 
        else if (playerValue == 21) 
        {
            JOptionPane.showMessageDialog(this, "You hit 21! Moving to the dealer's turn.");
            playerStays();
        }
    }

    private void playerStays() 
    {
        while (game.getDealer().calculateHandValue() < 17) 
        {
            game.getDealer().addCard(game.getDeck().deal());
        }
        updateDisplay();
        determineWinner();
    }

    private void determineWinner() 
    {
        int playerValue = game.getPlayer().calculateHandValue();
        int dealerValue = game.getDealer().calculateHandValue();

        if (dealerValue > 21 || playerValue > dealerValue) 
        {
            JOptionPane.showMessageDialog(this, "You win!");
            endGame(true);
        } 
        else 
        {
            JOptionPane.showMessageDialog(this, "Dealer wins!");
            endGame(false);
        }
    }

    private void endGame(boolean playerWon) 
    {
        if (playerWon) 
        {
            points += currentBet * 2;
        }
        updatePointsDisplay();

        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        startButton.setEnabled(true);
    }

    private void updateDisplay() 
    {
        dealerPanel.removeAll();
        JPanel dealerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        for (int i = 0; i < game.getDealer().getHand().size(); i++) 
        {
            JLabel cardLabel;
            if (i == 0) 
            {
                cardLabel = new JLabel("Hidden Card"); 
            } 
            else 
            {
                cardLabel = new JLabel(game.getDealer().getHand().get(i).toString());
            }
            dealerCardsPanel.add(cardLabel);
        }
        
        dealerPanel.add(dealerCardsPanel, BorderLayout.CENTER); 
        dealerCountLabel.setText("Total: Hidden"); 
        dealerPanel.add(dealerCountLabel, BorderLayout.SOUTH); 

        playerPanel.removeAll();
        JPanel playerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        for (Card card : game.getPlayer().getHand()) 
        {
            JLabel cardLabel = new JLabel(card.toString());
            playerCardsPanel.add(cardLabel);
        }
        
        playerPanel.add(playerCardsPanel, BorderLayout.CENTER);
        playerCountLabel.setText("Total: " + game.getPlayer().calculateHandValue());
        pointsLabel.setText("Points: " + points);
        playerPanel.add(playerCountLabel, BorderLayout.SOUTH); 
        playerPanel.add(pointsLabel, BorderLayout.NORTH); 

        dealerPanel.revalidate();
        dealerPanel.repaint();
        playerPanel.revalidate();
        playerPanel.repaint();
    }

    private void updatePointsDisplay() 
    {
        pointsLabel.setText("Points: " + points);
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            BlackjackGUI gui = new BlackjackGUI();
            gui.setVisible(true);
        });
    }
}