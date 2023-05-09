import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Список товаров для покупки: ");
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        File safeFile = new File("basket.bin");

        Basket basket = null;
        if (safeFile.exists()){
            basket = Basket.loadFromBinFile(safeFile);
        }else {
            basket = new Basket(products, prices);
        }

        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + products[i] + "  " + prices[i] + "  " + "шт/руб.");
        }
        int[] productBasket = new int[products.length];
        int[] prices2 = new int[prices.length];
        int korzina = 0;

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`\n");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] clientChoice = input.split(" ");
            int productNumber = Integer.parseInt(clientChoice[0]) - 1;//номер продукта
            int productCount = Integer.parseInt(clientChoice[1]);// кол-во продукта
            int currentPrice = prices[productNumber];//текущая цена продукта
            int sumProducts = currentPrice * productCount;// сумма выбранных продуктов
            basket.addToCart(productNumber, productCount);
            basket.saveBin(safeFile);

            productBasket[productNumber] += productCount;// увеличиваем кол-во товара
            prices2[productNumber] += sumProducts;// увеличиваем суммы товаров

            korzina += sumProducts;// Итоговая корзина
        }
        System.out.println("Ваша корзина: ");
        for (int i = 0; i < productBasket.length; i++) {
            if (productBasket[i] == 0) {
                continue;
            }
            System.out.println(products[i] + " " + productBasket[i] + " шт " + prices[i] + " руб/шт " + prices[i] + " рублей в сумме");
        }
        System.out.println("Итого: " + korzina + " руб");
    }
}