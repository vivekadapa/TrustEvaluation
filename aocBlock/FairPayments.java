

public class FairPayments {

    private int amountPaidByIOT;
    private int amountPaidByNodes;

    public int getAmountPaidByIOT() {
        return amountPaidByIOT;
    }

    public void setAmountPaidByIOT(int amountPaidByIOT) {
        this.amountPaidByIOT = amountPaidByIOT;
    }

    public int getAmountPaidByNodes() {
        return amountPaidByNodes;
    }

    public void setAmountPaidByNodes(int amountPaidByNodes) {
        this.amountPaidByNodes = amountPaidByNodes;
    }

    public FairPayments(int amountPaidByIOT, int amountPaidByNodes) {
        this.amountPaidByIOT = amountPaidByIOT;
        this.amountPaidByNodes = amountPaidByIOT;
    }

    public int addNodeAmount(int amount){
        this.amountPaidByNodes += amount;
        return amount;
    }

    // public static void main(String[] args) {
    //     Node nn1 = new Node("nn1", NodeType.nn, 20);
    //     Node cn1 = new Node("cn1", NodeType.cn, 20);
    //     Node cn2 = new Node("cn2", NodeType.cn, 20);
    //     Node cn3 = new Node("cn3", NodeType.cn, 20);
    //     Node cn4 = new Node("cn4", NodeType.cn, 20);
    //     List<Node> nodeList = new ArrayList<>();
    //     nodeList.add(nn1);
    //     nodeList.add(cn1);
    //     nodeList.add(cn2);
    //     nodeList.add(cn3);
    //     nodeList.add(cn4);
    //     int amountFromNodes = 0;
    //     for (Node i : nodeList) {
    //         amountFromNodes = amountFromNodes + i.giveStake(10);
    //     }
    //     System.out.println(amountFromNodes);
    //     FairPayments payment = new FairPayments(100, amountFromNodes);

    //     // case 1 No negative verifications
    //     for (Node i : nodeList) {
    //         int amount = (payment.getAmountPaidByNodes() + payment.getAmountPaidByIOT()) / nodeList.size();
    //         i.setamount(amount);
    //     }

    //     System.out.println(nn1.getamount());
    //     System.out.println(cn1.getamount());
    //     System.out.println(cn2.getamount());
    //     System.out.println(cn3.getamount());
    //     System.out.println(cn4.getamount());

    // }

}
