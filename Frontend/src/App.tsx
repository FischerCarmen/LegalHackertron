import React, { useEffect, useState } from 'react';
import { CHeader, CHeaderBrand, CHeaderNav, CNavItem, CNavLink, CContainer, CRow, CCol, CCard, CCardBody, CCardHeader, CCardText, CFormInput, CFormSelect, CPagination, CPaginationItem } from '@coreui/react';
import Sidebar from './components/Sidebar';
import './App.css';

interface Bewerbung {
    frist: string;
    prozess: string[];
    kontakt: {
        telefon: string;
        email: string;
        website: string;
    };
}

interface Förderung {
    id: number;
    institution: string;
    name: string;
    beschreibung: string;
    betrag: string;
    währung: string | null;
    kategorie: string;
    voraussetzungen: string[];
    bewerbung: Bewerbung;
    favicon: string;
    scrapeUrl: string;
}

const App: React.FC = () => {
    const [data, setData] = useState<Förderung[]>([]);
    const [expanded, setExpanded] = useState<number | null>(null);
    const [search, setSearch] = useState<string>('');
    const [selectedCategory, setSelectedCategory] = useState<string>('');
    const [selectedSearchOption, setSelectedSearchOption] = useState<string>('');
    const [selectedKategorie, setSelectedKategorie] = useState<string>('');
    const [currentPage, setCurrentPage] = useState<number>(1);
    const itemsPerPage = 15;

    useEffect(() => {
        fetch('http://localhost:8080/foerderung')
            .then(response => response.json())
            .then(data => setData(data));
    }, []);

    const toggleExpand = (index: number) => {
        setExpanded(expanded === index ? null : index);
    };

    const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearch(event.target.value);
    };

    const handleCategoryChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedCategory(event.target.value);
    };

    const handleSearchOptionChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedSearchOption(event.target.value);
    };

    const highlightText = (text: string, highlight: string) => {
        if (!highlight.trim()) {
            return text;
        }
        const regex = new RegExp(`(${highlight})`, 'gi');
        return text.replace(regex, (match) => `<span class="highlight">${match}</span>`);
    };

    const handleKategorieChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedKategorie(event.target.value);
    };

    const filteredData = data.filter(förderung => {
        const searchValue = search.toLowerCase();
        const searchField = selectedSearchOption.toLowerCase();
        const fieldValue = (förderung as any)[searchField]?.toString().toLowerCase() || '';
        return fieldValue.includes(searchValue) && (selectedCategory === '' || förderung.institution === selectedCategory) &&
        (selectedKategorie === '' || förderung.kategorie === selectedKategorie);
    });

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = filteredData.slice(indexOfFirstItem, indexOfLastItem);

    const totalPages = Math.ceil(filteredData.length / itemsPerPage);

    const uniqueInstitutions = Array.from(new Set(data.map(förderung => förderung.institution))).sort();

    const uniqueKategorien = Array.from(new Set(data.map(förderung => förderung.kategorie))).sort();

    return (
        <div className="d-flex">
            <Sidebar />
            <div className="flex-grow-1">
                <CHeader>
                    <CContainer fluid>
                        <CHeaderBrand href="#">Home</CHeaderBrand>
                        <CHeaderNav>
                            <CNavItem>
                                <CNavLink href="#">Kontakt</CNavLink>
                            </CNavItem>
                            <CNavItem>
                                <CNavLink href="#">Über uns</CNavLink>
                            </CNavItem>
                        </CHeaderNav>
                    </CContainer>
                </CHeader>

                <CContainer className="mt-4">
                    <CRow>
                        <CCol>
                            <div className="d-flex justify-content-between mt-3">
                                <CFormSelect value={selectedSearchOption} onChange={handleSearchOptionChange}>
                                    <option value="" disabled>Suchoption auswählen</option>
                                    <option value="institution">Institution</option>
                                    <option value="name">Name</option>
                                    <option value="beschreibung">Beschreibung</option>
                                    <option value="betrag">Betrag</option>
                                    <option value="voraussetzungen">Voraussetzungen</option>
                                    <option value="bewerbung">Bewerbung</option>
                                    <option value="frist">Frist</option>
                                    <option value="prozess">Prozess</option>
                                    <option value="kontakt">Kontakt</option>
                                    <option value="telefon">Telefon</option>
                                    <option value="email">Email</option>
                                    <option value="website">Website</option>
                                </CFormSelect>
                                <CFormInput
                                    className="ms-3"
                                    type="text"
                                    placeholder={`Suche in ${selectedSearchOption}`}
                                    value={search}
                                    onChange={handleSearchChange}
                                />
                            </div>
                            <div className="d-flex justify-content-between mt-3">
                                <CFormSelect className="me-3" value={selectedCategory} onChange={handleCategoryChange}>
                                    <option value="">Alle Institutionen</option>
                                    {uniqueInstitutions.map((institution, index) => (
                                        <option key={index} value={institution}>{institution}</option>
                                    ))}
                                </CFormSelect>
                                <CFormSelect className="ms-3" value={selectedKategorie}
                                             onChange={handleKategorieChange}>
                                    <option value="">Alle Kategorien</option>
                                    {uniqueKategorien.map((kategorie, index) => (
                                        <option key={index} value={kategorie}>{kategorie}</option>
                                    ))}
                                </CFormSelect>
                            </div>
                            <hr className="my-4"/>
                            <h3 className="title">Liste der Förderungen</h3>
                            <hr className="my-4"/>
                            {currentItems.map((förderung, index) => (
                                <CCard key={index} className="mt-3" onClick={() => toggleExpand(index)}
                                       style={{cursor: 'pointer'}}>
                                    <CCardHeader
                                        dangerouslySetInnerHTML={{__html: highlightText(förderung.institution + ' - ' + förderung.name, selectedSearchOption === 'name' ? search : '')}}></CCardHeader>
                                    <CCardBody>
                                    <CCardText
                                            dangerouslySetInnerHTML={{ __html: highlightText(förderung.beschreibung, selectedSearchOption === 'beschreibung' ? search : '') }}>
                                        </CCardText>
                                        {expanded === index && (
                                            <div>
                                                <CCardText>Kategorie: {förderung.kategorie}</CCardText> {/* Display kategorie */}
                                                <CCardText>Betrag: {förderung.betrag} {förderung.währung || ''}</CCardText>
                                                <CCardText>Voraussetzungen:</CCardText>
                                                <ul>
                                                    {förderung.voraussetzungen.map((voraussetzung, i) => (
                                                        <li key={i}>{voraussetzung}</li>
                                                    ))}
                                                </ul>
                                                <CCardText>Bewerbungsfrist: {förderung.bewerbung.frist}</CCardText>
                                                <CCardText>Bewerbungsprozess:</CCardText>
                                                <ul>
                                                    {förderung.bewerbung.prozess.map((schritt, i) => (
                                                        <li key={i}>{schritt}</li>
                                                    ))}
                                                </ul>
                                                <CCardText>Kontakt:</CCardText>
                                                <CCardText>Telefon: {förderung.bewerbung.kontakt.telefon}</CCardText>
                                                <CCardText>Email: {förderung.bewerbung.kontakt.email}</CCardText>
                                                <CCardText>Website: <a href={förderung.bewerbung.kontakt.website}>{förderung.bewerbung.kontakt.website}</a></CCardText>
                                                <img src={förderung.favicon} alt={`${förderung.name} favicon`} />
                                                <CCardText>Datenquelle: <a href={förderung.scrapeUrl}>{förderung.scrapeUrl}</a></CCardText>
                                            </div>
                                        )}
                                    </CCardBody>
                                </CCard>
                            ))}
                            <CPagination className="mt-3">
                                <CPaginationItem disabled={currentPage === 1}
                                                 onClick={() => setCurrentPage(currentPage - 1)}>Previous</CPaginationItem>
                                {[...Array(totalPages)].map((_, i) => (
                                    <CPaginationItem key={i} active={i + 1 === currentPage}
                                                     onClick={() => setCurrentPage(i + 1)}>
                                        {i + 1}
                                    </CPaginationItem>
                                ))}
                                <CPaginationItem disabled={currentPage === totalPages}
                                                 onClick={() => setCurrentPage(currentPage + 1)}>Next</CPaginationItem>
                            </CPagination>
                        </CCol>
                    </CRow>
                </CContainer>
            </div>
        </div>
    );
}

export default App;
