package mbfcme.ui.entry;
import javax.microedition.lcdui.*;

import mbfcme.storage.BitArrayContainer;
import mbfcme.ui.SliceDataContainer;
/*
 * Created until 26-Nov-2007 at 16:20:01.
 * 
 * Copyright (c) 2007 Majid Asgari Bidhendi / Squirrel Soft®
 *
 * This file is part of Mobile Bit Font Creator.
 *
 * Mobile Bit Font Creator is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Mobile Bit Font Creator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Mobile Bit Font Creator; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * Commercial licenses are also available, please
 * refer to the accompanying LICENSE.txt or visit
 * http://www.samancomputers.com for details.
 */
public class MLEntryCanvas extends javax.microedition.lcdui.Canvas{
  public static byte space=0;
  public static byte enter=1;
  public static char rightToLeft='r';
  public static char leftToRight='l';
  public static char centered='c';
  public static char justified='j';

  public static byte first=EntryCanvasProperties.first;
  public static byte middle=EntryCanvasProperties.middle;
  public static byte last=EntryCanvasProperties.last;
  public static byte seprate=EntryCanvasProperties.seprate;

  private byte[] text;
  private int spaceLengthInPixel;
  private BitArrayContainer font;
  private int displayWidth;
  private int displayHeight;
  private int backgroundColor;
  private int color;
  private int transparentColor;
  private char alignment;
  private int gapBetweenLines;
  private boolean wasLastLine;
  private boolean endOfParagraph;
  private int start=0;
  private int lastPageStart=0;
  private int pageStart=0;
  private int lIntend=0;
  private int uIntend=0;

  private int repeatNumber;
  private boolean isRepeated;
  private MultiLanguageECP properties;
  private String[] layoutNames;
  private byte layout;
  boolean comment1,comment2,comment3,comment4;
  private byte lastKey;
  private byte lastLayout;

  public MLEntryCanvas(String[] layoutNames,MultiLanguageECP properties,BitArrayContainer font,int spaceLengthInPixel,
                       int gapBetweenLines,int color,int transparentColor,
                       int backgroundColor,char alignment,int leftIntend,
                         int rightIntend,int upIntend,int downIntend,
                       boolean comment1,boolean comment2,boolean comment3,
                       boolean comment4){
    this.layoutNames=layoutNames;
    layout=0;
    this.comment1=comment1;
    this.comment2=comment2;
    this.comment3=comment3;
    this.comment4=comment4;
    this.text=new byte[0];
    this.properties=properties;
    this.font=font;
    this.spaceLengthInPixel=spaceLengthInPixel;
    this.gapBetweenLines=gapBetweenLines;
    this.color=color;
    this.transparentColor=transparentColor;
    this.backgroundColor=backgroundColor;
    this.alignment=alignment;
    displayWidth=this.getWidth();
    if(leftIntend+rightIntend<displayWidth){
      displayWidth -= (leftIntend + rightIntend);
      //this.firstLineIntend=firstLineIntend;
      this.lIntend = leftIntend;
    }
    displayHeight=this.getHeight();
    if(upIntend+downIntend<displayHeight){
      displayHeight -= (upIntend + downIntend);
      //this.firstLineIntend=firstLineIntend;
      this.uIntend = upIntend;
    }
    repeatNumber=1;isRepeated=false;
  }

  public byte[] getCurrentText(){
    return text;
  }

  //a simple method that fill all of places in an array with a number
  public static void fillArrayWith(int[] array,int number){
    for(int i=0;i<array.length;i++) array[i]=number;
  }

