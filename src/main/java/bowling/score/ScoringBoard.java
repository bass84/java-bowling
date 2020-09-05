package bowling.score;

import bowling.frame.Frames;
import bowling.pin.Pins;
import bowling.user.Player;

public class ScoringBoard {

	private final Player player;
	private final Frames frames;

	private ScoringBoard(Player player) {
		this.player = player;
		this.frames = Frames.newInstance();
	}

	public static ScoringBoard of(Player player) {
		return new ScoringBoard(player);
	}

	public boolean isEnd() {
		return frames.isEnd();
	}

	public void reflect(Pins pins) {
		frames.reflect(pins);
	}
}
