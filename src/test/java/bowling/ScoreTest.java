package bowling;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import bowling.pin.Pins;
import bowling.pitching.PitchingState;
import bowling.pitching.status.PitchingResult;
import bowling.score.NormalScore;
import bowling.score.Scores;

public class ScoreTest {

	@Test
	public void scoreOfNormalFrameTest() {
		Scores scores = Scores.newInstance();
		NormalScore score1_1 = NormalScore.of(PitchingResult.of(Pins.of(8), PitchingState.ON_GOING), 1);
		NormalScore score1_2 = NormalScore.of(PitchingResult.of(Pins.of(2), PitchingState.SPARE), 1);
		NormalScore score2_1 = NormalScore.of(PitchingResult.of(Pins.of(8), PitchingState.ON_GOING), 2);
		NormalScore score2_2 = NormalScore.of(PitchingResult.of(Pins.of(1), PitchingState.MISS), 2);
		scores.calculateScore(score1_1);
		scores.calculateScore(score1_2);
		scores.calculateScore(score2_1);
		scores.calculateScore(score2_2);

		assertThat(scores.getScores().get(0).getScore()).isEqualTo(18);
		assertThat(scores.getScores().get(1).getScore()).isEqualTo(27);
	}
}
