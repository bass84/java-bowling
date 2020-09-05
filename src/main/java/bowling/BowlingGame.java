package bowling;

import bowling.pin.Pins;
import bowling.score.ScoringBoard;
import bowling.user.Player;

public class BowlingGame {

	private final ScoringBoard scoringBoard;

	private BowlingGame(ScoringBoard scoringBoard) {
		this.scoringBoard = scoringBoard;
	}

	public BowlingGame init(Player player) {
		return new BowlingGame(ScoringBoard.of(player));
	}

	public boolean isEndGame() {
		return scoringBoard.isEnd();
	}

	public void pitchBall(Pins pins) {
		scoringBoard.reflect(pins);
	}

}
