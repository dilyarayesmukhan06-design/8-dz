// ======= Барлық код бір файлда =======
interface Beverage {
    String getDescription();
    double cost();
}

// ======= Негізгі сусындар =======
class Espresso implements Beverage {
    public String getDescription() {
        return "Эспрессо";
    }

    public double cost() {
        return 500;
    }
}

class Tea implements Beverage {
    public String getDescription() {
        return "Шай";
    }

    public double cost() {
        return 300;
    }
}

class Latte implements Beverage {
    public String getDescription() {
        return "Латте";
    }

    public double cost() {
        return 600;
    }
}

// ======= Абстрактты декоратор =======
abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;

    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        return beverage.getDescription();
    }

    public double cost() {
        return beverage.cost();
    }
}

// ======= Қоспалар =======
class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) {
        super(beverage);
    }

    public String getDescription() {
        return beverage.getDescription() + ", сүт";
    }

    public double cost() {
        return beverage.cost() + 100;
    }
}

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) {
        super(beverage);
    }

    public String getDescription() {
        return beverage.getDescription() + ", қант";
    }

    public double cost() {
        return beverage.cost() + 50;
    }
}

class WhippedCream extends BeverageDecorator {
    public WhippedCream(Beverage beverage) {
        super(beverage);
    }

    public String getDescription() {
        return beverage.getDescription() + ", крем";
    }

    public double cost() {
        return beverage.cost() + 150;
    }
}

class Syrup extends BeverageDecorator {
    public Syrup(Beverage beverage) {
        super(beverage);
    }

    public String getDescription() {
        return beverage.getDescription() + ", сироп";
    }

    public double cost() {
        return beverage.cost() + 120;
    }
}

// ======= Интерфейс төлем жүйесі үшін =======
interface IPaymentProcessor {
    void processPayment(double amount);
}

// ======= PayPal жүйесі =======
class PayPalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("PayPal арқылы төлем жасалды: " + amount + " тг");
    }
}

// ======= Stripe жүйесі (сыртқы) =======
class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Stripe жүйесімен төлем өтті: " + totalAmount + " тг");
    }
}

// ======= Stripe адаптері =======
class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;

    public StripePaymentAdapter(StripePaymentService stripeService) {
        this.stripeService = stripeService;
    }

    public void processPayment(double amount) {
        stripeService.makeTransaction(amount);
    }
}

// ======= KaspiPay жүйесі (басқа сыртқы жүйе) =======
class KaspiPayService {
    public void sendPayment(double money) {
        System.out.println("KaspiPay арқылы төлем жіберілді: " + money + " тг");
    }
}

// ======= KaspiPay адаптері =======
class KaspiPayAdapter implements IPaymentProcessor {
    private KaspiPayService kaspiService;

    public KaspiPayAdapter(KaspiPayService kaspiService) {
        this.kaspiService = kaspiService;
    }

    public void processPayment(double amount) {
        kaspiService.sendPayment(amount);
    }
}

// ======= Негізгі класс (main) =======
public class Main{
    public static void main(String[] args) {
        System.out.println("=== ☕ КАФЕДЕГІ ТАПСЫРЫСТАР (Декоратор) ===");

        Beverage order1 = new Espresso();
        order1 = new Milk(order1);
        order1 = new Sugar(order1);
        System.out.println(order1.getDescription() + " => Баға: " + order1.cost() + " тг");

        Beverage order2 = new Latte();
        order2 = new Syrup(order2);
        order2 = new WhippedCream(order2);
        System.out.println(order2.getDescription() + " => Баға: " + order2.cost() + " тг");

        Beverage order3 = new Tea();
        order3 = new Sugar(order3);
        System.out.println(order3.getDescription() + " => Баға: " + order3.cost() + " тг");

        System.out.println("\n=== 💳 ТӨЛЕМ ЖҮЙЕСІ (Адаптер) ===");

        IPaymentProcessor paypal = new PayPalPaymentProcessor();
        IPaymentProcessor stripe = new StripePaymentAdapter(new StripePaymentService());
        IPaymentProcessor kaspi = new KaspiPayAdapter(new KaspiPayService());

        paypal.processPayment(650);
        stripe.processPayment(870);
        kaspi.processPayment(350);

        System.out.println("\nБарлық төлем сәтті аяқталды ✅");
    }
}
