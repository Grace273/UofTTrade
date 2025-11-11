package use_case.view_profile;
import entity.User;
public interface ViewProfileUserDataAccessInterface {
    User getUserByUsername(String username);
}
