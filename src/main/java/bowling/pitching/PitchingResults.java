package bowling.pitching;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.util.CollectionUtils;

import bowling.pin.Pins;
import bowling.pitching.status.PitchingResult;

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

	private List<Pins> getAllKnockingDownPinsToDate(Pins knockingDownPins) {
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

	public List<String> getKnockingDownPinsSigns() {
		List<String> result = IntStream.range(0, pitchingResults.size())
									   .mapToObj(i -> getPitchingStateOf(i).getKnockingDownPinsSign(getKnockingDownPinsForSign(i + 1)))
									   .collect(toList());
		return result;
	}

	private Pins getKnockingDownPinsForSign(int size) {
		if (CollectionUtils.isEmpty(pitchingResults)) {
			return Pins.of(0);
		}
		int allKnockingDownPins = IntStream.range(0, size)
										   .map(i -> pitchingResults.get(i).getKnockingDownPins())
										   .sum();
		if (allKnockingDownPins < 10) {
			return Pins.of(pitchingResults.get(size - 1).getKnockingDownPins());
		}

		if (allKnockingDownPins > 10) {
			allKnockingDownPins = 10 % allKnockingDownPins;
		}
		return Pins.of(allKnockingDownPins);
	}

	private PitchingState getPitchingStateOf(int index) {
		return pitchingResults.get(index)
							  .getPitchingState();
	}

	public boolean isDonePitchingOfCurrentFrame() {
		return getLastPitchingState().canMoveNextFrame();
	}

	public int getPitchingCountToDate() {
		return pitchingResults.size();
	}
}
