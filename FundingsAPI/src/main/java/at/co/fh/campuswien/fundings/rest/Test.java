package at.co.fh.campuswien.fundings.rest;

import at.co.fh.campuswien.fundings.service.OehScraper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class Test {

    @GetMapping
    public List<String> test() {
        return OehScraper.scrape();
    }
}
