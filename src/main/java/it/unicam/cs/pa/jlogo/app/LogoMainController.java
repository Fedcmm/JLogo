package it.unicam.cs.pa.jlogo.app;

import it.unicam.cs.pa.jlogo.LogoCanvas;
import it.unicam.cs.pa.jlogo.LogoController;
import it.unicam.cs.pa.jlogo.LogoInstructionParser;
import it.unicam.cs.pa.jlogo.LogoProgramReader;
import it.unicam.cs.pa.jlogo.Point;
import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.ClosedArea;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class LogoMainController {

    public static final int DEFAULT_HEIGHT = 600;
    public static final int DEFAULT_WIDTH = 750;

    @FXML
    private Pane canvasPane;
    @FXML
    private Button loadButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button playPauseButton;

    private LogoController controller;


    @FXML
    public void initialize() {
        Canvas canvas = new LogoCanvas(canvasPane.widthProperty().intValue(), canvasPane.heightProperty().intValue());
        canvas.setOnLineDrawnListener(this::drawLine);
        canvas.setOnClosedAreaDrawnListener(this::drawClosedArea);

        controller = new LogoController(canvas, new LogoProgramReader(new LogoInstructionParser()));
    }

    @FXML
    private void onLoadClicked(Event event) {

    }

    @FXML
    private void onNextClicked(Event event) {
        if (!controller.executeNext())
            nextButton.setDisable(true);
    }

    @FXML
    private void onPlayPauseClicked(Event event) {
        ImageView imageView = new ImageView("/icons/icon_pause.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        playPauseButton.setGraphic(imageView);
    }


    private void drawLine(it.unicam.cs.pa.jlogo.model.Line line) {
        canvasPane.getChildren().add(toFxLine(line));
    }

    private void drawClosedArea(ClosedArea area) {
        canvasPane.getChildren().add(toFxPath(area));
    }


    /**
     * Converts a y coordinate value into one compatible with the JavaFx system (the 0
     * is at the top)
     */
    private double convertYCoordinate(double y) {
        return canvasPane.getHeight() - y;
    }

    /**
     * Converts a line from the Logo model into a JavaFx line
     *
     * @param line a {@link it.unicam.cs.pa.jlogo.model.Line}
     * @return a {@link Line} instance with the same attributes as the parameter
     */
    public Line toFxLine(it.unicam.cs.pa.jlogo.model.Line line) {
        Line fxLine = new Line(line.getA().x(), convertYCoordinate(line.getA().y()),
                line.getB().x(), convertYCoordinate(line.getB().y()));
        fxLine.setStrokeWidth(line.getSize());
        fxLine.setStroke(toFxColor(line.getColor()));
        return fxLine;
    }

    /**
     * Converts a closed area from the Logo model into a {@link Path}
     *
     * @param area a {@link ClosedArea}
     * @return a {@link Path} instance with the same attributes as the parameter
     */
    public Path toFxPath(ClosedArea area) {
        Path path = new Path();
        area.getLines().stream()
                .map(line -> new MoveTo(line.getA().x(), convertYCoordinate(line.getA().y())))
                .forEach(moveTo -> path.getElements().add(moveTo));
        Point firstPoint = area.getLines().get(0).getA();
        path.getElements().add(new MoveTo(firstPoint.x(), convertYCoordinate(firstPoint.y())));
        path.setFill(toFxColor(area.getFillColor()));
        return path;
    }

    /**
     * Converts an awt color into a JavaFx color
     *
     * @param color a {@link java.awt.Color}
     * @return a {@link Color} instance with the same rgb components as the parameter
     */
    public Color toFxColor(java.awt.Color color) {
        return Color.color(color.getRed(), color.getGreen(), color.getBlue());
    }
}
