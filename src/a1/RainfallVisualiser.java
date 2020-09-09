package a1;

import examples.MosaicCanvas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This file can be used to draw a chart that effectively represents rainfall data.  Just fill in
 * the definition of drawPicture with the code that draws your picture.
 */
public class RainfallVisualiser extends Application {

    private final static int ROWS = 40;        // rows in the mosaic
    private final static int COLUMNS = 40;     // columns in the mosaic
    private final static int SQUARE_SIZE = 15; // size of each square
    private MosaicCanvas mosaic;
    private int currentRed, currentGreen, currentBlue;  // The current color.
    private static double[] rainMonthly;

    /**
     * Draws a picture.  The parameters width and height give the size
     * of the drawing area, in pixels.
     */
    public void drawPicture(GraphicsContext g, int width, int height) {
        // TODO: draw the x-axis and y-axis
        // TODO: draw the monthly totals as a bar chart
    } // end drawPicture()


    //------ Implementation details: DO NOT EDIT THIS ------
    public void start(Stage stage) {
//        int width = 218 * 6 + 40;
//        int height = 500;
//        Canvas canvas = new Canvas(width, height);
//        drawPicture(canvas.getGraphicsContext2D(), width, height);
//        BorderPane root = new BorderPane(canvas);
//        root.setStyle("-fx-border-width: 4px; -fx-border-color: #444");
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.setTitle("Rainfall Visualiser");
//        stage.show();
//        stage.setResizable(false);

        mosaic = new MosaicCanvas(ROWS, COLUMNS, SQUARE_SIZE, SQUARE_SIZE);
        mosaic.setOnMousePressed(e -> doMouse(e));
//        mosaic.setOnMouseDragged( e -> doMouse(e));
        mosaic.clear();
        currentRed = 255;
        currentGreen = 0;
        currentBlue = 0;

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);

        root.setCenter(mosaic);
//        root.setTop( createMenuBar() );

        stage.setTitle("Rainfall Visualiser");
//        stage.setTitle("Mosaic Draw");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setX(150);  // Put the window at screen coords (150,100).
        stage.setY(100);
        stage.show();
    }
    //------ End of Implementation details ------


    public static void main(String[] args) {
//        System.out.print("Enter path: ");
//        var path = TextIO.getln();
//
//        String path = "C:\\Users\\Sabu Mini\\IdeaProjects\\Assignment 1 trialssrc/a1/MountSheridanStationCNS_analysed.csv";
//        TextIO.readFile(path);

        rainMonthly = new double[]{10, 20, 30, 40, 100, 120};

        launch();

    }

    private void doMouse(MouseEvent evt) {
        int row = mosaic.yCoordToRowNumber((int) evt.getY());
        int col = mosaic.xCoordToColumnNumber((int) evt.getX());
        if (row >= 0 && row < mosaic.getRowCount() && col >= 0 && col < mosaic.getColumnCount()) {
            // (the test in this if statement will be false if the user drags the
            //  mouse outside the canvas after pressing the mouse on the canvas)
//            paintSquare(row, col);
        }

        drawAxisX();
        drawAxisY();

        drawMonthly();

        for (int i = 0; i < rainMonthly.length; i++) {
            double v = rainMonthly[i];
            System.out.println(v);
        }

    }

    private void drawMonthly() {
        Color color = Color.rgb(10, 110, 100);
//        int row = mosaic.getRowCount() - 1;
        int[] rainBoxes = convertRainToBoxes(rainMonthly, mosaic.getRowCount());
        for (int col = 0; col < rainBoxes.length; col++) {
            int maxBox = rainBoxes[col];
//            for (int row = 0; row < mosaic.getRowCount() / 2; row++) {
            for (int row = 0; row < maxBox; row++) {
                int flippedRow = mosaic.getRowCount() - row - 2;
                mosaic.setColor(flippedRow, col + 1, color);
            }
        }
    }

    private int[] convertRainToBoxes(double[] arr, int rowCount) {
//        double maxVal = Collections.max(Arrays.asList(ArrayUtils.toObject(arr)));
        double maxVal = 0;
        for (int i = 0; i < arr.length; i++) {
            double v = arr[i];
            if (v > maxVal) {
                maxVal = v;
            }
        }
        System.out.println("maxVal=" + maxVal);
        System.out.println("rowCount=" + rowCount);
        int[] ret = new int[arr.length];
        double scale = (rowCount - 1)/ maxVal;
        System.out.println("scale = " + scale);
        for (int i = 0; i < arr.length; i++) {
            double v = arr[i];
            v = v * scale;
            ret[i] = (int)v;
            System.out.println("ret[i] = " + ret[i]);
        }
        return ret;

    }

    private void drawAxisX() {

        Color color = Color.rgb(0, 0, 255);
        int row = mosaic.getRowCount() - 1;
        for (int col = 0; col < mosaic.getColumnCount(); col++) {
            mosaic.setColor(row, col, color);
        }
    }

    private void drawAxisY() {

        Color color = Color.rgb(0, 255, 0);
        int col = 0;
        for (int row = 0; row < mosaic.getRowCount(); row++) {
            mosaic.setColor(row, col, color);
        }
    }


    private void paintSquare(int row, int col) {
        int r = currentRed;
        int g = currentGreen;
        int b = currentBlue;
        Color color = Color.rgb(r, g, b);
        mosaic.setColor(row, col, color);
    }

} // end SimpleGraphicsStarter
