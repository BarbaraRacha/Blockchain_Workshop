package ma.enset.blockchain_project;


import ma.enset.blockchain_project.classes.Block;
import ma.enset.blockchain_project.classes.Blockchain;
import ma.enset.blockchain_project.classes.Transaction;
import ma.enset.blockchain_project.classes.TransactionPool;

public class Main {
    public static void main(String[] args) {
        // Créer une nouvelle blockchain
        System.out.println("Création de Blockchain ................");
        Blockchain blockchain = new Blockchain();

        // Créer une transaction pool
        System.out.println("Création de transaction pool ................");
        TransactionPool transactionPool = new TransactionPool();

        // Ajouter des transactions à la transaction pool
        System.out.println("Ajout des transactions à la transaction pool ................");
        transactionPool.addTransaction(new Transaction("b1", "b11", 12, "Sign 1"));
        transactionPool.addTransaction(new Transaction("b2", "b22", 15, "Sign 2"));

        // Afficher les transactions en attente
        System.out.println("Transactions en attente:");
        for (Transaction tx : transactionPool.getPendingTransactions()) {
            System.out.println("\t"+tx.getSender() + " -> " + tx.getRecipient() + ": " + tx.getAmount());
        }

        // Créer un nouveau bloc avec les transactions en attente
        System.out.println("Création d'un nouveau bloc avec les transactions en attente ................");
        Block newBlock = new Block(
                blockchain.getLatestBlock().getIndex() + 1,
                System.currentTimeMillis(),
                blockchain.getLatestBlock().getPreviousHash(),
                blockchain.getLatestBlock().getHash(),
                transactionPool.getPendingTransactions().toString()
        );

        // Définir la difficulté de minage (plus le nombre est grand, plus c'est difficile)
        System.out.println("Définir la difficulté de minage ## difficulty = 2 ##(plus le nombre est grand, plus c'est difficile) ................");
        int difficulty = 2;

        // Miner le bloc
        System.out.println("Minage en cours...");
        blockchain.mineBlock(newBlock, difficulty);
        System.out.println("Bloc miné avec le hash : " + newBlock.getHash());

        // Ajouter le bloc à la blockchain
        System.out.println("Ajout du bloc à la blockchain ................");
        blockchain.addBlock(newBlock);

        // Afficher l'état actuel de la blockchain
        System.out.println("Affichage d'Eétat Actuel de Blockchain:");
        for (Block block : blockchain.getBlockchain()) {
            System.out.println("\tIndex: " + block.getIndex());
            System.out.println("\tTimestamp: " + block.getTimestamp());
            System.out.println("\tPrevious Hash: " + block.getPreviousHash());
            System.out.println("\tCurrent Hash: " + block.getHash());
            System.out.println("\tData: " + block.getData());
            System.out.println();
        }

        // Valider la blockchain
        System.out.println("Validation de la blockchain...");
        if (blockchain.validateChain()) {
            System.out.println("La blockchain est valide.");
        } else {
            System.out.println("La blockchain n'est pas valide.");
        }
    }

}
