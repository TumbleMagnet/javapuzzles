package be.rouget.puzzles.adventofcode.year2016.day8;

import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.annotations.VisibleForTesting;

import java.util.Objects;

public class TinyScreen {

    private RectangleMap<ScreenPixel> display;

    public TinyScreen(int width, int height) {
        this.display = new RectangleMap<>(width, height, ScreenPixel.OFF);
    }

    public void apply(ScreenCommand command) {
        switch (command.getOperation()) {
            case RECTANGLE:
                apply((RectangleCommand) command);
                return;
            case ROTATE_COLUMN:
                apply((RotateColumnCommand) command);
                return;
            case ROTATE_ROW:
                apply((RotateRowCommand) command);
                return;
            default:
                throw new IllegalArgumentException("Unsupported command " + command);
        }
    }

    private void apply(RectangleCommand rectangleCommand) {
        RectangleMap<ScreenPixel> updatedDisplay = new RectangleMap<ScreenPixel>(this.display);
        for (int x = 0; x < rectangleCommand.getWidth(); x++) {
            for (int y = 0; y < rectangleCommand.getHeight(); y++) {
                updatedDisplay.setElementAt(new Position(x, y), ScreenPixel.ON);
            }
        }
        this.display = updatedDisplay;
    }

    private void apply(RotateRowCommand rotateRowCommand) {
        RectangleMap<ScreenPixel> updatedDisplay = new RectangleMap<ScreenPixel>(this.display);
        for (int x = 0; x < display.getWidth(); x++) {
            int targetX = ( x + rotateRowCommand.getShift()) % display.getWidth();
            ScreenPixel pixel = display.getElementAt(new Position(x, rotateRowCommand.getRow()));
            updatedDisplay.setElementAt(new Position(targetX, rotateRowCommand.getRow()), pixel);
        }
        this.display = updatedDisplay;
    }

    private void apply(RotateColumnCommand rotateColumnCommand) {
        RectangleMap<ScreenPixel> updatedDisplay = new RectangleMap<ScreenPixel>(this.display);
        for (int y = 0; y < display.getHeight(); y++) {
            int targetY = (y + rotateColumnCommand.getShift()) % display.getHeight();
            ScreenPixel pixel = display.getElementAt(new Position(rotateColumnCommand.getColumn(), y));
            updatedDisplay.setElementAt(new Position(rotateColumnCommand.getColumn(), targetY), pixel);
        }
        this.display = updatedDisplay;
    }

    @VisibleForTesting
    protected RectangleMap<ScreenPixel> getDisplay() {
        return display;
    }

    public long countLitPixels() {
        return display.getElements().stream()
                .filter(e -> e.getValue() == ScreenPixel.ON)
                .count();
    }
}
