package it.unicam.cs.pa.jlogo.app;

import it.unicam.cs.pa.jlogo.LogoController;
import it.unicam.cs.pa.jlogo.Point;
import it.unicam.cs.pa.jlogo.model.ClosedArea;
import it.unicam.cs.pa.jlogo.model.Line;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;

public class MainSceneController {

    public static final int DEFAULT_HEIGHT = 600;
    public static final int DEFAULT_WIDTH = 750;

    //region FXML fields
    @FXML
    private Pane canvasPane;
    @FXML
    private Canvas fxCanvas;
    @FXML
    private Polygon cursorPolygon;
    @FXML
    private Button resetButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button startStopButton;
    @FXML
    private Slider intervalSlider;
    @FXML
    private Text sliderText;
    @FXML
    private Text infoText;
    //endregion

    private GraphicsContext canvasGraphics;
    private LogoController logoController;

    private final PauseTransition programTransition = new PauseTransition();
    private boolean programRunning = false;


    //region Initialization
    @FXML
    public void initialize() {
        Locale.setDefault(Locale.US);

        intervalSlider.valueProperty().addListener((obs, old, newValue) ->
                sliderText.setText(String.format("Interval: %.1fs", newValue.doubleValue())));

        canvasGraphics = fxCanvas.getGraphicsContext2D();
        canvasPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        programTransition.setOnFinished(this::executeProgramStep);
        Platform.runLater(this::initializeLogoController);
    }

    private void initializeLogoController() {
        ObservableLogoCanvas canvas = new ObservableLogoCanvas(fxCanvas.widthProperty().intValue(), fxCanvas.heightProperty().intValue());
        setupCanvasListeners(canvas);
        setupCursorListeners(((ObservableLogoCursor) canvas.getCursor()));

        cursorPolygon.setTranslateX(canvas.getHome().x() - 10);
        cursorPolygon.setTranslateY(convertYCoordinate(canvas.getHome().y()) - 10);

        logoController = new LogoController(canvas);
    }

    private void setupCanvasListeners(ObservableLogoCanvas canvas) {
        canvas.setOnLineDrawnListener(this::drawLine);
        canvas.setOnClosedAreaDrawnListener(this::drawClosedArea);
        canvas.setClearAction(this::clear);

        canvas.backColorProperty().addListener(((obs, old, newValue) ->
                canvasPane.setBackground(new Background(new BackgroundFill(toFxColor(newValue), null, null)))));
    }

    private void setupCursorListeners(ObservableLogoCursor cursor) {
        cursor.positionProperty().addListener(this::cursorPositionChanged);
        cursor.directionProperty().addListener(this::cursorDirectionChanged);
        cursor.lineColorProperty().addListener((obs, old, newValue) -> cursorPolygon.setStroke(toFxColor(newValue)));
    }
    //endregion


    //region Event handlers
    @FXML
    private void onSliderReleased(Event ignoredEvent) {
        if (!programRunning) return;

        programTransition.setDuration(Duration.seconds(intervalSlider.getValue()));
        programTransition.playFromStart();
    }

