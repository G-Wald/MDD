package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Integer> findThemesByUserId(Integer userId){
        List<Subscription> subscriptions = this.subscriptionRepository.findByUserId(userId);
        List<Integer> themeIds = new ArrayList<Integer>();

        for (Subscription subscription : subscriptions) {
            themeIds.add(subscription.getTheme().getId());
        }
        return themeIds;
    }

    public Boolean findByUserIdAndByThemeId(Integer userId, Integer themeId){
        return this.subscriptionRepository.findByUserIdAndThemeId(userId, themeId).isPresent();
    }

    public void save(Subscription subscription){
        this.subscriptionRepository.save(subscription);
    }

    @Transactional
    public void delete(Integer themeId, Integer userId){
        this.subscriptionRepository.deleteByUserIdAndThemeId(userId, themeId);
    }

}
