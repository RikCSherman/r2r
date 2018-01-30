# r2r

### Before Running
Download systems.csv from https://eddb.io/api and place it in the root directory
of the project.

Also download "expl_1000 (1),json" from http://edtools.ddns.net/expl.php?a=about and place it in the root directory
of the project.

### Marking visited Systems
Create a file called visited.txt in the root directory of the project. 
After visiting a system add it's name to this file and it will no longer appear in any planned routes.
One system name per line and capitalisation preserved.

### Running the app
usage: R2r -d <arg> -n <arg> -s <arg>

 -d,--distance <arg>   Max distance from start system
 -n,--number <arg>     Number of systems to include in route
 -s,--system <arg>     System name to start from

System names are case sensitive.

### Recomended
Run this with -Xmx4g -Xms4g -XX:+UseConcMarkSweepGC 
