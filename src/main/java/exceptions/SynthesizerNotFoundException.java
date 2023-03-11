package exceptions;

public class SynthesizerNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = -411730930503257038L;

	public SynthesizerNotFoundException(String message)
	{
		super(message);
	}
}
