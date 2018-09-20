package com.mf.smax.alexa.request;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.mf.smax.alexa.intenthandlers.*;


public class SMAXSkillStreamHandler extends SkillStreamHandler {
    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(new CancelandStopIntentHandler(), new GetOverviewtOfIncidentsIntentHandler(),
                        new HelpIntentHandler(), new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),new GetOverviewtOfREquestsIntentHandler(),
                        new GetOverviewtOfChangesIntentHandler(),new GetOverviewtOfProblemsIntentHandler(),
                        new GetCriticalIncidentsDescIntentHandler(),new GetCriticalProblemsDescIntentHandler(),
                        new GetCriticalChangesDescIntentHandler(),new GetCriticalRequestsDescIntentHandler()
                )
                .build();
    }

    public SMAXSkillStreamHandler() {
        super(getSkill());
    }
}
