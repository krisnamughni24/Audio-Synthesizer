package configuration;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EnvelopeConfiguration")
public class EnvelopeConfiguration
{
	private double delay = 0.0;
	private double attack = 0.0;
	private double decay = 0.0;
	private double hold = 0.0;
	private double release = 0.0;

	EnvelopeConfiguration()
	{
		// non parameter constructor needed for xml marshalling
	}

	public EnvelopeConfiguration(double delay, double attack, double decay, double hold,
			double release)
	{
		super();
		this.delay = delay;
		this.attack = attack;
		this.decay = decay;
		this.hold = hold;
		this.release = release;
	}

	public double getDelay()
	{
		return delay;
	}

	public void setDelay(double delay)
	{
		this.delay = delay;
	}

	public double getAttack()
	{
		return attack;
	}

	public void setAttack(double attack)
	{
		this.attack = attack;
	}

	public double getDecay()
	{
		return decay;
	}

	public void setDecay(double decay)
	{
		this.decay = decay;
	}

	public double getHold()
	{
		return hold;
	}

	public void setHold(double hold)
	{
		this.hold = hold;
	}

	public double getRelease()
	{
		return release;
	}

	public void setRelease(double release)
	{
		this.release = release;
	}
}
