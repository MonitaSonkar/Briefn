package com.smartwebarts.briefnx.dashboard.viewmodel;

public interface NewsResultCallback {

    boolean isInternetConnected();
    void showLoader();
    void hideLoader();
   /* void onSuccessCPDataResponse(CPTopProjectListResponse response);
    void onErrorCPDataResponse(String errMsg);
    void onSuccessAgentStats(AgentStatResponse agentStatResponse);
    void onErrorAgentStats(String errMsg);*/
}
