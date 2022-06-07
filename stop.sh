#Fichier sh utilitaire actuellement construit pour s'utiliser en environnement de développement
echo "[DEV MODE] Extinction de la plateforme HP-QUIZ"
echo ;
echo "Les services suivants vont être arrêtés :"
echo ;
echo "* Apache tomcat 9.0.55"
# echo "* BDD Postgreql"
echo "* HP-INDUS Bachend"
# echo "* HP-INDUS Frontend"
echo ;

#Extinction de tomcat
echo "EXTINCTION DE TOMCAT : "
bash c:/bin/apache-tomcat-9.0.55/bin/shutdown.sh
echo ;

#Extinction des BDD
echo ;

#Extinction des applications
echo "EXTINCTION DE HP-INDUS Backend - PID : " $(cat ./pid.file)
kill $(cat ./pid.file)
echo ;

wait -n $(cat ./pid.file);

#Suppression fichier
rm pid.file
rm nohup.out

