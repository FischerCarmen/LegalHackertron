package at.co.fh.campuswien.fundings.jobs;

import at.co.fh.campuswien.fundings.FoerderungRepository;
import at.co.fh.campuswien.fundings.enitity.Foerderung;
import at.co.fh.campuswien.fundings.service.AiParser;
import at.co.fh.campuswien.fundings.service.OehScraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScrapeJob {
    private static final Logger log = LoggerFactory.getLogger(ScrapeJob.class);
    AiParser aiParser;
    private final FoerderungRepository foerderungRepository;

    public ScrapeJob(AiParser aiParser, FoerderungRepository foerderungRepository) {
        this.aiParser = aiParser;
        this.foerderungRepository = foerderungRepository;
    }

    @Scheduled(cron = "0 0 8 1 * *")
    public List<Foerderung> scrape() {
        List<String> links = OehScraper.scrape("https://www.studienplattform.at/api/grantsout.php");
        List<Foerderung> foerderungs = new ArrayList<>();
        for (String link : links) {
            Foerderung newEl;
            try{
                newEl = aiParser.parseFoerderung(link);
                newEl.setScrapeUrl(link);
                foerderungRepository.save(newEl);
                log.info("New foerderung: " + newEl.getName());
            } catch (Exception e){
                log.info("Scrape failed: " + link);
                continue;
            }
            foerderungs.add(newEl);
        }
        return foerderungs;
    }
}