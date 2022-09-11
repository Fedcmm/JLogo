package it.unicam.cs.pa.jlogo.app;

import it.unicam.cs.pa.jlogo.LogoController;
import it.unicam.cs.pa.jlogo.Point;
import it.unicam.cs.pa.jlogo.model.ClosedArea;
import it.unicam.cs.pa.jlogo.model.Line;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class LogoMainController {

    public static final int DEFAULT_HEIGHT = 600;
    public static final int DEFAULT_WIDTH = 750;

    @FXML
    private Pane canvasPane;
    @FXML
    private Canvas fxCanvas;
    @FXML
    private Polygon cursorPolygon;
    @FXML
    private Button nextButton;
    @FXML
    private Button playPauseButton;
    @FXML
    private Slider intervalSlider;
    @FXML
    private Text sliderText;
    @FXML
    private Text infoText;

    private GraphicsContext canvasGraphics;
    private LogoController logoController;

    private final Timer timer = new Timer();
    private RunProgramTask task;
    private boolean timerRunning = false;


    @FXML
    public void initialize() {
        fxCanvas.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (fxCanvas.getHeight() != 0)
                initializeLogoController(fxCanvas.widthProperty().intValue(), fxCanvas.heightProperty().intValue());
        });
        fxCanvas.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (fxCanvas.getWidth() != 0)
                initializeLogoController(fxCanvas.widthProperty().intValue(), fxCanvas.heightProperty().intValue());
        });

        intervalSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (intervalSlider.isValueChanging()) return;

            sliderText.setText(String.format(Locale.ROOT, "Interval: %.1fs", newValue.doubleValue()));
            if (!timerRunning) return;

            task.cancel();
            task = new RunProgramTask();
            timer.schedule(task, 0, newValue.longValue() * 1000);
        });

        canvasGraphics = fxCanvas.getGraphicsContext2D();
    }

    private void initializeLogoController(int width, int height) {
        ObservableLogoCanvas canvas = new ObservableLogoCanvas(width, height);

        canvas.setOnLineDrawnListener(this::drawLine);
        canvas.setOnClosedAreaDrawnListener(this::drawClosedArea);
        canvas.setClearAction(this::clear);

        canvas.backColorProperty().addListener(((observable, oldValue, newValue) ->
                canvasPane.setBackground(new Background(new BackgroundFill(toFxColor(newValue), null, null)))));

        ObservableLogoCursor cursor = ((ObservableLogoCursor) canvas.getCursor());
        cursor.positionProperty().addListener(this::canvasPositionChanged);
        cursor.directionProperty().addListener(this::canvasDirectionChanged);
        cursor.lineColorProperty().addListener(
                (observable, oldValue, newValue) -> cursorPolygon.setStroke(toFxColor(newValue))
        );

        cursorPolygon.setTranslateX(canvas.getHome().x() - 10);
        cursorPolygon.setTranslateY(convertYCoordinate(canvas.getHome().y()) - 10);

        logoController = new LogoController(canvas);
    }



    @FXML
    private void onLoadClicked(Event ignoredEvent) {
        File file = openFileChooser();
        if (file != null) {
            try {
                logoController.loadProgram(file);

                nextButton.setDisable(false);
                infoText.setFill(Color.BLACK);
                infoText.setText("Loaded file \"" + file.getName() + "\"");
            } catch (IOException e) {
                infoText.setFill(Color.RED);
                infoText.setText(e.getMessage());
            }
        }
    }

    @FXML
    private void onNextClicked(Event ignoredEvent) {
        if (!logoController.executeNext())
            nextButton.setDisable(true);
    }

    @FXML
    private void onPlayPauseClicked(Event ignoredEvent) {
        if (timerRunning) {
            pause();
            return;
        }

        play();
    }


    private void canvasPositionChanged(ObservableValue<? extends Point> observable, Point oldValue, Point newValue) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), cursorPolygon);
        transition.setToX(newValue.x() - 10);
        transition.setToY(convertYCoordinate(newValue.y()) - 10);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();
    }

    private void canvasDirectionChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        KeyValue kv = new KeyValue(cursorPolygon.rotateProperty(), -(newValue.intValue()));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), kv));
        timeline.play();
    }

    private File openFileChooser() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Logo program file");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.jlp", "*.jlp"));
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        return chooser.showOpenDialog(canvasPane.getScene().getWindow());
    }

    private void play() {
        ((ImageView) playPauseButton.getGraphic()).setImage(new Image("/icons/icon_pause.png"));
        nextButton.setDisable(true);

        task = new RunProgramTask();
        timer.schedule(task, 0, (long) (intervalSlider.getValue() * 1000));
        timerRunning = true;
    }

    private void pause() {
        ((ImageView) playPauseButton.getGraphic()).setImage(new Image("/icons/icon_play.png"));
        nextButton.setDisable(false);
        task.cancel();
        timerRunning = false;
    }

    private void clear() {
        canvasGraphics.clearRect(0, 0, fxCanvas.getWidth(), fxCanvas.getHeight());
    }

    private void drawLine(Line line) {
        canvasGraphics.setStroke(toFxColor(line.getColor()));
        canvasGraphics.setLineWidth(line.getSize());
        canvasGraphics.strokeLine(line.getA().x(), convertYCoordinate(line.getA().y()),
                line.getB().x(), convertYCoordinate(line.getB().y()));
    }

    private void drawClosedArea(ClosedArea area) {
        drawLine(area.getLastLine());

        double[] xValues = area.getLines().stream().mapToDouble(line -> line.getA().x()).toArray();
        double[] yValues = area.getLines().stream().mapToDouble(line -> convertYCoordinate(line.getA().y())).toArray();
        canvasGraphics.setFill(toFxColor(area.getFillColor()));
        canvasGraphics.fillPolygon(xValues, yValues, xValues.length);
    }


    /**
     * Converts a y coordinate value into one compatible with the JavaFx canvas (the 0
     * is at the top)
     */
    private double convertYCoordinate(double y) {
        return fxCanvas.getHeight() - y;
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


    private class RunProgramTask extends TimerTask {

        @Override
        public void run() {
            if (!logoController.executeNext()) {
                pause();
                nextButton.setDisable(true);
            }
        }
    }
}
