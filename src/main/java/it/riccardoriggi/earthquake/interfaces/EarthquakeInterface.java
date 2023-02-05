package it.riccardoriggi.earthquake.interfaces;

import java.util.List;

import org.bson.Document;

import it.riccardoriggi.earthquake.beans.Earthquake;
import it.riccardoriggi.earthquake.exceptions.EarthquakeException;

public interface EarthquakeInterface {

	public void insertEarthquake(Earthquake earthquake) throws EarthquakeException;

	public void insertEarthquakes(List<Earthquake> list) throws EarthquakeException;

	public List<Document> getEarthquaks(Double minMagnitude, String startDate, String endDate, Double latitude, Double longitude, Double distance)
			throws EarthquakeException;

}
