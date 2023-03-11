package configuration;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Preset
{

	private String name = "Custom";
	private FilterConfiguration filterConfig;
	private EnvelopeConfiguration envConfig;

	Preset()
	{
		// no arg constructor needed for xml marshalling
	}

	public Preset(FilterConfiguration filterConfig, EnvelopeConfiguration envConfig)
	{
		this.filterConfig = filterConfig;
		this.envConfig = envConfig;
	}

	public Preset(String name, FilterConfiguration filterConfig, EnvelopeConfiguration envConfig)
	{
		this(filterConfig, envConfig);
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@XmlElement(name = "Filter")
	public FilterConfiguration getFilterConfig()
	{
		return filterConfig;
	}

	public void setFilterConfig(FilterConfiguration filterConfig)
	{
		this.filterConfig = filterConfig;
	}

	@XmlElement(name = "Envelope")
	public EnvelopeConfiguration getEnvConfig()
	{
		return envConfig;
	}

	public void setEnvConfig(EnvelopeConfiguration envConfig)
	{
		this.envConfig = envConfig;
	}

}
