package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;

public class SearchPlayersController {
    @FXML
    TextField userId;


    public void searchPlayers(ActionEvent actionEvent) {
        String username = userId.getText();
        User user = UsersDB.usersDB.getUserByUsername(username);
        if(user != null){
            System.out.println(1);
        }
    }

}
