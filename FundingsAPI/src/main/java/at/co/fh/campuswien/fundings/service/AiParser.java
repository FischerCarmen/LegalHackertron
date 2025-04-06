package at.co.fh.campuswien.fundings.service;

import at.co.fh.campuswien.fundings.enitity.Foerderung;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AiParser {
    Foerderung parseFoerderung(String targetUrl) throws JsonProcessingException;

    Foerderung parseFoerderung(String targetUrl, String additionalInformation) throws JsonProcessingException;
}
