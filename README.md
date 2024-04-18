 E-Shop_Prog2
 E-Shop Projekt für Prog2 mit Java
Intelli H4cks: 
- psvm

Mitglieder: Maxi, Simon, Marvin

TUTORIAL:
Name neben die Aufgabe schreiben und eintragen, ob die noch in Arbeit ist oder fertig.
Wenn man bei der Aufgabe hilft oder auch daran gearbeitet hat, dann seinen Namen hinzufügen

Bsp:
- Klasse Mitarbeiter erstellen: ~Simon (In bearbeitung)
- Klasse Kunde erstellen: ~ Marvin & Maxi (100%)

/**
  Diese Kommentare NICHT VERGESSEN
  WICHTIG: 
  Alles muss mit zb: @int (Name der Variable) Erklärung wofür sie da ist, versehen werden
  Ist für Java Doc
*/

Veränderungen
-Marvon hat Variablennamen verändert ----> bzeichnung zu artikelBezeichnung

Aufgaben:
- E-Shop Funktionen:
- Artikelklasse
- Personklasse & Kundenklasse
- Warenkorbklasse
- Bestand (Artikelliste)
- Kundenverwaltung------------------------------------------------(Maxi angefangen 40%)
- Mitarbeiterverwaltung
- Shopping Service
- GUI/CUI Funktionen(Shop Client)---------------------------------(Maxi angefangen 10%)
- 
- Speicher für Artikel
  - Jede Ein & Auslagerung wird als Ereignis mit Datum(Jahrestagsnummer) Mit Artikel(Nummer), sowie Mitarbeiter(Einlagern) und Kunde(gekauft)

-Exceptions
 - Doppelte nicht möglich
 - Mathe regelen /0 = NO GO
 - Abfangen von Fehleingaben zb. Preis int statt String usw
 - 

- Artikelklasse
   - Artikelnummer (Eindeutig)
   - Artikelbezeichnung
   - Bestand
   - Preis

- Personenklasse-------------------------------------------------------(Maxi angefangen)
   - Name
   - ID (Eindeutig)
   - Username (Eindeutig)
   - Passwort
   - E-mail (Eindeutig)

 - Mitarbeiterklasse extends Person
    - E-mail erstellen für Mitarbeiter

- Kundenklasse extends Person-------------------------------------------------------(Maxi angefangen)
  - Adresse(PLZ, Stadt, Ort, Straße)
  - E-mail für sich selber

- Warenkorb(Speicher für Kunde)
  - Mehrer Artikel
  - Stückzahl ändern
  - Artikel entfernen
  - Warenorb leeren
  - Kaufen
  - Artikel werden aus dem Bestand entfernt

- Ausgabe
  - Rechnung(Kunde, Datum, gekaufte Artikel inkl Stückzahl, Preis und Gesammt Preis Wie Kassenbon)

- Server:
- KundenFunktionKlasse 
   - Login Daten speichern
      - Login mit Username oder Email + Passwort
   - Registieren
      - Mit gültiger E-mail anmelden
      - Passwort erstellen
      - Username erstellen
      - ID(Erhöht sich automatisch)
      - Alles speichern, um sich nochmal ein zu loggen
   - Kundendaten aufrufen lassen
   - KundenDaten speichern(E-mail, Username, name, addresse, ID)
      - Speichert Daten in einer ArrayList
     
- MitarbeiterFunktionKlasse
   - Bereits Registrierter Mitarbeiter kann weitere Mitarbeiter Registieren
   - Einfügen von: Vorname, Nachname, Username, E-mail(FirmenMail), Passwort, ID(Automatisch hochzählen)
   - Daten Speichern, damit sich der neue Mitarbeiter selber anmelden kann (Username/E-mail + Passwort)
      - Speichert Daten in einer ArrayList
   - Mitarbeiter kann neue Artikel hinzufügen (Ruft ArtikelFunktionKlasse auf)
      - Kann bei bestehenden Artikeln den Bestand erhöhen 
      - Kann Artikel löschen
        
- ArtikelFunktionKlasse Wird von MitarbeiterFunktion, Warenkorb aufgerufen
- Erstellt mithilfe vom Konstruktor aus ArtikelKlasse einen artikel und speichert diesen in einer ArrayList
   - Artikelnummer, Bezeichnung, Preis, bestand
   - Artikel Liste Aufrufen
-
-
-

- GUI:

-Mitarbeiter Funktionen
- Nur Mitarbeiter kann weitern Mitarbeiter Registrieren 
 - Mitarbeiter einfügen/Registrieren
 - Neue Artikel einfügen
 - Bestand existierender verändern
 - (Mitarbeiter kann Ereignisliste sehen)

   
-Kunden Funktionen
 - Registrieren & Einloggen
 - Mehrere Artikel in Warenkorb legen
 - Artikel anzahl im Warenkorb verändern
 - Kann alle Artikel entfernen
 - Kann Artikel kaufen(Werden aus dem Korb entfernt & im Bestand verändert)
 - Bekommt Rechnung
-
-
-


