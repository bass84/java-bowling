package bowling.score;

import bowling.pin.Pins;

public class FinalScore implements Score {
	private static final int NEXT_PITCHING_COUNT_TO_REFLECT = 0;

	private int score;
	private final boolean lastPitching;

	private FinalScore(Pins knockingDownPins, boolean lastPitching) {
		this.score = knockingDownPins.getKnockingDownPins();
		this.lastPitching = lastPitching;
	}

	public static FinalScore of(Pins knockingDownPins, boolean lastPitching) {
		return new FinalScore(knockingDownPins, lastPitching);
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void addAdditionalScore(Score nextScore) {
		this.score += NEXT_PITCHING_COUNT_TO_REFLECT;
	}

	@Override
	public void reflectPreviousScore(Score previousScore) {
		this.score += previousScore.getScore();
	}

	@Override
	public boolean isDoneCalculates() {
		return lastPitching;
	}
}
