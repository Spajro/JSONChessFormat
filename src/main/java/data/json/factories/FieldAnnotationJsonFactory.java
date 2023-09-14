package data.json.factories;

import chess.formats.algebraic.AlgebraicUtility;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;

public class FieldAnnotationJsonFactory implements JsonFactory<FieldAnnotation> {
    private final AlgebraicUtility algebraicUtility = AlgebraicUtility.getInstance();

    public String toJson(FieldAnnotation field) {
        return "{\"position\": \"" +
                algebraicUtility.positionToAlgebraic(field) +
                "\",\"color\":\"" +
                GraphicAnnotation.drawColorToString(field.getColor()) +
                "\"}";
    }
}
