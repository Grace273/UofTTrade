package use_case.login;

/**
 * Input boundary for the login use case
 */

public interface LoginInputBoundary {

    /**
     * Executes login use case
     * @param loginInputData: The data needed to log a user in to the system
     */
    void execute(LoginInputData loginInputData);

}
