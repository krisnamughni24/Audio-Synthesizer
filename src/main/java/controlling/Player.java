package controlling;

import java.util.HashMap;
import java.util.Map;

import musical.Note;
import musical.Playable;

public class Player
{
	private Map<Note, SynthesizerConnection> noteSynthPairs = new HashMap<>();
	private SynthesizerPool synthPool;

	public Player(SynthesizerPool synthPool)
	{
		this.synthPool = synthPool;
	}

	public void noteOn(Note note)
	{
		SynthesizerConnection synthConnection = synthPool.acquireSynthesizer();
		synthConnection.noteOn(note);
		noteSynthPairs.put(note, synthConnection);
	}

	public void noteOff(Note note)
	{
		if (!noteSynthPairs.containsKey(note))
		{
			return;
		}

		SynthesizerConnection synthConnection = noteSynthPairs.get(note);
		synthConnection.noteOff();

		noteSynthPairs.remove(note);
		synthPool.releaseSynthesizer(synthConnection);
	}

	public void play(Playable playable)
	{
		playable.play(this);
	}
}
