package synthesis.jsyn.synthesizers;

import java.util.Optional;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.UnitOscillator;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;
import musical.Note;
import synthesis.ISynthesizer;
import synthesis.jsyn.OscillatorCircuit;

public abstract class JSynSynthesizer implements ISynthesizer
{
	private OscillatorCircuit oscillatorCircuit;
	private Synthesizer synthesizer;
	private Optional<Note> currentlyPlaying = Optional.empty();

	protected JSynSynthesizer(FilterConfiguration filterConfig, EnvelopeConfiguration envConfig)
	{
		initializeSynthesizer(filterConfig, envConfig);
		addShutdownHook();
	}

	protected abstract UnitOscillator getOscillator();

	@Override
	public void noteOn(Note note)
	{
		oscillatorCircuit.noteOn(note.getFrequency());
		currentlyPlaying = Optional.of(note);
	}

	@Override
	public void noteOff()
	{
		oscillatorCircuit.noteOff();
		currentlyPlaying = Optional.empty();
	}

	@Override
	public Optional<Note> isPlaying()
	{
		return currentlyPlaying;
	}

	@Override
	public void applyEnvelopeConfiguration(EnvelopeConfiguration envConfig)
	{
		oscillatorCircuit.applyEnvelopeConfiguration(envConfig);
	}

	@Override
	public void applyFilterConfiguration(FilterConfiguration filterConfig)
	{
		oscillatorCircuit.applyFilterConfiguration(filterConfig);
	}

	private void initializeSynthesizer(FilterConfiguration filterConfig,
			EnvelopeConfiguration envConfig)
	{
		oscillatorCircuit = new OscillatorCircuit(getOscillator(), filterConfig, envConfig);
		synthesizer = createSynthesizer(oscillatorCircuit);
	}

	private void addShutdownHook()
	{
		Runtime.getRuntime().addShutdownHook(new Thread(synthesizer::stop));
	}

	private Synthesizer createSynthesizer(OscillatorCircuit unitCircuit)
	{
		Synthesizer newSynthesizer = JSyn.createSynthesizer();
		LineOut lineOut = new LineOut();

		newSynthesizer.setRealTime(true);
		newSynthesizer.add(unitCircuit);
		newSynthesizer.add(lineOut);

		unitCircuit.getOutput().connect(0, lineOut.input, 0);
		unitCircuit.getOutput().connect(0, lineOut.input, 1);

		newSynthesizer.start();
		newSynthesizer.startUnit(lineOut);

		return newSynthesizer;
	}
}
