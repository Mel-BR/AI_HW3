package digits.entities;

import java.util.Comparator;


/*
 * This class allows us to compare HeapElements in the priority queue
 */
public class HeapElementComparator implements Comparator<HeapElement> {

	@Override
	public int compare(HeapElement he1, HeapElement he2) {
		if(he1.getConfRate()<he2.getConfRate())
			return 1;
		else if (he1.getConfRate()>he2.getConfRate())
			return -1;
		else
			return 0;
	}
}
