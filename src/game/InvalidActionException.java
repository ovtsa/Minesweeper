package game;

/** A custom exception to be thrown when the user types illegal input.
 * 
 * @author Nathan Jobe
 * @version %I%, %G%
 */
public class InvalidActionException extends Exception {
	public InvalidActionException(String errorMessage) {
		super(errorMessage);
	}
}
