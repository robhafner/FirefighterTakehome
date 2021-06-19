package main.firefighters;

import main.api.CityNode;

public class DistanceUtil {
    static int nodeDistance(CityNode start, CityNode end) {
        return Math.abs(end.getX() - start.getX()) + Math.abs(end.getY() - start.getY());
    }
}
