package gui.panels;

import javax.swing.event.ChangeListener;

import configuration.FilterConfiguration;
import gui.VariableSlider;

public class FilterConfigurationPanel extends ConfigurationPanel
{
	private static final int ROW_COUNT = 4;
	private static final int COLUMN_COUNT = 1;

	private static final long serialVersionUID = 1L;

	private VariableSlider frequencySlider;
	private VariableSlider resonanceSlider;
	private VariableSlider amplitudeSlider;

	private FilterConfiguration filterConfig;

	public FilterConfigurationPanel(FilterConfiguration filterConfiguration)
	{
		super(ROW_COUNT, COLUMN_COUNT);
		this.filterConfig = filterConfiguration;
		frequencySlider = getNewSlider("Frequency", 0, 3000, filterConfig.getFrequency());
		resonanceSlider = getNewSlider("Resonance", 1.0, 3.0, filterConfig.getResonance());
		amplitudeSlider = getNewSlider("Filter Amplitude", 0.01, 1.01, filterConfig.getAmplitude());

		super.add(frequencySlider);
		super.add(resonanceSlider);
		super.add(amplitudeSlider);

		ChangeListener changeListener = e -> applyConfiguration();
		frequencySlider.addChangeListener(changeListener);
		resonanceSlider.addChangeListener(changeListener);
		amplitudeSlider.addChangeListener(changeListener);
	}

	protected void applyConfiguration()
	{
		updateConfiguration();
		triggerChangeListeners();
	}

	protected void updateConfiguration()
	{
		filterConfig.setAmplitude(amplitudeSlider.getValue());
		filterConfig.setFrequency(frequencySlider.getValue());
		filterConfig.setResonance(resonanceSlider.getValue());
	}

	public FilterConfiguration getConfig()
	{
		return filterConfig;
	}
}
