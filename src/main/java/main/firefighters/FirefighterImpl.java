package main.firefighters;

import main.api.Building;
import main.api.CityNode;
import main.api.Firefighter;
import main.api.exceptions.NoFireFoundException;

public class FirefighterImpl implements Firefighter {
    private CityNode location;
    private int distanceTraveled = 0;

    public FirefighterImpl(CityNode location) {
        this.location = location;
    }

    @Override
    public CityNode getLocation() {
        return location;
    }

    @Override
    public int distanceTraveled() {
        return distanceTraveled;
    }

    @Override
    public void travelToAndExinguishFire(Building building) {
        distanceTraveled += DistanceUtil.nodeDistance(location, building.getLocation());
        location = building.getLocation();

        if (building.isBurning()) {
            try {
                building.extinguishFire();
            } catch (NoFireFoundException e) {
                // This should never occur. Consider API changes to remove this dead code.
            }
        }
    }
}
