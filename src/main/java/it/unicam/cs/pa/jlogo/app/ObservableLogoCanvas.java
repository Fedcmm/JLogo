package it.unicam.cs.pa.jlogo.app;

import it.unicam.cs.pa.jlogo.LogoCanvas;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.awt.Color;
import java.util.Objects;

/**
 * A canvas which stores the back color in a property and can execute a callback
 * when it is cleared
 */
public class ObservableLogoCanvas extends LogoCanvas {

    private final ObjectProperty<Color> backColor = new SimpleObjectProperty<>(this, "backColor");

    private Runnable clearAction;


    /**
     * Creates a new canvas with the specified dimensions and sets the cursor to
     * an {@link ObservableLogoCursor}
     *
     * @param width  the width of the canvas
     * @param height the height of the canvas
     */
    public ObservableLogoCanvas(int width, int height) {
        super(width, height, ObservableLogoCursor::new);

        backColor.set(Color.WHITE);
    }


    @Override
    public void setBackColor(Color color) {
        backColor.set(Objects.requireNonNull(color));
    }

    @Override
    public Color getBackColor() {
        return backColor.get();
    }

    @Override
    public void clear() {
        super.clear();
        if (clearAction != null) clearAction.run();
    }

    /**
     * @return the property holding the back color of this canvas
     */
    public ObjectProperty<Color> backColorProperty() {
        return backColor;
    }

    /**
     * Registers an action to be executed when this canvas is cleared
     *
     * @param clearAction the action
     */
    public void setClearAction(Runnable clearAction) {
        this.clearAction = clearAction;
    }
}
