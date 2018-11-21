package com.eddc.johnsonhenan.service.Johnson_henan_RelationDistribution.impl;

import com.eddc.johnsonhenan.mapper.Johnson_henan_RelationDistribution_listMapper;
import com.eddc.johnsonhenan.model.Johnson_henan_RelationDistribution_list;
import com.eddc.johnsonhenan.service.Johnson_henan_RelationDistribution.Johnson_henan_RelationDistribution_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "Johnson_henan_RelationDistribution_service")
public class Johnson_henan_RelationDistribution_serviceImpl implements Johnson_henan_RelationDistribution_service {

    @Autowired
    Johnson_henan_RelationDistribution_listMapper johnson_henan_relationDistribution_listMapper;

    @Override
    public int addJohnson_henan_RelationDistribution_list(Johnson_henan_RelationDistribution_list johnson_henan_relationDistribution_list) {
        return johnson_henan_relationDistribution_listMapper.insertSelective(johnson_henan_relationDistribution_list);
    }
}
