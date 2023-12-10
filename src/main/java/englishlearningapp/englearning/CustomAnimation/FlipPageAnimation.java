package englishlearningapp.englearning.CustomAnimation;

import javafx.animation.RotateTransition;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.util.Duration;

public class FlipPageAnimation {
    private RotateTransition rotateTransition;

    public FlipPageAnimation(Node flipObj) {
        rotateTransition = new RotateTransition(Duration.seconds(1), flipObj);
        rotateTransition.setAxis(new Point3D(0, 1, 0));
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
    }

    public void play() {
        rotateTransition.play();
    }

    public void setOnFinished(Runnable action) {
        rotateTransition.setOnFinished(event -> {
            action.run();
        });
    }

}
