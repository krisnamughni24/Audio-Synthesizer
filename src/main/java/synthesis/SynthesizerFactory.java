package synthesis;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;
import exceptions.SynthesizerNotFoundException;
import synthesis.jsyn.synthesizers.SawtoothSynthesizer;
import synthesis.jsyn.synthesizers.SinusSynthesizer;
import synthesis.jsyn.synthesizers.SquareSynthesizer;
import synthesis.jsyn.synthesizers.TriangleSynthesizer;
import utils.DefaultConstants;

public class SynthesizerFactory
{
	private static Map<OscillatorType, SynthBuilder> synthPerOscillatorType;

	static
	{
		synthPerOscillatorType = new EnumMap<>(OscillatorType.class);
		synthPerOscillatorType.put(OscillatorType.SINUS, SinusSynthesizer::new);
		synthPerOscillatorType.put(OscillatorType.SQUARE, SquareSynthesizer::new);
		synthPerOscillatorType.put(OscillatorType.SAWTOOTH, SawtoothSynthesizer::new);
		synthPerOscillatorType.put(OscillatorType.TRIANGLE, TriangleSynthesizer::new);
	}

	private SynthesizerFactory()
	{
		// Hide the public constructor
	}

	public static ISynthesizer build(OscillatorType type)
	{
		FilterConfiguration filterConfig = DefaultConstants.getFilterConfig();
		EnvelopeConfiguration envConfig = DefaultConstants.getEnvelopeConfig();

		return build(type, filterConfig, envConfig);
	}

	public static ISynthesizer build(OscillatorType type, FilterConfiguration filterConfig,
			EnvelopeConfiguration envConfig)
	{
		Optional<SynthBuilder> optionalBuilder = getSynthesizerBuilder(type);
		if (optionalBuilder.isPresent())
		{
			SynthBuilder builder = optionalBuilder.get();
			return builder.build(filterConfig, envConfig);
		}

		String message = String.format("Could not find synthesizer for %s", String.valueOf(type));
		throw new SynthesizerNotFoundException(message);
	}

	private static Optional<SynthBuilder> getSynthesizerBuilder(OscillatorType type)
	{
		return Optional.ofNullable(synthPerOscillatorType.get(type));
	}
}
