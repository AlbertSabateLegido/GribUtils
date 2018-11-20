package albert.sabate.grib;
import java.util.ArrayList;
import java.util.List;

import exceptions.WrongLatitudeException;
import exceptions.WrongLongitudeException;

public class Position {
	
	//0 <= latitude <= 360
	private double latitude;
	//0 <= longitude <= 180 (But in the creator is expressed with values between -90 to 90)
	private double longitude;
	
	public Position(double latitude,double longitude) throws WrongLatitudeException, WrongLongitudeException {
		if(wrongLatitude(latitude))    throw new WrongLatitudeException(latitude);
		if(wrongLongitude(longitude))  throw new WrongLongitudeException(longitude);
		this.latitude  = latitude;
		this.longitude = Math.abs(longitude-90);
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public int getGridLatitude(double ratio) {
		return roundOff(latitude/ratio);
	}
	
	public void setLatitude(double latitude) throws WrongLatitudeException {
		if(wrongLatitude(latitude))  throw new WrongLatitudeException(latitude);
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public int getGridLongitude(double ratio) {
		return roundOff(longitude/ratio);
	}
	
	public void setLongitude(double longitude) throws WrongLongitudeException {
		if(wrongLongitude(longitude))  throw new WrongLongitudeException(longitude);
		this.longitude = Math.abs(longitude-90);
	}
	
	public int generateIndex(double latRatio, double lonRatio) {
		double x = getGridLatitude(latRatio);
		double y = getGridLongitude(lonRatio);
		return (int) (y*(360/latRatio) + x);
	}
	
	private boolean wrongLatitude(double latitude) {
		return (latitude < 0 || latitude > 360);
	}
	
	private boolean wrongLongitude(double longitude) {
		return (longitude < -90 || longitude > 90);
	}
	
	private int roundOff(double i) {
		if ((i - (int)i) > 0.5) return (int)i + 1;
		return (int)i;
	}
	
	public static List<Integer> getSubGrid(Position xPosition,Position yPosition,double latRatio,double lonRatio) throws WrongLongitudeException, WrongLatitudeException {
		List<Integer> subGrid = new ArrayList<Integer>();
		//Check latitude for recursive call
		if(xPosition.getLatitude() > yPosition.getLatitude()) {
			try {
				subGrid.addAll(getSubGrid(xPosition,new Position(360,getActualLongitude(yPosition.getLongitude())),latRatio,lonRatio));
				subGrid.addAll(getSubGrid(new Position(0,getActualLongitude(xPosition.getLongitude())),yPosition,latRatio,lonRatio));
			} catch (WrongLatitudeException e) {
				e.printStackTrace();
			} catch (WrongLongitudeException e) {
				e.printStackTrace();
			}
			return subGrid;
		}
		//Check longitude for recursive call
		if(xPosition.getLongitude() > yPosition.getLongitude()) {
			try {
				subGrid.addAll(getSubGrid(xPosition, new Position(yPosition.getLatitude(),-90),latRatio,lonRatio));
				subGrid.addAll(getSubGrid(new Position(xPosition.getLatitude(),90),yPosition,latRatio,lonRatio));
			} catch (WrongLatitudeException e) {
				e.printStackTrace();
			} catch (WrongLongitudeException e) {
				e.printStackTrace();
			}
			return subGrid;
		}
		
		int rowSize = yPosition.getGridLatitude(latRatio) - xPosition.getGridLatitude(latRatio);
		for(int i = xPosition.getGridLongitude(lonRatio); i < yPosition.getGridLongitude(lonRatio); ++i) {
			List<Integer> row = new ArrayList<Integer>();
			int initialRowIndex = 0;		
			try {
				initialRowIndex = new Position(xPosition.getLatitude(),getActualLongitude((i*lonRatio))).generateIndex(latRatio,lonRatio);
			} catch (WrongLatitudeException e) {
				e.printStackTrace();
			} catch (WrongLongitudeException e) {
				e.printStackTrace();
			}
			for(int j = 0; j < rowSize; ++j) {
				row.add(initialRowIndex + j);
			}
			subGrid.addAll(row);
		}
		
		return subGrid;
	}
	
	public static double getActualLongitude(double longitude) {
		if(longitude > 0) return -(longitude-90);
		if(longitude < 0) return Math.abs(longitude-90);
		return 0;
	}
	
	
}
