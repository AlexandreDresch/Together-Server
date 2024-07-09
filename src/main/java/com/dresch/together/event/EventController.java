package com.dresch.together.event;

import com.dresch.together.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping
    public ResponseEntity<EventCreateResponse> createEvent(@RequestBody EventRequestPayload payload){
        Event newEvent = new Event(payload);

        this.eventRepository.save(newEvent);

        this.participantService.registerParticipantsToEvent(payload.emails_to_invite(), newEvent.getId());

        return ResponseEntity.ok(new EventCreateResponse(newEvent.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventDetails(@PathVariable UUID id){
        Optional<Event> event = this.eventRepository.findById(id);

        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
