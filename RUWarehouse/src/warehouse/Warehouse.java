package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        // IMPLEMENT THIS METHOD
        Product newProduct = new Product(id, name, stock, day, demand);

        int sectorIdx = id % 10;
        sectors[sectorIdx].add(newProduct);          
   }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        // IMPLEMENT THIS METHOD
        // iterate through array of sectors 
        for (int i = 0; i < sectors.length; i++)
        {
            if (sectors[i].getSize() != 0)
            {
                // make each sector into min heap
                int idxOfLastNonLeafNode = (sectors[i].getSize())/2;
                for (int j = idxOfLastNonLeafNode; j > 0; j--)
                {
                    sectors[i].sink(j);
                }
            }
            
        }
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {
       // IMPLEMENT THIS METHOD
       
       int sectorIndex = id % 10;
       int firstProduct = 1;
       int sectorCapacity  = 5;
       if(sectors[sectorIndex].getSize() == sectorCapacity)
       {
        sectors[sectorIndex].swap(firstProduct, sectorCapacity);
        sectors[sectorIndex].deleteLast();
        sectors[sectorIndex].sink(firstProduct);
       }
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        // IMPLEMENT THIS METHOD
        int sectorIndex = id % 10;
        Sector sectorOfProduct = sectors[sectorIndex];
        for (int i = 1; i <= 5; i++)
        {
            Product currentProductIndex = sectorOfProduct.get(i);
            if (currentProductIndex != null && currentProductIndex.getId() == id)
            {
                sectorOfProduct.get(i).updateStock(amount);
            }
        }
    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        // IMPLEMENT THIS METHOD
        int sectorIndex = id % 10;
        int productCount = sectors[sectorIndex].getSize();
        Sector productSector = sectors[sectorIndex];
        forLoop: 
        for (int i = 1; i <= productCount; i++)
        {
            int currentProductId = productSector.get(i).getId();
            if (currentProductId == id)
            {
                productSector.swap(i,productCount);
                productSector.deleteLast();
                productSector.sink(i);
                break forLoop;
            }
        }  
    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        // IMPLEMENT THIS METHOD
        int sectorIndex = id % 10;
        int productCount = sectors[sectorIndex].getSize();
        Sector productSector = sectors[sectorIndex];
        for (int i = 1; i <= productCount; i++)
        {
            int currentProductId = productSector.get(i).getId();
            if (currentProductId == id)
            {
                int currentStock = productSector.get(i).getStock();
                int currentPopularity = productSector.get(i).getPopularity();

                if(currentStock >= amount)
                {
                    // update stock
                    productSector.get(i).updateStock(-amount);

                    // update demand
                    productSector.get(i).updateDemand(amount);

                    //update last purchase day
                    productSector.get(i).setLastPurchaseDay(day);

                    //fix heap
                    int newPopularity = productSector.get(i).getPopularity();
                    
                    if (newPopularity > currentPopularity)
                    {
                        productSector.sink(i);
                    }

                    else if (newPopularity < currentPopularity)
                    {
                        productSector.swim(i);
                    }
                }
            }
        }
    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        // IMPLEMENT THIS METHOD
        Product newProduct = new Product(id, name, stock, day, demand);

        int sectorIdx = id % 10;
        int sectorCapacity  = 5;
        boolean productAdded = false;
        int currentSector;

        // item's sector is not full -> add to end
        if (sectors[sectorIdx].getSize() != sectorCapacity) 
        {
            sectors[sectorIdx].add(newProduct);
            fixHeap(id);
        }
        
        // item's sector is full  
       else 
       {
            if (sectorIdx == 9) { currentSector = 0;}
            else {currentSector = sectorIdx + 1;}

            whileLoop: 
            while (currentSector != sectorIdx)
            {
                //empty spot found in current sector (add product to end of sector)
                if (sectors[currentSector].getSize() < sectorCapacity)
                {
                    sectors[currentSector].add(newProduct);
                    productAdded = true;
                    fixHeap(id);
                    break whileLoop;
                }

                // current sector is full -> check next sector
                else 
                {
                    if (currentSector == 9) { currentSector = 0;}
                    else {currentSector += 1;}
                }
            }

            // all sectors are full -> evict as usual
            if (!productAdded)
            {
                //remove first Product
                evictIfNeeded(id);
                // int firstProduct = 1;
                // sectors[sectorIdx].swap(firstProduct, sectorCapacity);
                // sectors[sectorIdx].deleteLast();
                // sectors[sectorIdx].sink(firstProduct);

                //add new product
                sectors[sectorIdx].add(newProduct);
                fixHeap(id);
            }
       }      
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}
