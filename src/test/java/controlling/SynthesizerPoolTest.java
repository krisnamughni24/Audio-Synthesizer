package controlling;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import exceptions.MaximumSynthesizerCountReachedException;
import synthesis.OscillatorType;
import synthesis.SynthesizerFactory;

public class SynthesizerPoolTest
{

	private OscillatorType defaultType = OscillatorType.SINUS;

	@Test
	public void testGetInstance()

	{
		SynthesizerPool instance = getInstance();

		Assert.assertEquals(instance, getInstance());
	}

	@Test
	public void testAcquireSynthesizer()
	{
		List<SynthesizerConnection> connections = new ArrayList<>();
		for (int i = 0; i < SynthesizerPool.MAXIMUM_SYNTHESIZER_COUNT; i++)
		{
			connections.add(acquireConnection());
		}

		try
		{
			acquireConnection();
			Assert.fail("Expected MaximumSynthesizerCountReached-Exception!");
		}
		catch (MaximumSynthesizerCountReachedException e)
		{
			// Test passed
		}
		finally
		{
			for (SynthesizerConnection connection : connections)
			{
				releaseConnection(connection);
			}
		}
	}

	@Test
	public void testReleaseSynthesizer()
	{
		SynthesizerConnection changedConnection = acquireConnection();
		changedConnection.setSynthesizer(SynthesizerFactory.build(OscillatorType.SAWTOOTH));
		Assert.assertTrue(releaseConnection(changedConnection));

		SynthesizerConnection acquiredConnection = acquireConnection();
		Assert.assertTrue(releaseConnection(acquiredConnection));

		Assert.assertFalse(releaseConnection(acquiredConnection));

		SynthesizerConnection mockedConnection = mock(SynthesizerConnection.class);
		Assert.assertFalse(releaseConnection(mockedConnection));

		Assert.assertFalse(releaseConnection(null));
	}

	@Test
	public void testSetOscillatorType()
	{
		SynthesizerConnection acquiredConnection = acquireConnection();
		for (OscillatorType type : OscillatorType.values())
		{
			getInstance().setOscillatorType(type);
			Assert.assertEquals(type, acquiredConnection.getOscillatorType());
		}
		releaseConnection(acquiredConnection);
	}

	private boolean releaseConnection(SynthesizerConnection mockedConnection)
	{
		return getInstance().releaseSynthesizer(mockedConnection);
	}

	private SynthesizerConnection acquireConnection()
	{
		return getInstance().acquireSynthesizer();
	}

	private SynthesizerPool getInstance()
	{
		return getInstance(defaultType);
	}

	private SynthesizerPool getInstance(OscillatorType type)
	{
		return SynthesizerPool.getInstance(type);
	}
}
