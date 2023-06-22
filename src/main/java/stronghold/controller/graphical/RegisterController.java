package stronghold.controller.graphical;

import javafx.collections.SetChangeListener;
import javafx.scene.control.TextField;
import stronghold.controller.MenuController;

import java.util.EventListener;

public class RegisterController extends MenuController {
    public TextField usernameField;

    public void onUsernameFieldChange(EventListener eventListener){
        System.out.println(1);

    }
}