  /*for justified mode we must fill space array with proper length those are
   not exactly spaceLengthInPixel always and may be greater than it because of
   text slice must fill whole of screen width.
   after finding a proper slice depends on alignment type we must fill an array
   named spaceLengthArray that contained width of every space bitween words.
   in left,right and centered alignment array must be filled with
   spaceLengthInPixel only (see fillArrayWith method) but in justified mode we
   must fill in another way.see it blow*/
  private void fillJustifiedArray(int[] array,int wordsLen,int defaultNumber){
    int numberOfSpaces=array.length;
    int extraWidth=displayWidth-wordsLen;
    if(array.length==0) return;
    int extraInEverySpace=extraWidth/numberOfSpaces;
    fillArrayWith(array,defaultNumber+extraInEverySpace);
    int l=extraWidth-extraInEverySpace*numberOfSpaces;
    for(int i=0;i<l;i++) array[i]++;
  }

  /*this simple method can get a sub text of a text from given start to given
   end point of text*/
  public  byte[] getSubText(byte[] text,int start,int end){
    while(start<text.length){
        if ((text[start] == 0) || (text[start] == 1))
            start++;
        else break;
    }

    while(end>0){
        if ((text[end] == 0) || (text[end] == 1))
            end--;
        else break;
    }

    if ((end-start+1<0)) return null;
    byte[] temp=new byte[end-start+1];
    int revPos=-1;
    for(int i=start;i<end+1;i++){
      if(font.isRev(text[i])){
        if(revPos==-1) revPos=i-start;
        insertInArray(temp,revPos,text[i],i-start);
      }else if((revPos!=-1)&&((text[i]==space)||(text[i]==enter)))
        insertInArray(temp,revPos,text[i],i-start);
      else{
        revPos=-1;
        temp[i - start] = text[i];
      }
    }
    return temp;
  }

  private void insertInArray(byte[] array,int revPos,byte ch,int endIndexOfArray) {
    for(int i=endIndexOfArray;i>revPos;i--) array[i]=array[i-1];
    array[revPos]=ch;
  }


  /*this method can navigate the text for maximum slice of text that can fill the
   width of screen properly.
   text:is navigated text
   start:is start poit in text for navigating
   lineLengthInPixel:is width of written text slice*/
  private int getNextSlice(byte[] text,int start,SliceDataContainer dc){
    endOfParagraph=false;
    int position=start,
        lastPos=start,
        wordCounter=0,
        wordsLen=0,
        lastWordLen=0;
    while ((text[position]==space)||(text[position]==enter)){
        position++;
        lastPos++;
        start++;
    }
    while(true){
      if((position==text.length)||(text[position]==space)||
         (text[position]==enter)){
      if(wordsLen+lastWordLen+(wordCounter*spaceLengthInPixel)>displayWidth) break;
      wordsLen+=lastWordLen;
      wordCounter++;
      lastPos=position;
      lastWordLen=0;
      if(position==text.length) {wasLastLine=true;endOfParagraph=true;break;}
      if(text[position]==enter) {endOfParagraph=true;break;}
      }
      else if(!font.isTransparent(text[position]))
        lastWordLen+=(((font.getCharacter(text[position]))).getColsLength()+1);
      position++;
    }
    dc.lineLengthInPixel=wordsLen+(wordCounter-1)*spaceLengthInPixel;
    dc.numberOfWords=wordCounter;
    return(lastPos);
  }

  /* this method returns number of words in a text supposed that text
     is optimized by getOptimized method
  */
  private int numberOfWords(byte [] text){
    int position=0,counter=0;
    while(position<text.length){
      if((text[position]==space)||(text[position]==enter)) counter++;
      position++;
    }
    //if((text[text.length-1]==space)||(text[text.length-1]==enter)) return counter;
    //^ optimized text havn't space or enter in the last position
    return(++counter);
  }

  /* this method returns a text that is reverse of given text supposed text
     not ends with a space or enter
  */
  public static byte[] getReverse(byte[] text){
    int len=text.length;
    if((text[len-1]==space)||(text[len-1]==enter)) len--;
    byte[] temp=new byte[len];
    for(int i=0;i<len;i++) temp[len-i-1]=text[i];
    return(temp);
  }

