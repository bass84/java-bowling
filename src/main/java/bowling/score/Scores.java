package bowling.score;

import java.util.ArrayList;
import java.util.List;

public class Scores {
	private List<Score> scores;

	private Scores() {
		this.scores = new ArrayList<>();
	}

	public static Scores newInstance() {
		return new Scores();
	}

	public Scores calculateScore(Score score) {
		add(score);
		return execute();
	}

	private Scores execute() {
		Scores finalScores = newInstance();
		for (int i = 0; i < scores.size(); i++) {
			this.addAdditionalNextScores(i);
			this.reflectPreviousScore(i);
			this.addIntoFinalScores(i, finalScores);
		}
		return finalScores;
	}

	private void add(Score score) {
		scores.add(score);
	}

	private void addAdditionalNextScores(int index) {
		if (!shouldAddAdditionalNextScores(index)) {
			return;
		}

		Score score = scores.get(index);
		int nextScoreIndex = index + 1;

		for (int i = nextScoreIndex; i < nextScoreIndex + score.getNextPitchingCountToReflect(); i++) {
			score.addAdditionalScore(scores.get(i));
		}
	}

	private boolean shouldAddAdditionalNextScores(int currentIndex) {
		Score currentScore = scores.get(currentIndex);
		int currentMaxIndex = scores.size() - 1;
		int advancedPitchCount = currentMaxIndex - currentIndex;

		return currentScore.isItTimeToReflectNextScores(advancedPitchCount);
	}

	private void reflectPreviousScore(int index) {
		if (index == 0) {
			return;
		}

		Score previousScore = scores.get(index - 1);
		Score currentScore = scores.get(index);
		currentScore.reflectPreviousScore(previousScore);
	}

	private void addIntoFinalScores(int index, Scores finalScores) {
		Score score = scores.get(index);
		if (score.isDoneCalculates()) {
			finalScores.add(score);
		}
	}
}
