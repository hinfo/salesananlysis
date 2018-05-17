package br.com.hantunes.bean;

import java.math.BigDecimal;

/**
 *
 * @author henrique
 */
public class Salesman extends Person {

    private BigDecimal salary;

    public Salesman() {
    }

    ;

    public Salesman(int ID, String documentID, String name) {
        super(ID, documentID, name);
    }

    public Salesman(int ID, String doc, String name, BigDecimal salary) {
        super(ID, doc, name);
        this.salary = salary;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Salesman:"
                + "ID " + getID() + "\n"
                + "Name: " + getName() + "\n"
                + "Document " + getDocumentID() + "\n";
    }

}
