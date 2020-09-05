package bowling.score.status;

import bowling.pin.Pins;
import bowling.pitching.PitchingState;


public class PitchingResult {
	private final Pins knockingDownPins;
	private final PitchingState pitchingState;

	private PitchingResult(Pins knockingDownPins, PitchingState pitchingState) {
		this.knockingDownPins = knockingDownPins;
		this.pitchingState = pitchingState;
	}

	public static PitchingResult of(Pins knockingDownPins, PitchingState pitchingState) {
		return new PitchingResult(knockingDownPins, pitchingState);
	}

	public int getKnockingDownPins() {
		return knockingDownPins.getKnockingDownPins();
	}

	public PitchingState getPitchingState() {
		return pitchingState;
	}

	public boolean canMoveNextFrame() {
		return pitchingState.canMoveNextFrame();
	}

	public boolean isStrikeOrSpare() {
		return pitchingState.isStrikeOrSpare();
	}


}
