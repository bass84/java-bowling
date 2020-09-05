package bowling.frame;

import java.util.List;

import bowling.exception.FrameException;
import bowling.pin.Pins;
import bowling.score.NormalScore;
import bowling.score.PitchingResults;
import bowling.score.Score;
import bowling.score.status.PitchingResult;

public class NormalFrame implements Frame {
	private static final int END_OF_NORMAL_FRAME_NO = 9;

	private final int frameNo;
	private PitchingResults pitchingResults;

	private NormalFrame(int frameNo) {
		if (frameNo > END_OF_NORMAL_FRAME_NO) {
			throw new FrameException(String.format("일반 프레임의 번호는 %d를 넘을 수 없습니다.", END_OF_NORMAL_FRAME_NO));
		}

		this.pitchingResults = PitchingResults.newInstance();
		this.frameNo = frameNo;
	}

	public static Frame of(int frameNo) {
		return new NormalFrame(frameNo);
	}

	@Override
	public Score reflect(Pins knockingDownPins) {
		PitchingResult pitchingResult = pitchingResults.reflectPitching(knockingDownPins);

		return NormalScore.of(pitchingResult, frameNo);
	}

	@Override
	public boolean finish() {
		return pitchingResults.canMoveNextFrame();
	}

	@Override
	public List<String> getKnockingDownPins() {
		return pitchingResults.getKnockingDownPins();
	}
}
