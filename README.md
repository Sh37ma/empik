**Empik** - Zadanie rekrutacyjne backend

W celu weryfikacji danych w bazie danych można się połączyć z bazą h2 za pomocą jej consoli:
http://localhost:8080/h2-console/ - dane do logowania takie jak application.yaml

Do budowania projektu i zarządzania zależnościami użyłem maven'a.
Za pomocą jego pluginu generuję także kod użyty do mapowania klas.

### **Opis rozwiązania:**

Aplikacja ma jeden wystawiony endpoint, który otrzymuje nazwę użytkownika, a następnie przekazuje go do 'UserService'.
On natomiast napierw przekazuje login do 'GitHubService', który buduje żądany url za pomocą 'GitHubUrlProvider', wysyła GET request 
i zwraca otrzymane dane użytkownika zadeklarowane w 'GitHubUser' (reszta danych z api GitHub'a nie jest mapowana).
Podczas pobierania danych mogą wystąpić błedy przy żądaniu o dane nieistniejącego użytkownika, wiec dodana jest obsługa wyjątków.
Ograniczyłem się do 404 jako jeden specyficzny przypadek, a reszta exceptionów jest juz traktowana jako ogólny błąd GitHub serwera.

Wracając do 'UserService' jego następny krok zapisuje do bazy zwiększony licznik odwiedzin api dla tego loginu.
W przypadku problemów z bazą danych zdecydowałem się zwracać bardziej ogólny 'InternalApiException', by za dużo nie zdradzać
na temat tego co się dzieje w aplikacji. Nazwa tabeli to 'Visit' może nie być idealną nazwą ale inne pomysły jakie miałem
(np. UserFetchCount, UserRequestCount, RequestCount) nie wydawały mi się też zbyt dobre.

Ostatnim krokiem metody z 'UserService' jest przerobienie danych z GitHub na dane które będą zwracane z naszego api.
Klasa 'UserProcessor' zajmuje się najpierw wywołaniem MapStruct. Uznałem, że lepiej mieć oddzielne modele danych, 
dlatego skorzystałem z pomocy mapowania. Null pointery są weryfikowane przez sam MapsStruct, ale nie rozwiązuje to całkiem problemu,
dlatego zabezpieczyłem początek metody null checkiem.
Następnie ustawiane jest pole 'calculations' (wystepujące tylko w 'User'). Obliczenia te są napisane tak, 
aby zapobiec dzieleniu int przez int co prowadziło by do utraty części dziesiętnej. A także jest zabezpieczenie 
przed dzieleniem przez zero, jeśli liczba 'followers' jest zerem, to całe działanie jest pomijane 
i ustawiane jest zero w polu 'calculations'. Wybrałem takie rozwiązanie zamiast rzucania wyjątku, ze względu na to
by nie blokować działania aplikacji dla użytkowników, który mają liczbę 'followers' równą zero. Uważam, że użytkownik 
woli dostać takie dane, niż żadne i samą informację o błędzie. 

- Nazwa aplikacji i głównego pakietu nie jest idealna, ale uznałem, że nie jest to tutaj najważniejsze.
- Baza danych zapisuje dane w pliku, by po restarcie aplikacji nadal posiadała dane.
- Ze względu na prostotę i rozmiar aplikacji zdecydowałem się na adnotacyjne tworzenie Bean'ów. Jedynie tworzony jest 'RestTemplate'
bean (w 'GitHubServiceConfiguration') dla ułatwienia pisania unit testu dla 'GitHubService'. 
Mieszanie nie jest zbyt czystym rozwiązaniem, ale na potrzeby tego zadania sobie na to pozwoliłem. 
- Aplikacja jest pokryta unit testami w miejscach, gdzie jest to rozsądne.