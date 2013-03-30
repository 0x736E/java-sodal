java-sodal
==========

Java IRC Chat Client with a penchant for insanity.

Sodal is a framework for creating IRC chat bots using Java. I built it sometime around my last year(s) of College and have not maintained it since. It is presented here for archival purposes, I may one day get around to updating it, but don't count on it.

Used Libraries:

* PircBot: http://www.jibble.org/pircbot.php
* jMegaHal [optional]: http://www.jibble.org/jmegahal/
* jTwitter [optional]: http://www.winterwell.com/software/jtwitter.php

==========

Sodal is a modular java irc bot which you can use, like other bots, to monitor, log or administer IRC chatrooms.

Sodal uses the PircBot? library for connecting to IRC servers, the JMegaHal library for artificial intelligence, and maintains a host of other modules for anything from automatically reconnecting to a server on disconnect to filtering messages (eg. translating from one language to another).

Sodal is very flexible among its most useful features, you can load and unload modules (plugins) at will during runtime, filter chat responses etc.