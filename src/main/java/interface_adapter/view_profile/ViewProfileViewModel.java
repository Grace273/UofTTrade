package interface_adapter.view_profile;
import interface_adapter.ViewModel;

public class ViewProfileViewModel extends ViewModel<ViewProfileState> {

    public ViewProfileViewModel() {
        super("view profile");
        setState(new ViewProfileState());
    }

    public String getTitleText() {
        return getState().getTitleText();
    }
}