  /*this method gets a optimized text that sqenced spaces and enters is deleted
  in this but this method manipulates entered text, before that u must send a
  copy of ur text enter and space of end of text will be deleted*/
  private byte[] getOptimize(byte[] text){
    byte[] temp;
    int position=0,optimizedPosition=0;
    boolean spaceIsLast=false;
    while(position<text.length){
      if((spaceIsLast)&&((text[position]==space)||(text[position]==enter))){
        position++;
        continue;
      }
      else if((text[position]==space)||(text[position]==enter)) spaceIsLast=true;
      else spaceIsLast=false;
      text[optimizedPosition]=text[position];
      position++;
      optimizedPosition++;
    }
    if(spaceIsLast) optimizedPosition--;
    temp=new byte[optimizedPosition];
    for(int i=0;i<temp.length;i++) temp[i]=text[i];
    return(temp);
  }

  /*sequenced print is a method that can print a BitFont subtext from left to
   right.first character in given array must be first character in left.
   space array contains space width in pixels,
   disFromLeftInPixels determines gap from left and
   disFromTopInPixel from top*/
  private void sequencedPrint(Graphics g,byte[] subText,int[] spaceArray,
                              int disFromLeftInPixel,int disFromTopInPixel,boolean isLtoR){
    int spaceArrayPos=0,dis=0,i=0;
    for(;i<subText.length;i++){
      if(subText[i]==enter) break;
      if(subText[i]!=space){
        if(font.isTransparent(subText[i])&&isLtoR)
            dis-=(font.getCharacter(subText[i]).getColsLength()+1);
        font.getCharacter(subText[i]).paintIt(g, disFromTopInPixel,
                                              dis + disFromLeftInPixel,
                                              font.isTransparent(subText[i]) ? transparentColor:color);
        dis+=(font.getCharacter(subText[i]).getColsLength()+1);
        if(font.isTransparent(subText[i])&&!isLtoR)
            dis-=(font.getCharacter(subText[i]).getColsLength()+1);
      }
      else/*is space*/ dis+=spaceArray[spaceArrayPos++];
    }
  }

