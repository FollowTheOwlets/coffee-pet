package com.example.coffee_pet.service;

import com.example.coffee_pet.entity.Resource;
import com.example.coffee_pet.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public List<Resource> getResources(){
        return resourceRepository.findAll();
    }

    public int addResource(int addCount, String name){
        int maxCount = resourceRepository.findMaxCountByName(name);
        int currentCount = resourceRepository.findCountByName(name);

        resourceRepository.updateCountByName(Math.min(currentCount + addCount, maxCount), name);

        return Math.max(0,currentCount + addCount -  maxCount);
    }

}
