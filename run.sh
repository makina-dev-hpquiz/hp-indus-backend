#Fichier sh utilitaire actuellement construit pour s'utiliser en environnement de développement
echo "[DEV MODE] Démarrage de la plateforme HP-QUIZ "
echo "Les services suivants vont être démarrés :"

echo "* Apache tomcat 9.0.55"
# echo "* BDD Postgreql"
echo "* HP-INDUS-BACKEND"
echo "* HP-INDUS-FRONTED"
echo ;

# Récupération de la position actuelle
currentPosition=pwd

# Lancement de tomcat
echo "DEMARRAGE DE TOMCAT : "
bash c:/bin/apache-tomcat-9.0.55/bin/startup.sh

ipconfig | findstr \//C:"IPv4" > ipaddr.txt
ipaddr=$(grep -o "192.*" ipaddr.txt)
echo "SERVEUR TOMCAT OK : http://"$ipaddr":8080"
rm ipaddr.txt
echo ;

# Lancer BDD 
##

#Démarrage applicatif
#HP-Indus-Backend
nohup mvn spring-boot:run & echo $! > ./pid.file &
echo "HP-INDUS-BACKEND démarré, il sera dispoible dans quelques instants."


start "" "http://"$ipaddr":8082/apks/last"
curl $ipaddr+":8082/apks/last"
echo ;

#HP-Indus-Frontend
cd ../hp-indus-frontend/
./run.sh

read