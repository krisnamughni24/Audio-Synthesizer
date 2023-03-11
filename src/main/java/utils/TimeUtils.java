package utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeUtils
{
	private static final Logger LOGGER = Logger.getLogger("TimeUtils");

	private TimeUtils()
	{
		// Hide public constructor
	}

	public static void waitForASpecifiedTime(long timeToWait)
	{
		try
		{
			Thread.sleep(timeToWait);
		}
		catch (InterruptedException e)
		{
			LOGGER.log(Level.WARNING, "Interrupted sleeping of thread!", e);
			Thread.currentThread().interrupt();
		}
	}
}
