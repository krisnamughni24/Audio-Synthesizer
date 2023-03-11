package controlling;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;
import configuration.Preset;
import synthesis.OscillatorType;
import utils.DefaultConstants;

public class Controller
{
	private SynthesizerPool synthPool;
	private Player player;

	public Controller()
	{
		this.synthPool = SynthesizerPool.getInstance(DefaultConstants.getOscillatorType());
		this.player = new Player(synthPool);
	}

	public void setOscillatorType(OscillatorType type)
	{
		synthPool.setOscillatorType(type);
	}

	public void applyFilterConfiguration(FilterConfiguration filterConfig)
	{
		synthPool.applyFilterConfiguration(filterConfig);
	}

	public void applyEnvelopeConfiguration(EnvelopeConfiguration envelopeConfig)
	{
		synthPool.applyEnvelopeConfiguration(envelopeConfig);
	}

	public void applyPreset(Preset preset)
	{
		synthPool.applyEnvelopeConfiguration(preset.getEnvConfig());
		synthPool.applyFilterConfiguration(preset.getFilterConfig());
	}

	public Player getPlayer()
	{
		return player;
	}
}
