echo "DEPLOIEMENT HP-INDUS-BACKEND"

source params.sh

deployLocation="C:/bin/hp-indus-backend/"

echo "Extinction de l'application HP-INDUS-BACKEND"
bash $deployLocation"stop.sh"

echo "Suppression de l'ancienne version"
rm -r $deployLocation

echo "Déploiement de la nouvelle version"
mkdir $deployLocation
cd ../$targetFolder/$folder/
cp * $deployLocation

echo "Déploiement terminé"
ls -lh $deployLocation