package data.pgn;

import data.model.Diagram;
import data.model.MetaData;

import java.util.Optional;

public record ParsedPGN(MetaData metadata, Optional<Diagram> diagram) {
}
