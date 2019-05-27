package com.interstellar.transport.core.routefinder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.interstellar.transport.core.Galaxy;
import com.interstellar.transport.core.Planet;

public class TransportRouteFinder implements RouteFinder{
	
	private Galaxy galaxy;
	private Map<Planet, List<Planet>> shortestRouteMap = new HashMap<>();
	private final Map<Planet, Double> shortestWeightageMap = new HashMap<>();
	private volatile boolean isRouteMatrixReady = false;
	private Planet currentSourcePlanet = null;
	private final Lock lock = new ReentrantLock();
	
	public TransportRouteFinder(Galaxy galaxy) {
		this.galaxy = galaxy;
	}
	
	private void prepaerInitialWeightageMap() {
		galaxy.getPlanets().forEach(planet -> {
			shortestWeightageMap.put(planet, Double.MAX_VALUE);
		});
	}
	
	public List<Planet> getShortestRoute(Planet sourcePlanet, Planet destinationPlanet){
		//Preparing route matrix only when either matrix is not ready or matrix is not 
		// prepared according to sourcePlanet
		if(!(isRouteMatrixReady && sourcePlanet.equals(currentSourcePlanet)))
			prepareShortestRouteMatrixForEachReachablePlanet(sourcePlanet);
		return shortestRouteMap.get(destinationPlanet);
	}
	

	private void prepareShortestRouteMatrixForEachReachablePlanet(Planet source) {
		if(!lock.tryLock())
			return;
		currentSourcePlanet = source;
		prepaerInitialWeightageMap();
		shortestWeightageMap.put(source, 0d);
	    
	 
	    Set<Planet> exploredPlanet = new HashSet<>();
	    Set<Planet> unExploredPlanet = new HashSet<>();
	 
	    unExploredPlanet.add(source);
	 
	    while (unExploredPlanet.size() != 0) {
	    	Planet currentPlanet = getNearestPlanet(unExploredPlanet);
	        unExploredPlanet.remove(currentPlanet);
	        
	        currentPlanet.getNeighbourPlanets().forEach(route -> {
	        	Planet neighbourPlanet = route.getDestination();
	        	Double neighbourWeightage = route.getWeight();
	            if (!exploredPlanet.contains(neighbourPlanet)) {
	                calculateMinimumWeightage(neighbourPlanet, neighbourWeightage, currentPlanet);
	                unExploredPlanet.add(neighbourPlanet);
	            }
	        });
	        
	        exploredPlanet.add(currentPlanet);
	    }
	    
	    isRouteMatrixReady = true;
	}
	
	private Planet getNearestPlanet(Set<Planet> unExploredPlanet) {
	    Map<String, Object> minimalParam = new HashMap<>();
	    minimalParam.put("lowestWeightage", Double.MAX_VALUE);
	    
	    unExploredPlanet.forEach(planet -> {
	    	double planetWeightage = shortestWeightageMap.get(planet);
	        if (planetWeightage < (Double)minimalParam.get("lowestWeightage")) {
	        	minimalParam.put("lowestWeightage", planetWeightage);
	        	minimalParam.put("lowestWeightagePlanet", planet);
	            
	        }
	    });
	    return (Planet)minimalParam.get("lowestWeightagePlanet");
	}
	
	private void calculateMinimumWeightage(Planet neighbourPlanet,
						Double neighbourWeightage, Planet currentPlanet) {
		
	    Double WeightageFromSourceToPlanet = shortestWeightageMap.get(currentPlanet);
	    if (WeightageFromSourceToPlanet + neighbourWeightage < shortestWeightageMap.get(neighbourPlanet)) {
	    	shortestWeightageMap.put(neighbourPlanet, (WeightageFromSourceToPlanet + neighbourWeightage));
	        LinkedList<Planet> shortestPath = shortestRouteMap.get(currentPlanet) == null ? new LinkedList<>() : 
	        	new LinkedList<>(shortestRouteMap.get(currentPlanet));
	        shortestPath.add(currentPlanet);
	        shortestRouteMap.put(neighbourPlanet, shortestPath);
	    }
	}

	@Override
	public void resetGalaxy(Galaxy galaxy) {
		this.galaxy = galaxy;
		isRouteMatrixReady = false;
		currentSourcePlanet = null;
		
	}
	
}
