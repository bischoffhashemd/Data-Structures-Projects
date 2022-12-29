package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile("everything.in");
        StdOut.setFile("everything.out");

	// Use this file to test all methods
        Warehouse newWareHouse = new Warehouse();
        int lineNum = StdIn.readInt();
        int count = 0;
        while (count < lineNum)
        {
            String operation = StdIn.readString();

            if (operation.equals("add")) 
            {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand =StdIn.readInt();
                newWareHouse.addProduct(id, name, stock, day, demand);
            }

            else if (operation.equals("restock")) //restocking product
            {
                int id = StdIn.readInt();
                int stock = StdIn.readInt();
                newWareHouse.restockProduct(id, stock);
            }

            else if (operation.equals("delete")) //restocking product
            {
                int id = StdIn.readInt();
                newWareHouse.deleteProduct(id);
            }
            
            else if (operation.equals("purchase")) //restocking product
            {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                newWareHouse.purchaseProduct(id, day, amount);
            }   
            count += 1;
        }
        StdOut.println(newWareHouse);
    }
}
