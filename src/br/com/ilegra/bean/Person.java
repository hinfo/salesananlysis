package br.com.ilegra.bean;

/**
 *
 * @author henrique
 */
public class Person {
    private int ID;
    private String documentID;
    private String name;

    public Person() {
    }

    public Person(int ID, String documentID, String name) {
        this.ID = ID;
        this.documentID = documentID;
        this.name = name;
    }

    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
