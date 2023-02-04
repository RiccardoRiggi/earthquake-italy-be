package it.riccardoriggi.earthquake.interfaces;

import java.util.List;

import it.riccardoriggi.earthquake.beans.Earthquake;
import it.riccardoriggi.earthquake.exceptions.EarthquakeException;

public interface EarthquakeInterface {

	public Object prova();

	public void insertEarthquake(Earthquake earthquake) throws EarthquakeException;

	public void insertEarthquakes(List<Earthquake> list) throws EarthquakeException;


}
