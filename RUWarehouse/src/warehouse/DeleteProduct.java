package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile("deleteproduct.in");
        StdOut.setFile("deleteproduct.out");

	// Use this file to test deleteProduct
    Warehouse newWareHouse = new Warehouse();
        int lineNum = StdIn.readInt();
        int count = 0;
        while (count < lineNum)
        {
            String addOrRestock = StdIn.readString();

            if (addOrRestock.equals("delete")) //restocking product
            {
                int id = StdIn.readInt();
                newWareHouse.deleteProduct(id);
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
