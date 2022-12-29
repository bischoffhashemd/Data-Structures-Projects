package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile("addproduct.in");
        StdOut.setFile("addproduct.out");

        Warehouse newWareHouse = new Warehouse();
        int productsCount = StdIn.readInt();
        int count = 0;

        while (count < productsCount)
        {
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand =StdIn.readInt();
            
            newWareHouse.addProduct(id, name, stock, day, demand);
            count += 1;
        }
        StdOut.println(newWareHouse);
	// Use this file to test addProduct
    }
}
