# Tank2D

Wymagania: 
gradle - najlepiej najnowsza wersja, na linuksie chyba nie dziala prawidlowo wersja sciagnieta "z konsoli";
javafx - też najlepiej najnowsza wersja, ale powinny również działać wersje wstecz do 8;
jdk 11

# budowanie i uruchamianie programu
1. Pobrać z gita repezytorium
2. Otworzyć w konsoli CMD / BASH katalog, w którym znajduje się pobrane repozytorium
3. Zbudować projekt za pomocą polecenia ( windows / linux ): gradlew build lub ./gradlew build
4. Uruchomić program poleceniem (windows / linux ): gradlew run lub ./gradlew run
5. Testy (windows/linux) - gradlew test --rerun-tasks lub ./gradlew test --rerun-tasks

Aktualny stan:
- prosta wersja menu, bez obslugi wynikow
- poruszanie sie czolgiem (razem z obsluga kolizji)
- strzelanie czolgu i niszczenie muru (ceglanego)

// Gra jest niemal skończona, można jeszcze dodać pewne urozmaicenia, oraz dodać bardziej kreatywne mapy

Testowanie:
Testy dotyczą klasy GameComponents oraz Tank - poruszanie sie
W przypadku GameComponents możemy włączyć testy trybu dwóch graczy lub jednego.
W celu testowania trybu dwóch graczy należy w pliku build.gradle zakomentować wyrażenie: excludeTags 'twoPlayer'wystepujace w klamrach test{}
oraz w pliku TestGameComponents ustawić wartość multiplayer na true.
Jeśli chcemy testować tryb jednego gracza, to trzeba odkomentować excludeTags 'twoPlayer' i ustawić multiplayer na false.

wywołanie testów w konsoli:
gradlew test --rerun-tasks