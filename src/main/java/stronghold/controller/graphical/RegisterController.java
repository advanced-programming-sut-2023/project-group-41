package stronghold.controller.graphical;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import stronghold.controller.MenuController;

public class RegisterController extends MenuController {
    public TextField usernameField;


    public void onUsernameFieldChange(ActionEvent inputMethodEvent) {
        System.out.println(1);
    }

    public void sampleFunc(MouseEvent mouseEvent) {
        System.out.println(27);
        
    }
}
