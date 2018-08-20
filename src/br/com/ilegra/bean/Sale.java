package br.com.ilegra.bean;

import java.util.ArrayList;

/**
 *
 * @author henrique
 */
public class Sale {

    private double ID;
    private String salesmanName;
    private String customerName;
    private ArrayList<Item> itens;
    private double amountOfSale;

    public double getID() {
        return ID;
    }

    public void setID(double ID) {
        this.ID = ID;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<Item> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Item> itens) {
        this.itens = itens;
    }

    public double getAmountOfSale() {
        return amountOfSale;
    }

    public void setAmountOfSale(double amountOfSale) {
        this.amountOfSale = amountOfSale;
    }

}
