package michalbukalski;

import michalbukalski.MockedDeviceTester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> deviceSerialNumbers = new ArrayList<>();
        deviceSerialNumbers.add("AB12CD345678");
        deviceSerialNumbers.add("XYZ123456789");

        int maxRetries = 3;

        List<String> testResults = new ArrayList<>();
        MockedDeviceTester tester = new MockedDeviceTester(maxRetries);

        for (String deviceSN : deviceSerialNumbers) {
            tester.setDeviceSN(deviceSN);
            String testResult = tester.testDevice();
            testResults.add(testResult);
        }

        // Analiza wyników
        int totalTests = testResults.size();
        int passCount = 0, failCount = 0, inconclusiveCount = 0;
        for (String result : testResults) {
            switch (result) {
                case "PASS":
                    passCount++;
                    break;
                case "FAIL":
                    failCount++;
                    break;
                case "INCONCLUSIVE":
                    inconclusiveCount++;
                    break;
                default:
                    break;
            }
        }

        // Wyświetlenie wyników
        System.out.println("Total tests: " + totalTests);
        System.out.println("Pass count: " + passCount);
        System.out.println("Fail count: " + failCount);
        System.out.println("Inconclusive count: " + inconclusiveCount);

        double passPercentage = (double) passCount / totalTests * 100;
        double failPercentage = (double) failCount / totalTests * 100;
        double inconclusivePercentage = (double) inconclusiveCount / totalTests * 100;

        System.out.println("Pass percentage: " + passPercentage + "%");
        System.out.println("Fail percentage: " + failPercentage + "%");
        System.out.println("Inconclusive percentage: " + inconclusivePercentage + "%");
    }
}
