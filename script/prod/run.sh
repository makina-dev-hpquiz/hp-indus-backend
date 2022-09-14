echo "[PROD MODE] Démarrage de la plateforme HP-INDUS "
echo "Les services suivants vont être démarrés :"

echo "* Apache tomcat 9.0.55"
# echo "* BDD Postgreql"
echo "* HP-INDUS-BACKEND"
echo ;


ipconfig | findstr \//C:"IPv4" > ipaddr.txt
ipaddr=$(grep -o "192.*" ipaddr.txt)
rm ipaddr.txt
echo ;

# Lancement de tomcat
echo "DEMARRAGE DE TOMCAT : "
bash c:/bin/apache-tomcat-9.0.55/bin/startup.sh

echo "SERVEUR TOMCAT OK : http://"$ipaddr":8080"


# Lancer BDD 
##

#Démarrage applicatif
#HP-Indus-Backend
pidfile=pid.file

if [ -f "$pidfile" ]; then   
    echo "HP-INDUS-BACKEND est déjà démarré."
else 
    nohup java -jar hp_indus_backend.jar & echo $! > ./$pidfile &
    echo "HP-INDUS-BACKEND démarré, il sera dispoible dans quelques instants."
fi

start "http://"$ipaddr":8080/hp-indus/" 

read