package github.mrzakiakkari;

import java.text.NumberFormat;
import java.util.concurrent.ThreadLocalRandom;

public class LondonTubeStatistics
{

	String[] stations =
	{
		"Acton Town", "Angel", "Arsenal", "Baker Street", "Camden Town", "Charing Cross",
		"Ealing Broadway", "Earls Court", "Elephant & Castle", "Kilburn", "Leicester Square",
		"Liverpool Street", "Oxford Circus", "Paddington", "Piccadilly Circus", "Tooting",
		"Vauxhall", "Victoria", "Wembley Park", "Wimbledon"
	};

	String[] headers =
	{
		"Weekday", "Saturday", "Sunday", "Weekday", "Saturday", "Sunday"
	};

	int[][] data = new int[stations.length][headers.length];

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args)
	{
		new LondonTubeStatistics();
	}//end main

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public LondonTubeStatistics()
	{

		fillData(data);
		fillDataArrayWithGeneratedStatistics(data);

		printData(data);

		findBusiestStation(data);

		findPercChange(data);

	}//end constructor

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void fillData(int[][] data)
	{
		for (int[] data1 : data)
		{
			for (int col = 0; col < headers.length; col++)
			{
				switch (col)
				{
					case 0:
					case 3:
						//weekday
						data1[col] = ThreadLocalRandom.current().nextInt(50000, 140000 + 1);
						break;
					case 1:
					case 4:
						//Saturday
						data1[col] = ThreadLocalRandom.current().nextInt(30000, 80000 + 1);
						break;
					default:
						//Sunday
						data1[col] = ThreadLocalRandom.current().nextInt(5000, 15000 + 1);
						break;
				} //end switch
			} //end for
		} //end for

	}//end fillData()

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void printData(int[][] data)
	{

		System.out.format("%38s%35s\n", "Jan-Jun", "Jul-Dec");

		System.out.format("%-20s", " ");
		for (int i = 0; i < headers.length; i++)
		{
			System.out.format("%-10s", headers[i]);
			if (i == 2)
			{
				System.out.format("%-5s", "|");
			}
		}

		System.out.println("");

		for (int row = 0; row < data.length; row++)
		{
			System.out.format("%-20s", stations[row]);
			for (int col = 0; col < headers.length; col++)
			{
				System.out.format("%-10d", data[row][col]);

				if (col == 2)
				{
					System.out.format("%-5s", "|");
				}//end if
			}//end for
			System.out.println("");
		}//end for
	}//end method printData()

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void findBusiestStation(int[][] data)
	{

		int[] entryTotals = new int[stations.length];

		for (int row = 0; row < data.length; row++)
		{
			for (int col = 0; col < headers.length; col++)
			{
				entryTotals[row] += data[row][col];
			}//end for
		}//end for

		int largest = entryTotals[0];
		int largestStation = 0;

		for (int i = 0; i < stations.length; i++)
		{

			if (entryTotals[i] > largest)
			{
				largest = entryTotals[i];
				largestStation = i;
			}//end if
		}//end for

		System.out.println("The busiest station is " + stations[largestStation] + " with " + largest + " passengers");

	}//end method findBusiestStation()

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void findPercChange(int[][] data)
	{

		NumberFormat percForm = NumberFormat.getPercentInstance();

		percForm.setMaximumFractionDigits(2);

		int[][] sixMonthTotals = new int[stations.length][2];

		//Jan - Jun
		for (int row = 0; row < data.length; row++)
		{
			for (int col = 0; col < 3; col++)
			{
				sixMonthTotals[row][0] += data[row][col];
			}//end for
		}//end for

		//July - Dec
		for (int row = 0; row < data.length; row++)
		{
			for (int col = 3; col < headers.length; col++)
			{
				sixMonthTotals[row][1] += data[row][col];
			}//end for
		}//end for

		//^^small bit of repetition above. Could be reengineered as a single method with two separate calls
		double[] percentChange = new double[stations.length];

		for (int row = 0; row < sixMonthTotals.length; row++)
		{
			for (int col = 0; col <= 1; col++)
			{
				int diff = sixMonthTotals[row][1] - sixMonthTotals[row][0];
				percentChange[row] = (double) diff / sixMonthTotals[row][0];
			}//end for
		}//end for

		System.out.println("\nPercentage increase/decrease");
		for (int i = 0; i < data.length; i++)
		{
			System.out.println(stations[i] + ": " + percForm.format(percentChange[i]));
		}//end for

	}//end method findPercChange()

	public String printOutput(int[][] stationsDataArray)
	{
		String text = getHeader1()
				+ getHeader2()
				+ getStations(stationsDataArray)
				+ getBusiestStations(stationsDataArray)
				+ getHeader3()
				+ getPercentageDifferences(stationsDataArray);

		return text;
	}

	private void fillDataArrayWithGeneratedStatistics(int[][] stationsDataArray)
	{
		for (int[] stationData : stationsDataArray)
		{
			for (int dayIndex = 0; dayIndex < stationData.length; dayIndex++)
			{
				stationData[dayIndex] = generateStatistic(dayIndex);
			}
		}
	}

	private int generateStatistic(int dayIndex)
	{
		switch (dayIndex)
		{
			case yearFirstHalfWeekday:
			case yearSecondHalfWeekday:
				return ThreadLocalRandom.current().nextInt(weekdayLowerBound, weekdayUpperBound + 1);
			case 1:
			case 4:
				//Saturday
				return ThreadLocalRandom.current().nextInt(30000, 80000 + 1);
			default:
				//Sunday
				return ThreadLocalRandom.current().nextInt(5000, 15000 + 1);
		}
	}
	private final int yearFirstHalfWeekday = 0;
	private final int yearSecondHalfWeekday = 3;
	private final int weekdayLowerBound = 5000;
	private final int weekdayUpperBound = 140000;

	private String getHeader1()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private String getHeader2()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private String getStations(int[][] stationsDataArray)
	{
		String text = "";
		for (int stationIndex = 0; stationIndex < stationsDataArray.length; stationIndex++)
		{
			String name = stations[stationIndex];
			int[] stationData = stationsDataArray[stationIndex];

			text += getStationText(name, stationData);
		}
		return text;
	}

	private String getBusiestStations(int[][] stationsDataArray)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private String getHeader3()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private String getPercentageDifferences(int[][] stationsDataArray)
	{
		double[] percentages = calculatePercentageDifferences(stationsDataArray);
		String text = "";
		for (int index = 0; index < percentages.length; index++)
		{
			text += stations[index] + ":" + percentages[index] + System.lineSeparator();
		}
		return text;

	}

	private String getStationText(String name, int[] stationData)
	{
		String text = name + "\t";
		for (int index = 0; index < stationData.length; index++)
		{
			text = text + stationData[index] + "\t" + System.lineSeparator();;
		
		return text;
	}

	private double[] calculatePercentageDifferences(int[][] stationsDataArray)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
