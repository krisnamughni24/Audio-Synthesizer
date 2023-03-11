package synthesis.jsyn.configuration;

import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.FilterStateVariable;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;

public class ConfigurationHelper
{
	private ConfigurationHelper()
	{
		// Hide public constructor
	}

	public static void applyForEnvelope(EnvelopeDAHDSR envelope, EnvelopeConfiguration envConfig)
	{
		envelope.attack.set(envConfig.getAttack());
		envelope.decay.set(envConfig.getDecay());
		envelope.delay.set(envConfig.getDelay());
		envelope.hold.set(envConfig.getHold());
		envelope.release.set(envConfig.getRelease());
	}

	public static void applyForFilter(FilterStateVariable filter, FilterConfiguration filterConfig)
	{
		filter.amplitude.set(filterConfig.getAmplitude());
		filter.resonance.set(filterConfig.getResonance());
		filter.frequency.set(filterConfig.getFrequency());
	}
}
