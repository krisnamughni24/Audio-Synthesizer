package musical;

import exceptions.InvalidNoteStringException;
import utils.DefaultConstants;

public class NoteParser
{
	private NoteParser()
	{
		// Hide public constructor
	}

	private static final String NOTE_SCHEME_REGEX = "[a-gA-G]#?[0-8]?";
	private static final int EXCLUSIVE_OCTAVE_MAXIMUM = 9;

	public static NoteName getNoteName(String noteString)
	{
		checkNoteString(noteString);

		char noteBaseName = noteString.charAt(0);
		boolean noteStringIsSharp = noteString.contains("#");

		if ("E".equalsIgnoreCase(noteBaseName + "") && noteStringIsSharp)
		{
			noteBaseName = 'F';
			noteStringIsSharp = false;
		}

		if ("B".equalsIgnoreCase(noteBaseName + "") && noteStringIsSharp)
		{
			noteBaseName = 'C';
			noteStringIsSharp = false;
		}

		for (NoteName noteName : NoteName.values())
		{
			boolean noteBaseMatches = noteName.getName().contains(noteBaseName + "");
			boolean sharpMatches = noteName.isSharp() == noteStringIsSharp;
			if (noteBaseMatches && sharpMatches)
			{
				return noteName;
			}
		}

		String messageToFormat = "Could not find matching NoteName for noteString '%s'!";
		String formattedMessage = String.format(messageToFormat, noteString);
		throw new InvalidNoteStringException(formattedMessage);
	}

	public static int getOctave(String noteString)
	{
		checkNoteString(noteString);

		if (noteString.matches(".*[0-8]"))
		{
			int indexOfOctaveNumber = noteString.length() - 1;
			char octaveNumber = noteString.charAt(indexOfOctaveNumber);

			return Character.digit(octaveNumber, EXCLUSIVE_OCTAVE_MAXIMUM);
		}

		return DefaultConstants.DEFAULT_OCTAVE_NUMBER;
	}

	private static void checkNoteString(String noteString)
	{
		if (noteString == null)
		{
			throw new NullPointerException("Provided note string was null!");
		}

		if (!noteString.matches(NOTE_SCHEME_REGEX))
		{
			throw new InvalidNoteStringException("Provided note string did not match scheme");
		}
	}
}
