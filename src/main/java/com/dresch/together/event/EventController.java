package com.dresch.together.event;

import com.dresch.together.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        this.participantService.registerParticipantsToEvent(payload.emails_to_invite(), newEvent);

        return ResponseEntity.ok(new EventCreateResponse(newEvent.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventDetails(@PathVariable UUID id){
        Optional<Event> event = this.eventRepository.findById(id);

        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID id, @RequestBody EventRequestPayload payload){
        Optional<Event> event = this.eventRepository.findById(id);

        if(event.isPresent()) {
            Event rawEvent = event.get();

            rawEvent.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawEvent.setStartsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawEvent.setDestination(payload.destination());

            this.eventRepository.save(rawEvent);

            return ResponseEntity.ok(rawEvent);
        }

        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Event> confirmEvent(@PathVariable UUID id){
        Optional<Event> event = this.eventRepository.findById(id);

        if(event.isPresent()) {
            Event rawEvent = event.get();

            rawEvent.setIsConfirmed(true);

            this.eventRepository.save(rawEvent);
            this.participantService.triggerConfirmationEmailToParticipants(id);

            return ResponseEntity.ok(rawEvent);
        }

        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
