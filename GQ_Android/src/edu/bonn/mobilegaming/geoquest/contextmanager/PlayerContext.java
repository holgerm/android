/**
 * Implementation for the diploma thesis "Adaption in digitalen mobilen Lernspielen - Anwendung in GeoQuest"
 * 
 * @author Sabine Polko
 */
package edu.bonn.mobilegaming.geoquest.contextmanager;

public class PlayerContext {
	
	private int age = 0;
	private int techLevel = 0;
	private int activLevel = 0;
	private int knowLevel = 0;
	
	private boolean isEmpty = true;
	
	public boolean isEmpty() {
		return isEmpty;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		isEmpty = false;
		this.age = age;
	}

	public int getTechLevel() {
		return techLevel;
	}

	public void setTechLevel(int techLevel) {
		isEmpty = false;
		this.techLevel = techLevel;
	}

	public int getActivLevel() {
		return activLevel;
	}

	public void setActivLevel(int activLevel) {
		isEmpty = false;
		this.activLevel = activLevel;
	}


	public int getKnowledgeLevel() {
		return knowLevel;
	}

	public void setKnowledgeLevel(int knowLevel) {
		isEmpty = false;
		this.knowLevel = knowLevel;
	}
	


}
