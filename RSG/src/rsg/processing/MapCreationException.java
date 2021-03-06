package rsg.processing;

public class MapCreationException extends Exception
{
	private static final long serialVersionUID = 1L;

	public MapCreationException()
	{
		super();
	}
	
	public MapCreationException(String message)
	{
		super(message);
	}
	
	public MapCreationException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public MapCreationException(Throwable cause)
	{
		super(cause);
	}
}