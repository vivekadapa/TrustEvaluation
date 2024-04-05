import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Blockchain {

    public ArrayList<AocBlock> chain;

    public Blockchain(ArrayList<AocBlock> chain) {
        this.chain = chain;
    }

    public void addBlock(AocBlock aocBlock) {
        this.chain.add(aocBlock);
    }

    public void arbitrationAboutNegVerfication(AocBlock block, String y, String f) {
        HashMap<Envelope, Envelope> DAG = block.getDAG();
        if (DAG != null) {
            for (Entry<Envelope, Envelope> map : DAG.entrySet()) {
                Envelope key = map.getKey();
                Envelope value = map.getValue();
                System.out.println("\nKey: " + key);
                System.out.println("Value: " + value);
            }
        } else {
            System.out.println("DAG is null for this block.");
        }
    }

    public static void main(String[] args) throws Exception {
        ArrayList<AocBlock> chainList = new ArrayList<>();
        Blockchain blockchain = new Blockchain(chainList);
        AocBlock aocblock = new AocBlock(null);
        AocBlock block = aocblock.blockGeneration();
        blockchain.addBlock(block);
        blockchain.arbitrationAboutNegVerfication(block, "y1", "f");
    }
}
