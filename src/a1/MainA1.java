import textio.TextIO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainA1 {
    private static final int IDX_YEAR = 2;
    private static final int IDX_MONTH = 3;
    private static final int IDX_DAY = 4;
    private static final int IDX_RAIN = 5;
    private static final int NUM_MONTHS = 12;
    private static final int NUM_DAYS = 31;
    private static final double EMPTY_RAIN_VAL = -0.001;

    public static void main(String[] args) throws IOException {
        System.out.println("A1");

        String pathToCsv = "src/a1/IDCJAC0009_031205_1800_Data.csv";
//        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        String row;


        TextIO.readFile(pathToCsv);
//        TODO: Done Calculate monthly rainfall totals
//        TODO: Done minimum daily and maximum daily rainfall during each month (-1 bug to fix)

        int count = -1;
//        while ((row = csvReader.readLine()) != null) {
        double[][] rainDaily = new double[NUM_MONTHS][NUM_DAYS];
        for (int m = 0; m < NUM_MONTHS; m++) {
            for (int d = 0; d < NUM_DAYS; d++) {
                rainDaily[m][d] = EMPTY_RAIN_VAL;
            }
        }
        while (!TextIO.eof()) {
            row = TextIO.getln();
            System.out.println(row);
            count++;
            if (count < 1)
                continue;
// 1 		Product code
// 2		Bureau of Meteorology station number
// 3		Year
// 4		Month
// 5		Day
// 6		Daily rainfall (Millimetres)
// 7		Period over which daily rainfall was measured (days)
// 8		Quality of daily rainfall

            String[] data = row.split(",");

            String str = data[IDX_YEAR];
            System.out.println(str);
            int year = Integer.parseInt(str);
            System.out.println(year);

            str = data[IDX_MONTH];
            System.out.println(str);
            int month = Integer.parseInt(str);
            System.out.println(month);

            str = data[IDX_DAY];
            System.out.println(str);
            int day = Integer.parseInt(str);
            System.out.println(day);

            double rain = 0; //                missing val
            if (data.length > IDX_RAIN) {
                str = data[IDX_RAIN];
                System.out.println(str);
                rain = Double.parseDouble(str);
                System.out.println(rain);
            }


            int month_idx = month - 1;
            int day_idx = day - 1;

            double oldVal = rainDaily[month_idx][day_idx];
            System.out.println("oldVal =" + oldVal);

            double newVal = oldVal + rain;
            System.out.println("newVal =" + newVal);
            rainDaily[month_idx][day_idx] = newVal;

            // do something with the data
        }

        double[] rainMonthly = new double[NUM_MONTHS];
        double[] dailyMax = new double[NUM_MONTHS];
        double[] dailyMin = new double[NUM_MONTHS];

//            Fix -1s

        for (int m = 0; m < NUM_MONTHS; m++) {
            var monthTotal = 0.;
            var maxDay = Double.MIN_VALUE;
            var minDay = Double.MAX_VALUE;
            for (int d = 0; d < NUM_DAYS; d++) {
                var rain = rainDaily[m][d];
                if (rain == EMPTY_RAIN_VAL) {
                    System.out.println("ignore" + m +", " + d);
                    continue;
                }
                monthTotal += rain;
                if (maxDay < rain) {
                    maxDay = rain;
                }
                if (minDay > rain) {
                    minDay = rain;
                }
            }
            rainMonthly[m] = monthTotal;
            dailyMax[m] = maxDay;
            dailyMin[m] = minDay;
            System.out.println("monthTotal=" + monthTotal
                    + ", min=" + minDay + ", max= " + maxDay);
        }
//        csvReader.close();

        // Calculate monthly rainfall totals along with minimum daily and
        // maximum daily rainfall during each month, across the 18+ years' worth of data

    }
}

