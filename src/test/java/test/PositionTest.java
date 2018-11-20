package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import albert.sabate.grib.Position;
import exceptions.WrongLatitudeException;
import exceptions.WrongLongitudeException;

public class PositionTest {
	
	private double latRatio;
	private double lonRatio;
	
	@Before
	public void before() {
		latRatio = 0.25;
		lonRatio = 0.25;
	}
	
	@Test(expected = WrongLatitudeException.class)
	public void latitudeLessThan0Error() throws WrongLatitudeException, WrongLongitudeException {
		new Position(-1,0);
	}

	@Test(expected = WrongLatitudeException.class)
	public void latitudeGreaterThan360Error() throws WrongLatitudeException, WrongLongitudeException {
		new Position(361,0);
	}
	
	@Test(expected = WrongLongitudeException.class)
	public void longitudeLessThanMinus90Error() throws WrongLatitudeException, WrongLongitudeException {
		new Position(0,-91);
	}

	@Test(expected = WrongLongitudeException.class)
	public void longitudeGreaterThan90Error() throws WrongLatitudeException, WrongLongitudeException {
		new Position(0,91);
	}
	
	@Test
	public void getGridLatitude() throws WrongLatitudeException, WrongLongitudeException {
		Position position = new Position(40,0);
		assertEquals(160,position.getGridLatitude(latRatio));
	}
	
	@Test
	public void getGridLongitude() throws WrongLatitudeException, WrongLongitudeException {
		Position position = new Position(0,40);
		assertEquals(200,position.getGridLongitude(lonRatio));
	}
	
	@Test
	public void generateIndex() throws WrongLatitudeException, WrongLongitudeException {
		Position position = new Position(0,0);
		assertEquals(518400,position.generateIndex(latRatio, lonRatio));
	}
	
	@Test
	public void getPositions() throws WrongLatitudeException, WrongLongitudeException {
		Position xPosition = new Position(0,90);
		Position yPosition = new Position(5,85);
		List<Integer> positions = Position.getSubGrid(xPosition, yPosition, latRatio, lonRatio);
		assertEquals(400,positions.size());
	}
	
	@Test
	public void getPositionsWithLatitudeXGreaterThanLatitudeY() throws WrongLatitudeException, WrongLongitudeException {
		Position xPosition = new Position(350,45); //350,45
		Position yPosition = new Position( 5 ,35); // 5 ,55
		List<Integer> positions = Position.getSubGrid(xPosition, yPosition, latRatio, lonRatio);
		assertEquals(2400,positions.size());
	}
	
	@Test
	public void getPositionsWithLongitudeXGreaterThanLongitudeY() throws WrongLatitudeException, WrongLongitudeException {
		Position xPosition = new Position(0,-85); //0,175
		Position yPosition = new Position(5, 85); //5, 5
		List<Integer> positions = Position.getSubGrid(xPosition, yPosition, latRatio, lonRatio);
		assertEquals(800,positions.size());
	}
	
	@Test 
	public void getPositionsWithLatitudeXAndLongitudeXGreatherThanLatitudeYandLongitudeY() throws WrongLatitudeException, WrongLongitudeException {
		Position xPosition = new Position(355,-85); //355,175
		Position yPosition = new Position( 5 , 85 ); // 5 , 5
		List<Integer> positions = Position.getSubGrid(xPosition, yPosition, latRatio, lonRatio);
		assertEquals(1600,positions.size());
	}
}
