package com.sample.crashlytics.networkhelper;

/**
 * Created by manishdewan on 21/05/16.
 * interface with calls for success and failure case of network request
 */
public interface APICallResponseHandler {

  void apiSuccessResponse(String result);

  void apiFailureResponse(String error);
}
