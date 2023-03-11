package musical;

import org.junit.Assert;
import org.junit.Test;

import exceptions.InvalidNoteStringException;

public class NoteParserTest
{

	@Test
	public void testAllNoteNames()
	{
		for (NoteName noteName : NoteName.values())
		{
			Assert.assertEquals(noteName, noteName(noteName.getName()));
		}
	}

	@Test
	public void testSpecialCasesInNoteNames()
	{
		Assert.assertEquals(NoteName.F, noteName("E#"));
		Assert.assertEquals(NoteName.F, noteName("e#"));
		Assert.assertEquals(NoteName.C, noteName("B#"));
		Assert.assertEquals(NoteName.C, noteName("b#"));
	}

	@Test
	public void testAllNoteIdentifiersWithOctaves()
	{
		for (int octave = 0; octave < 9; octave++)
		{
			for (NoteName noteName : NoteName.values())
			{
				Assert.assertEquals(noteName, noteName(noteName.getName() + octave));
			}
		}
	}

	@Test
	public void testAllOctaves()
	{
		for (int octave = 0; octave < 9; octave++)
		{
			testOctave(octave);
		}
	}

	@Test
	public void testInvalidNotes()
	{
		testInvalidNote("Z");
		testInvalidNote("z");

		testInvalidNote("CC#0");
		testInvalidNote("CC#");
		testInvalidNote("CC");
		testInvalidNote("cc#0");
		testInvalidNote("cc#");
		testInvalidNote("cc");

		testInvalidNote("cb0");
		testInvalidNote("Cb0");
		testInvalidNote("Cb");
		testInvalidNote("cb");

		testInvalidNote("C # ");
		testInvalidNote("C # 0");
		testInvalidNote("C 0");
		testInvalidNote(" C");
		testInvalidNote("c # ");
		testInvalidNote("c # 0");
		testInvalidNote("c 0");
		testInvalidNote(" c");

		testInvalidNote("C9");
		testInvalidNote("C#9");
		testInvalidNote("c#9");
		testInvalidNote("c9");

		testInvalidNote("");
	}

	private NoteName noteName(String noteString)
	{
		return NoteParser.getNoteName(noteString);
	}

	private void testOctave(int octave)
	{
		for (NoteName noteName : NoteName.values())
		{
			Assert.assertEquals(octave, octave(noteName.getName() + octave));
		}
	}

	private int octave(String noteString)
	{
		return NoteParser.getOctave(noteString);
	}

	private void testInvalidNote(String noteString)
	{
		testInvalidNoteWithNoteName(noteString);
		testInvalidNoteWithOctaves(noteString);
	}

	private void testInvalidNoteWithNoteName(String noteString)
	{
		try
		{
			NoteParser.getNoteName(noteString);
			Assert.fail("Expected InvalidNoteStringException");
		}
		catch (InvalidNoteStringException e)
		{
			// Test passed
		}
	}

	private void testInvalidNoteWithOctaves(String noteString)
	{
		try
		{
			NoteParser.getOctave(noteString);
			Assert.fail("Expected InvalidNoteStringException");
		}
		catch (InvalidNoteStringException e)
		{
			// Test passed
		}
	}
}
