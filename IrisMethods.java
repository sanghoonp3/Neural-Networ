import java.util.ArrayList;
import java.util.Collections;

public class IrisMethods 
{
	
	public static double[] weightCalculator(double a, double b, double c, double d)
	{
		double total = a + b + c + d;
		double weights[] = new double[4];
		weights[0] = a / total;
		weights[1] = b / total;
		weights[2] = c / total;
		weights[3] = d / total;
		
		return weights;
	}
	
	public static double differenceAverage(ArrayList<Double> a)
	//Calculates the average of the differences of the averages of (sepal length, sepal width, petal length, petal width) individually from each iris type using an arraylist
	{
		double dif_12 = Math.abs(a.get(0) - a.get(1));
		double dif_13 = Math.abs(a.get(0) - a.get(2));
		double dif_23 = Math.abs(a.get(1) - a.get(2));
		
		double difTotal = dif_12 + dif_13 + dif_23;
		double difAverage = difTotal / 3;
		
		return difAverage;
	}
	
	public static double differenceAverage(double[] a)
	//Calculates the average of the differences of the averages of (sepal length, sepal width, petal length, petal width) individually from each iris type using an array
	{
		double dif_12 = Math.abs(a[0] - a[1]);
		double dif_13 = Math.abs(a[0] - a[2]);
		double dif_23 = Math.abs(a[1] - a[2]);
		
		double difTotal = dif_12 + dif_13 + dif_23;
		double difAverage = difTotal / 3;
		
		return difAverage;
	}
	
	public static double averageCalculator(ArrayList<Double> data)
	//Calculates the average
	{
		double total = 0;
		
		for(int i = 0; i < data.size(); i++)
		{
			total = total + data.get(i);
		}
		double average = total / data.size();
		
		return average;
	}
	
	public static ArrayList<Double> storeAverageInArray(ArrayList<Double> setosa, ArrayList<Double> versicolor, ArrayList<Double> virginica)
	{
		ArrayList<Double> average = new ArrayList<Double>();
		average.add(averageCalculator(setosa));
		average.add(averageCalculator(versicolor));
		average.add(averageCalculator(virginica));
		return average;
	}
	
	public static ArrayList<Double> reverseArray(int x, ArrayList<Double> sepal_length, ArrayList<Double> sepal_width, ArrayList<Double> petal_length, ArrayList<Double> petal_width)
	{
		ArrayList<Double> array = new ArrayList<Double>();
		array.add(sepal_length.get(x));
		array.add(sepal_width.get(x));
		array.add(petal_length.get(x));
		array.add(petal_width.get(x));
		return array;
	}
	
	//Used to display the result and return the String of what was printed
	public static String displayResults(ArrayList<Double> classification)
	{
		String iris = "";

		int maxIndex = classification.indexOf(Collections.max(classification));
		
		if(maxIndex == 0)
		{
			System.out.print("Setosa" + "\t" + "\t" + "\t");
			iris = "Setosa";
		}
		else if(maxIndex == 1)
		{
			System.out.print("Versicolor" + "\t" + "\t");
			iris = "Versicolor";
		}
		else
		{
			System.out.print("Virginica" + "\t" + "\t");
			iris = "Virginica";
		}
		
		return iris;
	}
}
