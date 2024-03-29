package com.github.kerner1000.etoro.stats.taxonomy.yahoo.services;

import com.github.kerner1000.etoro.stats.model.DefaultTaxonomy;
import com.github.kerner1000.etoro.stats.spring.boot.api.TaxonomyService;
import com.github.kerner1000.etoro.stats.taxonomy.yahoo.YahooAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.scheduler.Scheduler;

@RestController
public class DefaultYahooTaxonomyService implements TaxonomyService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultYahooTaxonomyService.class);

    private final YahooAPI api;

    private final Scheduler scheduler;

    @Autowired
    public DefaultYahooTaxonomyService(Scheduler scheduler, @Value("${app.apikey}") String apikey) {
        this.scheduler = scheduler;
        this.api = new YahooAPI(apikey);
    }

    @Override
    public DefaultTaxonomy getTaxonomy(String identifier, String instrument) {
        logger.info("Querying Yahoo API for identifier: '{}' and instrument: '{}'", identifier, instrument);
        DefaultTaxonomy taxonomy = buildTaxonomy(identifier, instrument);
        logger.debug("Got taxonomy: {} ", taxonomy);
        return taxonomy;
    }

    DefaultTaxonomy buildTaxonomy(String identifier, String instrument) {
        DefaultTaxonomy result = new DefaultTaxonomy();
        result.setIdentifier(identifier);
        result.setInstrument(instrument);
        switch (identifier) {
            case "industry":
                result.setValue(api.getIndustry(instrument));
                break;
            case "sector":
                result.setValue(api.getSector(instrument));
                break;
            case "name":
                result.setValue(api.getShortName(instrument));
                break;
            default:
                throw new RuntimeException("Unknown identifier " + identifier);
        }
        return result;
    }
}
