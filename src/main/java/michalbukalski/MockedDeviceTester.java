package michalbukalski;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MockedDeviceTester extends DeviceTester {
    private String deviceSN;

    // Konstruktor pozwalający na ustawienie maksymalnej liczby powtórzeń testu
    public MockedDeviceTester(int maxRetries) {
        super(maxRetries);
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    // Metoda symulująca testowanie urządzenia
    @Override
    public String testDevice() {
        // Symulacja zwracania losowego statusu testu
        String[] possibleStatuses = {"PASS", "FAIL", "INCONCLUSIVE"};
        int randomIndex = (int) (Math.random() * possibleStatuses.length);
        return possibleStatuses[randomIndex];
    }

    // Metoda symulująca pobranie ostatniego wyniku testu
    @Override
    public String getLastTestResult() {
        // Symulacja zwracania ostatniego testu zawsze z losowym statusem
        String[] possibleStatuses = {"PASS", "FAIL", "INCONCLUSIVE"};
        int randomIndex = (int) (Math.random() * possibleStatuses.length);
        String randomStatus = possibleStatuses[randomIndex];

        // Symulacja zwracania daty i numeru stanowiska
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String currentDate = LocalDateTime.now().format(formatter);
        String testingStation = "TS-" + (int) (Math.random() * 12 + 1);

        // Symulacja formatowania odpowiedzi do JSON
        return "{\"SN\": \"" + this.deviceSN + "\", \"Status\": \"" + randomStatus +
                "\", \"Date\": \"" + currentDate + "\", \"TestingStation\": \"" + testingStation + "\"}";
    }

    // Metoda symulująca raportowanie potencjalnego błędu
//    @Override
    public void reportPotentialError() {
        // Symulacja wysłania raportu potencjalnego błędu
        System.out.println("Mocked potential error reported for device: " + this.deviceSN);
    }
}
