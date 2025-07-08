import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class Book {
    protected String isbn;
    protected String title;
    protected int year;
    protected double price;

    public Book(String isbn, String title, int year, double price) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public abstract boolean isForSale();
    public abstract String processPurchase(String customerInfo);
}
class PaperBook extends Book {
    private int stock;

    public PaperBook(String isbn, String title, int year, double price, int stock) {
        super(isbn, title, year, price);
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean isForSale() {
        return true;
    }

    @Override
    public String processPurchase(String address) {
        ShippingService.ship(this, address);
        return "Paper book:Name '" + title + "' shipped to " + address;
    }
}
class EBook extends Book {
    private String fileType;

    public EBook(String isbn, String title, int year, double price, String fileType) {
        super(isbn, title, year, price);
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public boolean isForSale() {
        return true;
    }

    @Override
    public String processPurchase(String email) {
        MailService.send(this, email);
        return "EBook:Name '" + title + "' sent to " + email;
    }
}
class Demobook extends Book {
    public Demobook(String isbn, String title, int year) {
        super(isbn, title, year, 0);
    }

    @Override
    public boolean isForSale() {
        return false;
    }

    @Override
    public String processPurchase(String customerInfo) {
        return "Demo book :Name'" + title + "' is not for sale";
    }
}

class ShippingService {
    public static void ship(PaperBook book, String address) {
        // Not implementation yet
    }
}

class MailService {
    public static void send(EBook book, String email) {
        // Not implementation yet
    }
}

class Bookstore {
    private Map<String, Book> inventory;

    public Bookstore() {
        this.inventory = new HashMap<>();
    }

    public void addBook(Book book) {
        inventory.put(book.getIsbn(), book);
        System.out.println("Added book '" + book.getTitle() + "' to inventory");
    }

    public List<Book> removeOutdatedBooks(int yearsThreshold) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Book> outdatedBooks = inventory.values().stream()
                .filter(book -> (currentYear - book.getYear()) > yearsThreshold)
                .collect(Collectors.toList());

        outdatedBooks.forEach(book -> inventory.remove(book.getIsbn()));

        System.out.println("Removed " + outdatedBooks.size() + " outdated books");
        return outdatedBooks;
    }

    public double buyBook(String isbn, int quantity, String customerInfo) throws Exception {
        Book book = inventory.get(isbn);

        if (book == null) {
            throw new Exception("Book with ISBN " + isbn + " not found");
        }

        if (!book.isForSale()) {
            throw new Exception("Book '" + book.getTitle() + "' is not for sale");
        }

        if (book instanceof PaperBook) {
            PaperBook paperBook = (PaperBook) book;
            if (paperBook.getStock() < quantity) {
                throw new Exception("Not enough stock for book '" + book.getTitle() + "'");
            }
            paperBook.setStock(paperBook.getStock() - quantity);
        }

        System.out.println(book.processPurchase(customerInfo));
        return book.getPrice() * quantity;
    }

    public int getInventorySize() {
        return inventory.size();
    }
}
