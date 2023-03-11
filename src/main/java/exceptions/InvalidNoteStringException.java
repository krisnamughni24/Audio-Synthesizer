package exceptions;

public class InvalidNoteStringException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public InvalidNoteStringException()
	{
		super();
	}

	public InvalidNoteStringException(String message)
	{
		super(message);
	}
}
