package com.risk6441.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * This class defines Map and its list of continents.
 * @author Nirav
 * @see Territory
 * @see Continent
 */
public class Map {

	private HashMap<String, String> mapData;
	private List<Continent> continents;
	private HashMap<String, Continent> continentMap;
	
	public Map() {
		mapData = new HashMap<String, String>();
		continents = new ArrayList<Continent>();
		continentMap = new HashMap<String,Continent>();
	}
	
	/**
	 * @return the mapData
	 */
	public HashMap<String, String> getMapData() {
		return mapData;
	}
	
	/**
	 * @param mapData the mapData to set
	 */
	public void setMapData(HashMap<String, String> mapData) {
		this.mapData = mapData;
	}
	
	/**
	 * @return the continents
	 */
	public List<Continent> getContinents() {
		return continents;
	}
	
	/**
	 * @param continents the continents to set
	 */
	public void setContinents(List<Continent> continents) {
		this.continents = continents;
	}
	
	/**
	 * @return the continentMap
	 */
	public HashMap<String, Continent> getContinentMap() {
		return continentMap;
	}
	
	/**
	 * @param continentMap the continentMap to set
	 */
	public void setContinentMap(HashMap<String, Continent> continentMap) {
		this.continentMap = continentMap;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Map [mapData=" + mapData + ", continents=" + continents + ", continentMap=" + continentMap + "]";
	}

	
	
}
