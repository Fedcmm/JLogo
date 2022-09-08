package it.unicam.cs.pa.jlogo.app;

import it.unicam.cs.pa.jlogo.LogoController;
import it.unicam.cs.pa.jlogo.model.ClosedArea;
import it.unicam.cs.pa.jlogo.model.Line;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class LogoMainController {

    public static final int DEFAULT_HEIGHT = 600;
    public static final int DEFAULT_WIDTH = 750;

    @FXML
    private Pane canvasPane;
    @FXML
    private Canvas fxCanvas;
    @FXML
    private Text infoText;
    @FXML
    private Button loadButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button playPauseButton;

    private GraphicsContext gc;
    private LogoController controller;


    @FXML
    public void initialize() {
        fxCanvas.widthProperty().bind(canvasPane.widthProperty());
        fxCanvas.heightProperty().bind(canvasPane.heightProperty());

        fxCanvas.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (fxCanvas.getHeight() != 0)
                initializeLogoController(fxCanvas.widthProperty().intValue(), fxCanvas.heightProperty().intValue());
        });
        fxCanvas.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (fxCanvas.getWidth() != 0)
                initializeLogoController(fxCanvas.widthProperty().intValue(), fxCanvas.heightProperty().intValue());
        });

        gc = fxCanvas.getGraphicsContext2D();
    }

    private void initializeLogoController(int width, int height) {
        ObservableLogoCanvas canvas = new ObservableLogoCanvas(width, height);
        canvas.setOnLineDrawnListener(this::drawLine);
        canvas.setOnClosedAreaDrawnListener(this::drawClosedArea);

        canvas.backColorProperty().addListener(((observable, oldValue, newValue) ->
                canvasPane.setBackground(new Background(new BackgroundFill(toFxColor(newValue), null, null)))));

        controller = new LogoController(canvas);
    }

    @FXML
    private void onLoadClicked(Event event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Logo program file");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.jlp", "*.jlp"));
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        File file = chooser.showOpenDialog(canvasPane.getScene().getWindow());
        if (file != null) {
            try {
                controller.loadProgram(file);
            } catch (IOException e) {
                infoText.setFill(Color.RED);
                infoText.setText(e.getMessage());
            }
        }
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


    private void drawLine(Line line) {
        gc.setStroke(toFxColor(line.getColor()));
        gc.setLineWidth(line.getSize());
        gc.strokeLine(line.getA().x(), convertYCoordinate(line.getA().y()),
                line.getB().x(), convertYCoordinate(line.getB().y()));
    }

    private void drawClosedArea(ClosedArea area) {
        drawLine(area.getLastLine());

        double[] xValues = area.getLines().stream().mapToDouble(line -> line.getA().x()).toArray();
        double[] yValues = area.getLines().stream().mapToDouble(line -> convertYCoordinate(line.getA().y())).toArray();
        gc.setFill(toFxColor(area.getFillColor()));
        gc.fillPolygon(xValues, yValues, xValues.length);
    }


    /**
     * Converts a y coordinate value into one compatible with the JavaFx canvas (the 0
     * is at the top)
     */
    private double convertYCoordinate(double y) {
        return canvasPane.getHeight() - y;
    }

    /**
     * Converts an awt color into a JavaFx color
     *
     * @param color a {@link java.awt.Color}
     * @return a {@link Color} instance with the same rgb components as the parameter
     */
    private Color toFxColor(java.awt.Color color) {
        return Color.rgb(color.getRed(), color.getGreen(), color.getBlue());
    }
}
