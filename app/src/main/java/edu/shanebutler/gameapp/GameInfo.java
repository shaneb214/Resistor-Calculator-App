package edu.shanebutler.gameapp;

public class GameInfo
{
    public static int currentTotalSequence = 4;
    private static int sequenceIncreaseAmount = 2;

    public static void IncreaseSequenceAmount()
    {
           currentTotalSequence += sequenceIncreaseAmount;
    }
}
