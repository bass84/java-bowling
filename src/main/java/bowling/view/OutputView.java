package bowling.view;

import static java.util.stream.Collectors.joining;

import java.util.stream.IntStream;

import org.apache.logging.log4j.util.Strings;

import bowling.frame.Frames;
import bowling.user.Player;

public class OutputView {

	private static final String ONE_PARAM_OUTPUT = "|  %s  ";

	public static void viewInit(Player player) {

		String line2 = String.format("| %s  |      |      |      |      |      |      |      |      |      |      |", player.getName());
		String line3 = "|      |      |      |      |      |      |      |      |      |      |      |";

		System.out.println(getFrameLine());
		System.out.println(line2);
		System.out.println(line3);
	}

	private static String getFrameLine() {
		String name = "| NAME ";
		String frameNos = IntStream.range(1, 11)
								   .mapToObj(i -> i < 10 ? String.format("0%d", i) : String.format("%d", i))
								   .map(frameNo -> makeLine(frameNo))
								   .collect(joining());
		return name + frameNos + "|";
	}

	private static String makeLine(String... params) {
		String delimiter = "";
		if (params.length == 1) {
			delimiter = ONE_PARAM_OUTPUT;
		}

		return String.format(delimiter, params);

	}

	public static void viewPitchingResult(Player player, Frames frames) {
		String name = makeLine(player.getName());
		String knockingDownPinsSigns = getKnockingDownPinsSignsString(frames);

		System.out.println(getFrameLine());
		System.out.println(name + knockingDownPinsSigns + "|");
		System.out.println("      " + frames.getScoresToDate()
											.stream()
											.map(score -> makeLine(score.isDoneCalculates() ? String.valueOf(score.getScore()) : " "))
											.collect(joining())
						  );

	}

	private static String getKnockingDownPinsSignsString(Frames frames) {
		return IntStream.range(0, 10)
						.mapToObj(i -> frames.getKnockingDownPinsSignsOf(i)
											 .stream()
											 .collect(joining("|")))

						.map(sign -> makeLine(sign.equals(Strings.EMPTY) ? " " : sign))
						.collect(joining());
	}
}
