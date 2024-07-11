package com.dresch.together.activity;

import com.dresch.together.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public ActivityResponse registerActivity(ActivityRequestPayload payload, Event event) {
        Activity newActivity = new Activity(payload.title(), payload.occurs_at(), event);

        this.activityRepository.save(newActivity);

        return  new ActivityResponse(newActivity.getId());
    }

    public List<ActivityData> getAllActivitiesFromId(UUID eventId){
        return this.activityRepository.findByEventId(eventId).stream().map(activity -> new ActivityData(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();
    }
}
