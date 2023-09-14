package data.json.factories;

import chess.formats.algebraic.AlgebraicUtility;
import data.annotations.ArrowAnnotation;
import data.annotations.GraphicAnnotation;

public class ArrowAnnotationJsonFactory implements JsonFactory<ArrowAnnotation> {
    private final AlgebraicUtility algebraicUtility = AlgebraicUtility.getInstance();

    public String toJson(ArrowAnnotation arrow) {
        return "{" +
                "\"arrow\":\"" +
                algebraicUtility.positionToAlgebraic(arrow.getStartPosition()) +
                algebraicUtility.positionToAlgebraic(arrow.getEndPosition()) +
                "\",\"color\":\"" +
                GraphicAnnotation.drawColorToString(arrow.getColor()) +
                "\"}";
    }
}
