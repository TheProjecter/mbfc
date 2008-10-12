package mbfcme.ui.canvas;
import javax.microedition.lcdui.*;

import mbfcme.storage.BitArrayContainer;
import mbfcme.ui.SliceDataContainer;

import java.io.InputStream;
import java.io.DataInputStream;

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
public class BitFontCanvas extends javax.microedition.lcdui.Canvas implements CommandListener{
    public static byte space=0;
    public static byte enter=1;
    public static char rightToLeft='r';
    public static char leftToRight='l';
    public static char centered='c';
    public static char justified='j';

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
    private int lIntend=0;
    private int uIntend=0;

    public BitFontCanvas(byte[]text,BitArrayContainer font,int spaceLengthInPixel,
                         int gapBetweenLines,int color,int transparentColor,
                         int backgroundColor,char alignment,int leftIntend,
                         int rightIntend,int upIntend,int downIntend){
      this.text=text;
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
      setCommandListener(this);
      // add the Exit command
      addCommand(new Command("Exit", Command.EXIT, 1));
      addCommand(new Command("Next Page", Command.OK, 1));
      //text=getOptimize(text);
      /*Image image=Toolkit.getDefaultToolkit().getImage(filename);
      image=image.getScaledInstance(width,height,
                                       Image.SCALE_AREA_AVERAGING);*/

    }

    public BitFontCanvas(java.io.InputStream s,BitArrayContainer font,int spaceLengthInPixel,
                         int gapBetweenLines,int color,int transparentColor,
                         int backgroundColor,char alignment,int leftIntend,
                         int rightIntend,int upIntend,int downIntend){
      DataInputStream inS=new DataInputStream(s);
      try{
          byte[] lenTemp = new byte[4];
          inS.read(lenTemp);
          int len = bytesToInt(lenTemp);
          text = new byte[len];
          inS.read(text);
      }catch(Exception ex){}
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
      setCommandListener(this);
      // add the Exit command
      addCommand(new Command("Exit", Command.EXIT, 1));
      addCommand(new Command("Next Page", Command.OK, 1));
      //text=getOptimize(text);
    }

    public int bytesToInt(byte[] b){
      return  (((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16) |
               ((b[1] & 0xff) << 8) | (b[0] & 0xff));
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
         else{revPos=-1;
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
   /* private int numberOfWords(byte [] text){
      int position=0,counter=0;
      while(position<text.length){
        if((text[position]==space)||(text[position]==enter)) counter++;
        position++;
      }
      //if((text[text.length-1]==space)||(text[text.length-1]==enter)) return counter;
      //^ optimized text havn't space or enter in the last position
      return(++counter);
    } */

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

      int next=0,disFromLeft=0,disFromTop=uIntend;
      SliceDataContainer dc=new SliceDataContainer();
      wasLastLine=false;

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
               disFromLeft=displayWidth-dc.lineLengthInPixel+lIntend;
       }//09126184162 VASE KIE!!? IN CHE COMMENTIE
       sequencedPrint(g,getOptimize(currentSlice),spaceArray,disFromLeft,
                      disFromTop,font.getAlignment()==font.leftToRight);
       disFromTop+=font.getHeight()+gapBetweenLines;
       start=next+1;
       if(wasLastLine) break;
       if(disFromTop+font.getHeight()>displayHeight) break;
      }
      if(wasLastLine) start=0;
    }

  public void commandAction(Command command, Displayable displayable) {
    if (command.getCommandType() == Command.EXIT) {
      //fill your codes
    }
    if (command.getCommandType() == Command.OK) {
      this.repaint();
    }
  }
}