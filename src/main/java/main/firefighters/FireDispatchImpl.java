package main.firefighters;

import main.api.Building;
import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import main.util.PermutationGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FireDispatchImpl implements FireDispatch {
    private City city;
    private List<Firefighter> firefighters;

    public FireDispatchImpl(City city) {
        this.city = city;
    }

    @Override
    public void setFirefighters(int numFirefighters) {
        CityNode fireStationLocation = city.getFireStation().getLocation();

        firefighters = new ArrayList<>(numFirefighters < 0 ? 0 : numFirefighters);
        for (int i = 0; i < numFirefighters; i++) {
            firefighters.add(new FirefighterImpl(fireStationLocation));
        }
    }

    @Override
    public List<Firefighter> getFirefighters() {
        return null == firefighters ? Collections.emptyList() : firefighters;
    }

    @Override
    public void dispatchFirefighers(CityNode... burningBuildings) {
        if (burningBuildings == null || burningBuildings.length == 0) {
            return;
        }

        Building[] buildingsOnFire = new Building[burningBuildings.length];
        for (int i = 0; i < burningBuildings.length; i++) {
            Building b = city.getBuilding(burningBuildings[i]);
            buildingsOnFire[i] = b;
        }

        while (activeFires(buildingsOnFire)) {
            // Find fire fighter with shortest path.
            Firefighter firefighter = null;
            Building[] shortestPath = null;
            int shortestDistance = Integer.MAX_VALUE;

            for (int i = 0; i < firefighters.size(); i++) {
                Firefighter ff = firefighters.get(i);
                Building[] sp = findShortestPath(buildingsOnFire, city.getBuilding(ff.getLocation()));
                if (firefighter == null) {
                    firefighter = ff;
                    shortestPath = sp;
                    shortestDistance = pathDistance(sp);
                    continue;
                }

                int spDistance = pathDistance(sp);
                if (spDistance < shortestDistance ||
                        (spDistance == shortestDistance && ff.distanceTraveled() < firefighter.distanceTraveled())) {
                    firefighter = ff;
                    shortestPath = sp;
                    shortestDistance = spDistance;
                }
            }

            if (firefighter != null) {
                firefighter.travelToAndExinguishFire(shortestPath[1]);
                if (buildingsOnFire.length >= 1) {
                    buildingsOnFire = removeBuilding(buildingsOnFire, shortestPath[1]);
                } else {
                    buildingsOnFire = new Building[]{};
                }
            }
        }
    }

    private Building[] removeBuilding(Building[] buildings, Building buildingToRemove) {
        if (buildings.length == 0) {
            return buildings;
        }

        Building[] ret = new Building[buildings.length - 1];
        int i = 0;
        for (Building b : buildings) {
            if (!b.equals(buildingToRemove)) {
                ret[i++] = b;
            }
        }
        return ret;
    }

    private Building[] findShortestPath(Building[] burningBuildings, Building start) {
        Building[] allBuildings = new Building[burningBuildings.length + 1];
        allBuildings[0] = start;
        for (int i = 0; i < burningBuildings.length; i++) {
            allBuildings[i + 1] = burningBuildings[i];
        }
        List<Building[]> paths = PermutationGenerator.<Building>generate(allBuildings);
        Building[] shortestPath = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (Building[] path : paths) {
            if (path[0].getLocation().equals(start.getLocation())) {
                int pathDist = pathDistance(path);
                if (pathDist < shortestDistance) {
                    shortestPath = path;
                    shortestDistance = pathDist;
                }
            }
        }
        return shortestPath;
    }

    private int pathDistance(Building[] nodePath) {
        int distance = 0;
        for (int i = 1; i < nodePath.length; i++) {
            distance += DistanceUtil.nodeDistance(nodePath[i - 1].getLocation(), nodePath[i].getLocation());
        }
        return distance;
    }

    private boolean activeFires(Building[] buildings) {
        for (Building b : buildings) {
            if (b.isBurning()) {
                return true;
            }
        }
        return false;
    }
}



