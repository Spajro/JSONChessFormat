package chess.pieces;

import chess.Position;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Steps {
    public static Set<Position> basicSteps = Stream.of(
                    Position.of(-1, 0),
                    Position.of(1, 0),
                    Position.of(0, 1),
                    Position.of(0, -1))
            .collect(Collectors.toCollection(HashSet::new));
    public static Set<Position> knightSteps = Stream.of(
                    Position.of(2, 1),
                    Position.of(2, -1),
                    Position.of(1, 2),
                    Position.of(1, -2),
                    Position.of(-1, 2),
                    Position.of(-1, -2),
                    Position.of(-2, 1),
                    Position.of(-2, -1)).
            collect(Collectors.toCollection(HashSet::new));
    public static Set<Position> diagonalSteps = Stream.of(
                    Position.of(-1, -1),
                    Position.of(1, 1),
                    Position.of(-1, 1),
                    Position.of(1, -1))
            .collect(Collectors.toCollection(HashSet::new));
    public static Set<Position> fullSteps = Stream.of(
                    Position.of(1, 0),
                    Position.of(-1, 0),
                    Position.of(0, 1),
                    Position.of(0, -1),
                    Position.of(1, 1),
                    Position.of(1, -1),
                    Position.of(-1, 1),
                    Position.of(-1, -1))
            .collect(Collectors.toCollection(HashSet::new));
}
