package com.interstellar.transport.core.routefinder;

import java.util.List;

import com.interstellar.transport.core.Galaxy;
import com.interstellar.transport.core.Planet;

public interface RouteFinder {

	public List<Planet> getShortestRoute(Planet sourcePlanet, Planet destinationPlanet);
	public void resetGalaxy(Galaxy galaxy);
}
