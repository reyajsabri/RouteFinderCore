package com.interstellar.transport.core;

import java.util.Set;

public interface Planet {
	public String getName();
	public String getId();
	
	public Set<Route> getNeighbourPlanets();
	public void addNeighbourPlanet(Route route);

}
