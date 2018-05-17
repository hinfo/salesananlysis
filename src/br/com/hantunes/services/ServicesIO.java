package br.com.hantunes.services;

import br.com.hantunes.bean.Customer;
import br.com.hantunes.bean.Item;
import br.com.hantunes.bean.Sale;
import br.com.hantunes.bean.Salesman;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author henrique
 */
public class ServicesIO {

    public ArrayList readFile(String filename) {
        BufferedReader reader = null;
        ArrayList<String> lines = new ArrayList<>();

        try {
            File file = new File(filename);
            reader = new BufferedReader(new FileReader(filename));
            String line = null;

            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                } else if (line.isEmpty()
                        || !line.startsWith("001")
                        || !line.startsWith("002")
                        || !line.startsWith("003")) {
                    continue;
                }
                lines.add(line + "\n");
            }
            reader.close();
            file.delete();

        } catch (IOException e) {
            lines.add("Error");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return lines;

    }

    public void writeFile(String nameOfFile, ArrayList<String> dataFile) {

        try {
            File file = new File(nameOfFile);
            FileWriter fileOutput = new FileWriter(file);
            for (String data : dataFile) {
                fileOutput.write(data + "\n");
            }
            fileOutput.flush();
            fileOutput.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServicesIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServicesIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> processFile(ArrayList linesOfFile) {
        Sale sale = new Sale();
        ArrayList<String> dataFiles = new ArrayList<>();
        Customer customer = null;
        Salesman salesman = null;
        double mostExpensive = 0;
        double idMostExpensive = 0;
        String nameOfWorst = "";
        int countCustomers = 0;
        int countSalesman = 0;
        ArrayList<Item> itensSale = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Salesman> sellers = new ArrayList<>();
        ArrayList<Sale> sales = new ArrayList<>();

        for (int i = 0; i < linesOfFile.size(); i++) {

            String line = linesOfFile.get(i).toString();
            String[] fields;
            fields = line.split("ç");
            int typeOfID = Integer.parseInt(fields[0]);

            switch (typeOfID) {
                case 1:
                    salesman = new Salesman();
                    salesman.setID(Integer.parseInt(fields[0]));
                    salesman.setDocumentID(fields[1]);
                    salesman.setName(fields[2]);
                    salesman.setSalary(BigDecimal.valueOf(Double.parseDouble(fields[3])));
                    countSalesman += 1;
                    sellers.add(salesman);
                    break;
                case 2:
                    customer = new Customer();
                    customer.setID(Integer.parseInt(fields[0]));
                    customer.setDocumentID(fields[1]);
                    customer.setName(fields[2]);
                    customer.setBusinessArea(fields[3]);
                    countCustomers += 1;
                    customers.add(customer);
                    break;
                case 3:
                    sale = new Sale();
                    sale.setID(Double.parseDouble(fields[1]));
                    sale.setSalesmanName(fields[3]);
                    double amountOfItem = 0;
                    double amountOfItens = 0;
                    String fieldItens = fields[2].replace("[", "").replace("]", "");
                    String[] itens = fieldItens.split(",");

                    for (String iten : itens) {
                        Item item = new Item();
                        String[] dataField = iten.split("-");
                        item.setId(Double.parseDouble(dataField[0]));
                        item.setPrice(Double.parseDouble(dataField[2]));
                        item.setQuantity(Integer.valueOf(dataField[1]));
                        amountOfItem = item.getQuantity() * item.getPrice();
                        amountOfItens += amountOfItem;
                        itensSale.add(item);
                        sale.setItens(itensSale);
                        sale.setAmountOfSale(amountOfItens);
                    }
                    sales.add(sale);
                    break;
                default:
                    break;
            }

        }
        dataFiles.add("Amount of clients: " + countCustomers);
        dataFiles.add("Amount of Salesman: " + countSalesman);
        double amountAux = -999999;
        for (int i = 0; i < sales.size(); i++) {
//            log("Sale ID: " + sales.get(i).getID());
//            log("amount: " + sales.get(i).getAmountOfSale());
//            log("amountAux: " + amountAux);
//            log("Salesman: " + sales.get(i).getSalesmanName());
            nameOfWorst = sales.get(i).getSalesmanName();
            if (sales.get(i).getAmountOfSale() > amountAux) {
                amountAux = sales.get(i).getAmountOfSale();
                mostExpensive = amountAux;
                idMostExpensive = sales.get(i).getID();
                if (i == 0) {
                    nameOfWorst = sales.get(i).getSalesmanName();
                } else {
                    nameOfWorst = sales.get(i - 1).getSalesmanName();
                }
            }
        }
        dataFiles.add("Most Expensive Sale ID: " + idMostExpensive);
        dataFiles.add("Most Expensive Sale: " + mostExpensive);
        dataFiles.add("Worst: " + nameOfWorst);
//            log("*****************************");
//            log("Most Expensive Sale ID: " + idMostExpensive);
//            log("Most Expensive Sale: " + mostExpensive);
//            log("The best salesman: " + nameOfBest);
//            log("Worst: " + nameOfWorst);
//            log("*****************************");
        return dataFiles;
    }

    public void log(String string) {
        System.out.println(string);
    }

}
