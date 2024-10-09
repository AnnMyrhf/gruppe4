# gruppe4-handout
Gruppe 4 - Übung Handout

## Git mit IntelliJ benutzen: Local Repository und Remote Repository
In Verbindung mit der Entwicklungsumgebung IntelliJ IDEA wird die Nutzung von Git besonders benutzerfreundlich.
Im Folgenden wird die grundlegende Arbeit mit einem Local Repository (lokales Repository) und einem Remote Repository (externes Repository) beschrieben.

### Local Repository

Ein Local Repository befindet sich auf dem eigenen Rechner und enthält die vollständige Historie eines Projekts. Es wird zum lokalen Entwickeln und Testen verwendet. Mit IntelliJ kann ein neues Local Repository folgendermaßen erstellt und verwaltet werden:

+ Neues Git Repository initialisieren: Über VCS > Enable Version Control Integration kann ein neues Git-Repository für das Projekt erstellt werden.
+ Änderungen verfolgen: Änderungen an Dateien werden automatisch als „unversioned files“ erkannt und können in den Index (Staging Area) aufgenommen werden.
+ Änderungen committen: Über das Menü Commit können Änderungen am Code lokal gespeichert werden. Jeder Commit repräsentiert dabei einen bestimmten Entwicklungsstand.

### Remote Repository

Ein Remote Repository liegt auf einem externen Server (z. B. GitHub, GitLab) und ermöglicht die Zusammenarbeit im Team. Es dient als zentrale Anlaufstelle, um Code zu teilen und gemeinsam daran zu arbeiten. So wird ein Remote Repository in IntelliJ genutzt:

+ Remote Repository hinzufügen: Über VCS > Git > Remotes kann eine Verbindung zu einem externen Repository hergestellt werden. Hierzu muss die URL des Remote-Repositories eingetragen werden.
+ Push: Über den Push-Befehl werden lokale Commits ins Remote Repository hochgeladen. So werden die Änderungen für andere Teammitglieder verfügbar.
+ Pull und Fetch: Mit dem Pull-Befehl werden die neuesten Änderungen aus dem Remote Repository in das lokale Repository integriert. Der Fetch-Befehl lädt die Änderungen aus dem Remote Repository herunter, ohne sie sofort in den lokalen Branch zu integrieren.