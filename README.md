
# "Turistguide 1-2-3" - Matador

---

# Full Stack Skoleprojekt: Turistguide (Matador)
Dette dokument giver en oversigt over det aktuelle projekt, "matador", i forhold til kravene specificeret i "Turistguide 1" (Java backend), "Turistguide 2" (Web Frontend) og "Turistguide 3" (database + deployment). 
Projektet er en Spring Boot-applikation designet til at levere en webbrugerflade for administration af turistattraktioner.

I vores projekt har vi arbejdet med konceptet om Matador spillet som en turistguide. Hjemmesiden skal betragtes som en Matador-inspireret turistguide igennem København med udgangspunkt i spillets felter.

## Github Build Status
[![Java CI with Maven](https://github.com/jsdsdal/matador/actions/workflows/main-tests.yml/badge.svg)](https://github.com/jsdsdal/matador/actions/workflows/main-tests.yml)
## Overordnet
Vi har bestræbet os på at gennemføre kravene specificeret i opgaveformuleringen. Derudover har vi også forsøgt os at udvide dem efter vores egne visioner om hvad projektet kan blive til. Til dette formål har vi udspecificeret vores "diverse" krav nederst for siden. 

**"Matador"** konceptet i vores produkt er udarbejdet af en idé om hvordan vi forestillede os en turistguide kunne se ud

Hvis du står som udkantsdansker og ikke har den store kendskab til København andet end hvad der ses i fjernsynet og i nyhederne, så kender du om ikke andet helt sikkert Matador spillet. 
Vores mission er at bruge det klassiske Matador spil som en turistguide til dem længere væk fra storbyens omegn. Idet du følger turistguiden, vil den tage udgangspunkt i de veje der allerede kendes i spillet. Befinder du dig på Bernstorffsgade, så husker du nok at det er en af de mørkeblå gader tæt på Rådhuspladsen. Det er heldigvis ikke så langt fra virkeligheden, og du vil undervejs se hvilket andre højdepunkter befinder sig på samme vej - bl.a Tivoli og Hovedbanegården. Herfra kan du også tilføje hvad du selv lige finder, så de næste bliver inspireret.
På denne måde kortlægger du København som en fremmede by med noget familiært og nemt at huske, og forhåbentlig lægger op til en hyggelig snak derhjemme næste gang man kaster terningen på det gamle familiearvede Matador bræt, og bemærker at det var lige dén gade fra spillepladen hvor i fandt en lækker pølsevogn i København.

Nedenfor er en gennemgang af opgavekravene af underviserne med beskrivelser af hvordan vi har gennemført dem. 

---
## Detaljeret gennemgang af krav - "Turistguide 1."

### 1. Opret Spring Boot projekt
- **Status: OPFYLDT**
- **Detaljer:** Der er oprettet et Spring Boot projekt efter specifikationerne i opgavekravene. Herunder har vi `TouristRepository` med `@Repository` annotationen (en ArrayList i denne udgave - se [sammenligning her](https://github.com/TuristguideMatador/matador/compare/4368a15cf449d8e6b501775ee3b9ea903f931609...afa0defdb5c1b70301bd3b7510695293762275bc).Ligeledes er `TouristService` med `@Service` annotationen oprettet, samt `TouristController` med `@Controller` annotation.

### 2. Test af CRUD endpoints
- **Status: OPFYLDT**
- **Detaljer:** Vi har følgende endpoints:
-   **GET** `"/"` → hjem / index
-   **GET** `"/attractions"` → viser alle attraktioner
-   **GET** `"/{name}/tags"` → viser tags for attraktion
-   **GET** `"/attractions/add"` → form til at tilføje en attraktion
-   **POST** `"/attractions/add"` → tilføjer attraktion, herefter redirect efter tilføjelse
-   **POST** `"/attractions/delete/{name}"` → slet, herefter redirect til liste efter delete
-   **GET** `"/attractions/getDelete/{name}"` → bekræft sletning
-   **GET** `"/attractions/edit/{name}"` → opdater attraktion, henter attraktion
-   **POST** `"/attractions/edit/{name}"` → postere opdateringen, herefter redirect til liste

### 3. Velkomstside
- **Status: OPFYLDT**
- **Detaljer:** Den nuværende hjemside (`index.html`) er designet i [Miro](https://miro.com/app/board/uXjVGHaWmKg=/?share_link_id=242641054624) som en wireframe og efterlignet så vidt muligt i vores HTML+CSS.


---
## Detaljeret gennemgang af krav - "Turistguide 2."

### 1. Introduktion & Læringsmål (HTML, Thymeleaf, CSS)
*   **Status:** **OPFYLDT**
*   **Detaljer:** Projektet anvender Spring Boot med Thymeleaf til dynamiske HTML-sider og har CSS-styling (`main.css`) korrekt integreret. Controller-metoder returnerer visningsnavne, og data overføres via `Model`-objekter, hvilket erstatter `ResponseEntity` for HTML-visninger som specificeret.

### 2. Spring Boot Projektopsætning
*   **Status:** **OPFYLDT (med mindre afvigelse i navn)**
*   **Detaljer:** Projektet er en Spring Boot-applikation med Maven, og `pom.xml` inkluderer `spring-boot-starter-thymeleaf` og `spring-boot-starter-webmvc`. Pakkestrukturen er konsistent. Projektnavnet er dog "matador" i stedet for "TouristGuide". Forældreversionen af Spring Boot i `pom.xml` er opdateret til `3.2.3` for at sikre en stabil og moderne base.

### 3. Visning af alle turistattraktioner (attractionList.html)
*   **Status:** **OPFYLDT**
*   **Detaljer:** Endpoint `/attractions` (GET) viser `attractionList.html` med alle turistattraktioner i en tabel. Attraktionens navn, beskrivelse, lokation og tags vises. CSS (`main.css`) er korrekt inkluderet. En "Tilføj attraktion"-knap er nu tilføjet i bunden af siden.

### 4. Visning af tags for turistattraktioner (tags.html)
*   **Status:** **OPFYLDT**
*   **Detaljer:** `attractionList.html` indeholder en "Tags"-knap for hver attraktion, der navigerer til endpointet `/{name}/tags`. Dette endpoint viser `tags.html`, som korrekt præsenterer tags for den valgte attraktion samt en "Tilbage"-knap.

### 5. Oprettelse af turistattraktion (attraction-creation-form.html)
*   **Status:** **OPFYLDT**
*   **Detaljer:** Projektet understøtter oprettelse af nye turistattraktioner via et GET-endpoint (`/attractions/add`), som viser `attraction-creation-form.html`. Formularen tillader indtastning af navn, beskrivelse, lokation (dynamisk dropdown) og tags (dynamiske checkboxes). Data gemmes via et POST-endpoint (`/attractions/add`). `TouristAttraction`-modellen inkluderer `location` og en liste af `Tags`.

### 6. Ændring af turistattraktion (updateAttraction.html)
*   **Status:** **OPFYLDT**
*   **Detaljer:** `attractionList.html` inkluderer en "Opdater"-knap, der fører til endpointet `/attractions/edit/{name}`. Her vises `updateAttraction.html`, hvor navn er skrivebeskyttet, men beskrivelse, lokation og tags kan ændres. Ændringer gemmes via et POST-endpoint (`/attractions/edit/{name}`).

### 7. Sletning af turistattraktion
*   **Status:** **OPFYLDT**
*   **Detaljer:** En "Slet"-knap er tilgængelig på `attractionList.html`, som navigerer til en bekræftelsesside (`confirmDelete.html`). Efter bekræftelse slettes attraktionen via et POST-endpoint (`/attractions/delete/{name}`).

### 8. Web Layer Slice test af Controlleren
*   **Status:** **OPFYLDT (verifikation påkrævet/udvidelse anbefales)**
*   **Detaljer:** Filen `TouristControllerTest.java` eksisterer og anvender `@WebMvcTest` og `@MockitoBean`, hvilket indikerer korrekt opsætning for web layer slice testing med MockMVC og Mockito. Nogle kernefunktionaliteter (getAllAttractions, showTouristAttractionCreationForm, getTagsForTouristAttraction, addTouristAttraction) er testet. Dog mangler der tests for sletning og opdatering, samt at de eksisterende placeholder-tests (`register()`, `ShouldDeleteByName()`, `getUpdateAttraction()`, `updateAttraction()`) implementeres fuldt ud.

### 9. Ekstraopgaver
*   **Status:** **IKKE IMPLEMENTERET (som forventet)**
*   **Detaljer:** De specificerede ekstraopgaver (HTML/CSS-forbedringer, billetpris) er ikke implementeret, men disse var heller ikke kernekrav.

---
## Detaljeret gennemgang af krav - "Turistguide 3."

### 1. Databasedesign (ER diagram)
- **Status: OPFYLDT**
- Detaljer: Designet og udfyldt i [LucidChart](https://lucid.app/lucidchart/5ad35a05-2998-4c85-930f-d1cba19accae/edit?viewport_loc=140%2C-351%2C1705%2C865%2C0_0&invitationId=inv_41ea410b-dedf-4388-b45c-8520290a1703) mhb. på normmalisering af database.
### 2. Implementering af JDBCTemplate (MySQL database)
- **Status: OPFYLDT**
- **Detaljer:** `TouristRepository`klassen er omskrevet fra brug af hardcoded arraylist til SQL db queries + JDBCTemplate mhb. på at overholde CRUD.
### 3. MySQL Database
- **Status: OPFYLDT**
- **Detaljer:** `data.sql`og `schema.sql`er nu en del af vores projektstruktur og bliver anvendt i forb. med vores database deployed på Microsoft Azure MySQL.
### 4. CI/CD workflow
- **Status: OPFYLDT**
- **Detaljer:** Github workflows er rettet til at sammensætte tests og deployment til Azure. Ydermere er dokumentationen vedlagt fra Qodana i vores repository.
### 5. Deployment 🎉
- **Status: OPFYLDT**
- **Detaljer:** Webapp hosting + DB er hosted på Microsoft Azure.
---
## Detaljeret gennemgang af krav - "Diverse."

### 1. Wireframe og designsprog
- **Status: OPFYLDT**
- **Detaljer:** På [Miro.com](https://miro.com/app/board/uXjVGHaWmKg=/?share_link_id=412386312900) har vi optegnet en wireframe med de specifikationer vi kunne tænke os. Der er bestræbet her på at holde det simpelt og funktionelt fremfor visuelt banebrydende. Derudover er der defineret hex koder til de farver vi kunne tænke os at bruge igennem websiden.

### 2. Matador koncept
- **Status: OPFYLDT**
- **Detaljer:** Vi har udarbejdet projektet efter en ambition at lave turistguiden indrettet efter Matadorspillet. Dette afspejles heri af alle lokationerne er fra brættets farvede felter, samt det designsprog af CSS og HTML vi har udviklet efter Matadors spilleplade og æstetik.

