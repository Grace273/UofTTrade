package interface_adapter.messaging;

import use_case.messaging.MessagingOutputBoundary;
import use_case.messaging.MessagingOutputData;

/**
 * The Messaging presenter.
 */
public class MessagingPresenter implements MessagingOutputBoundary {

    private final MessagingViewModel viewModel;

    /**
     * Constructs a MessagingPresenter with the given view model.
     * @param viewModel the given view model based on the results of the messaging use case.
     */
    public MessagingPresenter(MessagingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSuccessView(MessagingOutputData data) {

        MessagingViewModel.State state = viewModel.getState();
        state.title = "Email " + data.getName();
        state.gmailUrl = data.getNormalizedurl();
        state.error = null;

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void presentFailureView(String errorMessage) {
        MessagingViewModel.State state = viewModel.getState();
        state.gmailUrl = null;
        state.error = errorMessage;

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
