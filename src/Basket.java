import java.io.*;
import java.util.Arrays;

import static java.util.Arrays.*;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String[] nameProducts;
    private int[] prices;
    private int[] quantities;

    public Basket() {
    }

    public Basket(String[] nameProducts, int[] prices) {
        this.nameProducts = nameProducts;
        this.prices = prices;
        this.quantities = new int[nameProducts.length];
    }

    public void addToCart(int productNum, int amount) {
        quantities[productNum] += amount;
    }

    public void printCart() {
        int endPrice = 0;
        System.out.println("Список покупок: ");
        for (int i = 0; i < nameProducts.length; i++) {
            if (quantities[i] > 0) {
                int currentPrices = prices[i] * quantities[i];
                endPrice += currentPrices;
                System.out.printf("%15s%4d р/шт.%4d шт.%6d p%n", nameProducts[i], prices[i], quantities[i], currentPrices);
            }
        }
        System.out.printf("Итого: %dp", endPrice);
    }

    public void saveTxt(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String nameProduct : nameProducts) {
                out.print(nameProduct + " ");
            }
            out.println();
            System.out.println();
            for (int price : prices) {
                out.print(price + " ");
            }
            out.println();
            System.out.println();
            for (int quantity : quantities) {
                out.print(quantity + " ");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String nameProducts1 = bufferedReader.readLine();
            String prices1 = bufferedReader.readLine();
            String quantities1 = bufferedReader.readLine();

            basket.nameProducts = nameProducts1.split(" ");
            basket.prices = Arrays.stream(prices1.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
            basket.quantities = Arrays.stream(prices1.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
    public void saveBin(File file){
        try (ObjectOutputStream x1 = new ObjectOutputStream(new FileOutputStream(file))){
            x1.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Basket loadFromBinFile(File file){
        Basket basket = null;
        try(ObjectInputStream x2 = new ObjectInputStream(new FileInputStream(file))){
            basket = (Basket) x2.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}
