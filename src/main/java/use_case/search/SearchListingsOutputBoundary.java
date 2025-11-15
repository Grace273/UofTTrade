package use_case.search;

public interface SearchListingsOutputBoundary {
    void prepareSucessView(SearchListingsOutputData outputData);
    void prepareFailView(String errormessage);
}
