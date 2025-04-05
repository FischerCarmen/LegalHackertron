package at.co.fh.campuswien.fundings.rest;

import at.co.fh.campuswien.fundings.FoerderungRepository;
import at.co.fh.campuswien.fundings.enitity.Foerderung;
import at.co.fh.campuswien.fundings.service.AiParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foerderung")
public class Test {
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
    public Foerderung parseFoerderung(@RequestBody String targetUrl) throws JsonProcessingException {
        return aiParser.parseFoerderung(targetUrl);
    }
}
