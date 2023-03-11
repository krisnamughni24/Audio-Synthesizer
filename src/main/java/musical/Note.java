package musical;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import utils.DefaultConstants;

@XmlRootElement
public class Note
{
	@XmlElement
	private NoteName noteName;
	@XmlElement
	private int octave;

	Note()
	{
		// no-arg contructor required for xml marshalling
	}

	public Note(String note)
	{
		this(note, NoteParser.getOctave(note));
	}

	public Note(String note, int octave)
	{
		this(NoteParser.getNoteName(note), octave);
	}

	public Note(NoteName noteName, int octave)
	{
		this.noteName = noteName;
		this.octave = octave;
	}

	public static List<Note> getNoteList()
	{
		List<Note> notesList = new ArrayList<>();

		for (NoteName noteName : NoteName.values())
		{
			notesList.add(new Note(noteName, DefaultConstants.DEFAULT_OCTAVE_NUMBER));
		}

		return notesList;
	}

	public double getFrequency()
	{
		return FrequencyParser.getFrequency(noteName, octave);
	}

	public NoteName getNoteName()
	{
		return noteName;
	}

	@Override
	public String toString()
	{
		return noteName.getName() + getOctave();
	}

	public boolean isSharpended()
	{
		return noteName.isSharp();
	}

	public int getOctave()
	{
		return octave;
	}
}
