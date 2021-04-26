genjks:clean
	#Controller
	keytool -genkeypair -keyalg RSA -dname "CN=Controller" -alias Controller -keystore Controller.jks -keypass uestcsdn -storepass uestcsdn
	keytool -exportcert -file Controller.cer -alias Controller -keystore Controller.jks -storepass uestcsdn
	#${HOSTNAME}
	keytool -genkeypair -keyalg RSA -dname "CN=${HOSTNAME}" -alias ${HOSTNAME} -keystore ${HOSTNAME}.jks -keypass uestcsdn -storepass uestcsdn
	keytool -exportcert -file ${HOSTNAME}.cer -alias ${HOSTNAME} -keystore ${HOSTNAME}.jks -storepass uestcsdn
	keytool -importcert -file ${HOSTNAME}.cer -alias ${HOSTNAME} -keystore Controller_trust.jks -storepass uestcsdn -keypass uestcsdn
	#Controller
	keytool -importcert -file Controller.cer -alias Controller -keystore ${HOSTNAME}_trust.jks -storepass uestcsdn -keypass uestcsdn
	mv ${HOSTNAME}*.jks Agent/src/main/resource
	mv Controller*.jks Controller/src/main/resource
	rm *.cer
clean:
	rm -f Agent/src/main/resource/*
	rm -f Controller/src/main/resource/*
