package eshop.enitities;

public abstract class Person {
  private String vorname;
  private String nachname;
  private String email;
  private int id;
  private String username;
  private String password;
  private int idzeahler = 1;

  public Person(String vorname, String nachname, String email, String username, String password, int id) {
      this.vorname = vorname;
      this.nachname = nachname;
      this.email = email;
      this.id = id;
      this.username = username;
      this.password = password;
      this.idzeahler++;
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

    public int getIdzeahler(){
     return this.idzeahler;
    }

    public boolean checkPasswort(String password) {
      return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Name: " + vorname + " " + nachname + " |Username: " + username + " " + "|ID: " + id;
    }
}
