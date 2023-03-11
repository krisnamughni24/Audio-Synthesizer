package synthesis.jsyn.synthesizers;

import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitOscillator;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;
import synthesis.OscillatorType;

public class TriangleSynthesizer extends JSynSynthesizer
{
	public TriangleSynthesizer(FilterConfiguration filterConfig, EnvelopeConfiguration envConfig)
	{
		super(filterConfig, envConfig);
	}

	@Override
	public OscillatorType getOscillatorType()
	{
		return OscillatorType.TRIANGLE;
	}

	@Override
	protected UnitOscillator getOscillator()
	{
		return new TriangleOscillator();
	}

}
