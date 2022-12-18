package Exceptions;

public class EmptyNickNameException extends Exception
{
	public EmptyNickNameException() {
		super("Please don't enter empty nickname");

	}

}
