import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SeparateData 
{
	//Generates Random data from iris.txt
	//only run if you need new data
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> trainingData = new ArrayList<String>();
		ArrayList<String> testData = new ArrayList<String>();
		
		ArrayList<String> setosaData = new ArrayList<String>();
		ArrayList<String> versicolorData = new ArrayList<String>();
		ArrayList<String> virginicaData = new ArrayList<String>();
		
		Scanner scan = new Scanner(new File("iris.txt"));
		
		while(scan.hasNextLine())
		{
			String line = scan.nextLine();
			
			if(line.contains("setosa"))
			{
				setosaData.add(line);
			}
			else if(line.contains("versicolor"))
			{
				versicolorData.add(line);
			}
			else if(line.contains("virginica"))
			{
				virginicaData.add(line);
			}
		}
		scan.close();
		
		for(int i = 0; i < 20; i++)
		{
			int x = randomNumber(setosaData.size());
			trainingData.add(setosaData.get(x - 1));
			setosaData.remove(setosaData.get(x - 1));
		}
		for(int i = 0; i < setosaData.size(); i++)
		{
			testData.add(setosaData.get(i));
		}
		
		for(int i = 0; i < 20; i++)
		{
			int x = randomNumber(versicolorData.size());
			trainingData.add(versicolorData.get(x - 1));
			versicolorData.remove(versicolorData.get(x - 1));
		}
		for(int i = 0; i < versicolorData.size(); i++)
		{
			testData.add(versicolorData.get(i));
		}
		
		for(int i = 0; i < 20; i++)
		{
			int x = randomNumber(virginicaData.size());
			trainingData.add(virginicaData.get(x - 1));
			virginicaData.remove(virginicaData.get(x - 1));
		}
		for(int i = 0; i < virginicaData.size(); i++)
		{
			testData.add(virginicaData.get(i));
		}
		
		FileWriter trainingFile = new FileWriter("TrainingData.txt");
		FileWriter testFile = new FileWriter("TestData.txt");
		
		for(int i = 0; i < trainingData.size(); i++)
		{
			trainingFile.write(trainingData.get(i) + "\r\n");
		}
		for(int i = 0; i < testData.size(); i++)
		{
			testFile.write(testData.get(i) + "\r\n");
		}
		
		trainingFile.close();
		testFile.close();
	}
	
	public static int randomNumber(int number)
	{
		int max = number;
		int min = 1;
		int range = max - min + 1;
		int rand = (int)(Math.random() * range) + min;
		return rand;
	}
}