  /*because of using weak method sequencedPrint,we must apply some changes in
   disFromLeftInPixel and subText before sending them to that method.see:
   abbreviations: left to right=lr
                  right to left=rl
                  distance from left side of canvas= dis
        reversed=rev
   1.for lr alignment
      a.for lr languages: dis=0,sequencedPrint of text
      b.for rl languages: dis=0,sequencedPrint of rev-text
   2.for rl alignment
      a.for lr languages: dis=displayWidth-wordsWidth,sequencedPrint of text
      b.for rl languages: dis=displayWidth-wordsWidth,sequencedPrint of rev-text
   3.for centered alignment
      a.for lr languages: dis=(displayWidth-wordsWidth)/2,sequencedPrint of text
      b.for rl languages: dis=(displayWidth-wordsWidth)/2,sequencedPrint of rev-text
   4.for justified alignment after filling space array in another way(see
   fillJustifiedArray method comments),
      a.for lr languages: dis=0,sequencedPrint of text
      b.for rl languages: dis=0,sequencedPrint of rev-text*/
  public void paint(Graphics g){
    g.setColor(backgroundColor);
    g.fillRect(0,0,this.getWidth(),this.getHeight());

    g.setColor(color);
    int disFromTop=0;

    if(comment1){
      g.drawString("0~9 for letters", 0, 0, Graphics.TOP | Graphics.LEFT);
      disFromTop += g.getFont().getHeight();
    }
    if(comment2){
      g.drawString("* key for delete", 0, disFromTop,
                   Graphics.TOP | Graphics.LEFT);
      disFromTop += g.getFont().getHeight();
    }
    if(comment4){
      g.drawString("# key for changing Key Pad Layouts", 0, disFromTop,
                   Graphics.TOP | Graphics.LEFT);
      disFromTop += g.getFont().getHeight();
    }
    if(comment3){
      if (this.hasRepeatEvents())
        g.drawString("Hold for change", 0, disFromTop,
                     Graphics.TOP | Graphics.LEFT);
      else
        g.drawString("Other Keys for next letter", 0, disFromTop,
                     Graphics.TOP | Graphics.LEFT);
      disFromTop += g.getFont().getHeight();
    }
    if(layoutNames!=null){
      g.drawString(layoutNames[layout], 0, disFromTop, Graphics.TOP | Graphics.LEFT);
      disFromTop += g.getFont().getHeight();
    }

    int next=0,disFromLeft=0;disFromTop+=uIntend;
    SliceDataContainer dc=new SliceDataContainer();
    wasLastLine=false;
    pageStart=start;

    while(next<text.length){
     if(text[start]==enter){
       disFromTop+=font.getHeight()+gapBetweenLines;
       if(disFromTop>displayHeight) break;
       start++;
       continue;
     }
     next=getNextSlice(text,start,dc);
     //if((text[next]==space)||(text[next]==enter)) next--;
     byte[] currentSlice=getSubText(text,start,next-1);
     if((currentSlice==null)||(currentSlice.length==0)) return;
     disFromLeft=lIntend;
     if(alignment==rightToLeft) disFromLeft=displayWidth-dc.lineLengthInPixel+lIntend;
       else if(alignment==centered) disFromLeft=(displayWidth-dc.lineLengthInPixel)/2+lIntend;
     if(font.getAlignment()==font.rightToLeft) currentSlice=getReverse(currentSlice);
     int[] spaceArray=new int[dc.numberOfWords-1];
     if(!(alignment==justified)) fillArrayWith(spaceArray,spaceLengthInPixel);
     else if (!endOfParagraph)/*alignment is justified*/
         fillJustifiedArray(spaceArray,dc.lineLengthInPixel,spaceLengthInPixel);
     else{ //last line in justified
         fillArrayWith(spaceArray, spaceLengthInPixel);
         if(font.getAlignment()==font.rightToLeft)
             disFromLeft=displayWidth-dc.lineLengthInPixel;
     }//09126184162 VASE KIE!!? IN CHE COMMENTIE
     sequencedPrint(g,getOptimize(currentSlice),spaceArray,disFromLeft,
                    disFromTop,font.getAlignment()==font.leftToRight);
     disFromTop+=font.getHeight()+gapBetweenLines;
     start=next+1;
     if(wasLastLine){lastPageStart=pageStart;break;}
     if(disFromTop+font.getHeight()>displayHeight) break;
    }
    if(wasLastLine) start=0;
  }

  /*this method calls when a key pressed,if that key pressed again in short time
    next method calls.only we must check*/
   //star key is "delete last char"
   public void keyPressed(int keyCode){
       if(keyCode==Canvas.KEY_STAR){
           //if the last page has only one character and it muse be deleted
           if(text.length-1==lastPageStart) start=0;
           else start=lastPageStart;
           deleteLast();
           repaint();
           repeatNumber = 1;
           isRepeated=false;
           return;
       }
       else if(keyCode==Canvas.KEY_POUND){
         layout =(byte)((layout + 1)%properties.getNumberOfLayouts());
         start=lastPageStart;
         repaint();
         return;
       }

       if(lastKey!=(keyCode-48)){
         repeatNumber = 1;
         isRepeated=false;
       }

   }

   private void deleteLast() {
       if(text.length==0) return;
       if(text.length==1) {text=new byte[0];return;}
       byte type=getType(text,text.length-2);
       //if(type==last) then nothing
       //else if(type==seprate) then nothing
       if(type==first)
           text[text.length-2]=searchAndGet(layout,text[text.length - 2], first, seprate);
       else if(type==middle)
           text[text.length-2]=searchAndGet(layout,text[text.length - 2], middle, last);
       byte[] newText=new byte[text.length-1];
       for(int i=0;i<newText.length;i++)
           newText[i]=text[i];
       text=newText;
   }

