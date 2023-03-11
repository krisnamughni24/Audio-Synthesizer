package controlling;

import java.util.Calendar;
import java.util.Date;

import musical.Note;

public class PlayState
{
	private Note note;
	private boolean isPlaying;
	private Date startTime = new Date(0);

	public PlayState(Note note)
	{
		setPlayingOn(note);
	}

	public PlayState()
	{
		setPlayingOff();
	}

	public boolean isPlaying()
	{
		return isPlaying;
	}

	public void setPlayingOn(Note note)
	{
		this.isPlaying = true;
		this.note = note;
		this.startTime = Calendar.getInstance().getTime();
	}

	public void setPlayingOff()
	{
		this.isPlaying = false;
		this.note = null;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public Note getNote()
	{
		return note;
	}
}
