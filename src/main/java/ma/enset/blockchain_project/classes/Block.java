package ma.enset.blockchain_project.classes;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;
@Data
@Getter @Setter
@Component
public class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private String data;
    private int nonce;

    public Block(int index, String previousHash, String data) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.data = data;
        this.hash = calculateHash();
        this.nonce = 0;
    }
    public Block(int index, long timestamp, String previousHash, String currentHash, String data) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.data = data;
        this.hash = calculateHash();
        this.nonce = 0;
    }


    public String calculateHash() {
        String input = index + previousHash + timestamp + data + nonce;
        return HashUtil.applySha256(input);
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined! : " + hash);
    }
}
