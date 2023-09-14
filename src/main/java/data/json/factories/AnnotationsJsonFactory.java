package data.json.factories;

import data.annotations.Annotations;
import data.annotations.ArrowAnnotation;
import data.annotations.FieldAnnotation;
import data.json.ListJsonFactory;

public class AnnotationsJsonFactory implements JsonFactory<Annotations> {
    private final ListJsonFactory listJsonFactory = new ListJsonFactory();
    private final JsonFactory<FieldAnnotation> fieldAnnotationJsonFactory=new FieldAnnotationJsonFactory();
    private final JsonFactory<ArrowAnnotation> arrowAnnotationJsonFactory=new ArrowAnnotationJsonFactory();

    public String toJson(Annotations annotations) {
        StringBuilder result = new StringBuilder();
        result.append('{');
        if (!annotations.getTextAnnotation().isEmpty()) {
            result.append("\"text\":\"")
                    .append(annotations.getTextAnnotation())
                    .append("\",");
        }
        if (!annotations.getArrowAnnotations().isEmpty()) {
            result.append("\"arrows\":")
                    .append(listJsonFactory.listToJson(annotations.getArrowAnnotations(), arrowAnnotationJsonFactory::toJson))
                    .append(",");
        }
        if (!annotations.getFieldAnnotations().isEmpty()) {
            result.append("\"fields\":")
                    .append(listJsonFactory.listToJson(annotations.getFieldAnnotations(), fieldAnnotationJsonFactory::toJson))
                    .append(',');
        }
        result.deleteCharAt(result.length() - 1);
        result.append('}');
        return result.toString();
    }
}
