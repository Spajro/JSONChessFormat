package data.json;

import chess.formats.algebraic.AlgebraicUtility;
import chess.formats.algebraic.RawAlgebraicFactory;
import chess.moves.raw.RawMove;
import data.annotations.Annotations;
import data.annotations.ArrowAnnotation;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;
import data.model.Diagram;
import data.model.metadata.GameData;

import java.util.Iterator;
import java.util.Stack;

public class PagedJsonFactory implements Iterator<String> {
    private final ListJsonFactory listJsonFactory = new ListJsonFactory();
    private final AlgebraicUtility algebraicUtility = AlgebraicUtility.getInstance();
    private final RawAlgebraicFactory rawAlgebraicFactory = new RawAlgebraicFactory();
    private final Stack<Token> stack = new Stack<>();

    private interface Token {
    }

    private record Start(Diagram diagram) implements Token {
    }

    private record Bracket() implements Token {
    }

    private record Coma() implements Token {
    }

    public PagedJsonFactory(Diagram diagram) {
        stack.add(new Start(diagram));
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public String next() {
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            Token token = stack.pop();
            if (token instanceof Start start) {
                Diagram diagram = start.diagram();
                result.append('{')
                        .append("\"moveName\":")
                        .append(diagram
                                .getCreatingMove()
                                .map(this::toJson)
                                .orElse("\"" + diagram.getMoveName() + "\"")
                        )
                        .append(",");
                if (!diagram.getMetaData().isEmpty()) {
                    result.append("\"metadata\":")
                            .append(listJsonFactory.listToJson(diagram.getGameData(), this::toJson))
                            .append(',');
                }
                if (!diagram.getAnnotations().isEmpty()) {
                    result.append("\"annotations\":")
                            .append(toJson(diagram.getAnnotations()))
                            .append(',');
                }
                if (diagram.isLazy()) {
                    result.append("\"movesList\":")
                            .append(listJsonFactory.listToJson(diagram.getLazyMovesList(), this::toJson))
                            .append('}');
                } else if (!diagram.getNextDiagrams().isEmpty()) {
                    result.append("\"moves\":[");
                    stack.add(new Bracket());
                    stack.add(new Start(diagram.getNextDiagrams().get(0)));
                    diagram.getNextDiagrams().subList(1, diagram.getNextDiagrams().size()).forEach(d -> {
                        stack.add(new Coma());
                        stack.add(new Start(d));
                    });
                }
                return result.toString();
            } else if (token instanceof Bracket) {
                result.append("]}");
            } else if (token instanceof Coma) {
                result.append(',');
            }
        }
        return result.toString();
    }


    private String toJson(RawMove rawMove) {
        return "\"" + rawAlgebraicFactory.moveToRawAlgebraic(rawMove) + "\"";
    }

    private String toJson(GameData metaData) {
        return '{' +
                "\"event\":" +
                metaData.event() +
                "," +
                "\"site\":" +
                metaData.site() +
                "," +
                "\"date\":" +
                metaData.date() +
                "," +
                "\"round\":" +
                metaData.round() +
                "," +
                "\"white\":" +
                metaData.white() +
                "," +
                "\"black\":" +
                metaData.black() +
                "," +
                "\"result\":" +
                metaData.result() +
                "," +
                "\"length\":" +
                metaData.length() +
                '}';
    }

    private String toJson(Annotations annotations) {
        StringBuilder result = new StringBuilder();
        result.append('{');
        if (!annotations.getTextAnnotation().isEmpty()) {
            result.append("\"text\":\"")
                    .append(annotations.getTextAnnotation())
                    .append("\",");
        }
        if (!annotations.getArrowAnnotations().isEmpty()) {
            result.append("\"arrows\":")
                    .append(listJsonFactory.listToJson(annotations.getArrowAnnotations(), this::toJson))
                    .append(",");
        }
        if (!annotations.getFieldAnnotations().isEmpty()) {
            result.append("\"fields\":")
                    .append(listJsonFactory.listToJson(annotations.getFieldAnnotations(), this::toJson))
                    .append(',');
        }
        result.deleteCharAt(result.length() - 1);
        result.append('}');
        return result.toString();
    }

    private String toJson(ArrowAnnotation arrow) {
        return "{" +
                "\"arrow\":\"" +
                algebraicUtility.positionToAlgebraic(arrow.getStartPosition()) +
                algebraicUtility.positionToAlgebraic(arrow.getEndPosition()) +
                "\",\"color\":\"" +
                GraphicAnnotation.drawColorToString(arrow.getColor()) +
                "\"}";
    }

    private String toJson(FieldAnnotation field) {
        return "{\"position\": \"" +
                algebraicUtility.positionToAlgebraic(field) +
                "\",\"color\":\"" +
                GraphicAnnotation.drawColorToString(field.getColor()) +
                "\"}";
    }
}
