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

Aufgaben:
- E-Shop Funktionen:
- Artikelklasse
- Personklasse & Kindenklasse
- Warenkorbklasse
- Bestand (Artikelliste)
- Kundenverwaltung
- Mitarbeiterverwaltung
- Shopping Service
- GUI Funktionen(Shop Client)
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
-
-
-
-
-
-
-
-
-
-
-
-
-
-
-
-
-
-
-
-
- GUI:
- Einloggen Möglichkeit
-Mitarbeiter Funktionen
 - Mitarbeiter einfügen/Registrieren
 - Neue Artikel einfügen
 - Bestand existierender verändern
 - (Mitarbeiter kann Ereignisliste sehen)

   
-Kunden Funktionen
 - Mehrere Artikel in Warenkorb legen
 - Artikel anzahl im Warenkorb verändern
 - Kann alle Artikel entfernen
 - Kann Artikel kaufen(Werden aus dem Korb entfernt & im Bestand verändert)
 - Bekommt Rechnung
-
-
-


