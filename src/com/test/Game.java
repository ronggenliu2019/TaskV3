package com.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Game {
    private final Desk desk = new Desk();
    private final int PLAYER_AMOUNT = 3;
    private int roundNum = 0;
    private int[] ranks = new int[PLAYER_AMOUNT];
    private boolean gameOver = false;

    ExecutorService executor = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        Game game = new Game();
        Future<Card[]> round;
        do {
            round = game.round();
        } while (!game.gameOver(round));
    }

    public Future<Card[]> round() {
        Future<Card[]> future = executor.submit(() -> {
            desk.shuffle();
            Card[] roundCards = new Card[PLAYER_AMOUNT];
            for (int i = 0; i < PLAYER_AMOUNT; i++) {
                roundCards[i] = desk.deal();
            }
            return roundCards;
        });
        return future;
    }

    private boolean gameOver(Future<Card[]> future) {
        try {
            Card[] cards = future.get();
            roundNum++;
            int winNum = 0;
            for (int i = 0; i < PLAYER_AMOUNT; i++) {
                ranks[i] = ranks[i] + cards[i].getRank();

                if (ranks[i] > 50 && winNum == 0) {
                    winNum = i + 1;
                    gameOver = true;
                }
            }
            System.out.println("Round" + roundNum + " = Sender[\"" + cards[0].getRank()
                    + ",\"" + cards[1].getRankName() + "\",\"" + cards[2].getRankName() + "\"] -> Player1=" +
                    ranks[0] + ", Player2=" + ranks[1] +
                    ", Player3=" + ranks[2] + ";");
            if(gameOver) {
                System.out.println("Player " + winNum + " Win!");
                executor.shutdownNow();
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }


}
