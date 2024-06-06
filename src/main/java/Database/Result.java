package Database;

import java.util.ArrayList;

public class Result {
    
    private int resultCode;
    private ArrayList<Object> dataset;
        
    /* ------------------------------------------------------------ */

    public Result (){
        dataset = new ArrayList<>();
    }
        
    /* ------------------------------------------------------------ */
    

    public void addCode (int resultCode){
        this.resultCode = resultCode;
    }

    public void addDatasetItem (Object item){
        dataset.add(item);
    }
        
    /* ------------------------------------------------------------ */
    
    public int getResultCodes (){
        return this.resultCode;
    }

    public ArrayList<Object> getDataset (){
        return this.dataset;
    }
}