package synthesis.jsyn;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.UnitOscillator;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;
import synthesis.jsyn.configuration.ConfigurationHelper;

public class OscillatorCircuit extends Circuit implements UnitsCircuit
{
	private UnitOscillator oscillator;
	private EnvelopeDAHDSR envelope;
	private FilterStateVariable filter;

	public OscillatorCircuit(UnitOscillator oscillator, FilterConfiguration filterConfig,
			EnvelopeConfiguration envConfig)
	{
		initializeUnits(oscillator);
		addUnits();
		connectUnits();

		applyEnvelopeConfiguration(envConfig);
		applyFilterConfiguration(filterConfig);
	}

	@Override
	public UnitOutputPort getOutput()
	{
		return filter.getOutput();
	}

	@Override
	public void noteOn(double frequency)
	{
		oscillator.frequency.set(frequency);
		envelope.input.set(1.0);
	}

	@Override
	public void noteOff()
	{
		envelope.input.set(0.0);
	}

	@Override
	public void applyFilterConfiguration(FilterConfiguration filterConfig)
	{
		ConfigurationHelper.applyForFilter(filter, filterConfig);
	}

	@Override
	public void applyEnvelopeConfiguration(EnvelopeConfiguration envelopeConfig)
	{
		ConfigurationHelper.applyForEnvelope(envelope, envelopeConfig);
	}

	private void initializeUnits(UnitOscillator oscillator)
	{
		this.oscillator = oscillator;
		envelope = new EnvelopeDAHDSR();
		filter = new FilterStateVariable();
	}

	private void addUnits()
	{
		super.add(envelope);
		super.add(oscillator);
		super.add(filter);
	}

	private void connectUnits()
	{
		envelope.output.connect(oscillator.amplitude);
		oscillator.output.connect(filter.input);
	}
}
