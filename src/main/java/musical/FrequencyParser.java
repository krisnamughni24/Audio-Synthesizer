package musical;

public class FrequencyParser
{
	private static final int LOWER_INDEX_LIMIT = 0;
	private static final int UPPER_INDEX_LIMIT = 108;
	private static final double EQUAL_12_TONE_TEMPERMENT = Math.pow(2, 1.0 / 12.0);
	private static final double PITCH_FREQUENCY_NOTE_A3 = 220.0;

	private FrequencyParser()
	{
		// static class -> no constructor needed
	}

	public static double getFrequency(NoteName noteName, int octave)
	{
		int semiTone = noteName.ordinal();
		return getFrequencyFromOctaveAndSemiTone(octave, semiTone);
	}

	public static double getFrequencyFromNoteIndex(int noteIndex)
	{
		checkIndexOfNote(noteIndex);

		int octave = noteIndex / 12;
		double semiTone = noteIndex % 12;

		return getFrequencyFromOctaveAndSemiTone(octave, (int) semiTone);
	}

	public static double getFrequencyFromOctaveAndSemiTone(int octave, int semiTone)
	{
		int indexOfNote = getIndexOfNote(octave, semiTone);
		checkIndexOfNote(indexOfNote);

		int halfStepsFromA = getHalfStepsFromA(indexOfNote);
		return getFrequency(halfStepsFromA);
	}

	private static double getFrequency(int halfStepsFromA)
	{
		double pitchFrequency = PITCH_FREQUENCY_NOTE_A3;
		double pitchRatio = Math.pow(EQUAL_12_TONE_TEMPERMENT, halfStepsFromA);

		return pitchRatio * pitchFrequency;
	}

	private static int getHalfStepsFromA(int indexOfNote)
	{
		int noteIndexPitchNoteA3 = getIndexOfNote(3, 9);

		return indexOfNote - noteIndexPitchNoteA3;
	}

	private static int getIndexOfNote(int octave, int semiTone)
	{
		if (octave < 0 || octave > 8)
		{
			throw new IndexOutOfBoundsException("Octave '" + octave + "' is out of range!");
		}

		int indexIncreaseFromOctave = octave * 12;
		return indexIncreaseFromOctave + semiTone;
	}

	private static void checkIndexOfNote(int noteIndex)
	{
		if (noteIndex < LOWER_INDEX_LIMIT || noteIndex > UPPER_INDEX_LIMIT)
		{
			throw new IndexOutOfBoundsException(noteIndex);
		}
	}
}
