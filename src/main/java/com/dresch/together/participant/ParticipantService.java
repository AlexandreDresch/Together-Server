package com.dresch.together.participant;

import com.dresch.together.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public ParticipantCreateResponse registerParticipantToEvent(String email, Event event){
        Participant newParticipant = new Participant(email, event);

        this.participantRepository.save(newParticipant);

        return new ParticipantCreateResponse(newParticipant.getId());
    }

    public void registerParticipantsToEvent(List<String> participantsToInvite, Event event) {
       List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, event)).toList();

       this.participantRepository.saveAll(participants);
    }

    public void triggerConfirmationEmailToParticipants(UUID eventId){}

    public void triggerConfirmationEmailToParticipant(String email){}
}
