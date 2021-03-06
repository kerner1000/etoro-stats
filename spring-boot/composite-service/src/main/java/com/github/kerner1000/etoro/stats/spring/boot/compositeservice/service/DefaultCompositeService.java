package com.github.kerner1000.etoro.stats.spring.boot.compositeservice.service;

import com.github.kerner1000.etoro.stats.model.*;
import com.github.kerner1000.etoro.stats.spring.boot.api.composite.CompositeService;
import com.github.kerner1000.etoro.stats.spring.boot.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DefaultCompositeService implements CompositeService {

    private final ServiceUtil serviceUtil;
    private CompositeIntegration integration;

    @Autowired
    public DefaultCompositeService(ServiceUtil serviceUtil, CompositeIntegration integration) {
        this.serviceUtil = serviceUtil;
        this.integration = integration;
    }

    @Override
    public Position2 getPosition(String instrument) {
        return integration.getPosition(instrument);
    }

    @Override
    public Position2 getOpenPosition(String instrument) {
        return integration.getOpenPosition(instrument);
    }

    @Override
    public DefaultTaxonomy getTaxonomy(String name, String instrument) {
        return integration.getTaxonomy(name, instrument);
    }


    @Override
    public List<Transaction> getTransactions(String instrument) {
        return integration.getTransactions(instrument);
    }

    @Override
    public List<Transaction> getOpenTransactions(String instrument) {
        return integration.getOpenTransactions(instrument);
    }

    @Override
    public List<Transaction> getOpenTransactions() {
        return integration.getOpenTransactions();
    }

    @Override
    public Transaction createTransaction(Transaction body) {
        return integration.createTransaction(body);
    }

    @Override
    public void deleteAll() {
        integration.deleteAll();
    }

    @Override
    public PositionGroup getOpenPositions() {
        return integration.getOpenPositions();
    }

    @Override
    public PositionGroups getOpenPositionsGrouped() {
        return integration.getOpenPositionsGrouped();
    }

    @Override
    public PositionGroups getOpenPositionsGroupedBySector() {
        return integration.getOpenPositionsGroupedBySector();
    }

    @Override
    public PositionGroups getOpenPositionsGroupedByIndustry() {
        return integration.getOpenPositionsGroupedByIndustry();
    }


    @Override
    public PositionGroups getSectorBreakdown() {
        return null;
    }
}
