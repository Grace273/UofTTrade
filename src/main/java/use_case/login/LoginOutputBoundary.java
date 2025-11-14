package use_case.login;

import java.io.IOException;

public interface LoginOutputBoundary {

    void prepareSuccessView(LoginOutputData loginOutputData);

    void prepareFailView(String failMessage);

}
