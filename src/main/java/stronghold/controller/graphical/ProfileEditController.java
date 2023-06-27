package stronghold.controller.graphical;

import stronghold.model.components.general.User;

public class ProfileEditController {
    public static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