     public void keyRepeated(int keyCode){
       if((keyCode<48)||(keyCode>57)) return;
       if(properties.chars[layout][keyCode-48][0].length==0) return;
       repeatNumber=repeatNumber%properties.chars[layout][keyCode-48][0].length+1;
   }

   public void keyReleased(int keyCode){
       if(!((keyCode<48)||(keyCode>57))){
         if (properties.chars[layout][keyCode - 48][0].length == 0)return;
         //if it is a numbered key (0~9)
         keyCode -= 48;
         byte newChar;
         if ( (repeatNumber == 1) && (!isRepeated)) {
           int len = text.length + 1;
           byte[] newText = new byte[len];
           for (int i = 0; i < text.length; i++)
             newText[i] = text[i];
           if (text.length == 0)
             newChar = properties.getSeprate(layout, keyCode, repeatNumber - 1);
           else if ( (text[text.length - 1] == space) ||
                    (text[text.length - 1] == enter)
                    || (properties.isLast(layout, text[text.length - 1])))
             newChar = properties.getSeprate(layout, keyCode, repeatNumber - 1);
           else newChar = properties.getLast(layout, keyCode, repeatNumber - 1);
           newText[newText.length - 1] = newChar;

           if (! ( (newChar == space) || (newChar == enter))) {
             if (text.length != 0) {
               byte lastType = getType(text, text.length - 1);
               if (lastType == seprate)
                 newText[len -
                     2] = searchAndGet(lastLayout, newText[len - 2], seprate, first);
               else if (lastType == last)
                 newText[len -
                     2] = searchAndGet(lastLayout, newText[len - 2], last, middle);
                 //lastType==first or last: don't need to change
             }
           }
           text = newText;
           isRepeated = true;
         }
         else { //no character has added
           if (text.length < 2)
             text[0] = properties.getSeprate(layout, keyCode, repeatNumber - 1);
           else {
             if ( (text[text.length - 2] == space) ||
                 (text[text.length - 2] == enter)
                 || (properties.isLast(layout, text[text.length - 1])))
               newChar = properties.getSeprate(layout, keyCode, repeatNumber - 1);
             else newChar = properties.getLast(layout, keyCode, repeatNumber - 1);
             text[text.length - 1] = newChar;
           }
         }
         lastKey = (byte) keyCode;
         lastLayout=layout;
         start=lastPageStart;
         while(!wasLastLine)
           repaint();
       }
       if(this.hasRepeatEvents()) {repeatNumber=1;isRepeated=false;}
       else{
             if ((keyCode<0)||(keyCode>9)) {
               repeatNumber = 1;
               isRepeated=false;
               return;
             }
             if(properties.chars[layout][keyCode][0].length==0) return;
             repeatNumber = repeatNumber % properties.chars[layout][keyCode][0].length + 1;
         }
   }

   private byte searchAndGet(int layout,byte ch,byte lastType,byte newType){
       byte[] group=properties.getGroup(layout,ch,lastType);
       if(newType==this.first) return group[0];
       if(newType==this.middle) return group[1];
       if(newType==this.last) return group[2];
       if(newType==this.seprate) return group[3];
       return this.seprate;
   }

   private byte getType(byte[] text,int i){
       //if(i==0) return first;
       //if(i==text.length-1) return last;
       if(text[i]==space) return space;
       if(text[i]==enter) return enter;
       boolean back=(i==0)||(text[i-1]==space)||(text[i-1]==enter)
                    ||(properties.isLast(layout,text[i-1]));
       boolean next=(i==text.length-1)||(text[i+1]==space)||(text[i+1]==enter);
       if(back&&next) return seprate;
       if(back) return first;
       if(next) return last;
       return(middle);
  }
}