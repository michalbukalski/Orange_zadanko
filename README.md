Aplikacja posiada 3 endpointy (zabezpieczone protokołem OAuth 2.0):

1. Test urządzenia

 

app.orange.pl/testDevice

 

Który przyjmuje parametr w body zapytania:

- Parametr SN służący do przekazania 12-sto znakowego kodu numeru seryjnego urządzenia, wszystkie znaki muszą być drukowane, może to być dowolna kombinacja liter i cyfr (bez znaków specjalnych).
- Numer testowanego stanowiska (Format TS-<numer_od_1_do_12>)

 

Endpoint ten zwraca SN testowanego urządzenia oraz 3 możliwe statusy:

PASS, FAIL, INCONCLUSIVE

 

Format odpowiedzi to JSON, przykładowo:

{
   "SN": "AB12CD345678",
   "Status": "PASS"
}

2. Pobranie ostatniego statusu

app.orange.pl/getLastTestResult

Który przyjmuje identycznie jak poprzednie zapytanie numer SN urządzenia (format bez zmian).

Endpoint zwraca SN testowanego urządzenia, status poprzedniego testu (ponownie możliwe statusy, to PASS, FAIL,  INCONCLUSIVE), datę oraz godzinę poprzedniego testu, numer stanowiska, na którym było testowane dane urządzenie.

Format odpowiedzi to JSON, przykładowo:

{
   "SN": "AB12CD345678",
   "Status": "PASS",
   "Date":"2024-01-12T12:35:00.000Z",
   "TestingStation": "TS-1"
}

 

3. Zgłoszenie potencjalnego błędu

app.orange.pl/reportPotentialError

 

Który przyjmuje identyczne dane jak endpoint /testDevice oraz datę testu.

Endpoint zwraca status odpowiedzi w postaci kodu przypisanego do pola Status, możliwe kody odpowiedzi:

200 - poprawnie przyjęto zgłoszenie
404 - urządzenie nie zostało odnalezione w bazie

Format odpowiedzi to JSON, przykładowo:

{

  "Status": "200"

}

 

---

W oparciu o podane dane wykonaj zadanie:

Stwórz program, który wykona test dla wybranego przez Ciebie urządzenia (SN musi być w formacie podanym w opisie endpointów).

Po uzyskaniu odpowiedzi niech program sprawdzi, czy drugi endpoint zwraca najnowszy status i czy pokrywa się on z wcześniej uzyskanym.

Jeżeli status testu, to  INCONCLUSIVE niech program powtórzy test maksymalnie 3 razy (lub do uzyskania statusu innego, niż INCONCLUSIVE), w przypadku uzyskania 3 razy statusu INCONCLUSIVE wykona zapytanie do endpoint'u reportPotentialError z danymi urządzenie oraz testu.

Program powinien w łatwy sposób umożliwić uruchomienie dla różnych urządzeń oraz umożliwiać modyfikację ilości wykonań przed zakończeniem działania.

Dodatkowo program powinien pozwalać na wykonanie testów powtarzalności, czyli np. wykonanie 40 testów, których wyniki powinny się pokrywać. Zaproponuj w jaki sposób można przedstawić wyniki takich testów, aby były czytelne i użyteczne.
