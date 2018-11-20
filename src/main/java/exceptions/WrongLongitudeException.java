package exceptions;

public class WrongLongitudeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double longitude;
	
	public WrongLongitudeException(double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "Wrong longitude: " + longitude;
	}

}
