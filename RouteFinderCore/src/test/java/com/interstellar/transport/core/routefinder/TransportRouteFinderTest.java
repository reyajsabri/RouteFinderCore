package com.interstellar.transport.core.routefinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.interstellar.transport.core.Galaxy;
import com.interstellar.transport.core.Planet;
import com.interstellar.transport.core.Route;

import org.junit.Assert;


public class TransportRouteFinderTest {
	
	@Test
	public void test() {
		
		Planet planetA = new PlanetImpl("A");
		Planet planetB = new PlanetImpl("B");
		Planet planetC = new PlanetImpl("C");
		Planet planetD = new PlanetImpl("D"); 
		Planet planetE = new PlanetImpl("E");
		Planet planetF = new PlanetImpl("F");
		
		planetA.addNeighbourPlanet(new RouteImpl(planetA.getName(), planetA, planetB, 10));
		planetA.addNeighbourPlanet(new RouteImpl(planetA.getName(), planetA, planetC, 15));
		 
		planetB.addNeighbourPlanet(new RouteImpl(planetB.getName(), planetB, planetD, 12));
		planetB.addNeighbourPlanet(new RouteImpl(planetB.getName(), planetB, planetF, 15));
		 
		planetC.addNeighbourPlanet(new RouteImpl(planetC.getName(), planetC, planetE, 10));
		 
		planetD.addNeighbourPlanet(new RouteImpl(planetD.getName(), planetD, planetE, 2));
		planetD.addNeighbourPlanet(new RouteImpl(planetD.getName(), planetD, planetF, 1));
		 
		planetF.addNeighbourPlanet(new RouteImpl(planetF.getName(), planetF, planetE, 5));
		 
		Galaxy galaxy = new GalaxyImpl();
		 
		galaxy.addPlanet(planetA);
		galaxy.addPlanet(planetB);
		galaxy.addPlanet(planetC);
		galaxy.addPlanet(planetD);
		galaxy.addPlanet(planetE);
		galaxy.addPlanet(planetF);
		 
		TransportRouteFinder finder = new TransportRouteFinder(galaxy);
		
		System.out.println("Start-> ");
		List<Planet> shortestRoute = finder.getShortestRoute(planetA, planetF);
		shortestRoute.stream().forEach(planet -> {
			System.out.println(planet.getName() + "-> " );
		});
		
		System.out.println(planetF.getName() + "-> End");
		
		Assert.assertTrue(shortestRoute.contains(planetA));
		Assert.assertTrue(shortestRoute.contains(planetB));
		Assert.assertTrue(shortestRoute.contains(planetD));
		
	}
	
	
	
	private class PlanetImpl implements Planet{
		
		private String name;
		private String id;
		private final Set<Route> neighbourPlanets = new HashSet<>();
	     
	    public PlanetImpl(String name) {
	        this.name = name;
	    }
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public Set<Route> getNeighbourPlanets() {
			return neighbourPlanets;
		}

		@Override
		public void addNeighbourPlanet(Route route) {
			neighbourPlanets.add(route);
			
		}
		
	}
	
	private class GalaxyImpl implements Galaxy{

		private final Set<Planet> planets = new HashSet<>();
	    
	    public void addPlanet(Planet planet) {
	    	planets.add(planet);
	    }

		public Set<Planet> getPlanets() {
			return planets;
		}
	}
	
	private class RouteImpl implements Route{

		private String id;
		private Planet source;
		private Planet destination;
		private double weight;
		
		public RouteImpl(String id, Planet source, Planet destination, double weight) {
			this.id = id;
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}
		
		@Override
		public String getId() {
			return id;
		}

		@Override
		public Planet getDestination() {
			return destination;
		}

		@Override
		public Planet getSource() {
			return source;
		}

		@Override
		public double getWeight() {
			return weight;
		}
		
	}

}
