import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class PointConfiguration {

    private javafx.scene.control.TextField xTextField;
    private javafx.scene.control.TextField yTextField;
    private List<Circle> circles = new ArrayList<>();

    public PointConfiguration(TextField xTextField, TextField yTextField) {
        this.xTextField = xTextField;
        this.yTextField = yTextField;
    }

    public TextField getxTextField() {
        return xTextField;
    }

    public void setxTextField(TextField xTextField) {
        this.xTextField = xTextField;
    }

    public TextField getyTextField() {
        return yTextField;
    }

    public void setyTextField(TextField yTextField) {
        this.yTextField = yTextField;
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public void setCircles(List<Circle> circles) {
        this.circles = circles;
    }
}
