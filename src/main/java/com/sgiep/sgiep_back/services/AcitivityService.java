package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@Service
public class AcitivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Cacheable(value = "activities")
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Cacheable(value = "activity", key = "#id")
    public Activity findById(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        return optionalActivity.orElse(null);
    }

    @CacheEvict(value = "activities", allEntries = true)
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @CacheEvict(value = "activity", key = "#id")
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }
}
