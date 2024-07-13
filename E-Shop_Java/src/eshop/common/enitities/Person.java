package eshop.common.enitities;

public abstract class Person {
  private String vorname;
  private String nachname;
  private String email;
  private int id;
  private String username;
  private String password;
  public static int idzeahler = 1;

  /**
   * @param vorname ist der Vorname vom Benutzer
   * @param nachname ist der Nachname vom Benutzer
   * @param email ist die E-mail vom Benutzer
   * @param username ist der Benutzername vom Benutzer
   * @param password ist das Password vom Benutzer
   * */
  public Person(String vorname, String nachname, String email, String username, String password) {
    this.vorname = vorname;
    this.nachname = nachname;
    this.email = email;
    this.id = idzeahler++;
    this.username = username;
    this.password = password;
  }

  public Person(String vorname, String nachname, String email, String username, String password, int id) {
    this.vorname = vorname;
    this.nachname = nachname;
    this.email = email;
    //TODO: Richtige ID vergabe
    if(id > idzeahler) {
      idzeahler = id + 1;
    }
    this.id = id;
    this.username = username;
    this.password = password;
  }

  // Getter und Setter für Vorname
  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  // Getter und Setter für Nachname
  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  // Getter und Setter für Email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  // Getter und Setter für ID
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  // Getter und Setter für Username
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  // Getter und Setter für Passwort
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean vergleich(String filter) {
    return username != null && username.equalsIgnoreCase(filter);  // Überprüft den Username
  }

  public boolean checkPasswort(String password) {
    return this.password.equals(password);
  }

  @Override
  public String toString() {
    return "Name: " + vorname + " " + nachname + " |Username: " + username + " " + "|ID: " + id;
  }
}
