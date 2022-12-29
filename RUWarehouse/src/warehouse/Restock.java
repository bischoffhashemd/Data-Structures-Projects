package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile("restock.in");
        StdOut.setFile("restock.out");

	// Uset his file to test restock
        Warehouse newWareHouse = new Warehouse();
        int lineNum = StdIn.readInt();
        int count = 0;
        while (count < lineNum)
        {
            String addOrRestock = StdIn.readString();

            if (addOrRestock.equals("restock")) //restocking product
            {
                int id = StdIn.readInt();
                int stock = StdIn.readInt();
                newWareHouse.restockProduct(id, stock);
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
