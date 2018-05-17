package br.com.hantunes.bean;

/**
 *
 * @author henrique
 */
public class Customer extends Person{
   String businessArea;

    public Customer() {
        super();
    }

    public Customer(int ID, String documentID, String name, String businessArea) {
        super(ID, documentID, name);
        this.businessArea = businessArea;
    }
    

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }
   
}
