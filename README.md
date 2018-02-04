# uk.co.rc418.r2r
This is an application to simplify plotting and following courses for a road to riches experience.
This is based on the excellent web tool at http://edtools.ddns.net/ by Reddit user cold-n-sour see
 https://www.reddit.com/r/EliteDangerous/comments/6hhcpq/road_to_riches_web_app/ 

### Marking visited Systems
The program uses a file to keep track of visited systems, `visited.txt`, to exclude them from future searches.
When the `Next System` button is pressed the previous system is marked as visited and saved 
to the `visited.txt` file.
One system name per line and capitalisation preserved.

### Running the app 
Once it has started enter your start system's name and press the 
`Next System` button. This will find the nearest road to riches system, display it's name
 and a list of the planets to visit. It also automatically copies the system name into the 
 system clipboard so you can then paste it straight into the galaxy map to plot a route.
 
 To make scripting easier, such as to use VoiceAttack, the following keys are mapped.
 ```
 <ctrl> + <N> (<cmd> + <N>) = Presses the next system button for you and copies system name to clipboard.
 <ctrl> + <P> (<cmd> + <P>) = Copy the planets list to the clipboard, maybe you could script some text to speech. 
```

### VoiceAttack example script
To come ...