package src.data.dts.pieces;

import src.data.dts.Position;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Steps {
    public static Set<Position> basicSteps = Stream.of(
                    new Position(-1, 0),
                    new Position(1, 0),
                    new Position(0, 1),
                    new Position(0, -1))
            .collect(Collectors.toCollection(HashSet::new));
    public static Set<Position> knightSteps = Stream.of(
                    new Position(2, 1),
                    new Position(2, -1),
                    new Position(1, 2),
                    new Position(1, -2),
                    new Position(-1, 2),
                    new Position(-1, -2),
                    new Position(-2, 1),
                    new Position(-2, -1)).
            collect(Collectors.toCollection(HashSet::new));
    public static Set<Position> diagonalSteps = Stream.of(
                    new Position(-1, -1),
                    new Position(1, 1),
                    new Position(-1, 1),
                    new Position(1, -1))
            .collect(Collectors.toCollection(HashSet::new));
    public static Set<Position> fullSteps = Stream.of(
                    new Position(1, 0),
                    new Position(-1, 0),
                    new Position(0, 1),
                    new Position(0, -1),
                    new Position(1, 1),
                    new Position(1, -1),
                    new Position(-1, 1),
                    new Position(-1, -1))
            .collect(Collectors.toCollection(HashSet::new));
}
