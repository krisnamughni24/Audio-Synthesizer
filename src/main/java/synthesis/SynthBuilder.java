package synthesis;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;
import synthesis.jsyn.synthesizers.JSynSynthesizer;

public interface SynthBuilder
{
	public JSynSynthesizer build(FilterConfiguration filterConfig, EnvelopeConfiguration envConfig);
}
