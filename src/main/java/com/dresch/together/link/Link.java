package com.dresch.together.link;

import com.dresch.together.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public Link(String title, String url, Event event){
        this.title = title;
        this.url = url;
        this.event = event;
    }
}
