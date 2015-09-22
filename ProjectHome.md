Show every languages on every MIDP Phones

In J2ME world, you can't sure that your application will show on every device with a desired phone. You just can request a font from j2me and it maybe be an available font or not. Then you can't count on this approach. Also if you use some text of a language except English, you will not sure that all MIDP phones can show characters.
In Mobile Bit Font Creator, another approach is used: You can design your desired font, type your text in a Java application on your PC and then attach your font and text (in new file type that created from application) to your JAR and run it on every Java-enabled devices. If your 1-byte font (has 256 characters) has multiple language character, MBFC can use that embedded font on every device to render your text with all that languages. By this rendering way (this is also fast!) you can ensure your text will show with the same way you want.

The stages of your work to do the job are:
1- Run MBFC application on your PC.
2- Create a project.
3- Define Chars: you must design all characters' appearance in 256 (or less) figures.
4- Define character groups: In complex-script languages like Parsi or Arabic, a letter has 4 figures: first, middle, last and separate figures. You must join every 4 figures that defined in previous stage together.
5- Link your groups to Unicode Chars.
6- Type a Unicode text on your PC application and click on a button to create a .mtx file.
7- Click on another button to create .mbf file from your font's figures.
8- Write  a MIDlet and use MBFC-ME library: You must use two files that created from last two stages  in you JAR file. Using MNFC-ME objects is very simple.

Please see this link:
http://mbfc.googlecode.com/files/Mobile%20Bit%20Font%20Creator.pps
or
http://majid.asgari.googlepages.com/MobileBitFontCreator.pps