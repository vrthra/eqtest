m=1
t=../test10000.txt
run:
	java -cp .:mutants/$(m) org.json.JSONTest $(t)  $(m)

compile:
	javac -d x JSON.java
	javac -cp x -d . JSONTest.java XJSON.java 

compilemutants:
	for i in mutants/*; do javac -d $$i/ $$i/org/json/*.java; done
