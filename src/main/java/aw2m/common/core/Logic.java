package aw2m.common.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * This class is supossed to host certain algorithms employed by the game logic
 * 
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class Logic {

	public static final byte NO_ROOT = 0;
	public static final byte UP_ROOT = 1;
	public static final byte DOWN_ROOT = 2;
	public static final byte LEFT_ROOT = 3;
	public static final byte RIGHT_ROOT = 4;
	public static int iterations = 0;
	
	public static boolean getDeathZone;
	static{
		getDeathZone = true;		
	}
	
	private static Set<GridCell> movementRadius;
	// private static LinkedList<GridCell> movementRadius;
	/**
	 * An area of the map, representing some GridCell objects inside a
	 * cross-like area
	 */
	private static Set<GridCell> crossArea;
	private static GridCell[][] map;
	// private static Unit attacker;
	private static byte defenseStars1;
	private static int defense2;
	private static int defenseP2;
	private static byte defenseStars2;
	private static Unit unit1;
	private static byte unit1Movement;
	private static byte unit1MovementType;
	// Lists for Dijkstra
	private static LinkedList<DijkstraElement> openList;
	public static LinkedList<DijkstraElement> closedList;
	private static LinkedList<DijkstraElement> pendingOpenList;

	/**
	 * Calculates and returns the movement radius for a given amount of movemen
	 * radius, starting on agiven location and on a given map; using private
	 * functions to calculate each transversable GridCell across the board,
	 * returning a HashSet containing such GridCell objects. This is a test
	 * method, and must be used only as such. It creates a mocking unit.
	 * 
	 * @param location
	 *            The initial location for the test to begin.
	 * @param map
	 *            The map on which the unit exists
	 * @return movementRadius the HashSet containing the GridCell objects
	 */
	// public static LinkedList<GridCell> calculateMovementRadius(GridCell
	// location, byte movementRadius, GridCell map[][]) {
	public static Set<GridCell> calculateMovementRadius(GridCell location,
			byte movementRadius, GridCell map[][]) {
		// Logic.movementRadius = new LinkedList<GridCell>();
		Logic.movementRadius = new LinkedHashSet<GridCell>();
		// DEBUG
		/* Hard coding an infantry for testing purposes */
		Logic.unit1 = new Unit();
		Logic.unit1.unitType = Unit.FIGHTER;
		Logic.unit1Movement = Unit.getMovement(Logic.unit1);
		Logic.unit1MovementType = Unit.getMovementType(Logic.unit1.unitType);
		Logic.unit1.location = location;
		Logic.map = map;
		Logic.unit1.location.unit = Logic.unit1;
		// Add unit1.location as first GridCell
		Logic.movementRadius.add(unit1.location);
		// Initial call to recursive method
		calculateOnGridCell(unit1.location, Logic.unit1Movement, NO_ROOT);
		return Logic.movementRadius;
	}

	/**
	 * Calculates and returns the movement radius of a unit, using private
	 * functions to calculate each transversable GridCell across the board,
	 * returning a HashSet containing such GridCell objects
	 * 
	 * @param unit
	 *            The unit of which the movement radius wants to be known
	 * @param map
	 *            The map on which the unit exists
	 * @return movementRadius the HashSet containing the GridCell objects
	 */
	// public static LinkedList<GridCell> calculateMovementRadius(Unit unit,
	// GridCell map[][]) {
	public static Set<GridCell> calculateMovementRadius(Unit unit,
			GridCell map[][]) {
		Logic.iterations = 0;
		Logic.movementRadius = new LinkedHashSet<GridCell>();
		Logic.unit1 = unit;
		Logic.unit1.unitType = unit.unitType;
		// DEBUG
		System.out.println("Movement of unit is: "
				+ Unit.getMovement(Logic.unit1));
		Logic.unit1Movement = Unit.getMovement(Logic.unit1);
		Logic.unit1MovementType = Unit.getMovementType(Logic.unit1.unitType);
		Logic.unit1.location = unit.location;
		Logic.map = map;

		// Add unit1.location as first GridCell
		Logic.movementRadius.add(unit1.location);
		// Initial call to recursive method
		calculateOnGridCell(unit1.location, Logic.unit1Movement, NO_ROOT);
		return Logic.movementRadius;
	}

	/**
	 * 
	 * @param location
	 * @param movement
	 */
	private static void calculateOnGridCell(GridCell location, byte movement,
			byte root) {
		searchUp(location, movement);
		searchDown(location, movement);
		searchLeft(location, movement);
		searchRight(location, movement);

		/*
		 * switch (root) { case NO_ROOT: searchUp(location, movement);
		 * searchDown(location, movement); searchLeft(location, movement);
		 * searchRight(location, movement); return; case UP_ROOT:
		 * searchDown(location, movement); searchLeft(location, movement);
		 * searchRight(location, movement); return; case DOWN_ROOT:
		 * searchUp(location, movement); searchLeft(location, movement);
		 * searchRight(location, movement); return; case LEFT_ROOT:
		 * searchUp(location, movement); searchDown(location, movement);
		 * searchRight(location, movement); return; case RIGHT_ROOT:
		 * searchUp(location, movement); searchDown(location, movement);
		 * searchLeft(location, movement); return; }
		 */
	}

	private static void searchUp(GridCell location, byte movement) {
		// DEBUG
		// System.out.println("searchUp (" + location.x + "," + location.y + ","
		// + movement + ")");
		Logic.iterations++;
		// System.out.println("Iterations: " + Logic.iterations);

		if (location.y > 0) { // Not on the upper border of the map
			// If it is the original GridCell, from where the unit is standing
			if (Logic.map[location.x][location.y - 1]
					.equals(Logic.unit1.location)) {
				// Add nothing, as source GridCell can't be a destiny
				return;
			}
			// If there is an enemy or friend unit on the destination
			if (Logic.map[location.x][location.y - 1].unit != null) {
				// If the unit is not an ally one
				if (Logic.map[location.x][location.y - 1].unit.player.team != Logic.unit1.player.team) {
					// Add nothing and return, as this GridCell is not
					// transversable
					return;
				}
			}
			// If the unit can transverse the upper gridCell
			if (Logic.map[location.x][location.y - 1].terrain
					.getCanTransverse(Logic.unit1MovementType)) {
				// Calculate if the unit has enough movement points to
				// transverse the upper gridCell
				if (movement
						- Logic.map[location.x][location.y - 1].terrain
								.getMovementCost(Logic.unit1MovementType) >= 0) {
					// DEBUG
					// System.out.println("La diferencia es: " + (movement
					// - Logic.map[location.x][location.y -
					// 1].terrain.getMovementCost(Logic.unit1MovementType)));

					// System.out.println("Movement still is: " + movement);
					// System.out.println("Adding new (" + location.x + "," +
					// (location.y - 1) + ")");
					Logic.movementRadius
							.add(Logic.map[location.x][location.y - 1]);
				}
				// If the unit can move after transversing the upper gridCell
				if (movement > 0) {
					calculateOnGridCell(
							Logic.map[location.x][location.y - 1],
							(byte) (movement - Logic.map[location.x][location.y - 1].terrain
									.getMovementCost(Logic.unit1MovementType)),
							UP_ROOT);
				}
			}
		}
	}

	private static void searchLeft(GridCell location, byte movement) {
		// DEBUG
		// System.out.println("searchLeft (" + location.x + "," + location.y +
		// "," + movement + ")");
		Logic.iterations++;
		// System.out.println("Iterations: " + Logic.iterations);
		if (location.x > 0) { // Not on the left border of the map
			// If it is the original GridCell, from where the unit is standing
			if (Logic.map[location.x - 1][location.y]
					.equals(Logic.unit1.location)) {
				// Add nothing, as source GridCell can't be a destiny
				return;
			}
			// If there is an enemy or friend unit on the destination
			if (Logic.map[location.x - 1][location.y].unit != null) {
				// If the unit is not an ally one
				if (Logic.map[location.x - 1][location.y].unit.player.team != Logic.unit1.player.team) {
					// Add nothing and return, as this GridCell is not
					// transversable
					return;
				}
			}
			// If the unit can transverse the gridCell to the left
			if (map[location.x - 1][location.y].terrain
					.getCanTransverse(Logic.unit1MovementType)) {

				// Calculate if the unit has enough movement points to
				// transverse the upper gridCell
				if (movement
						- Logic.map[location.x - 1][location.y].terrain
								.getMovementCost(Logic.unit1MovementType) >= 0) {
					// DEBUG
					// System.out.println("La diferencia es: " + (movement
					// - Logic.map[location.x -
					// 1][location.y].terrain.getMovementCost(Logic.unit1MovementType)));

					// System.out.println("Movement still is: " + movement);
					// System.out.println("Adding new (" + (location.x - 1) +
					// "," + location.y + ")");
					Logic.movementRadius
							.add(Logic.map[location.x - 1][location.y]);
				}
				// If the unit can move after transversing the upper gridCell
				if (movement > 0) {
					calculateOnGridCell(
							Logic.map[location.x - 1][location.y],
							(byte) (movement - Logic.map[location.x - 1][location.y].terrain
									.getMovementCost(Logic.unit1MovementType)),
							LEFT_ROOT);
				}
			}
		}
	}

	private static void searchDown(GridCell location, byte movement) {
		// DEBUG
		// System.out.println("searchDown (" + location.x + "," + location.y +
		// "," + movement + ")");
		Logic.iterations++;
		// System.out.println("Iterations: " + Logic.iterations);
		if (location.y < Logic.map[0].length - 1) { // Not on the bottom border
													// of the map
			// If it is the original GridCell, from where the unit is standing
			if (Logic.map[location.x][location.y + 1]
					.equals(Logic.unit1.location)) {
				// Add nothing, as source GridCell can't be a destiny
				return;
			}
			// If there is an enemy or friend unit on the destination
			if (Logic.map[location.x][location.y + 1].unit != null) {
				// If the unit is not an ally one
				if (Logic.map[location.x][location.y + 1].unit.player.team != Logic.unit1.player.team) {
					// Add nothing and return, as this GridCell is not
					// transversable
					return;
				}
			}
			// If the unit can transverse the gridCell down
			if (map[location.x][location.y + 1].terrain
					.getCanTransverse(Logic.unit1MovementType)) {

				// Calculate if the unit has enough movement points to
				// transverse the upper gridCell
				if (movement
						- Logic.map[location.x][location.y + 1].terrain
								.getMovementCost(Logic.unit1MovementType) >= 0) {
					// DEBUG
					// System.out.println("La diferencia es: " + (movement
					// - Logic.map[location.x][location.y +
					// 1].terrain.getMovementCost(Logic.unit1MovementType)));

					// System.out.println("Movement still is: " + movement);
					// System.out.println("Adding new (" + location.x + "," +
					// (location.y + 1) + ")");
					Logic.movementRadius
							.add(Logic.map[location.x][location.y + 1]);
				}
				// If the unit can move after transversing the upper gridCell
				if (movement > 0) {
					calculateOnGridCell(
							Logic.map[location.x][location.y + 1],
							(byte) (movement - Logic.map[location.x][location.y + 1].terrain
									.getMovementCost(Logic.unit1MovementType)),
							DOWN_ROOT);
				}
			}
		}
	}

	private static void searchRight(GridCell location, byte movement) {
		// DEBUG
		// System.out.println("searchRight (" + location.x + "," + location.y +
		// "," + movement + ")");
		Logic.iterations++;
		// System.out.println("Iterations: " + Logic.iterations);
		if (location.x < Logic.map.length - 1) { // Not on the right border of
													// the map
			// If the unit can transverse the gridCell to the left
			if (Logic.map[location.x + 1][location.y].terrain
					.getCanTransverse(Logic.unit1MovementType)) {

				// If it is the original GridCell, from where the unit is
				// standing
				if (Logic.map[location.x + 1][location.y]
						.equals(Logic.unit1.location)) {
					// Add nothing, as source GridCell can't be a destiny
					return;
				}
				// If there is an enemy or friend unit on the destination
				if (Logic.map[location.x + 1][location.y].unit != null) {
					// If the unit is not an ally one
					if (Logic.map[location.x + 1][location.y].unit.player.team != Logic.unit1.player.team) {
						// Add nothing and return, as this GridCell is not
						// transversable
						return;
					}
				}
				// If the unit can transverse the right gridCell
				if (map[location.x + 1][location.y].terrain
						.getCanTransverse(Logic.unit1MovementType)) {
					// Calculate if the unit has enough movement points to
					// transverse the upper gridCell
					if (movement
							- Logic.map[location.x + 1][location.y].terrain
									.getMovementCost(Logic.unit1MovementType) >= 0) {
						// DEBUG
						// System.out.println("La diferencia es: " + (movement
						// - Logic.map[location.x +
						// 1][location.y].terrain.getMovementCost(Logic.unit1MovementType)));

						// System.out.println("Movement still is: " + movement);
						// System.out.println("Adding new (" + (location.x + 1)
						// + "," + location.y + ")");
						Logic.movementRadius
								.add(Logic.map[location.x + 1][location.y]);
					}
					// If the unit can move after transversing the upper
					// gridCell
					if (movement > 0) {
						calculateOnGridCell(
								Logic.map[location.x + 1][location.y],
								(byte) (movement - Logic.map[location.x + 1][location.y].terrain
										.getMovementCost(Logic.unit1MovementType)),
								RIGHT_ROOT);
					}
				}
			}
		}
	}

	/**
	 * Returns a HashSet containing the GridCells inside, in a cross-like area
	 * of the specified size on the map, starting from one center location
	 * (i.e., one GridCell object on the map).
	 * 
	 * Used for calculating the range of indirect units. Can also be used to
	 * calulate the vision of a unit in Fog of War.
	 * 
	 * @param centerLocation
	 *            The center of the cross-like area
	 * @param size
	 *            The size (like max length) of the cross-like area
	 * @param map
	 *            The map, a bidimensional array of GridCell objects
	 * @return crossArea, a HashSet containing the GridCell objects inside such
	 *         area.
	 */
	public static Set<GridCell> getCrossArea(GridCell centerLocation,
			byte size, GridCell[][] map) {
		Logic.map = map;
		Logic.crossArea = new HashSet<GridCell>();
		// First, add the center of the cross to the Set
		Logic.crossArea.add(centerLocation);
		// Then, get the middle line
		Logic.addLeft(centerLocation, size);
		Logic.addRight(centerLocation, size);
		// Then, go up and down
		Logic.addUp(centerLocation, size);
		Logic.addDown(centerLocation, size);
		return Logic.crossArea;
	}

	/**
	 * Returns the killzone of an Indirect-attack unit, calculated by
	 * calculating a difference between two sets, as defined by the getCrossArea
	 * method. The sizes of each cross are the maximum and minimum range of the
	 * indirect unit, respectively.
	 * 
	 * @param centerLocation
	 * @param map
	 *            - The map
	 * @param u
	 *            - The indirect unit of whom the killzone wants to be known.
	 * @return A Set containing all the GridCells within the killzone.
	 */
	public static LinkedHashSet<GridCell> getIndirectDeathZOne(
			GridCell centerLocation, GridCell[][] map, Unit u) {
		byte maxSize = Unit.getMaxIndirectRange(u);
		byte minSize = Unit.getMinIndirectRange(u);
		LinkedHashSet<GridCell> difference = new LinkedHashSet<GridCell>(
				Logic.getCrossArea(centerLocation, maxSize, map));
		difference.removeAll(Logic.getCrossArea(centerLocation, minSize, map));
		return difference;
	}

	/**
	 * This method returns a Set of GridCell objects, representing the kill zone
	 * of a direct-attack unit. The method iterates through the movement radius
	 * collection of DijkstraElement objects, and adds the VNN neighbors of all
	 * empty GridCells.
	 * 
	 * @param map
	 * @param u
	 * @return
	 */
	public static LinkedHashSet<GridCell> getDirectDeathZone(GridCell[][] map,
			Unit u) {
		// This method returns a list of DijkstraElements
		// A DijkstraElement is necessary for the A* algorithm
		// It wraps a GridCell object inside another object containing a weight
		// value relative to the A* algorithm
		// The thing to do next is iterate through the list of DijkstraElements
		// as we add that GridCell to the deathzone set
		// As well as adding the VNN on each gridCell that has not an allied
		// unit occupying it.

		LinkedList<DijkstraElement> list = Logic
				.calculateMovementRadiusUsingAStar(u, map);
		
		//DEBUG
		//System.out.println("No. of DijkstraElement objects on Direct Movement Radius : " + list.size());

		// Set containing the killing zone GridCells
		LinkedHashSet<GridCell> killZone = new LinkedHashSet<GridCell>();

		// Iterating through the DijkstraElements
		for (DijkstraElement d : list) {
			// Add VNN of any gridCell on which the unit can move
			// A unit can only move for attack unto empty gridCells
			if (d.gridCell.unit == null) {
				killZone.addAll(Logic.getVNN(d.gridCell, map));
			}
			//Add the GridCells from the original location as well
			killZone.addAll(Logic.getVNN(list.getFirst().gridCell, map));
		}
		return killZone;
	}

	public static LinkedHashSet<GridCell> getDeathZone(GridCell[][] map, Unit u) {
		Logic.getDeathZone = true;
		if (u.isDirect(u.unitType)) {
			return Logic.getDirectDeathZone(map, u);
		} else {
			return Logic.getIndirectDeathZOne(u.location, map, u);
		}
	}

	/**
	 * Returns a Set containing the GridCell objects located up, down, left and
	 * right of the center location sent as parameter
	 * 
	 * @param location
	 * @param map
	 * @return
	 */
	public static Set<GridCell> getVNN(GridCell location, GridCell[][] map) {
		HashSet<GridCell> set = new HashSet<GridCell>();
		// Check if up exists
		if (location.y > 0) { // Not on the upper border of the map
			set.add(map[location.x][location.y - 1]);
		}
		// Check if down exists
		if (location.y < map[0].length - 1) { // Not on the bottom border of the
												// map
			set.add(map[location.x][location.y + 1]);
		}
		// Check if left exists
		if (location.x > 0) { // Not on the left border of the map
			set.add(map[location.x - 1][location.y]);
		}
		// Check if right exists
		if (location.x < Logic.map.length - 1) { // Not on the right border of
													// the map
			set.add(map[location.x + 1][location.y]);
		}
		return set;
	}

	private static void addLeft(GridCell location, byte size) {
		// If not on the limit size
		if (size > 0) {
			// If not on the left border of the grid
			if (location.x > 0) {
				// Add the GridCell to the left of the current location
				Logic.crossArea.add(Logic.map[location.x - 1][location.y]);
				// Apply same operation on the added GridCell
				// to continue the search to the left
				// DEBUG
				Logic.iterations++;
				//System.out.println("Iterations: " + Logic.iterations);
				Logic.addLeft(Logic.map[location.x - 1][location.y],
						(byte) (size - 1));
			}
		}
	}

	private static void addRight(GridCell location, byte size) {
		if (size > 0) {
			if (location.x < Logic.map.length - 1) { // Not on the right border
														// of the map
				// Add the GridCell to the right of the current location
				Logic.crossArea.add(Logic.map[location.x + 1][location.y]);
				// Apply same operation on the added GridCell
				// to continue the search to the right
				// DEBUG
				Logic.iterations++;
				//System.out.println("Iterations: " + Logic.iterations);
				Logic.addRight(Logic.map[location.x + 1][location.y],
						(byte) (size - 1));
			}
		}
	}

	private static void addUp(GridCell verticalCenter, byte size) {
		if (size > 0) {
			if (verticalCenter.y > 0) { // Not on the upper border of the map
				/*
				 * * Add the upper GridCell above the center, making it the
				 * center of the next new Line, that will be formed by the
				 * expansions to the left and right made by the methods addLeft
				 * and addRight
				 */
				// DEBUG
				Logic.iterations++;
				//System.out.println("Iterations: " + Logic.iterations);
				Logic.crossArea
						.add(Logic.map[verticalCenter.x][verticalCenter.y - 1]);
				// Search left and right across the X axis, creating a line
				// around the
				// verticalCenter location on the upper level
				Logic.addLeft(
						Logic.map[verticalCenter.x][verticalCenter.y - 1],
						(byte) (size - 1));
				Logic.addRight(
						Logic.map[verticalCenter.x][verticalCenter.y - 1],
						(byte) (size - 1));

				// Continue going up, decreasing the size of each line at a time
				Logic.addUp(Logic.map[verticalCenter.x][verticalCenter.y - 1],
						(byte) (size - 1));
			}
		}
	}

	private static void addDown(GridCell verticalCenter, byte size) {
		if (size > 0) {
			if (verticalCenter.y < Logic.map[0].length - 1) { // Not on the
																// bottom border
																// of the map
				/*
				 * Add the down GridCell above the center, making it the center
				 * of the next new Line, that will be formed by the expansions
				 * to the left and right made by the methods addLeft and
				 * addRight
				 */
				Logic.crossArea
						.add(Logic.map[verticalCenter.x][verticalCenter.y + 1]);
				// Search left and right across the X axis, creating a line
				// around the
				// verticalCenter location on the down level
				Logic.addLeft(
						Logic.map[verticalCenter.x][verticalCenter.y + 1],
						(byte) (size - 1));
				Logic.addRight(
						Logic.map[verticalCenter.x][verticalCenter.y + 1],
						(byte) (size - 1));

				// Continue going down, decreasing the size of each line at a
				// time
				Logic.addDown(
						Logic.map[verticalCenter.x][verticalCenter.y + 1],
						(byte) (size - 1));
			}
		}
	}

	public static byte getDefenseStars(Unit u) {
		return u.location.terrain.defenseStars;
	}

	public static int[] getAttackingBonusesAndPenalties(Unit attacker) {

		// Position1 : attack
		// Position2 : attackP
		int a[] = new int[2];

		// attack bonus of the attacking unit
		// Logic.attack1 = -1;
		a[0] = -1;
		// int defense = 0;
		// Logic.attackP1 = -1;
		a[1] = -1;
		// int defensePower = 0;

		// CO and Power general bonuses for all units
		switch (attacker.player.currentCO.id) {
		case CO.ANDY: // Hyper repair
			if (attacker.player.hasSuperPowerOn) {
				// Logic.attack1 = 20;
				a[1] = 20;
			}
			break;
		case CO.COLIN: // Power of Money
			// Logic.attack1 = -10;
			a[0] = -10;
			if (attacker.player.hasSuperPowerOn) {
				a[0] = ((100 + attacker.player.funds / 300) * 9 / 10) - 100;
			}
			break;
		case CO.KANBEI: // Normal Kanbei
			a[0] = 30;
			// Kanbei, Morale Boost, Samurai Spirit
			if (attacker.player.hasNoPower == false) {
				a[1] = 20;
			}
			break;
		case CO.LASH:
			// Lash Normal
			a[0] = attacker.location.terrain.defenseStars * 10;
			// Lash: Prime Tactics
			if (attacker.player.hasSuperPowerOn) {
				a[1] = attacker.location.terrain.defenseStars * 10;
			}
			break;
		case CO.HAWKE:
			a[0] = 10;
			break;
		case CO.STURM:
			a[0] = 20;
			if (attacker.player.hasNoPower = false) {
				a[1] = 20;
			}
			break;
		case CO.KINDLE:
			if (attacker.location.isProperty) {
				a[0] = 40;
				if (attacker.player.hasPowerOn) {
					a[1] = 50;
				}
				if (attacker.player.hasSuperPowerOn) {
					a[1] = 90;
				}
			} else {
				if (attacker.player.hasNoPower == false) {
					a[1] = 10;
				}
			}
			break;
		case CO.KOAL:
			if (attacker.location.terrain.terrainType == Terrain.ROAD) {
				a[0] = 10;
				if (attacker.player.hasPowerOn) {
					a[1] = 20;
				}
				if (attacker.player.hasSuperPowerOn) {
					a[1] = 30;
				}
			} else {
				if (attacker.player.hasNoPower == false) {
					a[1] = 10;
				}
			}
			break;
		}
		// Unit type specific bonuses
		// Inftry class
		if (Unit.isInftry(unit1Movement)) {
			switch (attacker.player.currentCO.id) {
			case CO.SAMI:
				a[0] = 30;
				// Double Time
				if (attacker.player.hasPowerOn) {
					a[1] = 20;
				}
				// Victory March
				if (attacker.player.hasSuperPowerOn) {
					a[1] = 50;
				}
				break;
			case CO.SENSEI:
				a[0] = 40;
				break;
			case CO.JESS:
				a[0] = -10;
			}
		}
		// Direct Vehicle class
		if (Unit.isVehicle(attacker.unitType)
				&& Unit.isDirect(attacker.unitType)) {
			switch (attacker.player.currentCO.id) {
			case CO.SAMI:
				a[0] = -10;
				break;
			case CO.MAX:
				a[0] = 20;
				if (attacker.player.hasPowerOn) {
					a[1] = 20;
				}
				if (attacker.player.hasSuperPowerOn) {
					a[1] = 40;
				}
				break;
			case CO.GRIT:
				a[0] = -20;
				break;
			case CO.SENSEI:
				a[0] = -10;
				break;
			case CO.JESS:
				a[0] = 10;
				if (attacker.player.hasPowerOn) {
					a[1] = 20;
				}
				if (attacker.player.hasSuperPowerOn) {
					a[1] = 40;
				}
				break;
			}
		}
		// Indirect vehicle class
		if (Unit.isIndirect(attacker.unitType)
				&& Unit.isVehicle(attacker.unitType)) {
			switch (attacker.player.currentCO.id) {
			case CO.MAX:
				a[0] = -10;
				break;
			case CO.GRIT:
				a[0] = 20;
				if (attacker.player.hasNoPower == false) {
					// DEBUG
					System.out.println("Bonus 30% attack for Grit");
					a[1] = 30;
				}
				break;
			case CO.SENSEI:
				a[0] = -10;
				break;
			case CO.JESS:
				a[0] = 10;
				if (attacker.player.hasPowerOn) {
					a[1] = 20;
				}
				if (attacker.player.hasSuperPowerOn) {
					a[1] = 40;
				}
				break;
			}
		}
		// Battle Copters
		if (attacker.unitType == Unit.B_COPTER) {
			switch (attacker.player.currentCO.id) {
			case CO.SAMI:
				a[0] = -10;
				break;
			case CO.MAX:
				a[0] = 20;
				if (attacker.player.hasPowerOn) {
					a[1] = 20;
				}
				if (attacker.player.hasSuperPowerOn) {
					a[1] = 40;
				}
				break;
			case CO.GRIT:
				a[0] = -20;
				break;
			case CO.SENSEI:
				a[0] = 50;
				if (attacker.player.hasNoPower == false) {
					a[1] = 25;
				}
				break;
			case CO.EAGLE:
				a[0] = 15;
				if (attacker.player.hasNoPower == false) {
					a[1] = 15;
				}
				break;
			case CO.DRAKE:
				a[0] = -30;
				break;
			case CO.JESS:
				a[0] = -10;
				break;
			}
		}
		// Plane Class
		if (Unit.isPlane(attacker.unitType)) {
			switch (attacker.player.currentCO.id) {
			case CO.SAMI:
				a[0] = -10;
				break;
			case CO.MAX:
				a[0] = 20;
				if (attacker.player.hasPowerOn) {
					a[1] = 20;
				}
				if (attacker.player.hasSuperPowerOn) {
					a[1] = 40;
				}
				break;
			case CO.GRIT:
				a[0] = -20;
				break;
			case CO.EAGLE:
				a[0] = 15;
				if (attacker.player.hasNoPower == false) {
					a[1] = 15;
				}
				break;
			case CO.DRAKE:
				a[0] = -30;
				break;
			case CO.JESS:
				a[0] = -10;
				break;
			}
		}
		// Direct Sea Units
		if (Unit.isDirect(attacker.unitType) && Unit.isSea(attacker.unitType)) {
			switch (attacker.player.currentCO.id) {
			case CO.SAMI:
				a[0] = -10;
				break;
			case CO.MAX:
				a[0] = 20;
				if (attacker.player.hasPowerOn) {
					a[1] = 20;
				}
				if (attacker.player.hasSuperPowerOn) {
					a[1] = 40;
				}
				break;
			case CO.GRIT:
				a[0] = -20;
				break;
			case CO.EAGLE:
				a[0] = -30;
				break;
			case CO.JESS:
				a[0] = -10;
				break;
			}
		}
		// Indirect Sea Units
		if (Unit.isIndirect(attacker.unitType) && Unit.isSea(attacker.unitType)) {
			switch (attacker.player.currentCO.id) {
			case CO.MAX:
				a[0] = -10;
				break;
			case CO.GRIT:
				a[0] = 20;
				// DEBUG
				if (attacker.player.hasNoPower == false) {
					System.out
							.println("Bonus 30% attack on Battleships for Grit");
					a[1] = 30;
				}
				break;
			case CO.EAGLE:
				a[0] = -30;
				break;
			case CO.JESS:
				a[0] = -10;
				break;
			}
		}
		// DEBUG
//		System.out.println("Attack1:" + a[0]);
//		System.out.println("AttackP1:" + a[1]);
		return a;
	}

	private static int[] getDefensiveBonusesAndPenalties(Unit defender) {
		int d[] = new int[3];
		// defense2
		d[0] = 0;
		// defenseP2
		d[1] = 0;
		// defenseStars: terrainD2
		d[2] = 0;

		// Set terrain defenseStars for defender
		d[2] = defender.location.terrain.defenseStars;
		if (defender.player.hasNoPower == false) {
			// if anyone has any power, 110% of defense
			switch (defender.player.currentCO.id) {
			case CO.ANDY:
			case CO.MAX:
			case CO.SAMI:
			case CO.NELL:
			case CO.HACHI:
			case CO.OLAF:
			case CO.GRIT:
			case CO.COLIN:
			case CO.SONJA:
			case CO.SENSEI:
			case CO.JESS:
			case CO.FLAK:
			case CO.LASH:
			case CO.ADDER:
			case CO.HAWKE:
			case CO.KINDLE:
				d[1] = 10;
				break;
			}
			if (defender.player.currentCO.id == CO.LASH
					&& defender.player.hasSuperPowerOn) {
				// Double defense for Lash w/ Prime Tactics
				d[2] = (defender.location.terrain.defenseStars * 2);
			}
			if (defender.player.currentCO.id == CO.KINDLE) {
			}
			// Defensive bonuses and penalties, CO specific
			switch (defender.player.currentCO.id) {
			case CO.KANBEI:
				d[0] = 30;
				if (defender.player.hasPowerOn) {
					d[1] = 10;
				}
				if (defender.player.hasSuperPowerOn) {
					d[1] = 30;
				}
				break;
			case CO.EAGLE:
				if (Unit.isAir(defender.unitType)) {
					d[0] = 10;
					if (defender.player.hasNoPower == false) {
						d[1] = 20;
					} else {
						d[1] = 10;
					}
				}
				break;
			case CO.DRAKE:
				if (Unit.isSea(defender.unitType)) {
					d[0] = 10;
					if (defender.player.hasNoPower == false) {
						d[1] = 20;
					} else {
						d[1] = 10;
					}
				}
				break;
			case CO.STURM:
				d[0] = 20;
				if (defender.player.hasNoPower == false) {
					d[1] = 30;
				}
				break;
			}
		}
		// No defense for Air units
		if (defender.isAir(defender.unitType)) {
			// DEBUG
			//System.out.println("Defender is AIR");
			d[2] = 0;
		}
		// DEBUG
//		System.out.println("Defense2:" + d[0]);
//		System.out.println("DefenseP2:" + d[1]);
//		System.out.println("DefenseStars for P2:" + d[2]);
		return d;
	}

	/**
	 * Calculates the damage inflicted by an attacking unit to a defending unit,
	 * taking into account all aspects of combat, such as HP, terrain, ammo,
	 * primary or secondary weapon. The damage does not account for
	 * counterattack. This reflects de estimated damage that pops on the GBA
	 * game, when about to engage another unit.
	 * 
	 * @param attacker
	 * @param defender
	 * @param a
	 * @param d
	 * @return
	 */
	public static double calculateDamage(Unit attacker, Unit defender, int a[],
			int d[]) {
		double damage = Math.floor(Unit.getDamageByChart(attacker, defender)
				* (100
				// -Defense for-each hp-defender
				// + standard bonus defense + Power/Super Defense
				- ((d[2] * defender.currentHP) + d[0] + d[1]))
				* (attacker.currentHP / 10) //
				* (100 + a[0] + a[1]) / 10000);
		// DEBUG
		//System.out.println("Damage without luck: " + damage);
		return damage;
	}

	/**
	 * The luck filter receives a damage value, previously calculated on
	 * calculateDamage method, and returns a new damage value, corrected for
	 * luck of the attacking CO and randomness. This is the final damage value
	 * that must be substracted from the defender's HP, such as 'new Defender HP
	 * = Defender HP - (damage/10)'
	 * 
	 * @param attacker
	 *            The attacking unit
	 * @param damage
	 *            A damage value, accounted for CO luck and randomness
	 * @return
	 */
	public static double luckFilter(Unit attacker, double damage) {
		switch (attacker.player.currentCO.id) {
		case CO.NELL:
			if (attacker.player.hasNoPower) {
				damage += (11 * Math.random());
			}
			if (attacker.player.hasPowerOn) {
				damage += (25 * Math.random());
			}
			if (attacker.player.hasSuperPowerOn) {
				damage += (45 * Math.random());
			}
			break;
		case CO.SONJA:
			damage -= (5 * Math.random());
			break;
		case CO.FLAK:
			if (attacker.player.hasNoPower) {
				damage += (-5 + 11 * Math.random());
			}
			if (attacker.player.hasSuperPowerOn) {
				damage += (-15 + 30 * Math.random());
			}
			if (attacker.player.hasSuperPowerOn) {
				damage += (-30 + 60 * Math.random());
			}
			break;
		}
		if (damage < 0) {
			damage = 0;
		}
		double tempdamage = damage;
		double randomness;
		if (damage % 10 > 0) {
			randomness = Math.random() * 10;
			if (randomness > (damage % 10)) {
				tempdamage = Math.floor(damage / 10) * 10;
			}
			if (randomness < (damage % 10)) {
				tempdamage = Math.ceil(damage / 10) * 10;
			}
		}
		// DEBUG
		//System.out.println("Damage with luck: " + tempdamage);
		return tempdamage;
	}

	/**
	 * This method is responsible of executing all the combat operations
	 * involving an attacking and a defending unit. The combat mechanics involve
	 * damage calculation inflicted on the defender by the attacker, passing
	 * that value through the luck filter, substracting the resulting damage to
	 * the defender HP.
	 * 
	 * NOTE that counterattack is also to be calculated, only if: a) The
	 * attacker is not indirect AND b) The defender is not indirect (a defending
	 * indirect can't counterattack) AND c) The defender can actually engage its
	 * attacker (as known by invoking the getEngagingWeapon method on the Unit
	 * class) AND the defender was not wiped out by the initial damage
	 * calculation.
	 * 
	 * To calculate the counterattack, now the defender is the attacker, with
	 * its new HP value, and the attacker becomes the defender, with its
	 * original HP value.
	 * 
	 * Also, ammunition must be substracted if the attacker and/or the defender
	 * used their primary weapon during combat.
	 * 
	 * This method modifies the Unit objects by reference. If a unit or units
	 * result destroyed as a result of combat, be sure to TAKE ALL MEASURES to
	 * properly REMOVE THE UNIT FROM THE GAME.
	 * 
	 * @param attacker
	 *            The attacking unit engaged in combat.
	 * @param defender
	 *            The defending unit engaged in combat.
	 * 
	 * @return The damage inflicted from the attacker to the defender.
	 */
	public static short combat(Unit attacker, Unit defender, boolean luck) {
		// CHECK FOR SONJA's condition for COUNTER BREAK BONUS: attack first
		// when defending.

		/*
		 * IF THE DEFENDER IS SONJA, and SUPERPOWER IS ON: "COUNTER BREAK" IF
		 * SONJA'S UNIT (defender) IS DIRECT IF SONJA UNIT (defender) CAN ENGAGE
		 * ATTACKING UNIT: //THE DEFENDER BECOMES THE ATTACKER. INVERT ROLES AND
		 * CALCULATE DAMAGE AS USUAL: - DEFENDER BECOMES ATTACKER. - ATTACKER
		 * BECOMES DEFENDER. According to the rules established, it
		 * hypotetically shoud be right ELSE Proceed as ussual, as indirects
		 * can't counterattack
		 */

		// DEBUG: STATS
//		System.out.println("Attacker: " + attacker.toString());
//		System.out.println("Defender: " + defender.toString());

		// Check for Sonja
		if (defender.player.currentCO.id == CO.SONJA
				&& defender.player.hasSuperPowerOn
				&& Unit.isDirect(defender.unitType)
				&& Unit.getEngagingWeapon(defender, attacker) != Unit.CANT_ENGAGE) {
			// COUNTER BREAK: SONJA attacks first when attacked
			Unit temp = attacker;
			attacker = defender;
			defender = temp;
		}
		// SUBSTRACT AMMO
		switch (Unit.getEngagingWeapon(attacker, defender)) {
		case Unit.PRIMARY_WEAPON:
			attacker.currentAmmo--;
		case Unit.SECONDARY_WEAPON:
			// Calculate new HP for defender
			double damageDealt = Logic.calculateDamage(attacker, defender,
					Logic.getAttackingBonusesAndPenalties(attacker),
					Logic.getDefensiveBonusesAndPenalties(defender));
			if (luck) {
				damageDealt = (short) (Logic.luckFilter(attacker, damageDealt) / 10);
			} else {
				damageDealt = damageDealt / 10;
				// DEBUG
				//System.out.println("Damage adjusted for HP: " + damageDealt);
			}
			damageDealt = (short) Math.round(damageDealt);
			double defenderHP = defender.currentHP - (damageDealt);

			// DEBUG
//			System.out.println("Damage dealt to defender(double): "
//					+ damageDealt);
//			System.out.println("Defender HP after attack (double): "
//					+ defenderHP);

			if (defenderHP < 0) {
				defenderHP = 0;
			} else {
				// If counterattack is possible
				// DEBUG
				//System.out.println("Start counterattack calculation");
				if (Unit.isDirect(attacker.unitType)
						&& Unit.isDirect(defender.unitType)
						&& Unit.getEngagingWeapon(defender, attacker) != Unit.CANT_ENGAGE) {

					// SUBSTRACT AMMO
					if (Unit.getEngagingWeapon(defender, attacker) == Unit.PRIMARY_WEAPON) {
						defender.currentAmmo--;
					}

					// Calculate basic counterattack: Inversed parameters for
					// normal attack
					double counterDamage = Logic.calculateDamage(defender,
							attacker,
							Logic.getAttackingBonusesAndPenalties(defender),
							Logic.getDefensiveBonusesAndPenalties(attacker));
					// Check for counterattack related bonuses
					switch (defender.player.currentCO.id) {
					case CO.SONJA:
						// 150% counterattack day-to-day ability
						counterDamage *= 1.5;
					case CO.KANBEI:
						if (attacker.player.hasSuperPowerOn) {
							// Counterattack bonus for Samurai Spirit
							counterDamage *= 1.5;
						}
					}
					if (luck) {
						counterDamage = Logic.luckFilter(defender,
								counterDamage) / 10;
					} else {
						counterDamage = Math.round(counterDamage / 10);
					}
					double attackerHP = attacker.currentHP - counterDamage;
					if (attackerHP < 0) {
						// Attacker was destroyed
						attacker.currentHP = 0;
					}
					attacker.currentHP = (byte) attackerHP;
				}
			}
			defender.currentHP = (byte) defenderHP;
//			// DEBUG
//			System.out.println("Attacker HP after combat (byte): "
//					+ attacker.currentHP);
//			System.out.println("Defender HP after combat (byte): "
//					+ defender.currentHP);

			return (short) damageDealt;
		case Unit.CANT_ENGAGE:
		default:
			return 0;
		}
		// MISSING: Destroy UNIT IF DEFEATED
		// ALSO: THIS METHOD COULD RETURN AN OUTCOME OBJECT, LIKE A SIZE 2
		// ARRAY, with OWN and ENEMY HPs stored
		// to be used on the search tree
		// ALSO
		// TAKE INTO ACCOUNT THE DISTINCT CRITERIA FOR THE EVALUATION FUNCTION;
		// INSTEAD OF JUST ONE
		// To add it as an additional dimension of the study
		// taking into account that each eval function renders different search
		// tree outcomes
	}

	/**
	 * This method engages clones of an attacking and defending unit, sent as
	 * parameters, to estimate the outcome of a battle. It returns a size-3
	 * array, representing the outcome of a combat as: 0) (Enemy) Defender HP
	 * after combat, 1) (Own) Attacker HP after combat, and 2) The damage dealt
	 * by the attacker to the defender, expresed in percentage.
	 * 
	 * @param attacker
	 * @param defender
	 * @return
	 */
	public static short[] combatSimulation(Unit attacker, Unit defender) {
		// Cloning units
		Unit a = Unit.cloneUnit(attacker);
		Unit d = Unit.cloneUnit(defender);
		// Engaging in "simulated" combat
		short damage = combat(a, d, false);
		// Take HP
		byte dHP = d.currentHP;
		byte aHP = a.currentHP;
		// Kill clones, then call garbage collector if necessary
		a = null;
		d = null;
		// Returning the outcome
		return new short[] { dHP, aHP, damage };
	}

	public static LinkedList<DijkstraElement> calculateMovementRadiusUsingAStar(Unit unit, GridCell map[][]) {
        
        Logic.iterations = 0;
        Logic.unit1 = unit;
        Logic.unit1.unitType = unit.unitType;
        Logic.unit1Movement = Unit.getMovement(Logic.unit1);
        Logic.unit1MovementType = Unit.getMovementType(Logic.unit1.unitType);
        Logic.unit1.location = unit.location;
        Logic.map = map;
        startAStar(unit.location, Unit.getMovement(Logic.unit1));
        
        //DEBUG
        //System.out.println("Movement radius of unit: " + Unit.getMovement(Logic.unit1));
    	//System.out.println("No. of GridCell objects on MovementRadius: " + Logic.closedList.size());
        
        return Logic.closedList;
    }

	private static void startAStar(GridCell location, byte movement) {
		// openList = new TreeMap <Integer, DijkstraElement> (new
		// ComparatorDijkstraElement());
		openList = new LinkedList<DijkstraElement>();
		closedList = new LinkedList<DijkstraElement>();
		DijkstraElement current = new DijkstraElement(location, movement);
		// Add current to openList
		openList.add(current);
		// DEBUG
		// System.out.println("Added to openlist: " + current.toString());
		do {
			if (openList.isEmpty()) {
				// DEBUG
				// System.out.println("OPEN LIST IS EMPTY!!!");
				break;
			}
			Logic.pendingOpenList = new LinkedList<DijkstraElement>();
			DijkstraElement temp;
			// Remove current from openList
			temp = openList.getFirst();
			openList.remove(temp);
			// DEBUG
			// System.out.println("Removed from openlist: " + temp.toString());
			// add to closedList
			closedList.add(temp);
			// DEBUG
			// System.out.println("Added to closedlist: " + temp.toString());
			// Check VNN. If transversable, add.
			Logic.checkVNN(temp);

			// Sort pendingOpenList before adding it's elements to openList
			Collections.sort(pendingOpenList);
			Logic.openList.addAll(pendingOpenList);
		} while (true);
	}

	private static void checkVNN(DijkstraElement center) {
		Logic.checkUp(center);
		Logic.checkLeft(center);
		Logic.checkDown(center);
		Logic.checkRight(center);

	}

	private static void checkUp(DijkstraElement center) {
		Logic.iterations++;
		GridCell location = center.gridCell;
		if (location.y > 0) { // Not on the upper border of the map
			// If it is the original GridCell, from where the unit is standing
			if (Logic.map[location.x][location.y - 1]
					.equals(Logic.unit1.location)) {
				// Add nothing, as source GridCell can't be a destiny
				return;
			}
			
			// If there is an enemy or friend unit on the destination
			if (Logic.map[location.x][location.y - 1].unit != null) {
				// If the unit is not an ally one
				if (Logic.map[location.x][location.y - 1].unit.player.team != Logic.unit1.player.team) {
					// Add nothing and return, as this GridCell is not
					// transversable
					return;
				}
			}
			// If the unit can transverse the upper gridCell
			if (Logic.map[location.x][location.y - 1].terrain
					.getCanTransverse(Logic.unit1MovementType)) {
				// Calculate if the unit has enough movement points to
				// transverse the upper gridCell
				if (center.movementPointsLeft
						- Logic.map[location.x][location.y - 1].terrain
								.getMovementCost(Logic.unit1MovementType) >= 0) {

					// System.out.println("La diferencia es: " +
					// (center.movementPointsLeft
					// - Logic.map[location.x][location.y -
					// 1].terrain.getMovementCost(Logic.unit1MovementType)));

					// System.out.println("Movement still is: " +
					// center.movementPointsLeft);
					// System.out.println("Adding new (" + location.x + "," +
					// (location.y - 1) + ")");

					//
					DijkstraElement d = new DijkstraElement(
							Logic.map[location.x][location.y - 1],
							(byte) (center.movementPointsLeft - Logic.map[location.x][location.y - 1].terrain
									.getMovementCost(Logic.unit1MovementType)));

					if (Logic.closedList.contains(d)) {
						// DEBUG
						// System.out.println("RECALCULATING with " +
						// d.toString());
						DijkstraElement old = Logic.closedList
								.get(Logic.closedList.indexOf(d));

						if (d.movementPointsLeft > old.movementPointsLeft) {
							Logic.closedList.remove(old);
							Logic.pendingOpenList.add(d);
						} else {
							// Already on closed List. New not better than old
							return;
						}
					}
					if (Logic.openList.contains(d)) {
						// DEBUG
						// System.out.println("Already on OPEN LIST: " +
						// d.toString());
						// System.out.println("RECALCULATING with " +
						// d.toString());
						DijkstraElement old = Logic.openList.get(Logic.openList
								.indexOf(d));
						if (d.movementPointsLeft > old.movementPointsLeft) {
							Logic.openList.remove(old);
							Logic.pendingOpenList.add(d);
						} else {
							// Already on open List. New not better than old
							return;
						}
					} else {
						// DEBUG
						// System.out.println("Added to PENDING openlist: " +
						// d.toString());
						Logic.pendingOpenList.add(d);
					}
					// Logic.movementRadius.add(Logic.map[location.x][location.y
					// - 1]);
				}
			}
		}

	}

	private static void checkLeft(DijkstraElement center) {
		Logic.iterations++;
		GridCell location = center.gridCell;
		if (location.x > 0) { // Not on the left border of the map
			// If it is the original GridCell, from where the unit is standing
			if (Logic.map[location.x - 1][location.y]
					.equals(Logic.unit1.location)) {
				// Add nothing, as source GridCell can't be a destiny
				return;
			}
			// If there is an enemy or friend unit on the destination
			if (Logic.map[location.x - 1][location.y].unit != null) {
				// If the unit is not an ally one
				if (Logic.map[location.x - 1][location.y].unit.player.team != Logic.unit1.player.team) {
					// Add nothing and return, as this GridCell is not
					// transversable
					return;
				}
			}
			// If the unit can transverse the gridCell to the left
			if (map[location.x - 1][location.y].terrain
					.getCanTransverse(Logic.unit1MovementType)) {

				// Calculate if the unit has enough movement points to
				// transverse the upper gridCell
				if (center.movementPointsLeft
						- Logic.map[location.x - 1][location.y].terrain
								.getMovementCost(Logic.unit1MovementType) >= 0) {

					// System.out.println("La diferencia es: " +
					// (center.movementPointsLeft
					// - Logic.map[location.x -
					// 1][location.y].terrain.getMovementCost(Logic.unit1MovementType)));

					// System.out.println("Movement still is: " +
					// center.movementPointsLeft);
					// System.out.println("Adding new (" + (location.x - 1) +
					// "," + location.y + ")");

					//
					DijkstraElement d = new DijkstraElement(
							Logic.map[location.x - 1][location.y],
							(byte) (center.movementPointsLeft - Logic.map[location.x - 1][location.y].terrain
									.getMovementCost(Logic.unit1MovementType)));
					if (Logic.closedList.contains(d)) {
						// DEBUG
						// System.out.println("RECALCULATING with " +
						// d.toString());
						DijkstraElement old = Logic.closedList
								.get(Logic.closedList.indexOf(d));

						if (d.movementPointsLeft > old.movementPointsLeft) {
							Logic.closedList.remove(old);
							Logic.pendingOpenList.add(d);
						} else {
							// Already on closed List. New not better than old
							return;
						}
					}
					if (Logic.openList.contains(d)) {
						// DEBUG
						// System.out.println("Already on OPEN LIST: " +
						// d.toString());
						// System.out.println("RECALCULATING with " +
						// d.toString());
						DijkstraElement old = Logic.openList.get(Logic.openList
								.indexOf(d));
						if (d.movementPointsLeft > old.movementPointsLeft) {
							Logic.openList.remove(old);
							Logic.pendingOpenList.add(d);
						} else {
							// Already on open List. New not better than old
							return;
						}
					} else {
						// DEBUG
						// System.out.println("Added to PENDING openlist: " +
						// d.toString());
						Logic.pendingOpenList.add(d);
					}
					// Logic.movementRadius.add(Logic.map[location.x -
					// 1][location.y]);
				}
			}
		}
	}

	private static void checkDown(DijkstraElement center) {
		Logic.iterations++;
		GridCell location = center.gridCell;
		if (location.y < Logic.map[0].length - 1) { // Not on the bottom border
													// of the map
			// If it is the original GridCell, from where the unit is standing
			if (Logic.map[location.x][location.y + 1]
					.equals(Logic.unit1.location)) {
				// Add nothing, as source GridCell can't be a destiny
				return;
			}
			// If there is an enemy or friend unit on the destination
			if (Logic.map[location.x][location.y + 1].unit != null) {
				// If the unit is not an ally one
				if (Logic.map[location.x][location.y + 1].unit.player.team != Logic.unit1.player.team) {
					// Add nothing and return, as this GridCell is not
					// transversable
					return;
				}
			}
			// If the unit can transverse the gridCell down
			if (map[location.x][location.y + 1].terrain
					.getCanTransverse(Logic.unit1MovementType)) {

				// Calculate if the unit has enough movement points to
				// transverse the upper gridCell
				if (center.movementPointsLeft
						- Logic.map[location.x][location.y + 1].terrain
								.getMovementCost(Logic.unit1MovementType) >= 0) {

					// System.out.println("La diferencia es: " +
					// (center.movementPointsLeft
					// - Logic.map[location.x][location.y +
					// 1].terrain.getMovementCost(Logic.unit1MovementType)));

					// System.out.println("Movement still is: " +
					// center.movementPointsLeft);
					// System.out.println("Adding new (" + location.x + "," +
					// (location.y + 1) + ")");

					//
					DijkstraElement d = new DijkstraElement(
							Logic.map[location.x][location.y + 1],
							(byte) (center.movementPointsLeft - Logic.map[location.x][location.y + 1].terrain
									.getMovementCost(Logic.unit1MovementType)));
					if (Logic.closedList.contains(d)) {
						// DEBUG
						// System.out.println("RECALCULATING with " +
						// d.toString());
						DijkstraElement old = Logic.closedList
								.get(Logic.closedList.indexOf(d));

						if (d.movementPointsLeft > old.movementPointsLeft) {
							Logic.closedList.remove(old);
							Logic.pendingOpenList.add(d);
						} else {
							// Already on closed List. New not better than old
							return;
						}
					}
					if (Logic.openList.contains(d)) {
						// DEBUG
						// System.out.println("Already on OPEN LIST: " +
						// d.toString());
						// System.out.println("RECALCULATING with " +
						// d.toString());
						DijkstraElement old = Logic.openList.get(Logic.openList
								.indexOf(d));
						if (d.movementPointsLeft > old.movementPointsLeft) {
							Logic.openList.remove(old);
							Logic.pendingOpenList.add(d);
						} else {
							// Already on open List. New not better than old
							return;
						}
					} else {
						// DEBUG
						// System.out.println("Added to PENDING openlist: " +
						// d.toString());
						Logic.pendingOpenList.add(d);
					}
					// Logic.movementRadius.add(Logic.map[location.x][location.y
					// + 1]);
				}
			}
		}
	}

	private static void checkRight(DijkstraElement center) {
		Logic.iterations++;
		GridCell location = center.gridCell;
		if (location.x < Logic.map.length - 1) { // Not on the right border of
													// the map
			// If the unit can transverse the gridCell to the left
			if (Logic.map[location.x + 1][location.y].terrain
					.getCanTransverse(Logic.unit1MovementType)) {

				// If it is the original GridCell, from where the unit is
				// standing
				if (Logic.map[location.x + 1][location.y]
						.equals(Logic.unit1.location)) {
					// Add nothing, as source GridCell can't be a destiny
					return;
				}
				// If there is an enemy or friend unit on the destination
				if (Logic.map[location.x + 1][location.y].unit != null) {
					// If the unit is not an ally one
					if (Logic.map[location.x + 1][location.y].unit.player.team != Logic.unit1.player.team) {
						// Add nothing and return, as this GridCell is not
						// transversable
						return;
					}
				}
				// If the unit can transverse the right gridCell
				if (map[location.x + 1][location.y].terrain
						.getCanTransverse(Logic.unit1MovementType)) {
					// Calculate if the unit has enough movement points to
					// transverse the upper gridCell
					if (center.movementPointsLeft
							- Logic.map[location.x + 1][location.y].terrain
									.getMovementCost(Logic.unit1MovementType) >= 0) {

						// System.out.println("La diferencia es: " +
						// (center.movementPointsLeft
						// - Logic.map[location.x +
						// 1][location.y].terrain.getMovementCost(Logic.unit1MovementType)));

						// System.out.println("Movement still is: " +
						// center.movementPointsLeft);
						// System.out.println("Adding new (" + (location.x + 1)
						// + "," + location.y + ")");

						DijkstraElement d = new DijkstraElement(
								Logic.map[location.x + 1][location.y],
								(byte) (center.movementPointsLeft - Logic.map[location.x + 1][location.y].terrain
										.getMovementCost(Logic.unit1MovementType)));

						if (Logic.closedList.contains(d)) {
							// DEBUG
							// System.out.println("RECALCULATING with " +
							// d.toString());
							DijkstraElement old = Logic.closedList
									.get(Logic.closedList.indexOf(d));

							if (d.movementPointsLeft > old.movementPointsLeft) {
								Logic.closedList.remove(old);
								Logic.pendingOpenList.add(d);
							} else {
								// Already on closed List. New not better than
								// old
								return;
							}
						}
						if (Logic.openList.contains(d)) {
							// DEBUG
							// System.out.println("Already on OPEN LIST: " +
							// d.toString());
							// System.out.println("RECALCULATING with " +
							// d.toString());
							DijkstraElement old = Logic.openList
									.get(Logic.openList.indexOf(d));
							if (d.movementPointsLeft > old.movementPointsLeft) {
								Logic.openList.remove(old);
								Logic.pendingOpenList.add(d);
							} else {
								// Already on open List. New not better than old
								return;
							}
						} else {
							// DEBUG
							// System.out.println("Added to PENDING openlist: "
							// + d.toString());
							Logic.pendingOpenList.add(d);
						}
						// Logic.movementRadius.add(Logic.map[location.x +
						// 1][location.y]);
					}
				}
			}
		}
	}
	/*
	 * public static LinkedList<DangerGridCell>
	 * getDangerZone(LinkedList<DijkstraElement> movementList) {
	 * LinkedList<DangerGridCell> dangerZone = new LinkedList<DangerGridCell>();
	 * double danger; for (DijkstraElement d : movementList) { danger = 0; if
	 * (new Random().nextInt(2) == 0) { danger = new Random().nextGaussian() *
	 * 100; } dangerZone.add(new DangerGridCell(d.gridCell, danger)); } return
	 * dangerZone; }
	 */
}
