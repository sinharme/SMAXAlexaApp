package com.mf.smax.alexa.intenthandlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.mf.smax.alexa.request.SMAXRestRequest;

import java.util.Optional;

public class GetOverviewtOfREquestsIntentHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler{
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(Predicates.intentName("GetOverviewOfRequests"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText = "";
        SMAXRestRequest http = new SMAXRestRequest();

        String cookie = null;
        try {
            cookie = "LWSSO_COOKIE_KEY=" + http.getAuthKey() + "; TENANTID=" + http.TENANTID;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            speechText = http.sendGetPriorityAndRecordCount(cookie, "Request");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Results", speechText)
                .build();
    }
}
