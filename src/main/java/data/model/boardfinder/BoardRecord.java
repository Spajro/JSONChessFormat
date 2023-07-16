package data.model.boardfinder;

import data.model.Diagram;

public class BoardRecord {
    private final Diagram diagram;
    private int distanceFromDiagram;

    public BoardRecord(Diagram diagram) {
        this.diagram = diagram;
        this.distanceFromDiagram = 0;
    }

    public BoardRecord(Diagram diagram, int distanceFromDiagram) {
        this.diagram = diagram;
        this.distanceFromDiagram = distanceFromDiagram;
    }

    public Diagram getDiagram() {
        return diagram;
    }

    public int getDistanceFromDiagram() {
        return distanceFromDiagram;
    }

    public void pushDownDistance() {
        distanceFromDiagram--;
    }
}
