package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcitivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    public Activity findById(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        return optionalActivity.orElse(null);
    }

    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

}
