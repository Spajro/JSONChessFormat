package data.json;

import chess.moves.raw.RawMove;
import data.annotations.Annotations;
import data.json.factories.AnnotationsJsonFactory;
import data.json.factories.GameDataJsonFactory;
import data.json.factories.JsonFactory;
import data.json.factories.RawMoveJsonFactory;
import data.model.Diagram;
import data.model.metadata.GameData;

import java.util.Iterator;
import java.util.Stack;

public class PagedJsonFactory implements Iterator<String> {
    private final ListJsonFactory listJsonFactory = new ListJsonFactory();
    private final JsonFactory<GameData> gameDataJsonFactory = new GameDataJsonFactory();
    private final JsonFactory<Annotations> annotationsJsonFactory = new AnnotationsJsonFactory();
    private final JsonFactory<RawMove> rawMoveJsonFactory=new RawMoveJsonFactory();
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
                                .map(rawMoveJsonFactory::toJson)
                                .orElse("\"" + diagram.getMoveName() + "\"")
                        )
                        .append(",");
                if (!diagram.getMetaData().isEmpty()) {
                    result.append("\"metadata\":")
                            .append(listJsonFactory.listToJson(diagram.getGameData(), gameDataJsonFactory::toJson))
                            .append(',');
                }
                if (!diagram.getAnnotations().isEmpty()) {
                    result.append("\"annotations\":")
                            .append(annotationsJsonFactory.toJson(diagram.getAnnotations()))
                            .append(',');
                }
                if (diagram.isLazy()) {
                    result.append("\"movesList\":")
                            .append(listJsonFactory.listToJson(diagram.getLazyMovesList(), rawMoveJsonFactory::toJson))
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
}
