package exceptions;

public class MaximumSynthesizerCountReachedException extends RuntimeException
{
	private static final long serialVersionUID = -1861463443453581997L;

	public MaximumSynthesizerCountReachedException()
	{
		super();
	}

	public MaximumSynthesizerCountReachedException(String message)
	{
		super(message);
	}
}
