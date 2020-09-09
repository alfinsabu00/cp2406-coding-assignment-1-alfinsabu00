package a1;

import textio.TextIO;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;

public class RainfallAnalyser {
    private static final int IDX_YEAR = 2;
    private static final int IDX_MONTH = 3;
    private static final int IDX_DAY = 4;
    private static final int IDX_RAIN = 5;
    private static final int NUM_MONTHS = 12;
    private static final int NUM_DAYS = 31;
    private static final double EMPTY_RAIN_VAL = -0.001;

    public static void main(String[] args) {
        try {
            System.out.println("Enter path name:");
            String pathToCsv = TextIO.getln();
            TextIO.readFile(pathToCsv);
            String pathToCsvFile = pathToCsv.replace(".csv", "_analysed.csv");
            TextIO.writeFile(pathToCsvFile);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e + "failed to process file incorrect exception triggered");
        }

        int count = -1;
        double[][] dailyRain = new double[NUM_MONTHS][NUM_DAYS];
        for (int m = 0; m < NUM_MONTHS; m++) {
            for (int d = 0; d < NUM_DAYS; d++) {
                dailyRain[m][d] = -1.;

            }
            try {
                String emptyCheck = TextIO.getln();
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e + "file is empty incorrect exception triggered");
            }
            while (!TextIO.eof()) {
                String row = TextIO.getln();
                count++;
                if (count < 1)
                    continue;
                String[] data = row.split(",");

                String index = data[IDX_YEAR];
                int year = Integer.parseInt(index);

                index = data[IDX_MONTH];
                int month = Integer.parseInt(index);

                index = data[IDX_DAY];
                int day = Integer.parseInt(index);
                System.out.println(day);

                double rain = 0;
                if (data.length > IDX_RAIN) {
                    index = data[IDX_RAIN];
                    rain = Double.parseDouble(index);
                }


                //System.out.println("year,month,total,min,max");
                int month_idx = month - 1;
                int day_idx = day - 1;

                double oldVal = dailyRain[month_idx][day_idx];

                double newVal = oldVal + rain;
                dailyRain[month_idx][day_idx] = newVal;

                double[] monthlyRain = new double[NUM_MONTHS];
                double[] dailyMax = new double[NUM_MONTHS];
                var maxDaily = Double.MIN_VALUE;
                var minDaily = Double.MAX_VALUE;
                for (int d = 0; d < NUM_DAYS; d++) {
                    var rain_counter = dailyRain[m][d];
                    if (rain_counter == -1) {
                        continue;
                    }
                    double monthlyTotal = dailyRain[m][d];
                    var rain_total = dailyRain[m][d];
                    monthlyTotal += rain_total;
                    if (maxDaily < rain_total) {
                        maxDaily = rain_total;
//                        TextIO.putf("%s,%s,%.2f,%s,%s \n", year, month, monthlyTotal, maxDaily, minDaily);
                    }
                    TextIO.putf("%s,%s,%.2f,%s,%s \n", year, month, monthlyTotal, maxDaily, minDaily);

                }
            }
        }
    }
}

// do something with the data