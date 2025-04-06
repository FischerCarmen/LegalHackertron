package at.co.fh.campuswien.fundings.enitity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Foerderung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String institution;

    private String name;

    private String beschreibung;

    private String betrag;

    private String waehrung;

    private String kategorie;

    @ElementCollection
    private List<String> voraussetzungen;

    @Embedded
    private Bewerbung bewerbung;

    private String favicon;
    private String scrapeUrl;

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBetrag() {
        return betrag;
    }

    public void setBetrag(String betrag) {
        this.betrag = betrag;
    }

    public String getWaehrung() {
        return waehrung;
    }

    public void setWaehrung(String waehrung) {
        this.waehrung = waehrung;
    }

    public List<String> getVoraussetzungen() {
        return voraussetzungen;
    }

    public void setVoraussetzungen(List<String> voraussetzungen) {
        this.voraussetzungen = voraussetzungen;
    }

    public Bewerbung getBewerbung() {
        return bewerbung;
    }

    public void setBewerbung(Bewerbung bewerbung) {
        this.bewerbung = bewerbung;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getScrapeUrl() {
        return scrapeUrl;
    }

    public void setScrapeUrl(String scrapeUrl) {
        this.scrapeUrl = scrapeUrl;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }
}

@Embeddable
class Bewerbung {

    private String frist;

    @ElementCollection
    private List<String> prozess;

    @Embedded
    private Kontakt kontakt;

    // Getter und Setter
    public String getFrist() {
        return frist;
    }

    public void setFrist(String frist) {
        this.frist = frist;
    }

    public List<String> getProzess() {
        return prozess;
    }

    public void setProzess(List<String> prozess) {
        this.prozess = prozess;
    }

    public Kontakt getKontakt() {
        return kontakt;
    }

    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }
}

@Embeddable
class Kontakt {

    private String telefon;

    private String email;

    private String website;

    // Getter und Setter
    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
