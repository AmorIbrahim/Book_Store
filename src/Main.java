import java.util.*;


public class Main {
    public static void main(String[] args) {
        Bookstore store = new Bookstore();

        store.addBook(new PaperBook("123", "Algo", 2025, 50, 10));
        store.addBook(new EBook("456", "Python Basics", 2020, 80, "PDF"));
        store.addBook(new Demobook("789", "C++", 2004));


        List<Book> outdated = store.removeOutdatedBooks(10); 
        try {

            double total = store.buyBook("456", 1, "amorebrahim58@gmail.com");
            System.out.println("Paid amount: $" + total);

            total = store.buyBook("123", 5, "19 Salah Hamed St, Cairo");
            System.out.println("Paid amount: $" + total);

            store.buyBook("789", 1, "amorebrahim58@gmail.com");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Current inventory size: " + store.getInventorySize());
    }
}
