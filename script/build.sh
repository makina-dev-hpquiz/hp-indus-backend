echo "BUILD HP-INDUS-BACKEND"

cd ..

rootFolder=$(pwd)
folder=hp-indus-backend
targetFolder=target
scriptFolder=script

mvn clean package
cd $targetFolder

echo "Creation du répertoire "$folder" est déplacement des items générés dedans"
mkdir $folder
mv hp_indus_backend.jar $folder

mv $scriptFolder $folder
cd $folder/$scriptFolder
mv * ..
cd .. 
rm -r $scriptFolder

echo "L'application est disponible dans le répertoire : "$targetFolder/$folder
ls -lh
