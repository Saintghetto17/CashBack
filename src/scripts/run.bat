javac -cp "../libs/picocli-4.7.6.jar" ../main/java/bank/Launcher.java ../main/java/bank/cli/*.java ../main/java/bank/bankComponents/*.java ../main/java/bank/tools/*.java -d ../out

java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-bank --name=Tinkoff --limit=5000
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-card --bank=Tinkoff --card=Tinkoff-MIR
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-current-cashback --bank=Tinkoff --card=Tinkoff-MIR --category=Restraunts --percent=5
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-current-cashback --bank=Tinkoff --card=Tinkoff-MIR --category=House --percent=5
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-current-cashback --bank=Tinkoff --card=Tinkoff-MIR --category=Others --percent=1 --permanent=1
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-bank --name=Saint-Petersburg --limit=3000
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-card --bank=Saint-Petersburg --card=Bank-Saint-Petersburg
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-current-cashback --bank=Saint-Petersburg --card=Bank-Saint-Petersburg --category=Tickets --percent=7 --permanent=1
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-current-cashback --bank=Saint-Petersburg --card=Bank-Saint-Petersburg --category=Others --percent=1.5 --permanent=1
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-bank --name=Alpha --limit=2000
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-card --bank=Alpha --card=Alpha-Credit
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-current-cashback --bank=Alpha --card=Alpha-Credit --category=Restraunts --percent=3
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-current-cashback --bank=Alpha --card=Alpha-Credit --category=Oil --percent=5 --permanent=1
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-card --bank=Alpha --card=Alpha-MIR
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-current-cashback --bank=Alpha --card=Alpha-MIR --category=Oil --percent=5
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-current-cashback --bank=Alpha --card=Alpha-MIR --category=Tickets --percent=3
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-current-cashback --bank=Alpha --card=Alpha-MIR --category=Others --percent=1 --permanent=1
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher card-list
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher choose-card --category=Tickets --value=3500
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-transaction --bank=Tinkoff --card=Tinkoff-MIR --category=House --value=30000000
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher add-transaction --bank=Alpha --card=Alpha-MIR --category=Others --value=30000
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher estimate-cashback
java -classpath "../out;../libs/picocli-4.7.6.jar" bank.Launcher choose-card --category=House --value=3000000

pause