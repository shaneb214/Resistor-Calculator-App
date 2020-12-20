package edu.shanebutler.gameapp;

import java.util.ArrayList;
import java.util.Random;

public class GameInfo
{
    public static ArrayList<Integer> sequence = new ArrayList<>();

    public static boolean PlayerLost = true;
    public static int roundNumber = 1;
    public static int startingSequenceAmount;
    public static int currentSequenceAmount;
    private static int sequenceIncreaseAmount = 2;
    public static int playerScore = 0;
    private static int scoreIncreasePerRound = 2;

    public static void AddRandomNumbersToSequence(int amountToAdd)
    {
        Random rng = new Random();

        for(int i = 0; i < amountToAdd; i++)
        {
            sequence.add(rng.nextInt(currentSequenceAmount));
        }
    }

    public static void ResetSequence()
    {
        sequence.clear();
        AddRandomNumbersToSequence(startingSequenceAmount);
    }

    public static void GoToNextRound()
    {
        roundNumber++;
        playerScore += scoreIncreasePerRound;

        currentSequenceAmount += sequenceIncreaseAmount;
        AddRandomNumbersToSequence(sequenceIncreaseAmount);
    }

    public static void Reset()
    {
        sequence.clear();
        roundNumber = 1;
        playerScore = 0;
    }
}
