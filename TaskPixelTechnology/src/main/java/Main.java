import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private List<Group> groups = new ArrayList<>();

    private final int sceneWidth = 950;
    private final int sceneHeight = 650;

    private final String path = new File("src\\main\\resources\\images").getAbsolutePath();

    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane borderPane = new BorderPane();

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.50));
        vBox.getChildren().addAll(new Label("POINTS"), new Label(""));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);

        Image image = new Image(new FileInputStream(path + "\\rtg.png"));

        ImageView topLeftImage = createImageView(image, vBox);
        ImageView topRightImage = createImageView(image, vBox);
        ImageView bottomLeftImage = createImageView(image, vBox);
        ImageView bottomRightImage = createImageView(image, vBox);

        GridPane gridPane = new GridPane();
        Group groupPane1 = new Group();
        groupPane1.getChildren().add(topLeftImage);
        Group groupPane2 = new Group();
        groupPane2.getChildren().add(topRightImage);
        Group groupPane3 = new Group();
        groupPane3.getChildren().add(bottomLeftImage);
        Group groupPane4 = new Group();
        groupPane4.getChildren().add(bottomRightImage);

        GridPane.setMargin(groupPane1, new Insets(5,5,5,5));
        GridPane.setMargin(groupPane2, new Insets(5,5,5,5));
        GridPane.setMargin(groupPane3, new Insets(5,5,5,5));
        GridPane.setMargin(groupPane4, new Insets(5,5,5,5));

        groups.add(groupPane1);
        groups.add(groupPane2);
        groups.add(groupPane3);
        groups.add(groupPane4);

        gridPane.add(groupPane1, 0, 0);
        gridPane.add(groupPane2, 1, 0);
        gridPane.add(groupPane3, 0, 1);
        gridPane.add(groupPane4, 1, 1);

        borderPane.setLeft(scrollPane);
        borderPane.setCenter(gridPane);

        Scene scene = new Scene(borderPane, sceneWidth, sceneHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("App");
        primaryStage.show();
    }

    public PointConfiguration createPoint(TextField xTextField, TextField yTextField) {
        PointConfiguration pointConfiguration = new PointConfiguration(xTextField, yTextField);

        xTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                try {
                    for (Circle c : pointConfiguration.getCircles()) {
                        if ((Double.parseDouble(newValue) > 5) && (Double.parseDouble(newValue) < 210))
                            c.setCenterX(Double.parseDouble(newValue));
                    }
                }
                catch (NumberFormatException e) {
                    System.err.println("Wrong input");
                }
            }
        });

        yTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                try {
                    for (Circle c : pointConfiguration.getCircles()) {
                        if ((Double.parseDouble(newValue) > 5) && (Double.parseDouble(newValue) < 303))
                            c.setCenterY(Double.parseDouble(newValue));
                    }
                }
                catch (NumberFormatException e) {
                    System.err.println("Wrong input");
                }
            }
        });

        return pointConfiguration;
    }

    public void addPoint(VBox vBox, PointConfiguration pointConfiguration) {
        Label point = new Label("Point:");
        Label xLabel = new Label("x=");
        Label yLabel = new Label("y=");
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(xLabel, pointConfiguration.getxTextField(), yLabel, pointConfiguration.getyTextField());
        vBox.getChildren().addAll(point, hBox);
    }

    public ImageView createImageView(Image image, VBox vBox) {
        ImageView imageView = new ImageView(image);

        imageView.setCursor(Cursor.CROSSHAIR);

        imageView.setOnMouseClicked((event) -> {
                Circle circle;
                PointConfiguration point = createPoint(new TextField("" + event.getX()), new TextField("" + event.getY()));
                for (Group g : groups) {
                    g.getChildren().add(circle = createCircle(event.getX(), event.getY(), 5, point));
                    point.getCircles().add(circle);
                }
                addPoint(vBox, point);
        });

        return imageView;
    }

    public Circle createCircle(double x, double y, double r, PointConfiguration pointConfiguration) {
        Circle circle = new Circle(x, y, r);

        circle.setCursor(Cursor.HAND);

        circle.setOnMouseDragged((event) -> {

            Circle c = (Circle) (event.getSource());

            c.setCenterX(event.getX());
            c.setCenterY(event.getY());

            if (event.getX() < 5) c.setCenterX(5);
            if (event.getX() > 212) c.setCenterX(212);
            if (event.getY() < 5) c.setCenterY(5);
            if (event.getY() > 303) c.setCenterY(303);

            if (pointConfiguration.getCircles().contains(circle)) {
                pointConfiguration.getxTextField().setText("" + c.getCenterX());
                pointConfiguration.getyTextField().setText("" + c.getCenterY());
            }

        });

        return circle;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
