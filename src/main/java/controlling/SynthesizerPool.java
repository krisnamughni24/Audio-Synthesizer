package controlling;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;
import exceptions.MaximumSynthesizerCountReachedException;
import synthesis.ISynthesizer;
import synthesis.OscillatorType;
import synthesis.SynthesizerFactory;

public class SynthesizerPool
{
	public static final int DEFAULT_SYNTHESIZER_COUNT = 5;
	public static final int MAXIMUM_SYNTHESIZER_COUNT = 15;

	private static SynthesizerPool instance;

	private List<SynthesizerConnection> freeConnections = new ArrayList<>();
	private List<SynthesizerConnection> acquiredConnections = new ArrayList<>();

	private OscillatorType oscillatorType;

	private SynthesizerPool(OscillatorType oscillatorType)
	{
		this.oscillatorType = oscillatorType;
		loadSynthesizers(DEFAULT_SYNTHESIZER_COUNT);
	}

	public static SynthesizerPool getInstance(OscillatorType oscillatorType)
	{
		if (instance == null)
		{
			instance = new SynthesizerPool(oscillatorType);
		}

		instance.setOscillatorType(oscillatorType);
		return instance;
	}

	public SynthesizerConnection acquireSynthesizer()
	{
		if (freeConnections.isEmpty())
		{
			enhanceSynthesizerPool();
			return acquireSynthesizer();
		}

		SynthesizerConnection synth = freeConnections.remove(0);
		acquiredConnections.add(synth);
		return synth;
	}

	public boolean releaseSynthesizer(SynthesizerConnection synthConnection)
	{
		if (!acquiredConnections.contains(synthConnection))
		{
			return false;
		}

		if (!oscillatorType.equals(synthConnection.getOscillatorType()))
		{
			return acquiredConnections.remove(synthConnection);
		}

		acquiredConnections.remove(synthConnection);
		return freeConnections.add(synthConnection);
	}

	public void setOscillatorType(OscillatorType type)
	{
		if (oscillatorType.equals(type))
		{
			return;
		}

		this.oscillatorType = type;
		applyForEachInList(conn -> conn.setSynthesizer(createSynthesizer()), freeConnections);
		applyForEachInList(conn -> conn.setSynthesizer(createSynthesizer()), acquiredConnections);
	}

	public void applyFilterConfiguration(FilterConfiguration filterConfig)
	{
		Consumer<SynthesizerConnection> updateFilter = conn -> conn
				.applyFilterConfiguration(filterConfig);

		applyForEachInList(updateFilter, freeConnections);
		applyForEachInList(updateFilter, acquiredConnections);
	}

	public void applyEnvelopeConfiguration(EnvelopeConfiguration envConfig)
	{
		Consumer<SynthesizerConnection> updateEnv = conn -> conn
				.applyEnvelopeConfiguration(envConfig);

		applyForEachInList(updateEnv, freeConnections);
		applyForEachInList(updateEnv, acquiredConnections);
	}

	private void enhanceSynthesizerPool()
	{
		if (acquiredConnections.size() < MAXIMUM_SYNTHESIZER_COUNT)
		{
			freeConnections.add(createSynthConnection());
			return;
		}
	
		throw new MaximumSynthesizerCountReachedException();
	}

	private void loadSynthesizers(int amount)
	{
		for (int i = 0; i < amount; i++)
		{
			enhanceSynthesizerPool();
		}
	}

	private SynthesizerConnection createSynthConnection()
	{
		return new SynthesizerConnection(createSynthesizer());
	}

	private ISynthesizer createSynthesizer()
	{
		return SynthesizerFactory.build(oscillatorType);
	}

	private void applyForEachInList(Consumer<SynthesizerConnection> action,
			List<SynthesizerConnection> list)
	{
		list.forEach(action);
	}
}
