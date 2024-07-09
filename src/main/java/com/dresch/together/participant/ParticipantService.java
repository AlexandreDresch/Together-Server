package com.dresch.together.participant;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    public void registerParticipantsToEvent(List<String> participantsToInvite, UUID eventId) {}

    public void triggerConfirmationEmailToParticipants(UUID eventId){}
}
