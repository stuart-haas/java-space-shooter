package com.spaceshooter.map;

public enum Id {

	Arrive(), 
	Avoid(), 
	CollisionNode(1),
	Evade(), 
	Flee(), 
	FollowPath(),
	Particle(),
	Player(0), 
	Pursue(), 
	Seek(),
	Seeker(5),  
	Tile(),
	Wander();
	
	int value;
	
	public int value() {
		return value;
	}
	 
    private Id(int value) {
        this.value = value;
    }
    
    private Id() {}
}
