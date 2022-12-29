package warehouse;

public class PurchaseProduct {
    public static void main(String[] args) {
        StdIn.setFile("purchaseproduct.in");
        StdOut.setFile("purchaseproduct.out");

	// Use this file to test purchaseProduct
    Warehouse newWareHouse = new Warehouse();
        int lineNum = StdIn.readInt();
        int count = 0;
        while (count < lineNum)
        {
            String addOrRestock = StdIn.readString();

            if (addOrRestock.equals("purchase")) //restocking product
            {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                newWareHouse.purchaseProduct(id, day, amount);
            }
            
            else 
            {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand =StdIn.readInt();
                newWareHouse.addProduct(id, name, stock, day, demand);
            }
            count += 1;
        }
        StdOut.println(newWareHouse);
    }
}
