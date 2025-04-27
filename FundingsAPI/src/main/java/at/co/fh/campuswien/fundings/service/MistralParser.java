package at.co.fh.campuswien.fundings.service;

import at.co.fh.campuswien.fundings.enitity.Foerderung;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class MistralParser implements AiParser {
    static private final String MISTRAL_ADDRESS = "https://api.mistral.ai/v1/chat/completions";
    static private final String MESSAGE_TEMPLATE = "Schreibe die informationen aus der website, inkludiere die url des favicons der website, nur das json keine weiteren texte: %s in folgendem datenmodell zusammen: { \"institution\": \"Name der Institution\", \"name\": \"Name der F\u00F6rderung\", \"beschreibung\": \"Kurze Beschreibung der F\u00F6rderung\", \"betrag\": \"F\u00F6rderh\u00F6he (falls zutreffend)\", \"waehrung\": \"W\u00E4hrungseinheit\",\"kategorie\": \"Art der Förderung\", \"voraussetzungen\": [ \"Voraussetzung 1\", \"Voraussetzung 2\" ], \"bewerbung\": { \"frist\": \"\", \"prozess\": [ \"Schritt 1 der Bewerbung\", \"Schritt 2 der Bewerbung\" ], \"kontakt\": { \"telefon\": \"Telefonnummer\", \"email\": \"E-Mail-Adresse\", \"website\": \"Webseiten-Link\" } }, \"favicon\": \"path/to/image\" }, wenn du mehrere Förderungen findest gib ein array mit allen gefundenen Förderungen in diesem Datenmodell zurück, wrappe auch einzelne ergebnisse in ein array, %s, wähle die Kategorie aus den folgenden: Auslandsstipendiuem, Stipendium, Familie, Frauen, Sonstige, Forschungspreis, Talentförderung, Sozialhilfe, Vergünstigung";

    private final RestTemplate restTemplate;

    public MistralParser() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Foerderung[] parseFoerderung(String targetUrl) throws JsonProcessingException {
        return parseFoerderung(targetUrl, "");
    }

    public Foerderung[] parseFoerderung(String targetUrl, String additionalInformation) throws JsonProcessingException {
        String url = UriComponentsBuilder.fromHttpUrl(MISTRAL_ADDRESS).toUriString();

        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "mistral-large-latest");

        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", String.format(MESSAGE_TEMPLATE, targetUrl, additionalInformation));

        HttpHeaders headers = new HttpHeaders();
        String authToken = System.getenv("MISTRAL_BEARER");
        headers.add("Authorization", "Bearer " + authToken);

        payload.put("messages", new Map[]{message});
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
        String res = restTemplate.postForObject(url, requestEntity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(res);

        var targetJson = rootNode.path("choices").get(0).path("message").path("content").textValue();
        targetJson = targetJson.substring(targetJson.indexOf('['));

        return mapper.readValue(targetJson, Foerderung[].class);
    }
}
