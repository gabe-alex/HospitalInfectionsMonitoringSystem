package com.badomega.hims.controllers;

import com.badomega.hims.dtos.AlertDTO;
import com.badomega.hims.dtos.PasswordChangeDTO;
import com.badomega.hims.entities.Disease;
import com.badomega.hims.entities.Interaction;
import com.badomega.hims.entities.Person;
import com.badomega.hims.enums.SpreadRisk;
import com.badomega.hims.repositories.InteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AlertController {
    @Autowired
    private InteractionRepository interactionRepository;

    @RequestMapping(value = "/srv/getAlerts", method = RequestMethod.GET)
    @ResponseBody
    public List<AlertDTO> showPasswordChangeForm(PasswordChangeDTO passwordChangeDTO) {
        List<AlertDTO> alerts = new ArrayList<>();
        Set<Interaction> validInteractions = interactionRepository.getValid();

        for(Interaction interaction : validInteractions)
        {
            if(interaction.getTargetPerson() != null) {
                List<Disease> selfToTargetDiseases = newRiskyDiseases(interaction.getSelf(), interaction.getTargetPerson());
                List<Disease> tergetToSelfDiseases = newRiskyDiseases(interaction.getTargetPerson(), interaction.getSelf());

                if (selfToTargetDiseases.size() > 0 || tergetToSelfDiseases.size() > 0) {
                    alerts.add(new AlertDTO(interaction.getSelf(), interaction.getTargetPerson(), selfToTargetDiseases, tergetToSelfDiseases));
                }
            }
        }

        return alerts;
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
}
