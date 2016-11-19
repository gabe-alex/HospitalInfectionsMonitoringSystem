package com.badomega.hims.controllers;

import com.badomega.hims.dtos.InteractionDTO;
import com.badomega.hims.entities.Interaction;
import com.badomega.hims.entities.InteractionDisease;
import com.badomega.hims.entities.Person;
import com.badomega.hims.repositories.InteractionRepository;
import com.badomega.hims.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class InteractionController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InteractionRepository interactionRepository;

    @RequestMapping(value = "/srv/addInteraction", method = RequestMethod.POST)
    public String addInteraction(@Valid InteractionDTO interactionDTO, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            Interaction interaction = new Interaction();
            interaction.setSelfMacAddress(interactionDTO.getSelfMacAddress());
            interaction.setTargetMacAddress(interactionDTO.getTargetMacAddress());
            interaction.setStartTimestamp(interactionDTO.getStartTimestamp());
            interaction.setEndTimestamp(interactionDTO.getEndTimestamp());
            interaction.setMinDistance(interactionDTO.getMinDistance());

            Set<InteractionDisease> interactionDiseases = new HashSet<>();
            InteractionDisease interactionDisease = new InteractionDisease();
            for(Person selfPerson : personRepository.findAll())
            {
                if(selfPerson.getPhone_mac_address() == interaction.getSelfMacAddress())
                {

                }
            }
            interaction.setInteractionDiseases(interactionDiseases);
        }
        return "redirect:/managedb";
    }
}
