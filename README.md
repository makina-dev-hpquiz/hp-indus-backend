# HP-INDUS-BACKEND



Cette plateforme a pour objectif de servir le projet principal HP-QUIZ en proposant plusieurs outils à la fois de débug mais également de suivi de projet. 
L'application est utilisable sur navigateur (chrome) ainsi que sur navigateur mobile.

Les fonctionnalités principales sont : 
* Module de gestion des APKs permettant de télécharger les différentes APK liés à l'ensemble du projet
* Module de gestion d'incidents permettant d'ouvrir et de gérer des tickets de bug.
* Module d'accès rapide permettant en un seul lieux d'avoir un ensemble de liens url.

L'application serveur fonctionne avec 
* L'application Web hp-indus-frontend 
* Tomcat en serveur de stockage
* Postgresql en BDD

## Installation

* Eclipse https://www.eclipse.org/downloads/
* Git
* maven https://maven.apache.org/
* tomcat https://tomcat.apache.org/
* sonarqube https://docs.sonarqube.org/latest/setup/get-started-2-minutes/
* postgresql https://www.postgresql.org/download/
* JDK 11 https://www.oracle.com/fr/java/technologies/javase/jdk11-archive-downloads.html
* JsonFormatter

#### Installation JDK 
Installation Java 11, la version openjdk 11.0.15 2022-04-19 est utilisé dans le projet
Nécessite de set la variable Système JAVA_HOME
C:\Program Files\Eclipse Adoptium\jdk-11.0.15.10-hotspot\

#### Installation maven

Installer dans un répertoire C:\bin 

Ajouter le chemin vers Maven en variable système dans Path
```
C:\bin\apache-maven-3.8.5\bin
```

Dans un nouveau terminal, executer la commande mvn --version pour vérifier la bonne installation.


Utiliser la commande suivante pour récupérer les dépendances :
```
mvn clean install -DskipTests
```


#### Git

```
git clone git@github.com:VHauchecorn/hp-indus-backend.git
```

#### Tomcat
Installer le tomcat (utilisé lors des développement apache-tomcat-9.0.55 ) dans un répertoire C:\bin 


Pour que tomcat soit accessible sur votre réseau local :
Centre Réseau et partage => Paramètre de partages avancés
Invité ou public (profil actuel)
	Activer la découverte du réseau
	Activer le partage de fichiers et d'imprimantes

Créer les répertoires images et apks utilisé par l'application serveur.
```
mkdir C:\bin\apache-tomcat-9.0.55\webapps\APK
```

```
mkdir C:\bin\apache-tomcat-9.0.55\webapps\images
```

#### Postgresql
##### Installation 
Pour installer Postgresql, lancer l'executable télécharger précédemment.
Différents écrans vont appraître : 

Un écran demandera d'installer les packages, dans mon cas j'ai installer les suivants : 
* PostgreSQL Server
* Aide au développement
* pgAdmin 4
* StackBuilder
* Command Line Tools

Un écran suivant le mot de passe superadmin : root
Laisser par défaut le port d'écoute : 5432
Sélectionneer la Locale (langage) utilisé par le cluster de base de données : French, France

Le serveur Postgresql démarre par défaut de manière automatique au démarrage du système d'exploitation.

##### Configuration 
Lancer l'application pgAdmin et se connecter.
Dans login/Group Roles, ajouter l'utilisateur appli avec le mot de passe 1234.
Ajouter à l'utilisateur appli, les privilèges : 
* Can login
* Create databases

Créer la base de données incident avec comme propriétaire l'utilisateur appli

#### Sonarqube 
J'ai télécharger la version standalone à installer directement sur son poste.
Lors de l'installation, choisir : 
Login : admin
nouveau mot de passe : azerty

Pour démarrer l'application se rendre dans le répertoire d'installation, puis bin puis l'OS : 
```
C:\sonarqube\bin\windows-x86-64\
```

Lancer StartSonar.bat directement en cliquant dessus.
L'application sera accessible dans quelques instants à l'adresse http://localhost:9000/

#### JsonFormatter

Dans chrome rajouter l'extension JsonFormatter.
Cette extension met en forme les retours Json.

## Configuration

## Commandes de lancement

Les scripts on pour objectif de démarrer/eteindre plusieurs logiciels :
* Lui-même hp-indus-backend
* hp-indus-frontend
* tomcat

### En mode développement
Les scripts sont présents dans le répertoire script/dev/

2 fichiers sh de commandes sont disponibles permettant de lancer et éteindre l'application web

```
./run.sh
```

```
./stop.sh
```



### En mode production

En mode production hp-indus-frontend n'est pas démarré, il est présent dans le tomcat

Les scripts sont présents dans le réperetoire script/prod/

```
./run.sh
```

```
./stop.sh
```


L'application est accessible à l'adresse :

```
http://localhost:8082
```

```
http://[ip-addr]:8082
```

## Build de l'application

Pour build l'application, utiliser la commande suivante :

```
mvn clean package
```

hp_indus_backend.jar est généré dans le répertoire target/

Il est éxecutable avec la commande :

```
java -jar hp_indus_backend.jar
```

Le script script/build.sh permet de build l'application est de reconstituer un répertoire hp-indus-backend prêt à être utilisé.





