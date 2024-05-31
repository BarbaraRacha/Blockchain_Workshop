package ma.enset.blockchain_project.classes;

import ma.enset.blockchain_project.classes.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class TransactionPool {
    private List<Transaction> pendingTransactions;

    public TransactionPool() {
        this.pendingTransactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        this.pendingTransactions.add(transaction);
    }

    public ArrayList<Transaction> getPendingTransactions() {
        return new ArrayList<Transaction>(this.pendingTransactions);
    }

    public void removeTransaction(Transaction transaction) {
        this.pendingTransactions.remove(transaction);
    }

    public void clear() {
        this.pendingTransactions.clear();
    }
}

