# gruppe4-handout
Gruppe 4 - Übung Handout 

**1. Was ist Git und warum sollte es verwendet werden? (Katja)**

Git ist ein verteiltes Versionskontrollsystem, das Änderungen an Dateien – vor allem Quellcode – verwaltet. Es ermöglicht mehreren Entwicklern, parallel zu arbeiten, Änderungen nachzuverfolgen und bei Bedarf auf ältere Versionen zurückzugreifen.

**2. Grundlegende Git Befehle (Maik)**

1. **`git init`**  
   Initialisiert ein neues Git-Repository im aktuellen Verzeichnis.

2. **`git clone <URL>`**  
   Klont ein bestehendes Repository von einer URL in ein neues Verzeichnis.

3. **`git status`**  
   Zeigt den aktuellen Status des Arbeitsverzeichnisses (z.B. geänderte oder untracked Dateien).

4. **`git add <Datei>`**  
   Fügt eine Datei zur Staging-Area hinzu (bereit zum Commit).

5. **`git commit -m "Nachricht"`**  
   Speichert die Änderungen aus der Staging-Area dauerhaft im Repository mit einer Nachricht.

6. **`git push`**  
   Überträgt die lokalen Commits in ein entferntes Repository (z.B. auf GitHub).

7. **`git pull`**  
   Holt die Änderungen von einem entfernten Repository und integriert sie in das lokale Repository.

8. **`git fetch`**  
   Holt die Änderungen von einem entfernten Repository, ohne sie zu integrieren.

9. **`git merge <Branch>`**  
   Integriert Änderungen von einem anderen Branch in den aktuellen Branch.

10. **`git branch`**  
    Listet alle lokalen Branches auf oder erstellt einen neuen Branch (z.B. `git branch <neuer-Branch>`).

11. **`git checkout <Branch>`**  
    Wechselt zu einem anderen Branch.

12. **`git log`**  
    Zeigt die Historie der Commits an.

13. **`git diff`**  
    Zeigt die Unterschiede zwischen Dateien im Arbeitsverzeichnis, der Staging-Area oder zwischen Commits.

**3. Branches und ihre Nutzung, Umgang mit Merge-Konflikten (Ann-Kathrin)**

Git sollte verwendet werden, weil es die Zusammenarbeit erleichtert, Versionskonflikte minimiert und die Entwicklungshistorie eines Projekts sichert. Durch Branches können neue Features isoliert entwickelt und getestet werden, ohne das Hauptprojekt zu beeinträchtigen.

**4. Git mit IntelliJ/PyCharm benutzen: Local Repository und Remote Repository (Monique)**

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

### Zusammenspiel von Local und Remote Repository

Das lokale Repository dient zum Entwickeln und Testen von Code, während das Remote Repository zur Synchronisation und Zusammenarbeit genutzt wird. Änderungen werden erst lokal committet und dann ins Remote Repository gepusht. Teammitglieder können diese Änderungen dann durch Pull-Befehle in ihre eigenen lokalen Repositories integrieren.

Das Zusammenspiel dieser beiden Repository-Arten gewährleistet eine sichere und effiziente Verwaltung von Code und erleichtert die Zusammenarbeit im Team. IntelliJ IDEA bietet hierfür eine benutzerfreundliche Oberfläche, die viele Arbeitsschritte vereinfacht.

**5. Nützliche Git-Tools und Plattformen (z. B. GitHub) (Katja)**

Nützliche Git-Tools und Plattformen umfassen **GitHub**, **GitLab** und **Bitbucket**. Sie bieten Hosting für Git-Repositories, erleichtern die Zusammenarbeit in Teams und bieten Funktionen wie Pull Requests, Issue-Tracking und CI/CD-Integration.

Zusätzlich gibt es Tools wie **Sourcetree** und **GitKraken** für visuelle Git-Interfaces, die die Nutzung von Git-Befehlen vereinfachen. **GitLens** für Visual Studio Code erweitert den Editor um Git-Features wie Commit-Tracking und Autorenverlauf.

Clear Naming Conventions für Branches erleichtern die Übersicht und das Management von verschiedenen Entwicklungszweigen. Git-Tools wie git mergetool bieten visuelle Hilfsmittel zur Lösung von Merge-Konflikten und machen den Prozess effizienter.

