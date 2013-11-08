package aw2m.common.study;

/**
 * Class for testing, as always.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 16/10/2013 - 03:50:06 AM
 */
public class MainStudy {

    public static void main(String args[]) {
        /*
         * Test of an algorithm that creates Unit objects evenly
         * distributed on a map, according to its size and number of units.
         *
         * @author Yomero
         */

        //Size of map
        byte sizeX = 25;
        byte sizeY = 22;

        //Number of units to create
        byte numberOfUnits = 25;
        //DEBUG
        System.out.println("Number of units is " + numberOfUnits);

        //The sqrt serves to distribute units uniformly across the map
        double sqrt2 = Math.sqrt(numberOfUnits);
        //DEBUG
        System.out.println("Sqrt is " + sqrt2);

        sqrt2 = Math.floor(sqrt2);
        //DEBUG
        System.out.println("Floored Sqrt is " + sqrt2);

        byte sqrt = (byte) sqrt2;
        //DEBUG
        System.out.println("Byte Sqrt is " + sqrt);

        //X & Y increments according to sqrt
        byte xIncrement = (byte) Math.floor(sizeX / sqrt);
        //DEBUG
        System.out.println("xIncrement is " + xIncrement);

        byte yIncrement = (byte) Math.floor(sizeY / sqrt);
        //DEBUG
        System.out.println("yIncrement is " + yIncrement);

        //FOR counters
        byte xCounter = 0;
        byte yCounter = 0;

        //Total units deployed on map
        int totalCounter = 0;

        boolean stopFlag = false;

        for (yCounter = 0; yCounter < sizeY; yCounter += yIncrement) {
            for (xCounter = 0; xCounter < sizeX; xCounter += xIncrement) {
                //DEBUG
                System.out.println("(x,y) is (" + xCounter + "," + yCounter + ")");
                totalCounter++;
                if (totalCounter == numberOfUnits) {
                    stopFlag = true;
                    break;
                }
            }
            if (stopFlag) {
                break;
            }
        }
        //DEBUG
        System.out.println("Total iterations: " + totalCounter);

        /*
         * Calculating if it is possible to deploy enemy units same way.
         * Using a gap to not replace units already deployed.
         */

        //Deployment of other units started at 0, 0

        //Analize last iteration/place of deployment
        //DEBUG
        System.out.println("Last unit was deployed at ("
                + xCounter
                + ","
                + yCounter
                + ")");

        if (yCounter < sizeY) {
            //DEBUG
            System.out.println(yCounter + "<" + sizeY);
        }
        if (xCounter < sizeX) {
            //DEBUG
            System.out.println(xCounter + "<" + sizeX);
        }

        //Gap can be in the space between rows and columns of units already deployed
        //But enemy units must be separated from own units by at least 1 square
        //Assuming artilleries were deployed, as to enter in their Deathzone
        //Also, GRIT artilleries.

        //So, an increment is how many squares a new unit is deployed, on X & Y

        //The gap is a phase-shift on at least 2 gridCells.
        //At least one GridCell far from each other enemy unit
        //So, halfway

        //So, x and y increments should be at least 3 to perform properly
        //They also can be even or odd numbers

        //If it is odd

        byte phaseX = 0;
        if (xIncrement % 2 != 0) {
            //DEBUG
            System.out.println("xIncrement is odd: " + xIncrement);
            //Then get half, and round it
            //phase = (byte) Math.round(xIncrement / 2);
            phaseX = (byte) Math.floor(xIncrement / 2);
            phaseX++;
        }
        else {
            //DEBUG
            System.out.println("xIncrement is even: " + xIncrement);
            //If its even, it should be at least 2
            if (xIncrement >= 2){
                //DEBUG
                System.out.println("xIncrement is >= 2: " + xIncrement);
                //Then, just divide by 2
                phaseX = (byte) Math.floor(xIncrement / 2);
            }
        }
        //DEBUG
        System.out.println("PhaseX is: " + phaseX);
        
        byte phaseY = 0;
        if (yIncrement % 2 != 0) {
            //DEBUG
            System.out.println("yIncrement is odd: " + yIncrement);
            //Then get half, and round it
            //phase = (byte) Math.round(xIncrement / 2);
            phaseY = (byte) Math.floor(yIncrement / 2);
            phaseY++;
            //Math.rint(sqrt2);
        }
        else {
            //DEBUG
            System.out.println("yIncrement is even: " + yIncrement);
            //If its even, it should be at least 2
            if (xIncrement >= 2){
                //DEBUG
                System.out.println("yIncrement is >= 2: " + yIncrement);   
                //Then, just divide by 2
                phaseY = (byte) Math.floor(yIncrement / 2);
            }
        }
        //DEBUG
        System.out.println("PhaseY is: " + phaseY);
        
        //Then use the phase shift to deploy enemy units
        //Add phaseX and phaseY to original xCounter & yCounter        
        
        int totalEnemyCounter = 0;

        boolean stopFlag2 = false;

        for (yCounter = phaseY; yCounter < sizeY; yCounter += yIncrement) {
            for (xCounter = phaseX; xCounter < sizeX; xCounter += xIncrement) {
                //DEBUG
                System.out.println("(x,y) is (" + xCounter + "," + yCounter + ")");
                totalEnemyCounter++;
                if (totalEnemyCounter == numberOfUnits) {
                    stopFlag2 = true;
                    break;
                }
            }
            if (stopFlag2) {
                break;
            }
        }
        //DEBUG
        System.out.println("Total enemy iterations: " + totalCounter);
        
        int array[][] = new int[4][3];
        System.out.println("Size x: " + array.length);
        System.out.println("Size y: " + array[0].length);
    }
}