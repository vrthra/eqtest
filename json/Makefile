m=1
t=../log
run:
	java -cp .:mutants/$(m) org.json.JSONTest $(t)  $(m)

compile:
	javac -d x JSON.java
	javac -cp x -d . JSONTest.java XJSON.java  JSONFilter.java

compilemutants:
	for i in mutants/*; do echo $$i; javac -d $$i/ $$i/org/json/*.java; done

teststr:
	python3 ../grammar-fuzz.py 10 10000 | java -cp . org.json.JSONFilter

clean:
	rm -rf org x
