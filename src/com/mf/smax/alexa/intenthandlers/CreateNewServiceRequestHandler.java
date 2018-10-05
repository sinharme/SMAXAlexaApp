package com.mf.smax.alexa.intenthandlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class CreateNewServiceRequestHandler implements RequestHandler {
    public String description ="";
    public boolean canHandle(HandlerInput handlerInput) {

        return handlerInput.matches(Predicates.intentName("CreateNewServiceRequest"));

    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        //SkillRequestInformatio skillRequestInformation
        IntentRequest request= (IntentRequest)handlerInput.getRequestEnvelope().getRequest();
        Intent intent = request.getIntent();
        Map<String, Slot> mapSlot = intent.getSlots();
        String slot="";
        for (Map.Entry<String,Slot> entry : mapSlot.entrySet()){
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue().getValue());
            slot+=entry.getValue().getName() + "  " + entry.getValue().getValue();

        }


        return handlerInput.getResponseBuilder()
                .withSpeech(slot)
                .withSimpleCard("Results", slot)
                .withReprompt(slot)
                .build();
    }
}
