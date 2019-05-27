package com.interstellar.transport.core;

import java.util.Set;

public interface Galaxy {

	public void addPlanet(Planet planet);
	Set<Planet> getPlanets();
}
