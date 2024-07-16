package eshop.common.enitities;
/**
 * Repräsentiert eine allgemeine Person im E-Shop.
 *
 * <p>Die {@code Person}-Klasse ist eine abstrakte Basisklasse, die die gemeinsamen Eigenschaften und Methoden für alle Personen im E-Shop definiert.</p>
 */
public abstract class Person {
  private String vorname;
  private String nachname;
  private String email;
  private int id;
  private String username;
  private String password;
  public static int idzeahler = 1;
  /**
   * Konstruktor für die {@code Person}-Klasse.
   *
   * <p>Initialisiert eine neue Person mit den angegebenen Werten für Vorname, Nachname, E-Mail, Benutzername und Passwort.</p>
   *
   * @param vorname    der Vorname der Person
   * @param nachname   der Nachname der Person
   * @param email      die E-Mail-Adresse der Person
   * @param username   der Benutzername der Person
   * @param password   das Passwort der Person
   */
  public Person(String vorname, String nachname, String email, String username, String password) {
    this.vorname = vorname;
    this.nachname = nachname;
    this.email = email;
    this.id = idzeahler++;
    this.username = username;
    this.password = password;
  }
  /**
   * Konstruktor für die {@code Person}-Klasse mit ID.
   *
   * <p>Initialisiert eine neue Person mit den angegebenen Werten für Vorname, Nachname, E-Mail, Benutzername, Passwort und ID.</p>
   *
   * <p>Die ID wird auf den maximalen Wert erhöht, wenn der übergebene ID-Wert größer als der aktuelle {@code idzaehler} ist.</p>
   *
   * @param vorname    der Vorname der Person
   * @param nachname   der Nachname der Person
   * @param email      die E-Mail-Adresse der Person
   * @param username   der Benutzername der Person
   * @param password   das Passwort der Person
   * @param id         die eindeutige ID der Person
   */
  public Person(String vorname, String nachname, String email, String username, String password, int id) {
    this.vorname = vorname;
    this.nachname = nachname;
    this.email = email;
    if(id > idzeahler) {
      idzeahler = id + 1;
    }
    this.id = id;
    this.username = username;
    this.password = password;
  }

  /**
   * Gibt den Vornamen der Person zurück.
   *
   * @return der Vorname der Person
   */
  public String getVorname() {
    return vorname;
  }
  /**
   * Setzt den Vornamen der Person.
   *
   * @param vorname der neue Vorname der Person
   */
  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  // Getter und Setter für Nachname
  /**
   * Gibt den Nachnamen der Person zurück.
   *
   * @return der Nachname der Person
   */
  public String getNachname() {
    return nachname;
  }
  /**
   * Setzt den Nachnamen der Person.
   *
   * @param nachname der neue Nachname der Person
   */
  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  // Getter und Setter für Email
  /**
   * Gibt die E-Mail-Adresse der Person zurück.
   *
   * @return die E-Mail-Adresse der Person
   */
  public String getEmail() {
    return email;
  }
  /**
   * Setzt die E-Mail-Adresse der Person.
   *
   * @param email die neue E-Mail-Adresse der Person
   */
  public void setEmail(String email) {
    this.email = email;
  }

  // Getter und Setter für ID
  /**
   * Gibt die ID der Person zurück.
   *
   * @return die ID der Person
   */
  public int getId() {
    return id;
  }
  /**
   * Setzt die ID der Person.
   *
   * @param id die neue ID der Person
   */
  public void setId(int id) {
    this.id = id;
  }

  // Getter und Setter für Username
  /**
   * Gibt den Benutzernamen der Person zurück.
   *
   * @return der Benutzername der Person
   */
  public String getUsername() {
    return username;
  }
  /**
   * Setzt den Benutzernamen der Person.
   *
   * @param username der neue Benutzername der Person
   */
  public void setUsername(String username) {
    this.username = username;
  }

  // Getter und Setter für Passwort
  /**
   * Gibt das Passwort der Person zurück.
   *
   * @return das Passwort der Person
   */
  public String getPassword() {
    return password;
  }
  /**
   * Setzt das Passwort der Person.
   *
   * @param password das neue Passwort der Person
   */
  public void setPassword(String password) {
    this.password = password;
  }
  // Vergleichsmethode für den Benutzernamen

  /**
   * Vergleicht den Benutzernamen der Person mit einem angegebenen Filter.
   *
   * <p>Die Überprüfung erfolgt unabhängig von der Groß- und Kleinschreibung.</p>
   *
   * @param filter der Filter-String zum Vergleich
   * @return {@code true}, wenn der Benutzername mit dem Filter übereinstimmt, andernfalls {@code false}
   */
  public boolean vergleich(String filter) {
    return username != null && username.equalsIgnoreCase(filter);  // Überprüft den Username
  }
  /**
   * Überprüft, ob das übergebene Passwort mit dem gespeicherten Passwort übereinstimmt.
   *
   * <p>Vergleicht das übergebene Passwort mit dem gespeicherten Passwort und gibt {@code true} zurück, wenn sie übereinstimmen.</p>
   *
   * @param password das Passwort, das überprüft werden soll
   * @return {@code true}, wenn das übergebene Passwort mit dem gespeicherten Passwort übereinstimmt, andernfalls {@code false}
   */
  public boolean checkPasswort(String password) {
    return this.password.equals(password);
  }
  /**
   * Gibt eine String-Darstellung der Person zurück.
   *
   * <p>Zeigt den Namen, den Benutzernamen und die ID der Person an.</p>
   *
   * @return eine String-Darstellung der Person im Format "Name: Vorname Nachname |Username: Benutzername |ID: ID"
   */
  @Override
  public String toString() {
    return "Name: " + vorname + " " + nachname + " |Username: " + username + " " + "|ID: " + id;
  }
}
