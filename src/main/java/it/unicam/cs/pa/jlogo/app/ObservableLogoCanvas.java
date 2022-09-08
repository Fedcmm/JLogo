package it.unicam.cs.pa.jlogo.app;

import it.unicam.cs.pa.jlogo.LogoCanvas;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.awt.Color;

public class ObservableLogoCanvas extends LogoCanvas {

    private final ObjectProperty<Color> backColor = new SimpleObjectProperty<>(this, "backColor");


    /**
     * Creates a new canvas with the specified dimensions
     *
     * @param width  the width of the canvas
     * @param height the height of the canvas
     */
    public ObservableLogoCanvas(int width, int height) {
        super(width, height);
    }


    @Override
    public void setBackColor(Color color) {
        backColor.set(color);
    }

    @Override
    public Color getBackColor() {
        return backColor.get();
    }

    /**
     * @return the property holding the back color of this canvas
     */
    public ObjectProperty<Color> backColorProperty() {
        return backColor;
    }
}
