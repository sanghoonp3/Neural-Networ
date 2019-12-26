import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *  Sanghoon Park's Independent Study: Iris Classification 
 * 
 * THIS ERROR INFORMATION IS FOR THE CURRENT DATA IN TESTDATA.TXT AND TRAININGDATA.TXT
 * this can vary based on SeparateData.java chooses, which separates the data with random decisions for the training data and test data.
 * 
 * Weighted:
 * 		50 total average weight: 		0 Setosa, 3 Versicolor, 1 Virginica classified incorrectly		Accuracy: 4.44% error
 * 		20 random data average weight: 	0 Setosa, 2 Versicolor, 1 Virginica classified incorrectly		Accuracy: 3.33% error
 * 		Clustroid:						0 Setosa, 3 Versicolor, 0 Virginica classified incorrectly		Accuracy: 3.33% error
 * 
 * Un-Weighted:
 * 		50 total average: 		0 Setosa, 4 Versicolor, 2 Virginica classified incorrectly				Accuracy: 6.67% error
 * 		20 random data average: 0 Setosa, 2 Versicolor, 2 Virginica classified incorrectly				Accuracy: 4.44% error
 * 		Clustroid: 				0 Setosa, 1 Versicolor, 2 Virginica classified incorrectly				Accuracy: 3.33% error
 * 
 * 
 * Weight for 50 and 20 is average of the difference of the absolute value of each flower's section
 * This is to show which weight is higher based on the significant difference between each section
 * sections with similar sizes will have a lower weight while sections with larger differences 
 * will be weighted more since there is more to distinguish between each flower. 
 * It is essentially a centroid of all 50 values and a centroid of the 20 random values.
 * 
 * Weight for clustroid is that the smallest number has the highest priority.
 * NOT THE BEST, BUT DON'T KNOW ANY OTHER WAY TO WEIGHT
 * 
 * THE BEST ON AVERAGE SO FAR IS: the 50 average, weighted.
 * This current run, the clustroid(weighted and unweighted) and the 20 random data average are the best. 
 * 
 * IMPORTANT: JUST A REMINDER:
 * As stated earlier, generating a new TestData.txt and TrainingData.txt by running SeparateData.java will result in new data points chosen.
 * This will result in different error probabilities.
 * 
 */

public class Iris 
{
	//This stores all data from iris.txt
	private static ArrayList<Double> setosa_sepal_length = new ArrayList<Double>();
	private static ArrayList<Double> setosa_sepal_width = new ArrayList<Double>();
	private static ArrayList<Double> setosa_petal_length = new ArrayList<Double>();
	private static ArrayList<Double> setosa_petal_width = new ArrayList<Double>();
	private static ArrayList<Double> versicolor_sepal_length = new ArrayList<Double>();
	private static ArrayList<Double> versicolor_sepal_width = new ArrayList<Double>();
	private static ArrayList<Double> versicolor_petal_length = new ArrayList<Double>();
	private static ArrayList<Double> versicolor_petal_width = new ArrayList<Double>();
	private static ArrayList<Double> virginica_sepal_length = new ArrayList<Double>();
	private static ArrayList<Double> virginica_sepal_width = new ArrayList<Double>();
	private static ArrayList<Double> virginica_petal_length = new ArrayList<Double>();
	private static ArrayList<Double> virginica_petal_width = new ArrayList<Double>();
	
	//This stores the training data from TrainingData.txt
	private static ArrayList<Double> training_setosa_sepal_length = new ArrayList<Double>();
	private static ArrayList<Double> training_setosa_sepal_width = new ArrayList<Double>();
	private static ArrayList<Double> training_setosa_petal_length = new ArrayList<Double>();
	private static ArrayList<Double> training_setosa_petal_width = new ArrayList<Double>();
	private static ArrayList<Double> training_versicolor_sepal_length = new ArrayList<Double>();
	private static ArrayList<Double> training_versicolor_sepal_width = new ArrayList<Double>();
	private static ArrayList<Double> training_versicolor_petal_length = new ArrayList<Double>();
	private static ArrayList<Double> training_versicolor_petal_width = new ArrayList<Double>();
	private static ArrayList<Double> training_virginica_sepal_length = new ArrayList<Double>();
	private static ArrayList<Double> training_virginica_sepal_width = new ArrayList<Double>();
	private static ArrayList<Double> training_virginica_petal_length = new ArrayList<Double>();
	private static ArrayList<Double> training_virginica_petal_width = new ArrayList<Double>();
	
	//This stores the test data from TestData.txt
	private static ArrayList<Double> test_setosa_sepal_length = new ArrayList<Double>();
	private static ArrayList<Double> test_setosa_sepal_width = new ArrayList<Double>();
	private static ArrayList<Double> test_setosa_petal_length = new ArrayList<Double>();
	private static ArrayList<Double> test_setosa_petal_width = new ArrayList<Double>();
	private static ArrayList<Double> test_versicolor_sepal_length = new ArrayList<Double>();
	private static ArrayList<Double> test_versicolor_sepal_width = new ArrayList<Double>();
	private static ArrayList<Double> test_versicolor_petal_length = new ArrayList<Double>();
	private static ArrayList<Double> test_versicolor_petal_width = new ArrayList<Double>();
	private static ArrayList<Double> test_virginica_sepal_length = new ArrayList<Double>();
	private static ArrayList<Double> test_virginica_sepal_width = new ArrayList<Double>();
	private static ArrayList<Double> test_virginica_petal_length = new ArrayList<Double>();
	private static ArrayList<Double> test_virginica_petal_width = new ArrayList<Double>();
	
	//These averages are from when all 50 were averaged together
	private static final double[] SETOSA_AVERAGE = new double[] {5.006, 3.481, 1.464, 0.244};
	private static final double[] VERSICOLOR_AVERAGE = new double[] {5.936, 2.77, 4.26, 1.326};
	private static final double[] VIRGINICA_AVERAGE = new double[] {6.588, 2.974, 5.552, 2.026}; 
	
