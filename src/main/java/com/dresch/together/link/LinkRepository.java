package com.dresch.together.link;

import com.dresch.together.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {
    public List<Link> findByEventId(UUID eventId);
}
