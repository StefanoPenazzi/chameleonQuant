/**
 * 
 */
package indicators;

import data.source.internal.dataset.DatasetI;

/**
 * @author stefanopenazzi
 *
 */
public abstract class IndicatorAbstract implements IndicatorI {

    protected DatasetI dataSet;
	
    public IndicatorAbstract() {
		
	}
    
	public IndicatorAbstract(DatasetI dataSet) {
		this.dataSet = dataSet;
		if(!dataSetCheck()) {
			//run exception
		}
		
	}
	
	public abstract boolean dataSetCheck();
}
