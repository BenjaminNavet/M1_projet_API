package fr.istic.goodenough.ccn.api.data;

import com.opencsv.CSVReader;
import fr.istic.goodenough.ccn.api.engine.Customer;
import fr.istic.goodenough.ccn.api.engine.CustomerImpl;
import fr.istic.goodenough.ccn.api.engine.Product;
import fr.istic.goodenough.ccn.api.engine.ProductImpl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhonyData {

    /** Generate a map of users from CSV data
     * @return map containing customers objects with customers id as key */
    public static Map<String,Customer> generatePhonyCustomers(){
        Map<String, Customer> phonyCustomers = new HashMap<>();
        List<String[]> csvContent = readCSV("phonyCustomers.csv");
        if (csvContent != null){
            for (String[] line: csvContent) {
                phonyCustomers.put(line[0], new CustomerImpl(line[1],line[2],Integer.parseInt(line[0])));
            }
        }
        return phonyCustomers;
    }

    /** Generate a map of products from CSV data
     * @return map containing products objects with products id as key */
    public static Map<String, Product> generatePhonyProducts(){
        Map<String, Product> phonyProducts = new HashMap<>();
        List<String[]> csvContent = readCSV("phonyProducts.csv");
        if (csvContent != null){
            for (String[] line: csvContent) {
                phonyProducts.put(line[0], new ProductImpl(
                        Integer.parseInt(line[0]),
                        line[1],
                        line[2],
                        Double.parseDouble(line[4]),
                        Integer.parseInt(line[5]),line[3]));
            }
        }
        return phonyProducts;
    }

    /** Read a CSV file from the root compiled project path,
     *  remove the header line and return the data in a list of string arrays
     *  @param csvName file name with extension
     *  @return list of string arrays, each arrays contains the data of a line*/
    private static List<String[]> readCSV(String csvName){
        InputStream input = PhonyData.class.getResourceAsStream("../../../../../../" + csvName);
        try (CSVReader reader = new CSVReader(new InputStreamReader(input))) {
            List<String[]> rawData = reader.readAll();
//            rawData.forEach(x -> System.out.println(Arrays.toString(x)));
            rawData.remove(0); // Delete header line
            return rawData;
        }
        catch (Exception e) {e.printStackTrace();}
        return null;
    }

}
