@echo off
echo.
echo parseaza toate documentele care nu sunt .xml din folderul "inputuri" si le salveaza ca xml si conllu in "outputuri"
set classpath=data/POStagger.jar;data/maxent-3.0.0.jar;data/OpenNLP.jar;data/GGSEngine2.jar

java -Xmx512M -Dfile.encoding=utf-8 uaic.postagger.tagger.HybridPOStagger data/posRoDiacr.model data/posDictRoDiacr.txt data/guesserTagset.txt data/posreduction.ggf inputuri outputuri

java -Xms512M -Xmx512M -jar data/xmlToConll.jar
pause