package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile("betteraddproduct.in");
        StdOut.setFile("betteraddproduct.out");
        
        // Use this file to test betterAddProduct
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
            
            newWareHouse.betterAddProduct(id, name, stock, day, demand);
            count += 1;
        }
        StdOut.println(newWareHouse);
    }
}
