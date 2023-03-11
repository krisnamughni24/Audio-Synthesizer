package musical;

import java.util.Arrays;
import java.util.List;

import controlling.Player;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import utils.TimeUtils;

@XmlRootElement
public class Melody implements Playable
{
	@XmlAttribute
	private long duration;
	@XmlElement
	private Note[] notes;

	Melody()
	{
		// no-arg contructor required for xml marshalling
	}

	public Melody(long durationInMillis, Note... notes)
	{
		this.duration = durationInMillis;
		this.notes = notes;
	}

	@Override
	public void play(Player player)
	{
		for (Note note : notes)
		{
			player.noteOn(note);
			TimeUtils.waitForASpecifiedTime(duration);
			player.noteOff(note);
		}
	}

	@Override
	public List<Note> getNotes()
	{
		return Arrays.asList(notes);
	}

}
