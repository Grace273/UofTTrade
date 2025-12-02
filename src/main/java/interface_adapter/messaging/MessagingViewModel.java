package interface_adapter.messaging;

import interface_adapter.ViewModel;

/**
 * The Viewmodel for the Messaging view.
 */
public class MessagingViewModel extends ViewModel<MessagingViewModel.State> {
    public static final String VIEW_NAME = "messaging";

    /**
     *  The State imported by the View model.
     */
    public static class State {
        public String title;
        public String gmailUrl;
        public String error;
    }

    public MessagingViewModel() {
        super(VIEW_NAME);

        final State initial = new State();
        initial.title = "";
        initial.gmailUrl = null;
        initial.error = null;

        setState(initial);
    }
}