    @FXML
    private void onResetClicked(Event ignoredEvent) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Attention");
        dialog.setContentText("Do you want to delete all drawings and reset program execution?");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
        dialog.showAndWait().filter(result -> result == ButtonType.YES).ifPresent(r -> reset());
    }

    @FXML
    private void onSaveClicked(Event ignoredEvent) {
        File file = showSaveFileChooser();
        if (file != null) {
            try {
                logoController.save(file);
                showInfoMessage("Written file \"" + file.getName() + "\"");
            } catch (IOException e) {
                showErrorMessage(e.getMessage());
            }
        }
    }

    @FXML
    private void onLoadClicked(Event ignoredEvent) {
        File file = showLoadFileChooser();
        if (file != null) {
            try {
                logoController.loadProgram(file);
                setStoppedAppearance();
                showInfoMessage("Loaded file \"" + file.getName() + "\"");
            } catch (IOException e) {
                showErrorMessage(e.getMessage());
            }
        }
    }

    @FXML
    private void onNextClicked(Event ignoredEvent) {
        if (!logoController.executeNext()) {
            nextButton.setDisable(true);
            startStopButton.setDisable(true);
        }
    }

    @FXML
    private void onStartStopClicked(Event ignoredEvent) {
        if (programRunning) {
            stopExecution();
            return;
        }

        startExecution();
    }

    @FXML
    private void onLinkClicked(Event ignoredEvent) {
        try {
            Desktop.getDesktop().browse(URI.create("https://icons8.com"));
        } catch (IOException e) {
            showErrorMessage("Unable to launch browser");
        }
    }
    //endregion

    //region File choosers
    private File showSaveFileChooser() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose save destination");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plain text files (*.txt)", "*.txt"));
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        return chooser.showSaveDialog(canvasPane.getScene().getWindow());
    }

    private File showLoadFileChooser() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Logo program file");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plain text files (*.txt)", "*.txt"));
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        return chooser.showOpenDialog(canvasPane.getScene().getWindow());
    }
    //endregion

    //region Program execution
    private void executeProgramStep(ActionEvent ignoredEvent) {
        if (logoController.executeNext()) {
            startExecution();
            return;
        }

        stopExecution();
    }
    private void startExecution() {
        setStartedAppearance();

        programTransition.setDuration(Duration.seconds(intervalSlider.getValue()));
        programTransition.playFromStart();
        programRunning = true;
    }

    private void stopExecution() {
        setStoppedAppearance();

        programTransition.pause();
        programRunning = false;
    }

    private void setStartedAppearance() {
        ((ImageView) startStopButton.getGraphic()).setImage(new Image("/icons/icon_pause.png"));
        startStopButton.getTooltip().setText("Stop the execution of the program");
        startStopButton.setText("Stop");
        resetButton.setDisable(true);
        saveButton.setDisable(true);
        loadButton.setDisable(true);
        nextButton.setDisable(true);
    }

    private void setStoppedAppearance() {
        ((ImageView) startStopButton.getGraphic()).setImage(new Image("/icons/icon_play.png"));
        startStopButton.getTooltip().setText("Start the execution of the program");
        startStopButton.setText("Start");
        resetButton.setDisable(false);
        saveButton.setDisable(false);
        loadButton.setDisable(false);
        startStopButton.setDisable(false);
        nextButton.setDisable(false);
    }
    //endregion

    //region Cursor changed
    private void cursorPositionChanged(ObservableValue<? extends Point> ignored1, Point ignored2, Point newValue) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), cursorPolygon);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.setToX(newValue.x() - 10);
        transition.setToY(convertYCoordinate(newValue.y()) - 10);
        transition.play();
    }

    private void cursorDirectionChanged(ObservableValue<? extends Number> ignored1, Number ignored2, Number newValue) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        KeyValue kv = new KeyValue(cursorPolygon.rotateProperty(), -(newValue.doubleValue()));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), kv));
        timeline.play();
    }
    //endregion

    //region Drawing
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
    //endregion

    private void reset() {
        setStoppedAppearance();

        logoController.reset();
        clear();
    }

    private void clear() {
        canvasGraphics.clearRect(0, 0, fxCanvas.getWidth(), fxCanvas.getHeight());
    }

    private void showInfoMessage(String message) {
        infoText.setFill(Color.BLACK);
        infoText.setText(message);
    }

    private void showErrorMessage(String message) {
        infoText.setFill(Color.RED);
        infoText.setText(message);
    }

    /**
     * Converts a y coordinate value into one compatible with the JavaFx canvas (the 0
     * is at the top)
     */
    private double convertYCoordinate(double y) {
        return fxCanvas.getHeight() - y;
    }

    /**
     * Converts a java.awt color into a JavaFx color
     */
    private Color toFxColor(java.awt.Color color) {
        return Color.rgb(color.getRed(), color.getGreen(), color.getBlue());
    }
}
