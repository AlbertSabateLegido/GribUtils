package exceptions;

public class WrongLatitudeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double latitude;
	
	public WrongLatitudeException(double latitude) {
		this.latitude = latitude;
	}
	
	@Override
	public String toString() {
		return "Wrong latitude: " + latitude;
	}

}
