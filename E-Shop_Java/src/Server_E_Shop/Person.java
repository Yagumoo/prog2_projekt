package Server_E_Shop;

public class Person {
  String vorname;
  String nachname;
  String email;
  int id;
  String username;
  String password;

  public Person(String vorname, String nachname, String email, String username, String password, int id){
      this.vorname = vorname;
      this.nachname = nachname;
      this.email = email;
      this.id = id;
      this.username = username;
      this.password = password;
  }

  public void printAll(){
      System.out.println();
  }
}
