package data.pgn;

import chess.board.ChessBoard;
import chess.moves.RawMove;
import chess.pieces.Piece;
import chess.results.MoveResult;
import chess.results.PromotionResult;
import chess.results.ValidMoveResult;
import chess.utility.AlgebraicUtility;
import chess.utility.ShortAlgebraicParser;
import data.model.Diagram;
import data.model.MetaData;

import java.util.*;

public class PGNParser {
    private static final PGNParser pgnParser = new PGNParser();

    private PGNParser() {
    }

    public static PGNParser getInstance() {
        return pgnParser;
    }

    private final ShortAlgebraicParser shortAlgebraicParser = new ShortAlgebraicParser();
    private final AlgebraicUtility algebraicUtility = AlgebraicUtility.getInstance();

    public ParsedPGN parsePGN(String pgn) {
        int split = pgn.lastIndexOf(']') + 1;
        String metadata = pgn.substring(0, split);
        MetaData metaData = parseMetadata(metadata);
        String moves = pgn.substring(split);
        return new ParsedPGN(metaData, parseMoves(moves));
    }

    private MetaData parseMetadata(String metadata) {
        HashMap<String, String> metadataMap = new HashMap<>();
        Arrays.stream(metadata.split("\n"))
                .map(s -> s.substring(1, s.length() - 1))
                .forEach(s -> {
                    int index = s.indexOf(" ");
                    metadataMap.put(s.substring(0, index), s.substring(index));
                });
        return new MetaData(
                metadataMap.get("Event"),
                metadataMap.get("Site"),
                metadataMap.get("Date"),
                metadataMap.get("Round"),
                metadataMap.get("White"),
                metadataMap.get("Black"),
                metadataMap.get("Result")
        );
    }

    private Optional<Diagram> parseMoves(String input) {
        List<String> moves = Arrays.stream(input.split(" "))
                .filter(s -> !(s.charAt(s.length() - 1) == '.'))
                .map(String::trim)
                .toList();
        Diagram root = new Diagram();
        Optional<List<RawMove>> optionalRawMoves = parseMoves(root, moves);
        if (optionalRawMoves.isPresent()) {
            Diagram oldNode = root;
            Diagram newNode;
            for (RawMove rawMove : optionalRawMoves.get()) {
                newNode = oldNode.makeMove(rawMove, null);
                if (newNode == oldNode) {
                    return Optional.empty();
                } else {
                    oldNode = newNode;
                }
            }
            return Optional.of(root);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<RawMove>> parseMoves(Diagram node, List<String> moves) {
        ChessBoard chessBoard = node.getBoard();
        List<RawMove> result = new LinkedList<>();
        for (String move : moves) {
            if (isGameEnd(move)) {
                return Optional.of(result);
            }
            RawMove rawMove = shortAlgebraicParser.parseShortAlgebraic(move, chessBoard);
            result.add(rawMove);
            MoveResult moveResult = chessBoard.makeMove(rawMove);
            if (moveResult.isValid()) {
                ValidMoveResult validMoveResult;
                if (moveResult instanceof PromotionResult promotionResult) {
                    Optional<Piece.Type> optionalType = algebraicUtility.parsePromotion(move);
                    if (optionalType.isEmpty()) {
                        return Optional.empty();
                    }
                    validMoveResult = promotionResult.type(optionalType.get());
                } else {
                    validMoveResult = (ValidMoveResult) moveResult;
                }
                chessBoard = validMoveResult.getResult();
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(result);
    }

    private boolean isGameEnd(String move) {
        return move.equals("1-0") || move.equals("0-1") || move.equals("1/2-1/2");
    }
}
