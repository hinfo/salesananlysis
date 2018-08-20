package br.com.ilegra.services;

import br.com.ilegra.bean.Customer;
import br.com.ilegra.bean.Item;
import br.com.ilegra.bean.Sale;
import br.com.ilegra.bean.Salesman;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
 * Reading and processing files raw data in .dat files
 * Maybe if files was in a json file it's better to read
 */
public class ServicesIO implements Services {

    private String FIELDS_SPLIT = "ç";
    private String ITEM_SPLIT = "-";
    private String FIELD_ITEM_SPLIT = ",";
    private String PATTERN_COD = "00";

    @Override
    public ArrayList<String> processFile(ArrayList linesOfFile) {
        ArrayList<String> dataFiles = new ArrayList<>();
        Sale sale = new Sale();
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
            fields = line.split(FIELDS_SPLIT);
            int typeOfID = Integer.parseInt(fields[0]);
            switch (typeOfID) {
                case 1:
                    salesman = new Salesman();
                    salesman.setID(Integer.parseInt(fields[0]));
                    salesman.setDocumentID(fields[1]);
                    salesman.setName(fields[2]);
                    salesman.setSalary(BigDecimal.valueOf(Double.parseDouble(fields[3])));
                    countSalesman++;
                    sellers.add(salesman);
                    break;
                case 2:
                    Customer customer = new Customer();
                    customer.setID(Integer.parseInt(fields[0]));
                    customer.setDocumentID(fields[1]);
                    customer.setName(fields[2]);
                    customer.setBusinessArea(fields[3]);
                    countCustomers++;
                    customers.add(customer);
                    break;
                case 3:
                    sale = new Sale();
                    sale.setID(Double.parseDouble(fields[1]));
                    sale.setSalesmanName(fields[3]);
                    double amountOfItem = 0.0;
                    double amountOfItens = 0.0;
                    //Separeting fields.
                    String fieldItens = fields[2].replace("[", "").replace("]", "");
                    //Separeting itens on fields
                    String[] itens = fieldItens.split(FIELD_ITEM_SPLIT);

                    for (String itemOnLine : itens) {
                        Item item = new Item();
                        String[] dataField = itemOnLine.split(ITEM_SPLIT);
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
        return dataFiles;
    }

    @Override
    public void writeFile(String nameOfFile, ArrayList<String> dataFile) {

        try {
            File file = new File(nameOfFile);
            FileWriter fileOutput = new FileWriter(file);
            for (String data : dataFile) {
                fileOutput.write(data + "\n");
            }
            fileOutput.flush();
            fileOutput.close();
        } catch (IOException ex) {
            System.out.println("Error while saving data to output file!\n" + ex);
        }
    }

    @Override
    public ArrayList<String> readFile(String filename) {
        BufferedReader reader = null;
        ArrayList<String> lines = new ArrayList<>();
        File file = new File(filename);

        try {
            String line = "";
            reader = new BufferedReader(new FileReader(filename));

            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                } else if (line.isEmpty()){
                    continue;
                } 
                else if (line.startsWith(PATTERN_COD)){
                    lines.add(line + "\n");
                } 
                
            }
            reader.close();
            file.delete();
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found!\n" + ex);
        } catch (IOException ex) {
            System.out.println("Error while reading file!\n" + ex);
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

    public void log(String string) {
        System.out.println(string);
    }

}
