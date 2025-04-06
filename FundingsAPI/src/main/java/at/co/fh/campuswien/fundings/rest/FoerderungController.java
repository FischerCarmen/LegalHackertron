package at.co.fh.campuswien.fundings.rest;

import at.co.fh.campuswien.fundings.FoerderungRepository;
import at.co.fh.campuswien.fundings.enitity.Foerderung;
import at.co.fh.campuswien.fundings.jobs.ScrapeJob;
import at.co.fh.campuswien.fundings.service.AiParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/foerderung")
public class FoerderungController {
    @Autowired
    FoerderungRepository foerderungRepository;
    @Autowired
    AiParser aiParser;

    @GetMapping
    public List<Foerderung> get() {
        return foerderungRepository.findAll();
    }

    @PostMapping
    public Foerderung post(@RequestBody Foerderung foerderung) {
        return foerderungRepository.save(foerderung);
    }

    @GetMapping(path = "/parse")
    public Foerderung[] parseFoerderung(@RequestBody String targetUrl) throws JsonProcessingException {
        return aiParser.parseFoerderung(targetUrl);
    }

    @PostMapping(path = "/scrape")
    public List<Foerderung> scrapeFoerderung() {
        ScrapeJob scrapeJob = new ScrapeJob(aiParser, foerderungRepository);
        return scrapeJob.scrape();
    }

    @PutMapping(path="/{id}")
    public Foerderung put(@RequestBody String additionalInformation, @PathVariable("id")long id) throws JsonProcessingException {
        Optional<Foerderung> existingFoerderung = foerderungRepository.findById(id);
        if (existingFoerderung.isPresent()) {
            Foerderung foerderung = existingFoerderung.get();
            var updated = this.aiParser.parseFoerderung(foerderung.getScrapeUrl() ,additionalInformation);
            updated[0].setId(foerderung.getId());
            foerderungRepository.save(updated[0]);
            return updated[0];
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
