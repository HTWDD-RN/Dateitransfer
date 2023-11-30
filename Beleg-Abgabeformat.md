# Belegabgabe

* Gepackte Datei im Format tar.gz (keine anderen Formate erlaubt)
* Archivname: sXXXXX.tar.gz     mit sXXXXX Ihrer S-Nummer
* Laden Sie das Archiv auf https://idefix.informatik.htw-dresden.de/rn-beleg hoch. Bei Problemen mit dem Upload-Server senden Sie es per E-Mail an Herrn Paul (paul@informatik.htw-dresden.de).
* Nur Belege, die ohne Fehlermeldung auf idefix hochgeladen wurden, werden als rechtzeitig abgegeben bewertet!
* Sie sollten im Vorfeld Ihr Beleg-Archiv lokal mit dem im Repository bereitgestellten Bash-Testscript auf Korrektheit prüfen. Erst wenn dieses ohne Fehler die Daten überträgt, sollten Sie Ihr Archiv hochladen.

* Der Server idefix ist nur hochschulintern oder per VPN erreichbar.
* Struktur des Archivs: 

```
sXXXXX		← Ihre eigene S-Nummer  
│  
├── README.md	   (in der ersten Zeile sollte Ihr vollständiger Name stehen)  
├── make.sh        (Bashscript zur Compilierung Ihres Projektes)  
├── filetransfer   (Parameter-Client: client host port filename     Parameter-Server: server port)  
├── doc/           (Projektdokumentation)  
├── bin/           (Binärdateien)  
│   ├── *.class  
│   └── ...  
└── src/           (Quellcode)  
    ├── *.java  
    └── ...
```

* In der README.md steht Ihr Name und die Funktionalität der Programme, also was funktioniert und was funktioniert nicht. Das Format der README.md ist plain ASCII (Markdown) kein Word etc.  
Testen Sie konkret folgende Szenarien und geben das Ergebnis an:
  * Funktion Ihres Clients + Server ohne Fehlersimulation
  * Funktion Ihres Clients + Server mit Fehlersimulation
  * Funktion Ihres Clients + Server über Hochschulproxy
  * Funktion Ihres Clients + Hochschulserver ohne Fehlersimulation
  * Funktion Ihres Clients + Hochschulserver mit Fehlersimulation
* Mittels Aufruf von `make.sh` sollen die Klassen neu erstellt werden können.
* Sie können auch mit Packages arbeiten, was bedeutet, dass Unterverzeichnisse in bin/ und src/ existieren. Es muss aber gewährleistet sein, dass mit dem Aufruf von make.sh alle Programme korrekt erstellt werden und ein Aufruf von filetransfer korrekt funktioniert.
* Das Skript `filetransfer` sollte beim Aufruf aus dem sXXXXX-Verzeichnis funktionieren aber auch beim Aufruf aus dem eine Ebene höher gelegenen Verzeichnis. Dies kann über den classpath-Parameter in den Skripten realisiert werden (z.B. `java -cp bin:sXXXXX/bin FileCopy $1 $2 $3`). Die übertragene Datei sollte im aktuellen Verzeichnis gespeichert werden.
  * Bsp.:  `./s1234/filetransfer client localhost 3333 test/dresden.jpg`
  