	//These averages are from when all 50 were averaged together
	private static final double[] SEPAL_LENGTH_AVERAGE = new double[] {5.006, 5.936, 6.588};
	private static final double[] SEPAL_WIDTH_AVERAGE = new double[] {3.481, 2.77, 2.974};
	private static final double[] PETAL_LENGTH_AVERAGE = new double[] {1.464, 4.26, 5.552};
	private static final double[] PETAL_WIDTH_AVERAGE = new double[] {0.244, 1.326, 2.026};
	
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		//DataReader stores the data based on the name of the file and the name in the data(the name of the iris)
		DataReader("iris.txt");
		DataReader("TrainingData.txt");
		DataReader("TestData.txt");

		//Test 50 case average Setosa values, 20 random case average Setosa values, and clustroid Setosa values
		//Tests over the 30 remaining test data irises for all tests
		System.out.println("Setosa:");
		System.out.println("\t" + "50 Case: " + "\t" + "\t" + "20 Random Case: " + "\t" + "50 Case UW: " + "\t" + "\t" + "20 Random Case UW: " + "\t" + "Clustroid: " + "\t" + "\t" + "Clustroid UW: ");
		int FiftySetosaCount = 0;
		int TwentySetosaCount = 0;
		int ClustroidSetosaCount = 0;
		int FiftySetosaCountUW = 0;			//UW = Unweighted
		int TwentySetosaCountUW = 0;
		int ClustroidSetosaCountUW = 0;
		
		//Runs through all test data for setosa iris 
		for(int i = 0; i < test_setosa_sepal_length.size(); i++)
		{
			System.out.print((i + 1) + ": " + "\t");
			//This classifies the setosa iris for the weighted 50 average and the weighted 20 random average
			ArrayList<String> iris = testAlgorithm(test_setosa_sepal_length.get(i), test_setosa_sepal_width.get(i), test_setosa_petal_length.get(i), test_setosa_petal_width.get(i), true); //First boolean is weighted vs un-weighted
			if(!(iris.get(0)).equals("Setosa")) 
			{
				FiftySetosaCount++;
			}
			if(!(iris.get(1)).equals("Setosa")) 
			{
				TwentySetosaCount++;
			}
			//This classifies the setosa iris for the unweighted 50 average and the weighted 20 random average
			ArrayList<String> irisUW = testAlgorithm(test_setosa_sepal_length.get(i), test_setosa_sepal_width.get(i), test_setosa_petal_length.get(i), test_setosa_petal_width.get(i), false);
			if(!(irisUW.get(0)).equals("Setosa")) 
			{
				FiftySetosaCountUW++;
			}
			if(!(irisUW.get(1)).equals("Setosa")) 
			{
				TwentySetosaCountUW++;
			}
			//This classifies the setosa iris for the weighted clustroid average
			String clustroidIris = clustroidTestAlgorithm(test_setosa_sepal_length.get(i), test_setosa_sepal_width.get(i), test_setosa_petal_length.get(i), test_setosa_petal_width.get(i), true);
			if(!clustroidIris.equals("Setosa")) 
			{
				ClustroidSetosaCount++;
			}
			//This classifies the setosa iris for the unweighted clustroid average
			String clustroidIrisUW = clustroidTestAlgorithm(test_setosa_sepal_length.get(i), test_setosa_sepal_width.get(i), test_setosa_petal_length.get(i), test_setosa_petal_width.get(i), false);
			if(!clustroidIrisUW.equals("Setosa")) 
			{
				ClustroidSetosaCountUW++;
			}
			System.out.println();
		}
		//This prints the error for each classification for the same test data
		System.out.print("Error: " + "\t" + FiftySetosaCount + "/" + test_setosa_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + FiftySetosaCountUW + "/" + test_setosa_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + TwentySetosaCount + "/" + test_setosa_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + TwentySetosaCountUW + "/" + test_setosa_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + ClustroidSetosaCount + "/" + test_setosa_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + ClustroidSetosaCountUW + "/" + test_setosa_sepal_length.size());
		System.out.println("\n");
		
		
		//Test 50 case average Versicolor values, 20 random case average Versicolor values, and clustroid Versicolor values
		//Tests over the 30 remaining test data irises for all tests
		System.out.println("Versicolor: ");
		System.out.println("\t" + "50 Case: " + "\t" + "\t" + "20 Random Case: " + "\t" + "50 Case UW: " + "\t" + "\t" + "20 Random Case UW: " + "\t" + "Clustroid: " + "\t" + "\t" + "Clustroid UW: ");
		int FiftyVersicolorCount = 0;
		int TwentyVersicolorCount = 0;
		int ClustroidVersicolorCount = 0;
		int FiftyVersicolorCountUW = 0;			//UW = Unweighted
		int TwentyVersicolorCountUW = 0;
		int ClustroidVersicolorCountUW = 0;
		
