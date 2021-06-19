package main.scenarios;

import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import main.api.Pyromaniac;
import main.api.exceptions.FireproofBuildingException;
import main.impls.CityImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;

public class BasicScenarios {
    @Test
    public void noFire() throws FireproofBuildingException {
        City basicCity = new CityImpl(5, 5, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();

        fireDispatch.setFirefighters(1);
        fireDispatch.dispatchFirefighers();
        Firefighter firefighter = fireDispatch.getFirefighters().get(0);
        Assert.assertEquals(0, firefighter.distanceTraveled());
    }

    @Test
    public void noFirefighters() throws FireproofBuildingException {
        City basicCity = new CityImpl(5, 5, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();

        CityNode fireNode = new CityNode(0, 1);
        Pyromaniac.setFire(basicCity, fireNode);

        fireDispatch.setFirefighters(0);
        fireDispatch.dispatchFirefighers();
        Assert.assertEquals(0, fireDispatch.getFirefighters().size());
        Assert.assertTrue(basicCity.getBuilding(fireNode).isBurning());
    }

    @Test
    public void singleFire() throws FireproofBuildingException {
        City basicCity = new CityImpl(5, 5, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();

        CityNode fireNode = new CityNode(0, 1);
        Pyromaniac.setFire(basicCity, fireNode);

        fireDispatch.setFirefighters(1);
        fireDispatch.dispatchFirefighers(fireNode);
        Assert.assertFalse(basicCity.getBuilding(fireNode).isBurning());
    }

    @Test
    public void singleFireDistanceTraveledDiagonal() throws FireproofBuildingException {
        City basicCity = new CityImpl(2, 2, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();

        // Set fire on opposite corner from Fire Station
        CityNode fireNode = new CityNode(1, 1);
        Pyromaniac.setFire(basicCity, fireNode);

        fireDispatch.setFirefighters(1);
        fireDispatch.dispatchFirefighers(fireNode);

        Firefighter firefighter = fireDispatch.getFirefighters().get(0);
        Assert.assertEquals(2, firefighter.distanceTraveled());
        Assert.assertEquals(fireNode, firefighter.getLocation());
    }

    @Test
    public void singleFireDistanceTraveledAdjacent() throws FireproofBuildingException {
        City basicCity = new CityImpl(2, 2, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();

        // Set fire on adjacent X position from Fire Station
        CityNode fireNode = new CityNode(1, 0);
        Pyromaniac.setFire(basicCity, fireNode);

        fireDispatch.setFirefighters(1);
        fireDispatch.dispatchFirefighers(fireNode);

        Firefighter firefighter = fireDispatch.getFirefighters().get(0);
        Assert.assertEquals(1, firefighter.distanceTraveled());
        Assert.assertEquals(fireNode, firefighter.getLocation());
    }

    @Test
    public void simpleDoubleFire() throws FireproofBuildingException {
        City basicCity = new CityImpl(2, 2, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();


        CityNode[] fireNodes = {
                new CityNode(0, 1),
                new CityNode(1, 1)};
        Pyromaniac.setFires(basicCity, fireNodes);

        fireDispatch.setFirefighters(1);
        fireDispatch.dispatchFirefighers(fireNodes);

        Firefighter firefighter = fireDispatch.getFirefighters().get(0);
        Assert.assertEquals(2, firefighter.distanceTraveled());
        Assert.assertEquals(fireNodes[1], firefighter.getLocation());
        Assert.assertFalse(basicCity.getBuilding(fireNodes[0]).isBurning());
        Assert.assertFalse(basicCity.getBuilding(fireNodes[1]).isBurning());
    }

    @Test
    public void doubleFirefighterDoubleFire() throws FireproofBuildingException {
        City basicCity = new CityImpl(2, 2, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();

        CityNode[] fireNodes = {
                new CityNode(0, 1),
                new CityNode(1, 0)};
        Pyromaniac.setFires(basicCity, fireNodes);

        fireDispatch.setFirefighters(2);
        fireDispatch.dispatchFirefighers(fireNodes);

        List<Firefighter> firefighters = fireDispatch.getFirefighters();
        int totalDistanceTraveled = 0;
        boolean firefighterPresentAtFireOne = false;
        boolean firefighterPresentAtFireTwo = false;
        for (Firefighter firefighter : firefighters) {
            totalDistanceTraveled += firefighter.distanceTraveled();

            if (firefighter.getLocation().equals(fireNodes[0])) {
                firefighterPresentAtFireOne = true;
            }
            if (firefighter.getLocation().equals(fireNodes[1])) {
                firefighterPresentAtFireTwo = true;
            }
        }

        Assert.assertEquals(2, totalDistanceTraveled);
        Assert.assertTrue(firefighterPresentAtFireOne);
        Assert.assertTrue(firefighterPresentAtFireTwo);
        Assert.assertFalse(basicCity.getBuilding(fireNodes[0]).isBurning());
        Assert.assertFalse(basicCity.getBuilding(fireNodes[1]).isBurning());
    }

    @Test
    public void tripleFirefighterTripleFire() throws FireproofBuildingException {
        City basicCity = new CityImpl(4, 4, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();

        CityNode[] fireNodes = {
                new CityNode(0, 1),
                new CityNode(1, 0),
                new CityNode(2, 2)};
        Pyromaniac.setFires(basicCity, fireNodes);

        fireDispatch.setFirefighters(3);
        fireDispatch.dispatchFirefighers(fireNodes);

        List<Firefighter> firefighters = fireDispatch.getFirefighters();
        int totalDistanceTraveled = 0;
        boolean firefighterPresentAtFireThree = false;
        boolean firefighterPresentAtFireTwo = false;
        boolean firefighterPresentAtFireStation = false;
        for (Firefighter firefighter : firefighters) {
            totalDistanceTraveled += firefighter.distanceTraveled();

            if (firefighter.getLocation().equals(fireNodes[2])) {
                firefighterPresentAtFireThree = true;
            }
            if (firefighter.getLocation().equals(fireNodes[1])) {
                firefighterPresentAtFireTwo = true;
            }
            if (firefighter.getLocation().equals(basicCity.getFireStation().getLocation())) {
                firefighterPresentAtFireStation = true;
            }
        }

        Assert.assertEquals(5, totalDistanceTraveled);
        Assert.assertTrue(firefighterPresentAtFireThree);
        Assert.assertTrue(firefighterPresentAtFireTwo);
        Assert.assertTrue(firefighterPresentAtFireStation);
        Assert.assertFalse(basicCity.getBuilding(fireNodes[0]).isBurning());
        Assert.assertFalse(basicCity.getBuilding(fireNodes[1]).isBurning());
        Assert.assertFalse(basicCity.getBuilding(fireNodes[2]).isBurning());
    }

    @Test
    public void randomFireStationLocationAndRandomFireLocationsWithRectangularGrid() throws FireproofBuildingException {
        int rows = 10;
        int cols = 20;
        City basicCity = new CityImpl(rows, cols, createRandomCityNode(rows, cols));
        FireDispatch fireDispatch = basicCity.getFireDispatch();
        CityNode fireStationLocation = basicCity.getFireStation().getLocation();

        int fires = 3;
        CityNode[] fireNodes = new CityNode[fires];
        for (int i = 0; i < fires; ) {
            CityNode fireNode = createRandomCityNode(rows, cols);
            if (fireNode.equals(fireStationLocation)) {
                // Fire can't occur at Fire Station
                continue;
            }
            fireNodes[i++] = fireNode;
        }

        Pyromaniac.setFires(basicCity, fireNodes);

        fireDispatch.setFirefighters(1);
        fireDispatch.dispatchFirefighers(fireNodes);

        int totalDistanceTraveled = 0;
        for (Firefighter firefighter : fireDispatch.getFirefighters()) {
            totalDistanceTraveled += firefighter.distanceTraveled();
        }
        Assert.assertTrue(totalDistanceTraveled > 0);
        for (int i = 0; i < fires; i++) {
            Assert.assertFalse(basicCity.getBuilding(fireNodes[i]).isBurning());
        }
    }

    private CityNode createRandomCityNode(int rows, int cols) {
        Random random = new Random();
        return new CityNode(Math.abs(random.nextInt() % rows), Math.abs(random.nextInt() % cols));
    }
}
