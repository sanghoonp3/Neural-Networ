import java.util.ArrayList;
import java.util.Collections;

public class Clustroid
{		
	public static int clustroidPicker(ArrayList<Double> sepal_length, ArrayList<Double> sepal_width, ArrayList<Double> petal_length, ArrayList<Double> petal_width)
	{
		ArrayList<Double> clustroidDistance = new ArrayList<Double>();
		ArrayList<Double> clustroidAverage = new ArrayList<Double>();
		
		for(int i = 0; i < sepal_length.size(); i++)
		{
			for(int j = 0; j < sepal_length.size(); j++)
			{
				double distance = 0;
				
				double sepal_length_difference = Math.abs(sepal_length.get(i) - sepal_length.get(j));
				double sepal_width_difference = Math.abs(sepal_width.get(i) - sepal_width.get(j));
				double petal_length_difference = Math.abs(petal_length.get(i) - petal_length.get(j));
				double petal_width_difference = Math.abs(petal_width.get(i) - petal_width.get(j));
				
				distance = sepal_length_difference + sepal_width_difference + petal_length_difference + petal_width_difference;
				
				if(distance != 0)
				{
					clustroidDistance.add(distance);
				}
				else
				{}
			}
			double total = 0;
			
			for(int k = 0; k < clustroidDistance.size(); k++)
			{
				total = total + clustroidDistance.get(k);
			}
			
			double average = total / (clustroidDistance.size() - 1);
			clustroidAverage.add(average);
			clustroidDistance.clear();
		}
		int clustroid = clustroidAverage.indexOf(Collections.min(clustroidAverage));
		
		return clustroid;
	}
	
	public static double[] clustroidWeightCalculator(int x, ArrayList<Double> sepal_length, ArrayList<Double> sepal_width, ArrayList<Double> petal_length, ArrayList<Double> petal_width)
	{
		double weight_total = sepal_length.get(x) + sepal_width.get(x) + petal_length.get(x) + petal_width.get(x);
		
		double[] weight = new double[4];
		
		weight[0] = Math.abs(1 - sepal_length.get(x)) / weight_total;
		weight[1] = Math.abs(1 - sepal_width.get(x)) / weight_total;
		weight[2] = Math.abs(1 - petal_length.get(x)) / weight_total;
		weight[3] = Math.abs(1 - petal_width.get(x)) / weight_total;
		
		return weight;
	}
}