		for(int i = 0; i < test_versicolor_sepal_length.size(); i++)
		{
			System.out.print((i + 1) + ": " + "\t");
			//This classifies the versicolor iris for the weighted 50 average and the weighted 20 random average
			ArrayList<String> iris = testAlgorithm(test_versicolor_sepal_length.get(i), test_versicolor_sepal_width.get(i), test_versicolor_petal_length.get(i), test_versicolor_petal_width.get(i), true); //First boolean is weighted vs un-weighted	
			if(!(iris.get(0)).equals("Versicolor")) 
			{
				FiftyVersicolorCount++;
			}
			if(!(iris.get(1)).equals("Versicolor")) 
			{
				TwentyVersicolorCount++;
			}
			//This classifies the versicolor iris for the weighted 50 average and the unweighted 20 random average
			ArrayList<String> irisUW = testAlgorithm(test_versicolor_sepal_length.get(i), test_versicolor_sepal_width.get(i), test_versicolor_petal_length.get(i), test_versicolor_petal_width.get(i), false);
			if(!(irisUW.get(0)).equals("Versicolor")) 
			{
				FiftyVersicolorCountUW++;
			}
			if(!(irisUW.get(1)).equals("Versicolor")) 
			{
				TwentyVersicolorCountUW++;
			}
			//This classifies the versicolor iris for the weighted clustroid average
			String clustroidIris = clustroidTestAlgorithm(test_versicolor_sepal_length.get(i), test_versicolor_sepal_width.get(i), test_versicolor_petal_length.get(i), test_versicolor_petal_width.get(i), true);
			if(!clustroidIris.equals("Versicolor")) 
			{
				ClustroidVersicolorCount++;
			}
			//This classifies the versicolor iris for the unweighted clustroid average
			String clustroidIrisUW = clustroidTestAlgorithm(test_versicolor_sepal_length.get(i), test_versicolor_sepal_width.get(i), test_versicolor_petal_length.get(i), test_versicolor_petal_width.get(i), false);
			if(!clustroidIrisUW.equals("Versicolor")) 
			{
				ClustroidVersicolorCountUW++;
			}
			System.out.println();
			
		}
		//This prints the error for each classification for the same test data
		System.out.print("Error: " + "\t" + FiftyVersicolorCount + "/" + test_versicolor_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + TwentyVersicolorCount + "/" + test_versicolor_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + FiftyVersicolorCountUW + "/" + test_versicolor_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + TwentyVersicolorCountUW + "/" + test_versicolor_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + ClustroidVersicolorCount + "/" + test_versicolor_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + ClustroidVersicolorCountUW + "/" + test_versicolor_sepal_length.size());
		System.out.println("\n");
		
		
		//Test 50 case average Virginica values, 20 random case average Virginica values, and clustroid Virginica values
		//Tests over the 30 remaining test data irises for all tests
		System.out.println("Virginica:");
		System.out.println("\t" + "50 Case: " + "\t" + "\t" + "20 Random Case: " + "\t" + "50 Case UW: " + "\t" + "\t" + "20 Random Case UW: " + "\t" + "Clustroid: " + "\t" + "\t" + "Clustroid UW: ");
		int FiftyVirginicaCount = 0;
		int TwentyVirginicaCount = 0;
		int ClustroidVirginicaCount = 0;
		int FiftyVirginicaCountUW = 0;			//UW = Unweighted
		int TwentyVirginicaCountUW = 0;
		int ClustroidVirginicaCountUW = 0;
		
