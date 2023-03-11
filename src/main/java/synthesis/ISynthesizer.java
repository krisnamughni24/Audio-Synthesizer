package synthesis;

import java.util.Optional;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;
import musical.Note;

public interface ISynthesizer
{
	public void noteOn(Note note);

	public void noteOff();

	public Optional<Note> isPlaying();

	public OscillatorType getOscillatorType();

	public void applyEnvelopeConfiguration(EnvelopeConfiguration envConfig);

	public void applyFilterConfiguration(FilterConfiguration filterConfig);
}
