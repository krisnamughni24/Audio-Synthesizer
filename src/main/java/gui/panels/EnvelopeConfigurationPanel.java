package gui.panels;

import javax.swing.event.ChangeListener;

import configuration.EnvelopeConfiguration;
import gui.VariableSlider;

public class EnvelopeConfigurationPanel extends ConfigurationPanel
{
	private static final int columnCount = 1;

	private static final int rowCount = 5;

	private static final long serialVersionUID = 1L;

	private EnvelopeConfiguration envConfig;

	private VariableSlider delaySlider;
	private VariableSlider attackSlider;
	private VariableSlider decaySlider;
	private VariableSlider holdSlider;
	private VariableSlider releaseSlider;

	public EnvelopeConfigurationPanel(EnvelopeConfiguration envConfig)
	{
		super(rowCount, columnCount);
		this.envConfig = envConfig;

		delaySlider = new VariableSlider("Delay", 0, 5, (int) envConfig.getDelay());
		attackSlider = new VariableSlider("Attack", 0, 5, (int) envConfig.getAttack());
		decaySlider = new VariableSlider("Decay", 0, 5, (int) envConfig.getDecay());
		holdSlider = new VariableSlider("Hold", 0, 5, (int) envConfig.getHold());
		releaseSlider = new VariableSlider("Release", 0, 5, (int) envConfig.getRelease());

		super.add(delaySlider);
		super.add(attackSlider);
		super.add(decaySlider);
		super.add(holdSlider);
		super.add(releaseSlider);

		ChangeListener listener = e -> applyConfiguration();
		delaySlider.addChangeListener(listener);
		attackSlider.addChangeListener(listener);
		decaySlider.addChangeListener(listener);
		holdSlider.addChangeListener(listener);
		releaseSlider.addChangeListener(listener);
	}

	@Override
	public EnvelopeConfiguration getConfig()
	{
		return envConfig;
	}

	@Override
	protected void applyConfiguration()
	{
		updateConfiguration();
		triggerChangeListeners();
	}

	@Override
	protected void updateConfiguration()
	{
		envConfig.setDelay(delaySlider.getValue());
		envConfig.setAttack(attackSlider.getValue());
		envConfig.setDecay(decaySlider.getValue());
		envConfig.setHold(holdSlider.getValue());
		envConfig.setRelease(releaseSlider.getValue());
	}
}