		//Runs through all the test data for virginica iris
		for(int i = 0; i < test_virginica_sepal_length.size(); i++)
		{
			System.out.print((i + 1) + ": " + "\t");
			//This classifies the virginica iris for the weighted 50 average and the weighted 20 random average
			ArrayList<String> iris = testAlgorithm(test_virginica_sepal_length.get(i), test_virginica_sepal_width.get(i), test_virginica_petal_length.get(i), test_virginica_petal_width.get(i), true); //First boolean is weighted vs un-weighted
			if(!(iris.get(0)).equals("Virginica")) 
			{
				FiftyVirginicaCount++;
			}
			if(!(iris.get(1)).equals("Virginica")) 
			{
				TwentyVirginicaCount++;
			}
			//This classifies the virginica iris for the unweighted 50 average and the weighted 20 random average
			ArrayList<String> irisUW = testAlgorithm(test_virginica_sepal_length.get(i), test_virginica_sepal_width.get(i), test_virginica_petal_length.get(i), test_virginica_petal_width.get(i), false); 
			if(!(irisUW.get(0)).equals("Virginica")) 
			{
				FiftyVirginicaCountUW++;
			}
			if(!(irisUW.get(1)).equals("Virginica")) 
			{
				TwentyVirginicaCountUW++;
			}
			//This classifies the virginica iris for the weighted clustroid average
			String clustroidIris = clustroidTestAlgorithm(test_virginica_sepal_length.get(i), test_virginica_sepal_width.get(i), test_virginica_petal_length.get(i), test_virginica_petal_width.get(i), true);
			if(!clustroidIris.equals("Virginica")) 
			{
				ClustroidVirginicaCount++;
			}
			//This classifies the virginica iris for the unweighted clustroid average 
			String clustroidIrisUW = clustroidTestAlgorithm(test_virginica_sepal_length.get(i), test_virginica_sepal_width.get(i), test_virginica_petal_length.get(i), test_virginica_petal_width.get(i), false);
			if(!clustroidIrisUW.equals("Virginica")) 
			{
				ClustroidVirginicaCountUW++;
			}
			System.out.println();
		}
		//This prints the error for each classification for the same test data
		System.out.print("Error: " + "\t" + FiftyVirginicaCount + "/" + test_virginica_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + TwentyVirginicaCount + "/" + test_virginica_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + FiftyVirginicaCountUW + "/" + test_virginica_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + TwentyVirginicaCountUW + "/" + test_virginica_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + ClustroidVirginicaCount + "/" + test_virginica_sepal_length.size());
		System.out.print("\t" + "\t" + "\t" + ClustroidVirginicaCountUW + "/" + test_virginica_sepal_length.size());
		System.out.println("\n");
	}
	
	//Stores all the training data and test data as well as the original dataset for later use
	public static void DataReader(String data) throws NumberFormatException, IOException
	{		
		double sepal_length;
		double sepal_width;
		double petal_length;
		double petal_width;
		String name;	
		
		Scanner scan = new Scanner(new File(data));
		scan.useDelimiter(",");
		
		while(scan.hasNextLine())
		{
			String line = scan.nextLine();
			
			String[] segment = line.split(",");
			
			sepal_length = Double.parseDouble(segment[0]);
			sepal_width = Double.parseDouble(segment[1]);
			petal_length = Double.parseDouble(segment[2]);
			petal_width = Double.parseDouble(segment[3]);
			name = segment[4];
			
			if(name.contains("setosa"))
			{
				if(data.contains("Training"))
				{
					training_setosa_sepal_length.add(sepal_length);
					training_setosa_sepal_width.add(sepal_width);
					training_setosa_petal_length.add(petal_length);
					training_setosa_petal_width.add(petal_width);
				}
				else if(data.contains("Test"))
				{
					test_setosa_sepal_length.add(sepal_length);
					test_setosa_sepal_width.add(sepal_width);
					test_setosa_petal_length.add(petal_length);
					test_setosa_petal_width.add(petal_width);
				}
				else
				{
					setosa_sepal_length.add(sepal_length);
					setosa_sepal_width.add(sepal_width);
					setosa_petal_length.add(petal_length);
					setosa_petal_width.add(petal_width);
				}
			}
			else if(name.contains("versicolor"))
			{
				if(data.contains("Training"))
				{
					training_versicolor_sepal_length.add(sepal_length);
					training_versicolor_sepal_width.add(sepal_width);
					training_versicolor_petal_length.add(petal_length);
					training_versicolor_petal_width.add(petal_width);
				}
				else if(data.contains("Test"))
				{
					test_versicolor_sepal_length.add(sepal_length);
					test_versicolor_sepal_width.add(sepal_width);
					test_versicolor_petal_length.add(petal_length);
					test_versicolor_petal_width.add(petal_width);
				}
				else
				{
					versicolor_sepal_length.add(sepal_length);
					versicolor_sepal_width.add(sepal_width);
					versicolor_petal_length.add(petal_length);
					versicolor_petal_width.add(petal_width);
				}
			}
			else if(name.contains("virginica"))
			{
				if(data.contains("Training"))
				{
					training_virginica_sepal_length.add(sepal_length);
					training_virginica_sepal_width.add(sepal_width);
					training_virginica_petal_length.add(petal_length);
					training_virginica_petal_width.add(petal_width);
				}
				else if(data.contains("Test"))
				{
					test_virginica_sepal_length.add(sepal_length);
					test_virginica_sepal_width.add(sepal_width);
					test_virginica_petal_length.add(petal_length);
					test_virginica_petal_width.add(petal_width);
				}
				else
				{
					virginica_sepal_length.add(sepal_length);
					virginica_sepal_width.add(sepal_width);
					virginica_petal_length.add(petal_length);
					virginica_petal_width.add(petal_width);
				}
			}
		}
		scan.close();		
	}
	
	//This determines the final decision for the 50 case and the 20 random case
	//and returns an arraylist with the string values.
	public static ArrayList<String> testAlgorithm(double a, double b, double c, double d, Boolean isWeighted)
	{	
		//This is for 50 cases average
		ArrayList<Double> setosa_average = new ArrayList<Double>();
		ArrayList<Double> versicolor_average = new ArrayList<Double>();
		ArrayList<Double> virginica_average = new ArrayList<Double>();
		for(int i = 0; i < 4; i++)
		{
			setosa_average.add(SETOSA_AVERAGE[i]);
			versicolor_average.add(VERSICOLOR_AVERAGE[i]);
			virginica_average.add(VIRGINICA_AVERAGE[i]);
		}
		
		//This stores the Error Probabilites for the 50 average case
		ArrayList<Double> setosa = ErrorProb(a, b, c, d, setosa_average, isWeighted);
		ArrayList<Double> versicolor = ErrorProb(a, b, c, d, versicolor_average, isWeighted);
		ArrayList<Double> virginica = ErrorProb(a, b, c, d, virginica_average, isWeighted);
		
		//This is for the random 20 random training data average
		ArrayList<Double> trainingSepalLength = IrisMethods.storeAverageInArray(training_setosa_sepal_length, training_versicolor_sepal_length, training_virginica_sepal_length);
		ArrayList<Double> trainingSepalWidth = IrisMethods.storeAverageInArray(training_setosa_sepal_width, training_versicolor_sepal_width, training_virginica_sepal_width);
		ArrayList<Double> trainingPetalLength = IrisMethods.storeAverageInArray(training_setosa_petal_length, training_versicolor_petal_length, training_virginica_petal_length);
		ArrayList<Double> trainingPetalWidth = IrisMethods.storeAverageInArray(training_setosa_petal_width, training_versicolor_petal_width, training_virginica_petal_width);
		
		ArrayList<Double> setosaAverage = IrisMethods.reverseArray(0, trainingSepalLength, trainingSepalWidth, trainingPetalLength, trainingPetalWidth);
		ArrayList<Double> versicolorAverage = IrisMethods.reverseArray(1, trainingSepalLength, trainingSepalWidth, trainingPetalLength, trainingPetalWidth);
		ArrayList<Double> virginicaAverage = IrisMethods.reverseArray(2, trainingSepalLength, trainingSepalWidth, trainingPetalLength, trainingPetalWidth);
				
		//This stores the Error Probabilites for the 20 random training data average
		ArrayList<Double> setosa2 = twentyRandomErrorProb(a, b, c, d, setosaAverage, isWeighted);
		ArrayList<Double> versicolor2 = twentyRandomErrorProb(a, b, c, d, versicolorAverage, isWeighted);
		ArrayList<Double> virginica2 = twentyRandomErrorProb(a, b, c, d, virginicaAverage, isWeighted);
		
		double setosa_total, setosa_total_2;			setosa_total = setosa_total_2 = 0;
		double versicolor_total, versicolor_total_2;	versicolor_total = versicolor_total_2 = 0;
		double virginica_total, virginica_total_2;		virginica_total = virginica_total_2 = 0;
		
		//This adds all the probability of each individual part of the iris and stores them
		//Both for the 50 average and the 20 random average
		for(int i = 0; i < setosa.size(); i++)
		{
			setosa_total = setosa_total + setosa.get(i);
			setosa_total_2 = setosa_total_2 + setosa2.get(i);
			versicolor_total = versicolor_total + versicolor.get(i);
			versicolor_total_2 = versicolor_total_2 + versicolor2.get(i);
			virginica_total = virginica_total + virginica.get(i);
			virginica_total_2 = virginica_total_2 + virginica2.get(i);
		}
		
		//This adds the final probability of the 50 case average classfication
		ArrayList<Double> fifty_case_classification = new ArrayList<Double>();
		fifty_case_classification.add(setosa_total);
		fifty_case_classification.add(versicolor_total);
		fifty_case_classification.add(virginica_total);
		
		//This adds the final probability of the 20 random case average classfication
		ArrayList<Double> twenty_rand_classification = new ArrayList<Double>();
		twenty_rand_classification.add(setosa_total_2);
		twenty_rand_classification.add(versicolor_total_2);
		twenty_rand_classification.add(virginica_total_2);
		
		//This returns the and displays the final decision and stores the string in an arraylist and returns the arraylist
		ArrayList<String> iris = new ArrayList<String>();
		String fifty_iris = IrisMethods.displayResults(fifty_case_classification);
		String twenty_iris = IrisMethods.displayResults(twenty_rand_classification);
		
		iris.add(fifty_iris);
		iris.add(twenty_iris);
		
		return iris;
	}
	
	//This calculates the error probability of the 50 case average
	public static ArrayList<Double> ErrorProb(double a, double b, double c, double d , ArrayList<Double> e, Boolean isWeighted)
	{
		//This is for 50 case average
		double f = IrisMethods.differenceAverage(SEPAL_LENGTH_AVERAGE);
		double g = IrisMethods.differenceAverage(SEPAL_WIDTH_AVERAGE);
		double h = IrisMethods.differenceAverage(PETAL_LENGTH_AVERAGE);
		double i = IrisMethods.differenceAverage(PETAL_WIDTH_AVERAGE);
		
		//Weights for 50 case average
		double[] weights = IrisMethods.weightCalculator(f, g, h, i);
		
		if(isWeighted == true)
		{
			//Error % for 50 case average weighted and returns an arraylist
			double sepal_length_error = (1 - (Math.abs(a - e.get(0)) / e.get(0))) * weights[0];
			double sepal_width_error = (1 - (Math.abs(b - e.get(1)) / e.get(1))) * weights[1];
			double petal_length_error = (1 - (Math.abs(c - e.get(2)) / e.get(2))) * weights[2];
			double petal_width_error = (1 - (Math.abs(d - e.get(3)) / e.get(3))) * weights[3];
			
			ArrayList<Double> returnArray = new ArrayList<Double>();
			returnArray.add(sepal_length_error);
			returnArray.add(sepal_width_error);
			returnArray.add(petal_length_error);
			returnArray.add(petal_width_error);

			return returnArray;
		}
		else
		{
			//Error % for 50 case average unweighted and returns an arraylist
			double sepal_length_error_nw = (1 - (Math.abs(a - e.get(0)) / e.get(0)));
			double sepal_width_error_nw = (1 - (Math.abs(b - e.get(1)) / e.get(1)));
			double petal_length_error_nw = (1 - (Math.abs(c - e.get(2)) / e.get(2)));
			double petal_width_error_nw = (1 - (Math.abs(d - e.get(3)) / e.get(3)));
			
			ArrayList<Double> returnArray_nw = new ArrayList<Double>();
			returnArray_nw.add(sepal_length_error_nw);
			returnArray_nw.add(sepal_width_error_nw);
			returnArray_nw.add(petal_length_error_nw);
			returnArray_nw.add(petal_width_error_nw);

			return returnArray_nw;
		}		
	}
	
	//This calculates the error probability of the 20 random average
	public static ArrayList<Double> twentyRandomErrorProb(double a, double b, double c, double d , ArrayList<Double> e, Boolean isWeighted)
	{
		//This is for 20 random training data average
		double f = IrisMethods.differenceAverage(IrisMethods.storeAverageInArray(training_setosa_sepal_length, training_versicolor_sepal_length, training_virginica_sepal_length));
		double g = IrisMethods.differenceAverage(IrisMethods.storeAverageInArray(training_setosa_sepal_width, training_versicolor_sepal_width, training_virginica_sepal_width));
		double h = IrisMethods.differenceAverage(IrisMethods.storeAverageInArray(training_setosa_petal_length, training_versicolor_petal_length, training_virginica_petal_length));
		double i = IrisMethods.differenceAverage(IrisMethods.storeAverageInArray(training_setosa_petal_width, training_versicolor_petal_width, training_virginica_petal_width));
		
		//Weights for 20 random training data average
		double[] weights2 = IrisMethods.weightCalculator(f, g, h, i);
		
		if(isWeighted == true)
		{
			//Error % for 20 random data average weighted and returns an arraylist
			double random_20_sepal_length_error = (1 - (Math.abs(a - e.get(0)) / e.get(0))) * weights2[0];
			double random_20_sepal_width_error = (1 - (Math.abs(b - e.get(1)) / e.get(1))) * weights2[1];
			double random_20_petal_length_error = (1 - (Math.abs(c - e.get(2)) / e.get(2))) * weights2[2];
			double random_20_petal_width_error = (1 - (Math.abs(d - e.get(3)) / e.get(3))) * weights2[3];
			
			ArrayList<Double> returnArray2 = new ArrayList<Double>();
			returnArray2.add(random_20_sepal_length_error);
			returnArray2.add(random_20_sepal_width_error);
			returnArray2.add(random_20_petal_length_error);
			returnArray2.add(random_20_petal_width_error);
			
			return returnArray2;
		}
		else
		{	
			//Error % for 20 random data average unweighted and returns an arraylist
			double random_20_sepal_length_error_nw = (1 - (Math.abs(a - e.get(0)) / e.get(0)));
			double random_20_sepal_width_error_nw = (1 - (Math.abs(b - e.get(1)) / e.get(1)));
			double random_20_petal_length_error_nw = (1 - (Math.abs(c - e.get(2)) / e.get(2)));
			double random_20_petal_width_error_nw = (1 - (Math.abs(d - e.get(3)) / e.get(3)));
		
			ArrayList<Double> returnArray2_nw = new ArrayList<Double>();
			returnArray2_nw.add(random_20_sepal_length_error_nw);
			returnArray2_nw.add(random_20_sepal_width_error_nw);
			returnArray2_nw.add(random_20_petal_length_error_nw);
			returnArray2_nw.add(random_20_petal_width_error_nw);
			
			return returnArray2_nw;
		}
	}
	
	//Passes information into the clustroidError method to determine the final decision
	public static String clustroidTestAlgorithm(double sepal_length, double sepal_width, double petal_length, double petal_width, Boolean isWeighted)
	{
		ArrayList<Double> clustroidErrorArray = clustroidError(sepal_length, sepal_width, petal_length, petal_width, isWeighted);
		String clustroidIris = IrisMethods.displayResults(clustroidErrorArray);
		return clustroidIris;
	}
	
	//Calculates the error probability of the clustroid
	public static ArrayList<Double> clustroidError(double sepal_length, double sepal_width, double petal_length, double petal_width, Boolean isWeighted)
	{
		//Calculates the clustroid of the training data
		int	setosa_clustroid_index = Clustroid.clustroidPicker(training_setosa_sepal_length, training_setosa_sepal_width, 
												 			training_setosa_petal_length, training_setosa_petal_width);

		int versicolor_clustroid_index = Clustroid.clustroidPicker(training_versicolor_sepal_length, training_versicolor_sepal_width, 
																training_versicolor_petal_length, training_versicolor_petal_width);
		
		int virginica_clustroid_index = Clustroid.clustroidPicker(training_virginica_sepal_length, training_virginica_sepal_width, 
															   training_virginica_petal_length, training_virginica_petal_width);

		//Sets the clustroid to an arraylist separated by parts of the iris
		ArrayList<Double> sepal_length_clustroid_average = new ArrayList<Double>();
		sepal_length_clustroid_average.add(training_setosa_sepal_length.get(setosa_clustroid_index));
		sepal_length_clustroid_average.add(training_versicolor_sepal_length.get(versicolor_clustroid_index));
		sepal_length_clustroid_average.add(training_virginica_sepal_length.get(virginica_clustroid_index));
		
		ArrayList<Double> sepal_width_clustroid_average = new ArrayList<Double>();
		sepal_width_clustroid_average.add(training_setosa_sepal_width.get(setosa_clustroid_index));
		sepal_width_clustroid_average.add(training_versicolor_sepal_width.get(versicolor_clustroid_index));
		sepal_width_clustroid_average.add(training_virginica_sepal_width.get(virginica_clustroid_index));
		
		ArrayList<Double> petal_length_clustroid_average = new ArrayList<Double>();
		petal_length_clustroid_average.add(training_setosa_petal_length.get(setosa_clustroid_index));
		petal_length_clustroid_average.add(training_versicolor_petal_length.get(versicolor_clustroid_index));
		petal_length_clustroid_average.add(training_virginica_petal_length.get(virginica_clustroid_index));
		
		ArrayList<Double> petal_width_clustroid_average = new ArrayList<Double>();
		petal_width_clustroid_average.add(training_setosa_petal_width.get(setosa_clustroid_index));
		petal_width_clustroid_average.add(training_versicolor_petal_width.get(versicolor_clustroid_index));
		petal_width_clustroid_average.add(training_virginica_petal_width.get(virginica_clustroid_index));
		
		//Calculates the weights of the clustroid based on the clustroid selected
		double[] setosa_weights = Clustroid.clustroidWeightCalculator(0, sepal_length_clustroid_average, sepal_width_clustroid_average, petal_length_clustroid_average, petal_width_clustroid_average);
		double[] versicolor_weights = Clustroid.clustroidWeightCalculator(1, sepal_length_clustroid_average, sepal_width_clustroid_average, petal_length_clustroid_average, petal_width_clustroid_average);
		double[] virginica_weights = Clustroid.clustroidWeightCalculator(2, sepal_length_clustroid_average, sepal_width_clustroid_average, petal_length_clustroid_average, petal_width_clustroid_average);

		//Calculates the final error probability for each iris type both weighted and unweighted
		double setosa_total = (1 - Math.abs((sepal_length - sepal_length_clustroid_average.get(0)) / sepal_length_clustroid_average.get(0))) + 
					   		  (1 - Math.abs((sepal_width - sepal_width_clustroid_average.get(0)) / sepal_width_clustroid_average.get(0))) + 
					   		  (1 - Math.abs((petal_length - petal_length_clustroid_average.get(0)) / petal_length_clustroid_average.get(0))) +
					   		  (1 - Math.abs((petal_width - petal_width_clustroid_average.get(0)) / petal_width_clustroid_average.get(0)));	
			
		double versicolor_total = (1 - Math.abs((sepal_length - sepal_length_clustroid_average.get(1)) / sepal_length_clustroid_average.get(1))) + 
								  (1 - Math.abs((sepal_width - sepal_width_clustroid_average.get(1)) / sepal_width_clustroid_average.get(1))) + 
								  (1 - Math.abs((petal_length - petal_length_clustroid_average.get(1)) / petal_length_clustroid_average.get(1))) +
								  (1 - Math.abs((petal_width - petal_width_clustroid_average.get(1)) / petal_width_clustroid_average.get(1)));	
		
		double virginica_total = (1 - Math.abs((sepal_length - sepal_length_clustroid_average.get(2)) / sepal_length_clustroid_average.get(2))) + 
				   				 (1 - Math.abs((sepal_width - sepal_width_clustroid_average.get(2)) / sepal_width_clustroid_average.get(2))) + 
				   				 (1 - Math.abs((petal_length - petal_length_clustroid_average.get(2)) / petal_length_clustroid_average.get(2))) +
				   				 (1 - Math.abs((petal_width - petal_width_clustroid_average.get(2)) / petal_width_clustroid_average.get(2)));	
		
		double setosa_total_weighted = (1 - Math.abs((sepal_length - sepal_length_clustroid_average.get(0)) / sepal_length_clustroid_average.get(0))) * setosa_weights[0] + 
		   		  					   (1 - Math.abs((sepal_width - sepal_width_clustroid_average.get(0)) / sepal_width_clustroid_average.get(0))) * setosa_weights[1] + 
		   		  					   (1 - Math.abs((petal_length - petal_length_clustroid_average.get(0)) / petal_length_clustroid_average.get(0))) * setosa_weights[2] +
		   		  					   (1 - Math.abs((petal_width - petal_width_clustroid_average.get(0)) / petal_width_clustroid_average.get(0))) * setosa_weights[3];	

		double versicolor_total_weighted = (1 - Math.abs((sepal_length - sepal_length_clustroid_average.get(1)) / sepal_length_clustroid_average.get(1))) * versicolor_weights[0] + 
										   (1 - Math.abs((sepal_width - sepal_width_clustroid_average.get(1)) / sepal_width_clustroid_average.get(1))) * versicolor_weights[1] + 
										   (1 - Math.abs((petal_length - petal_length_clustroid_average.get(1)) / petal_length_clustroid_average.get(1))) * versicolor_weights[2] +
										   (1 - Math.abs((petal_width - petal_width_clustroid_average.get(1)) / petal_width_clustroid_average.get(1))) * versicolor_weights[3];	

		double virginica_total_weighted = (1 - Math.abs((sepal_length - sepal_length_clustroid_average.get(2)) / sepal_length_clustroid_average.get(2))) * virginica_weights[0] + 
	   				 					  (1 - Math.abs((sepal_width - sepal_width_clustroid_average.get(2)) / sepal_width_clustroid_average.get(2))) * virginica_weights[1] + 
	   				 					  (1 - Math.abs((petal_length - petal_length_clustroid_average.get(2)) / petal_length_clustroid_average.get(2))) * virginica_weights[2] +
	   				 					  (1 - Math.abs((petal_width - petal_width_clustroid_average.get(2)) / petal_width_clustroid_average.get(2))) * virginica_weights[3] ;	
		
		//Based on whether it is weighted or unweighted, it adds the value to an arraylist and returns the arraylist
		if(isWeighted == false)
		{
			ArrayList<Double> returnArray = new ArrayList<Double>();
			//Error % for clustroid unweighted
			returnArray.add(setosa_total);
			returnArray.add(versicolor_total);
			returnArray.add(virginica_total);
			return returnArray;
		}
		else
		{
			ArrayList<Double> returnArray_weighted = new ArrayList<Double>();
			//Error % for clustroid weighted
			returnArray_weighted.add(setosa_total_weighted);
			returnArray_weighted.add(versicolor_total_weighted);
			returnArray_weighted.add(virginica_total_weighted);
			return returnArray_weighted;
		}		
	}

	//This is prints out all the data in a table form
	public static void testData()
	{
		DecimalFormat df = new DecimalFormat("##.####");
		
		System.out.println("\t" + "\t" + "SL Max" + "\t" + "SL Min"+ "\t" + "SL Ave" + "\t" + "SW Max" + "\t" + "SW Min" + "\t" + "SW Ave" + "\t" + 
		"PL Max" + "\t" + "PL Min" + "\t" + "PL Ave" + "\t" + "PW Max" + "\t" + "PW Min" + "\t"+ "PW Ave" + "\t" + "C#" + "\t" + "C SL" + "\t" + "C SW" + "\t" + "C PL" + "\t" + "C PW");
		
		//Setosa max and min for sepal length and width and petal length and width
		System.out.print("setosa" + "\t" + "\t");
		
		double setosa_sl_total = 0;
		double setosa_sw_total = 0;
		double setosa_pl_total = 0;
		double setosa_pw_total = 0;
		
		double maxSetosa_S_Length = Collections.max(training_setosa_sepal_length); //Max setosa sepal length
		double minSetosa_S_Length = Collections.min(training_setosa_sepal_length); //Min setosa sepal length
		for(int i = 0; i < training_setosa_sepal_length.size(); i++)
		{
			setosa_sl_total = setosa_sl_total + training_setosa_sepal_length.get(i);
		}
		double averageSetosa_S_Length = setosa_sl_total / training_setosa_sepal_length.size(); //Average setosa sepal length
		
		double maxSetosa_S_Width = Collections.max(training_setosa_sepal_width); //Max setosa sepal width
		double minSetosa_S_Width = Collections.min(training_setosa_sepal_width); //Min setosa sepal width
		for(int i = 0; i < training_setosa_sepal_width.size(); i++)
		{
			setosa_sw_total = setosa_sw_total + training_setosa_sepal_width.get(i);
		}
		double averageSetosa_S_Width = setosa_sw_total / training_setosa_sepal_width.size(); //Average setosa sepal width
		
		double maxSetosa_P_Length = Collections.max(training_setosa_petal_length); //Max setosa petal length
		double minSetosa_P_Length = Collections.min(training_setosa_petal_length); //Min setosa petal length
		for(int i = 0; i < training_setosa_petal_length.size(); i++)
		{
			setosa_pl_total = setosa_pl_total + training_setosa_petal_length.get(i);
		}
		double averageSetosa_P_Length = setosa_pl_total / training_setosa_petal_length.size(); //Average setosa petal length
		
		double maxSetosa_P_Width = Collections.max(training_setosa_petal_width); //Max setosa petal width
		double minSetosa_P_Width = Collections.min(training_setosa_petal_width); //Min setosa petal length
		for(int i = 0; i < training_setosa_petal_width.size(); i++)
		{
			setosa_pw_total = setosa_pw_total + training_setosa_petal_width.get(i);
		}
		double averageSetosa_P_Width = setosa_pw_total / training_setosa_petal_width.size(); //Average setosa petal width
		
		System.out.print(maxSetosa_S_Length + "\t" + minSetosa_S_Length + "\t" + df.format(averageSetosa_S_Length) + "\t");
		System.out.print(maxSetosa_S_Width + "\t" + minSetosa_S_Width + "\t" + df.format(averageSetosa_S_Width) + "\t");
		System.out.print(maxSetosa_P_Length + "\t" + minSetosa_P_Length + "\t" + df.format(averageSetosa_P_Length) + "\t");
		System.out.print(maxSetosa_P_Width + "\t" + minSetosa_P_Width + "\t" + df.format(averageSetosa_P_Width) + "\t");
		
		int x = Clustroid.clustroidPicker(training_setosa_sepal_length, training_setosa_sepal_width, training_setosa_petal_length, training_setosa_petal_width);
		System.out.print(x + 1 + "\t" + training_setosa_sepal_length.get(x) + "\t" + training_setosa_sepal_width.get(x) + "\t" + training_setosa_petal_length.get(x) + "\t" + training_setosa_petal_width.get(x) + "\n");
	
		//Versicolor max and min for sepal length and width and petal length and width
		System.out.print("versicolor" + "\t");
		
		double versicolor_sl_total = 0;
		double versicolor_sw_total = 0;
		double versicolor_pl_total = 0;
		double versicolor_pw_total = 0;
		
		double maxVersicolor_S_Length = Collections.max(training_versicolor_sepal_length); //Max versicolor sepal length
		double minVersicolor_S_Length = Collections.min(training_versicolor_sepal_length); //Min versicolor sepal length
		for(int i = 0; i < training_versicolor_sepal_length.size(); i++)
		{
			versicolor_sl_total = versicolor_sl_total + training_versicolor_sepal_length.get(i);
		}
		double averageVersicolor_S_Length = versicolor_sl_total / training_versicolor_sepal_length.size(); //Average versicolor sepal length
		
		double maxVersicolor_S_Width = Collections.max(training_versicolor_sepal_width); //Max versicolor sepal width
		double minVersicolor_S_Width = Collections.min(training_versicolor_sepal_width); //Min versicolor sepal width
		for(int i = 0; i < training_versicolor_sepal_width.size(); i++)
		{
			versicolor_sw_total = versicolor_sw_total + training_versicolor_sepal_width.get(i);
		}
		double averageVersicolor_S_Width = versicolor_sw_total / training_versicolor_sepal_width.size(); //Average versicolor sepal width
		
		double maxVersicolor_P_Length = Collections.max(training_versicolor_petal_length); //Max versicolor petal length
		double minVersicolor_P_Length = Collections.min(training_versicolor_petal_length); //Min versicolor petal length
		for(int i = 0; i < training_versicolor_petal_length.size(); i++)
		{
			versicolor_pl_total = versicolor_pl_total + training_versicolor_petal_length.get(i);
		}
		double averageVersicolor_P_Length = versicolor_pl_total / training_versicolor_petal_length.size(); //Average versicolor petal length
		
		double maxVersicolor_P_Width = Collections.max(training_versicolor_petal_width); //Max versicolor petal width
		double minVersicolor_P_Width = Collections.min(training_versicolor_petal_width); //Min versicolor petal width
		for(int i = 0; i < training_versicolor_petal_width.size(); i++)
		{
			versicolor_pw_total = versicolor_pw_total + training_versicolor_petal_width.get(i);
		}
		double averageVersicolor_P_Width = versicolor_pw_total / training_versicolor_petal_width.size(); //Average versicolor petal width
		
		System.out.print(maxVersicolor_S_Length + "\t" + minVersicolor_S_Length + "\t" + df.format(averageVersicolor_S_Length) + "\t");
		System.out.print(maxVersicolor_S_Width + "\t" + minVersicolor_S_Width + "\t" + df.format(averageVersicolor_S_Width) + "\t");
		System.out.print(maxVersicolor_P_Length + "\t" + minVersicolor_P_Length + "\t" + df.format(averageVersicolor_P_Length) + "\t");
		System.out.print(maxVersicolor_P_Width + "\t" + minVersicolor_P_Width + "\t" + df.format(averageVersicolor_P_Width) + "\t");
		
		int y = Clustroid.clustroidPicker(training_versicolor_sepal_length, training_versicolor_sepal_width, training_versicolor_petal_length, training_versicolor_petal_width);
		System.out.print(y + 1 + "\t" + training_versicolor_sepal_length.get(y) + "\t" + training_versicolor_sepal_width.get(y) + "\t" + training_versicolor_petal_length.get(y) + "\t" + training_versicolor_petal_width.get(y) + "\n");
				
		//Viriginica max and min for sepal length and width and petal length and width
		System.out.print("virginica" + "\t");
		
		double virginica_sl_total = 0;
		double virginica_sw_total = 0;
		double virginica_pl_total = 0;
		double virginica_pw_total = 0;
		
		double maxViriginica_S_Length = Collections.max(training_virginica_sepal_length); //Max virginica sepal length
		double minViriginica_S_Length = Collections.min(training_virginica_sepal_length); //Min virginica sepal length
		for(int i = 0; i < training_virginica_sepal_length.size(); i++)
		{
			virginica_sl_total = virginica_sl_total + training_virginica_sepal_length.get(i);
		}
		double averageViriginica_S_Length = virginica_sl_total / training_virginica_sepal_length.size(); //Average virginica sepal length
		
		double maxViriginica_S_Width = Collections.max(training_virginica_sepal_width); //Max virginica sepal width
		double minViriginica_S_Width = Collections.min(training_virginica_sepal_width); //Min virginica sepal width
		for(int i = 0; i < training_virginica_sepal_width.size(); i++)
		{
			virginica_sw_total = virginica_sw_total + training_virginica_sepal_width.get(i);
		}
		double averageViriginica_S_Width = virginica_sw_total / training_virginica_sepal_width.size(); //Average virginical sepal width
		
		double maxViriginica_P_Length = Collections.max(training_virginica_petal_length); //Max virginica petal length
		double minViriginica_P_Length = Collections.min(training_virginica_petal_length); //Min virginical petal length
		for(int i = 0; i < training_virginica_petal_length.size(); i++)
		{
			virginica_pl_total = virginica_pl_total + training_virginica_petal_length.get(i);
		}
		double averageViriginica_P_Length = virginica_pl_total / training_virginica_petal_length.size(); //Average virginica petal length
		
		double maxViriginica_P_Width = Collections.max(training_virginica_petal_width); //Max virginica petal width
		double minViriginica_P_Width = Collections.min(training_virginica_petal_width); //Min virginica petal width
		for(int i = 0; i < training_virginica_petal_width.size(); i++)
		{
			virginica_pw_total = virginica_pw_total + training_virginica_petal_width.get(i);
		}
		double averageViriginica_P_Width = virginica_pw_total / training_virginica_petal_width.size(); //Average virginica petal width
		
		System.out.print(maxViriginica_S_Length + "\t" + minViriginica_S_Length + "\t" + df.format(averageViriginica_S_Length) + "\t");
		System.out.print(maxViriginica_S_Width + "\t" + minViriginica_S_Width + "\t" + df.format(averageViriginica_S_Width) + "\t");
		System.out.print(maxViriginica_P_Length + "\t" + minViriginica_P_Length + "\t" + df.format(averageViriginica_P_Length) + "\t");		
		System.out.print(maxViriginica_P_Width + "\t" + minViriginica_P_Width + "\t" + df.format(averageViriginica_P_Width) + "\t");
		
		int z = Clustroid.clustroidPicker(training_virginica_sepal_length, training_virginica_sepal_width, training_virginica_petal_length, training_virginica_petal_width);
		System.out.print(z + 1 + "\t" + training_virginica_sepal_length.get(z) + "\t" + training_virginica_sepal_width.get(z) + "\t" + training_virginica_petal_length.get(z) + "\t" + training_virginica_petal_width.get(z) + "\n");
	}
}
