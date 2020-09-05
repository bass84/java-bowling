package bowling.score;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.util.CollectionUtils;

import bowling.pin.Pins;
import bowling.pitching.PitchingState;
import bowling.score.status.PitchingResult;

public class PitchingResults {

	private List<PitchingResult> pitchingResults;

	private PitchingResults() {
		this.pitchingResults = new ArrayList<>();
	}

	public static PitchingResults newInstance() {
		return new PitchingResults();
	}

	public PitchingResult reflectPitching(Pins knockingDownPins) {
		PitchingState nextPitchingState = getNextPitchingStateUsingKnockingDownPins(knockingDownPins);
		PitchingResult pitchingResult = PitchingResult.of(knockingDownPins, nextPitchingState);
		pitchingResults.add(pitchingResult);

		return pitchingResult;
	}

	private PitchingState getNextPitchingStateUsingKnockingDownPins(Pins knockingDownPins) {
		List<Pins> allKnockingDownPinsToDate = getAllKnockingDownPinsToDate(knockingDownPins);
		return getLastPitchingState().reflect(allKnockingDownPinsToDate);
	}

	public List<Pins> getAllKnockingDownPinsToDate(Pins knockingDownPins) {
		List<Pins> allKnockingDownPins = getAllKnockingDownPins();
		allKnockingDownPins.add(knockingDownPins);

		return allKnockingDownPins;
	}

	private List<Pins> getAllKnockingDownPins() {
		if (CollectionUtils.isEmpty(pitchingResults)) {
			return new ArrayList<>();
		}
		return pitchingResults.stream()
							  .map(s -> Pins.of(s.getKnockingDownPins()))
							  .collect(toList());
	}

	private PitchingState getLastPitchingState() {
		if (CollectionUtils.isEmpty(pitchingResults)) {
			return PitchingState.READY;
		}
		return pitchingResults.get(pitchingResults.size() - 1)
							  .getPitchingState();
	}

	public List<String> getKnockingDownPins() {
		return IntStream.range(0, pitchingResults.size())
						.mapToObj(i -> {
							PitchingState pitchingState = pitchingResults.get(i).getPitchingState();
							return pitchingState.getKnockingDownPinsSign(getTotalKnockingDownPinsOfFrame(i + 1));
						})
						.collect(toList());
	}

	private Pins getTotalKnockingDownPinsOfFrame(int size) {
		if (CollectionUtils.isEmpty(pitchingResults)) {
			return Pins.of(0);
		}
		return Pins.of(IntStream.range(0, size)
								.map(i -> pitchingResults.get(i).getKnockingDownPins())
								.sum());
	}

	public boolean canMoveNextFrame() {
		return getLastPitchingState().canMoveNextFrame();
	}

	public int getPitchingCountToDate() {
		return pitchingResults.size();
	}
}
