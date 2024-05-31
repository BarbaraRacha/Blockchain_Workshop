package ma.enset.blockchain_project.classes;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

@Getter
public class Transaction {
    // Getters and Setters
    private String sender;
    private String recipient;
    private double amount;
    private String signature;

    public Transaction(String sender, String recipient, double amount, String signature) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.signature = signature;
    }

    public void signTransaction(PrivateKey privateKey) {
        String data = sender + recipient + amount;
        signature = sign(data, privateKey);
    }

    public boolean verifyTransaction(PublicKey publicKey) {
        String data = sender + recipient + amount;
        return verify(data, signature, publicKey);
    }

    private String sign(String data, PrivateKey privateKey) {
        try {
            Signature signer = Signature.getInstance("SHA256withECDSA");
            signer.initSign(privateKey);
            signer.update(data.getBytes());
            return Base64.getEncoder().encodeToString(signer.sign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean verify(String data, String signature, PublicKey publicKey) {
        try {
            Signature verifier = Signature.getInstance("SHA256withECDSA");
            verifier.initVerify(publicKey);
            verifier.update(data.getBytes());
            return verifier.verify(Base64.getDecoder().decode(signature));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}

