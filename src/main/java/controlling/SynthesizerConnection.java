package controlling;

import java.util.Optional;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;
import musical.Note;
import synthesis.ISynthesizer;
import synthesis.OscillatorType;

public class SynthesizerConnection
{
	private ISynthesizer synthesizer;

	public SynthesizerConnection(ISynthesizer synthesizer)
	{
		this.synthesizer = synthesizer;
	}

	public ISynthesizer getSynthesizer()
	{
		return this.synthesizer;
	}

	public void setSynthesizer(ISynthesizer synthesizer)
	{
		Optional<Note> optionalNote = this.synthesizer.isPlaying();
		if (optionalNote.isPresent())
		{
			this.synthesizer.noteOff();
			synthesizer.noteOn(optionalNote.get());
		}

		this.synthesizer = synthesizer;
	}

	public void noteOn(Note note)
	{
		synthesizer.noteOn(note);
	}

	public void noteOff()
	{
		synthesizer.noteOff();
	}

	public void applyFilterConfiguration(FilterConfiguration filterConfig)
	{
		synthesizer.applyFilterConfiguration(filterConfig);
	}

	public void applyEnvelopeConfiguration(EnvelopeConfiguration envConfig)
	{
		synthesizer.applyEnvelopeConfiguration(envConfig);
	}

	public OscillatorType getOscillatorType()
	{
		return synthesizer.getOscillatorType();
	}
}
