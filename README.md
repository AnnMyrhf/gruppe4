# gruppe4-handout
Gruppe 4 - Übung Handout

**1. Was ist Git und warum sollte es verwendet werden?

**2. Grundlegende Git Befehle**

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

**3. Branches und ihre Nutzung, Umgang mit Merge-Konflikten**

Branches sind in Git isolierte Arbeitsbereiche, die es ermöglichen, 
parallel an verschiedenen Features oder Bugfixes zu arbeiten, ohne den Hauptentwicklungsstrang (main) zu beeinträchtigen.

Merge-Konflikte entstehen, wenn zwei Personen dieselbe Datei gleichzeitig ändern und Git nicht automatisch entscheiden kann, welche Änderungen beibehalten werden sollen.
Um Merge-Konflikte zu lösen, muss eine Person die betroffenen Dateien manuell bearbeiten und entscheiden, welche Änderungen übernommen werden sollen.

Regelmäßiges Mergen von Branches in den Hauptzweig hilft, Konflikte frühzeitig zu erkennen und zu lösen und verhindert größere Probleme am Ende eines Projekts.

Clear Naming Conventions für Branches erleichtern die Übersicht und das Management von verschiedenen Entwicklungszweigen. Git-Tools wie git mergetool bieten visuelle Hilfsmittel zur Lösung von Merge-Konflikten und machen den Prozess effizienter.
**4. Git mit IntelliJ/PyCharm benutzen: Local Repository und Remote Repository**

**5. Nützliche Git-Tools und Plattformen (z. B. GitHub)**

