package edu.shanebutler.gameapp;

public class GameInfo
{
    public static int currentTotalSequence;
    private static int sequenceIncreaseAmount = 2;

    public static void IncreaseSequenceAmount()
    {
           currentTotalSequence += sequenceIncreaseAmount;
    }

}
