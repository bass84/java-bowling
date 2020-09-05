package bowling.score;

import bowling.exception.BowlingScoreException;
import bowling.score.status.PitchingResult;

public class NormalScore implements Score {

	private int score;
	private int nextPitchingCountToReflect;
	private PitchingResult pitchingResult;

	private NormalScore(PitchingResult pitchingResult, int frameNo) {
		this.score = pitchingResult.getKnockingDownPins();
		this.nextPitchingCountToReflect = pitchingResult.getPitchingState().getNextPitchingCountToReflect(frameNo);
		this.pitchingResult = pitchingResult;
	}

	public static NormalScore of(PitchingResult pitchingResult, int frameNo) {
		return new NormalScore(pitchingResult, frameNo);
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getNextPitchingCountToReflect() {
		return nextPitchingCountToReflect;
	}

	@Override
	public void addAdditionalScore(Score nextScore) {
		minusNextPitchingCountToReflect();
		this.score += nextScore.getScore();
	}

	@Override
	public void reflectPreviousScore(Score previousScore) {
		if (previousScore.getNextPitchingCountToReflect() > 0) {
			throw new BowlingScoreException("아직 이전 프레임의 점수 계산이 끝나지 않았습니다.");
		}
		this.score += previousScore.getScore();
	}

	private void minusNextPitchingCountToReflect() {
		if (nextPitchingCountToReflect <= 0) {
			throw new BowlingScoreException("더 이상 점수를 반영할 수 없습니다.");
		}
		nextPitchingCountToReflect -= 1;
	}

	@Override
	public boolean isItTimeToReflectNextScores(int advancedPitchingCount) {
		return nextPitchingCountToReflect > 0 &&
			   advancedPitchingCount > 0 &&
			   nextPitchingCountToReflect == advancedPitchingCount;
	}

	@Override
	public boolean isDoneCalculates() {
		return pitchingResult.canMoveNextFrame() && nextPitchingCountToReflect == 0;
	}
}
