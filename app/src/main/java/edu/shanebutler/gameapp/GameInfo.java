package edu.shanebutler.gameapp;

import java.util.ArrayList;
import java.util.Random;

public class GameInfo
{
    public static ArrayList<Integer> sequence = new ArrayList<>();

    public static int roundNumber;
    public static int startingSequenceAmount;
    public static int currentSequenceAmount;
    private static int sequenceIncreaseAmount = 2;

    public static void AddRandomNumbersToSequence(int amountToAdd)
    {
        Random rng = new Random();

        for(int i = 0; i < amountToAdd; i++)
        {
            sequence.add(rng.nextInt(startingSequenceAmount));
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
        currentSequenceAmount += sequenceIncreaseAmount;
    }
}
