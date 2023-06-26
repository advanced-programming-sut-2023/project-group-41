package stronghold.controller.graphical;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;

public class GraphicalProfileCartController {
    //public User user;

    public ImageView avatarPic;
    public Label userNameLabel;
    public Label nicknameLabel;
    public Label emailLabel;
    public Label sloganLabel;
    public Button sendFriendReqButton;
    public Button showScoreboardButton;
    public HBox imageHBox;
    public HBox usernameHBox;
    public HBox nicknameHBox;
    public HBox emailHBox;
    public HBox sloganHbox;

    {
        User user = new User("nima", "adf", "nim", "mjahfdahlkfjhdklhflsh", 5, "kosnnt", "kobs");
        //avatarPic.setImage();
        //imageHBox.getChildren().add(avatarPic);

        userNameLabel.setText(user.getUsername());
        usernameHBox.getChildren().add(userNameLabel);

        nicknameLabel.setText(user.getNickname());
        nicknameHBox.getChildren().add(nicknameLabel);

        emailLabel.setText(user.getEmail());
        emailHBox.getChildren().add(emailLabel);

        sloganLabel.setText(user.getSlogan());
        sloganHbox.getChildren().add(sloganLabel);
    }
    public void sendFriendReqHandler(MouseEvent mouseEvent) {

    }

    public void showScoreboardHandler(MouseEvent mouseEvent) {

    }
}
