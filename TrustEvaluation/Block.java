import java.util.List;

class Block{
    private long timeStamp;
    private List<Transaction> transactions;
    private String previousHash;
    private String blockHash;
    
    public Block(List<Transaction> transactions, String previousHash, String blockHash) {
        this.timeStamp = System.currentTimeMillis();
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.blockHash = blockHash;
    }



    public long getTimeStamp() {
        return timeStamp;
    }



    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }



    public List<Transaction> getTransactions() {
        return transactions;
    }



    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }



    public String getPreviousHash() {
        return previousHash;
    }



    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }



    public String getBlockHash() {
        return blockHash;
    }



    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    } 



    
}