package synthesis;

import org.junit.Assert;
import org.junit.Test;

import exceptions.SynthesizerNotFoundException;

public class SynthesizerFactoryTest
{

	@Test
	public void test()
	{
		for (OscillatorType type : OscillatorType.values())
		{
			ISynthesizer synth = SynthesizerFactory.build(type);
			Assert.assertEquals(type, synth.getOscillatorType());
		}

		try
		{
			SynthesizerFactory.build(null);
			Assert.fail("Expected SynthesizerNotFound Exception!");
		}
		catch (SynthesizerNotFoundException e)
		{
			// Test passed
		}
	}

}
