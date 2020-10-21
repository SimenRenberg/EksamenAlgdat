# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering

Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet


# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)

Vi har brukt git til å dokumentere arbeidet vårt. Jeg har 16 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

* Oppgave 1: Oppgaven ble løst ved å kopiere løsningen fra kompendiet (5.2 3a), med endringer for å passe på at forelder-referansen ble med.
* Oppgave 2: Oppgaven ble løst ved å benytte meg av bredde-først traversering, fra kodegjennomgangen fra uke 9, da fordi hele treet må løpes gjennom.
* Oppgave 3 a): I det jeg anser som oppgave 3a), førstePostorden() ble oppgaven løst ved å bruke koden for funksjonen printPreOrder() i AlgDat2020 uke 9 Depth First-videoen til Andre Brodtkorb. Da første i postOrder alltid vil være den siste noden lengst til venstre, var det enkelt og oversette metodikken for traversering fra preOrder til postOrder.
* Oppgave 3 b): I det jeg anser som oppgave 3b), nestePostorden(), ble oppgaven løst ved å oversette fremgangsmåten i kompendiet (5.1.7), som du finner rett under Programkode 5.1.7 h), fra tekst til kode. I tilfellet hvor neste node i postorden finnes lengre nedover i subtreet for forelderen sitt høyre-barn, blir metoden førstePostorder() brukt på nytt, da med p.foreldre.høyre som rot.
* Oppgave 4 a): Anser igjen første funksjonen, preorden(), som oppgave a). Oppgaven ble løst ved å først finne den første postorden-noden som det skal utføres en oppgave på, og traverserer hele treet gjennom bruken av p = nestePostorden(p).
* Oppgave 4 b): I den rekursive måten å traversere treet brukte jeg samme kode som er gått igjennom i AlgDat 2020 uke 09 depth first-videoen til Andre Brodtkorb, med System.out.println() byttet ut med oppgave.utførOppgave(p.verdi).
* Oppgave 5 a): For serialiseringen var det bare viktig å huske på å legge verdiene i køen med bruk av addLast(), for å passe på at det ikke ble kluss i rekkefølgen, når verdiene blir tatt ut med removeFirst().
* Oppgave 5 b): I deserialiseringen er det enkelt og greit bare å benytte seg av leggTil() metoden som var tilgjengelig for oss. Legger inn en og en ting fra arrayet gjennom bruk av en foreach-loop (var item : data). Jeg føler at denne løsningen er  altfor enkel til å faktisk fungere, men alle arraylistene jeg har testet med gir grønt resultat i enhetstesten, og identiske før-og-etter trær...  