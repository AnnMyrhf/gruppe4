# gruppe4-handout
Gruppe 4 - Übung Handout

**1. Was ist Git und warum sollte es verwendet werden? Grundlegende Git-Befehle (z. B. git init, git add, git commit, git push)**

Git ist ein verteiltes Versionskontrollsystem, das Änderungen an Dateien – vor allem Quellcode – verwaltet. Es ermöglicht mehreren Entwicklern, parallel zu arbeiten, Änderungen nachzuverfolgen und bei Bedarf auf ältere Versionen zurückzugreifen.

Git sollte verwendet werden, weil es die Zusammenarbeit erleichtert, Versionskonflikte minimiert und die Entwicklungshistorie eines Projekts sichert. Durch Branches können neue Features isoliert entwickelt und getestet werden, ohne das Hauptprojekt zu beeinträchtigen.

**2. Branches und ihre Nutzung, Umgang mit Merge-Konflikten**

Branches sind in Git isolierte Arbeitsbereiche, die es ermöglichen, 
parallel an verschiedenen Features oder Bugfixes zu arbeiten, ohne den Hauptentwicklungsstrang (main) zu beeinträchtigen.

Merge-Konflikte entstehen, wenn zwei Personen dieselbe Datei gleichzeitig ändern und Git nicht automatisch entscheiden kann, welche Änderungen beibehalten werden sollen.
Um Merge-Konflikte zu lösen, muss eine Person die betroffenen Dateien manuell bearbeiten und entscheiden, welche Änderungen übernommen werden sollen.

Regelmäßiges Mergen von Branches in den Hauptzweig hilft, Konflikte frühzeitig zu erkennen und zu lösen und verhindert größere Probleme am Ende eines Projekts.

Clear Naming Conventions für Branches erleichtern die Übersicht und das Management von verschiedenen Entwicklungszweigen. Git-Tools wie git mergetool bieten visuelle Hilfsmittel zur Lösung von Merge-Konflikten und machen den Prozess effizienter.
**3. Git mit IntelliJ/PyCharm benutzen: Local Repository und Remote Repository**

**4. Nützliche Git-Tools und Plattformen (z. B. GitHub)**

Nützliche Git-Tools und Plattformen umfassen **GitHub**, **GitLab** und **Bitbucket**. Sie bieten Hosting für Git-Repositories, erleichtern die Zusammenarbeit in Teams und bieten Funktionen wie Pull Requests, Issue-Tracking und CI/CD-Integration.

Zusätzlich gibt es Tools wie **Sourcetree** und **GitKraken** für visuelle Git-Interfaces, die die Nutzung von Git-Befehlen vereinfachen. **GitLens** für Visual Studio Code erweitert den Editor um Git-Features wie Commit-Tracking und Autorenverlauf.