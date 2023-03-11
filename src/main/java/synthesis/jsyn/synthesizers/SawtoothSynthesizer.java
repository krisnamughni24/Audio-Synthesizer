package synthesis.jsyn.synthesizers;

import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.UnitOscillator;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;
import synthesis.OscillatorType;

public class SawtoothSynthesizer extends JSynSynthesizer
{
	private static final OscillatorType TYPE = OscillatorType.SAWTOOTH;

	public SawtoothSynthesizer(FilterConfiguration filterConfig, EnvelopeConfiguration envConfig)
	{
		super(filterConfig, envConfig);
	}

	@Override
	public OscillatorType getOscillatorType()
	{
		return TYPE;
	}

	@Override
	protected UnitOscillator getOscillator()
	{
		return new SawtoothOscillator();
	}

}
