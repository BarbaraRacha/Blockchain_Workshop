package ma.enset.blockchain_project.classes;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
@Data
@AllArgsConstructor
@Getter
@Setter
public class Blockchain {
    private ArrayList<Block> blockchain;
    private int difficulty;
    private int difficultyAdjustmentInterval;
    private TransactionPool transactionPool;

    public Blockchain() {
        this.blockchain = new ArrayList<>();
        this.difficulty = 4; // Par exemple, le nombre de zéros initiaux requis
        this.difficultyAdjustmentInterval = 10; // Ajuste la difficulté tous les 10 blocs
        this.transactionPool = new TransactionPool();

        // Créer le bloc génésis
        Block genesisBlock = new Block(0, "0", "Genesis Block");
        genesisBlock.mineBlock(this.difficulty);
        this.blockchain.add(genesisBlock);
    }

    public Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    public void addBlock(Block newBlock) {
        newBlock.setPreviousHash(this.getLatestBlock().getHash());
        newBlock.mineBlock(this.difficulty);
        this.blockchain.add(newBlock);

        if (this.blockchain.size() % this.difficultyAdjustmentInterval == 0) {
            adjustDifficulty();
        }
    }
    public void mineBlock(Block block, int difficulty) {
        block.setNonce(0);
        String target = new String(new char[difficulty]).replace('\0', '0'); // Create a string with difficulty * "0"
        while (!block.getHash().substring(0, difficulty).equals(target)) {
            block.setNonce(block.getNonce() + 1);
            block.setHash(block.calculateHash());
        }
    }

    public void adjustDifficulty() {
        // Implémentez l'ajustement de la difficulté basé sur le taux de minage
        Long startTime = this.blockchain.get(this.blockchain.size() - this.difficultyAdjustmentInterval).getTimestamp();
        Long endTime = this.blockchain.get(this.blockchain.size() - 1).getTimestamp();
        Long timeTaken = (Long) endTime - startTime;
        long expectedTime = this.difficultyAdjustmentInterval * 10000; // Par exemple, 10 secondes par bloc

        if (timeTaken < expectedTime) {
            this.difficulty++;
        } else if (timeTaken > expectedTime) {
            this.difficulty--;
        }
    }

    public boolean validateBlock(Block block) {
        String calculatedHash = block.calculateHash();
        if (!calculatedHash.equals(block.getHash())) {
            return false;
        }
        if (!calculatedHash.startsWith(new String(new char[this.difficulty]).replace('\0', '0'))) {
            return false;
        }
        return true;
    }

    public boolean validateChain() {
        for (int i = 1; i < blockchain.size(); i++) {
            Block currentBlock = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);

            if (!validateBlock(currentBlock)) {
                return false;
            }

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionPool.addTransaction(transaction);
    }

    public Block getBlockByIndex(int index) {
        return blockchain.get(index);
    }
    public void minePendingTransactions(String minerAddress) {
        ArrayList<Transaction> transactions = this.transactionPool.getPendingTransactions();
        String data = "";
        for (Transaction tx : transactions) {
            data += tx.toString();
        }

        Block newBlock = new Block(this.blockchain.size(),this.getLatestBlock().getHash(), data.toString());
        this.addBlock(newBlock);
        this.transactionPool.clear();
    }
}
