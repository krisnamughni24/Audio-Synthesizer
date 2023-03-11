package configuration;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FilterConfiguration")
public class FilterConfiguration
{

	private double frequency = 0.0;
	private double resonance = 0.0;
	private double amplitude = 0.0;

	FilterConfiguration()
	{
		// non parameter constructor needed for xml marshalling
	}

	public FilterConfiguration(double frequency, double resonance, double amplitude)
	{
		this.setFrequency(frequency);
		this.setResonance(resonance);
		this.setAmplitude(amplitude);
	}

	public double getAmplitude()
	{
		return amplitude;
	}

	public void setAmplitude(double amplitude)
	{
		this.amplitude = amplitude;
	}

	public double getResonance()
	{
		return resonance;
	}

	public void setResonance(double resonance)
	{
		this.resonance = resonance;
	}

	public double getFrequency()
	{
		return frequency;
	}

	public void setFrequency(double frequency)
	{
		this.frequency = frequency;
	}
}
