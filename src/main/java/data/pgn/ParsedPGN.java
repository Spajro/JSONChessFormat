package data.pgn;

import chess.moves.raw.RawMove;
import data.model.metadata.MetaData;

import java.util.ArrayDeque;
import java.util.Optional;

public record ParsedPGN(MetaData metadata, Optional<ArrayDeque<RawMove>> moves) {
}
