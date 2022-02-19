package be.rouget.puzzles.adventofcode.year2021.day23;

public enum AmphipodType {
    AMBER(LocationCode.ROOM_A, 1),
    BRONZE(LocationCode.ROOM_B, 10),
    COPPER(LocationCode.ROOM_C, 100),
    DESERT(LocationCode.ROOM_D, 1000);

    private final LocationCode targetRoom;
    private final int energyPerMove;

    AmphipodType(LocationCode targetRoom, int energyPerMove) {
        this.targetRoom = targetRoom;
        this.energyPerMove = energyPerMove;
    }

    public LocationCode getTargetRoom() {
        return targetRoom;
    }

    public int getEnergyPerMove() {
        return energyPerMove;
    }
}
