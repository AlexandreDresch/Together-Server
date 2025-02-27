package com.dresch.together.link;

import com.dresch.together.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public LinkResponse registerLink(LinkRequestPayload payload, Event event) {
        Link newLink = new Link(payload.title(), payload.url(), event);

        this.linkRepository.save(newLink);

        return  new LinkResponse(newLink.getId());
    }

    public List<LinkData> getAllLinksFromId(UUID eventId){
        return this.linkRepository.findByEventId(eventId).stream().map(link -> new LinkData(link.getId(), link.getTitle(), link.getUrl())).toList();
    }
}
