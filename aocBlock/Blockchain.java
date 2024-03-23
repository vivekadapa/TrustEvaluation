import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Blockchain {
    
    public ArrayList<AocBlock> chain;
        
    public Blockchain(ArrayList<AocBlock> chain){
        this.chain = chain;   
    }

    public void addBlock(AocBlock aocBlock){
        this.chain.add(aocBlock);
    }
    
    public void arbitrationAboutNegVerfication(AocBlock block,String y,String f) {
        System.out.println(block.getDAG());
         if(this.chain.contains(block)){
            LinkedHashMap<Envelope, Envelope> DAG = (LinkedHashMap<Envelope, Envelope>) block.getDAG();
            for(Entry<Envelope, Envelope> map : DAG.entrySet()){
                if(map.getKey().getType() == EnvelopeType.envvt || map.getKey().getType() == EnvelopeType.envcm){
                        if(map.getKey().getType() == EnvelopeType.envvt){
                            System.out.println(map.getValue());
                        }
                }
            }
         }
    }

    public static void main(String[] args) {
        ArrayList<AocBlock> chainList = new ArrayList<>();
        Blockchain blockchain = new Blockchain(chainList);
        AocBlock aocblock = new AocBlock(null);
        AocBlock block = aocblock.blockGeneration();
        blockchain.addBlock(block);
        blockchain.arbitrationAboutNegVerfication(block,"y1","f");
        // System.out.println(block.getDAG());
    }
}
