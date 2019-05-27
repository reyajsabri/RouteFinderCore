package com.interstellar.transport.core;

public interface Route {
	public String getId();
    public Planet getDestination();
    public Planet getSource();
    public double getWeight();
}
