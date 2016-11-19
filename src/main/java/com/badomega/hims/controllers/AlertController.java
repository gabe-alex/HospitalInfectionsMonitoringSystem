package com.badomega.hims.controllers;

import com.badomega.hims.dtos.AlertDTO;
import com.badomega.hims.dtos.LoggedPairDTO;
import com.badomega.hims.dtos.RuleBreakDTO;
import com.badomega.hims.entities.Beacon;
import com.badomega.hims.entities.Disease;
import com.badomega.hims.entities.Interaction;
import com.badomega.hims.entities.Person;
import com.badomega.hims.enums.LocationType;
import com.badomega.hims.enums.Role;
import com.badomega.hims.enums.SpreadRisk;
import com.badomega.hims.repositories.InteractionRepository;
import com.badomega.hims.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class AlertController {
    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private PersonRepository personRepository;


    @RequestMapping(value = "/srv/getAlerts", method = RequestMethod.GET)
    @ResponseBody
    public List<AlertDTO> getAlerts() {
        List<AlertDTO> alerts = new ArrayList<>();
        List<LoggedPairDTO> loggedPairs = new ArrayList<>();
        Set<Interaction> validInteractions = interactionRepository.getValid();

        for(Interaction interaction : validInteractions)
        {
            if(interaction.getTargetPerson() != null) {
                LoggedPairDTO loggedPair = getLoggedPair(interaction.getSelfPerson(), interaction.getTargetPerson(), loggedPairs);
                if(loggedPair != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(interaction.getStartTimestamp());
                    calendar.add(Calendar.MINUTE, -2);
                    if(loggedPair.getInteractionDate() == null || loggedPair.getInteractionDate().before(calendar.getTime())) {
                        List<Disease> selfToTargetDiseases = newRiskyDiseases(interaction.getSelfPerson(), interaction.getTargetPerson());
                        List<Disease> tergetToSelfDiseases = newRiskyDiseases(interaction.getTargetPerson(), interaction.getSelfPerson());

                        if (selfToTargetDiseases.size() > 0 || tergetToSelfDiseases.size() > 0) {
                            alerts.add(new AlertDTO(interaction, selfToTargetDiseases, tergetToSelfDiseases));
                        }
                    }
                    loggedPairs.remove(loggedPair);
                }
                loggedPairs.add(new LoggedPairDTO(interaction.getSelfPerson(), interaction.getTargetPerson(), interaction.getStartTimestamp()));
            }
        }

        return alerts;
    }

    private LoggedPairDTO getLoggedPair(Person selfPerson, Person otherPerson, List<LoggedPairDTO> loggedPairs)
    {
        for (LoggedPairDTO loggedPair : loggedPairs)
        {
            if((loggedPair.getSelfPerson().getId() == selfPerson.getId() && loggedPair.getOtherPerson().getId() == otherPerson.getId()) ||
                    (loggedPair.getSelfPerson().getId() == otherPerson.getId() && loggedPair.getOtherPerson().getId() == selfPerson.getId()))
            {
                return loggedPair;
            }
        }
        return null;
    }

    private List<Disease> riskyDiseases(Person person)
    {
        List<Disease> riskyDiseases = new ArrayList<>();

        for(Disease disease : person.getDiseases())
        {
            if(disease.getSpreadRisk() == SpreadRisk.HIGH)
            {
                riskyDiseases.add(disease);
            }
        }

        return riskyDiseases;
    }

    private List<Disease> newRiskyDiseases(Person sourcePerson, Person targetPerson)
    {
        List<Disease> newRiskyDiseases = new ArrayList<Disease>();

        for(Disease sourceDisease : riskyDiseases(sourcePerson))
        {
            boolean found = false;

            for(Disease targetDisease : riskyDiseases(targetPerson))
            {
                if(sourceDisease.getId() == targetDisease.getId())
                {
                    found = true;
                }
            }

            if(!found) {
                newRiskyDiseases.add(sourceDisease);
            }
        }

        return newRiskyDiseases;
    }

    @RequestMapping(value = "/srv/getRuleBreaks", method = RequestMethod.GET)
    @ResponseBody
    public List<RuleBreakDTO> getRuleBreaks()
    {
        List<RuleBreakDTO> ruleBreaks = new ArrayList<>();

        for(Person person : personRepository.findAll())
        {
            ruleBreaks.addAll(getRuleBreaks(person));
        }

        return ruleBreaks;
    }

    private List<RuleBreakDTO> getRuleBreaks(Person person)
    {
        List<RuleBreakDTO> ruleBreaks = new ArrayList<>();

        Date lastUsedToiledTime = null;
        Beacon lastUsedToiledPlace = null;
        Date lastWashedTime = null;
        Beacon lastWashedPlace = null;

        if(person.getInteractionsFrom() != null) {
            for (Interaction interaction : person.getInteractionsFrom()) {
                if (interaction.getTargetBeacon() != null && interaction.getTargetBeacon().getLocation_type() == LocationType.TOILET) {
                    lastUsedToiledTime = interaction.getEndTimestamp();
                    lastUsedToiledPlace = interaction.getTargetBeacon();
                }

                if (interaction.getTargetBeacon() != null && interaction.getTargetBeacon().getLocation_type() == LocationType.SINK) {
                    lastWashedTime = interaction.getEndTimestamp();
                    lastWashedPlace = interaction.getTargetBeacon();

                    if (person.getRole() == Role.PATIENT && interaction.getStartTimestamp() != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(interaction.getStartTimestamp());
                        calendar.add(Calendar.MINUTE, -5);

                        if (lastUsedToiledTime != null && lastUsedToiledTime.after(calendar.getTime())) {
                            ruleBreaks.add(new RuleBreakDTO(
                                    lastUsedToiledTime,
                                    person,
                                    null,
                                    lastUsedToiledPlace,
                                    "The patient didn't wash their hands after using the toilet."));
                        }
                    }
                }

                if (person.getRole() == Role.DOCTOR && interaction.getTargetPerson() != null && interaction.getTargetPerson().getRole() == Role.PATIENT) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(interaction.getStartTimestamp());
                    calendar.add(Calendar.MINUTE, -5);

                    if (lastWashedTime == null || lastWashedTime.after(calendar.getTime())) {
                        ruleBreaks.add(new RuleBreakDTO(
                                interaction.getStartTimestamp(),
                                person,
                                interaction.getTargetPerson(),
                                lastWashedPlace,
                                "The doctor didn't wash their hands before consulting the patient."));
                    }
                }
            }
        }

        if(lastUsedToiledTime != null && lastWashedTime == null && person.getRole() == Role.PATIENT)
        {
            ruleBreaks.add(new RuleBreakDTO(
                    lastUsedToiledTime,
                    person,
                    null,
                    lastUsedToiledPlace,
                    "The patient didn't wash their hands after using the toilet."));
        }

        return ruleBreaks;
    }
}
