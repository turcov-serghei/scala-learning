import java.math.BigDecimal;
import java.util.*;

public class ForComprehensionExample {
    
    static class Account {
        String id;
        List<Wallet> wallets;
        Account(String id, List<Wallet> wallets) { this.id = id; this.wallets = wallets; }
    }
    
    static class Wallet {
        String currency;
        BigDecimal balance;
        Wallet(String currency, BigDecimal balance) { this.currency = currency; this.balance = balance; }
    }
    
    public static Optional<BigDecimal> getBalance(
            Optional<Account> accountOpt, String currency) {
        
        // Imperative: явные проверки + .get()
        if (accountOpt.isEmpty()) return Optional.empty();
        Account account = accountOpt.get();
        
        Optional<Wallet> walletOpt = account.wallets.stream()
            .filter(w -> w.currency.equals(currency))
            .findFirst();
        if (walletOpt.isEmpty()) return Optional.empty();
        
        return Optional.of(walletOpt.get().balance);
    }
    
    public static Optional<BigDecimal> getBalanceFunctional(
            Optional<Account> accountOpt, String currency) {
        
        // Declarative: flatMap + map цепочка
        return accountOpt
            .flatMap(account ->
                account.wallets.stream()
                    .filter(w -> w.currency.equals(currency))
                    .findFirst()
                    .map(wallet -> wallet.balance)
            );
    }
    
    public static void main(String[] args) {
        Wallet usdWallet = new Wallet("USD", new BigDecimal("1000.00"));
        Account account = new Account("ACC-1", List.of(usdWallet));
        
        System.out.println(getBalanceFunctional(Optional.of(account), "USD"));
        // Optional[1000.00]
        
        System.out.println(getBalanceFunctional(Optional.of(account), "EUR"));
        // Optional.empty
        
        System.out.println(getBalanceFunctional(Optional.empty(), "USD"));
        // Optional.empty
    }
}