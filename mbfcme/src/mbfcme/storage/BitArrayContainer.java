package mbfcme.storage;
import java.io.*;
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

public class BitArrayContainer {
  static final public int oneByteLength=256;
  static final public int twoByteLength=65536;//=256*256
  static final public char constantWidth='C';
  static final public char varifiedWidth='V';
  static final public char rightToLeft='R';
  static final public char leftToRight='L';
  static final public byte False=0;
  static final public byte True=1;
  private BitArray [] array;
  private byte []transparentArray;
  private char widthType;
  private byte height,defaultWidth;
  private int len;
  private char alignment;
  private int revAfter=0;
  public BitArrayContainer(int length,char aWidthType,char anAlignment,byte aHeight,byte aDefaultWidth) {
    alignment=anAlignment;
    len=length;
    defaultWidth=aDefaultWidth;
    array=new BitArray[length];
    widthType=aWidthType;
    height=aHeight;
    transparentArray=new byte[len];
    for(int i=0;i<len;i++){
      array[i] = new BitArray(height, defaultWidth);
      transparentArray[i]=False;
    }
  }
 public BitArrayContainer(InputStream inS){
   this.openFromFile(inS);
  }

  public boolean isRev(byte ch){
    if(revAfter<2) return false;
    return (((ch>-1)? ch:ch+256)>=revAfter);
  }


 protected void setTransparents(byte[] array){
     transparentArray=array;
 }
 protected void setChar(BitArray[] array){
     this.array=array;
 }

 public char getWidthType() {return widthType;}
 public byte getHeight(){return height;}
 public byte getDefaultWidth(){return defaultWidth;}
 public char getAlignment(){return alignment;}
 public int getLen(){return len;}

 public boolean addCharacter(int number,BitArray figure){
   try{
   array[number]=figure;}catch(Exception e){return(false);}
   return(true);
 }
 public BitArray getCharacter(int number){
   try{
     if(number<0) number+=256;
     return(array[number]);
   }catch(Exception e){return(null);}
 }

 public boolean isTransparent(int number){
   if(number<0) number+=256;
   return (transparentArray[number]==True);
 }

 public boolean openFromFile(InputStream s){
   try{
     DataInputStream inS=new DataInputStream(s);
     //reading number of chars in font
     len=256;
     array=new BitArray[len];
     //readin height of font
     byte[] temp=new byte[1];
     inS.read(temp);
     height=temp[0];
     //reading font type
     inS.read(temp);
     widthType=this.varifiedWidth;
     if(temp[0]==1) widthType=this.constantWidth;
     //reading font alignment
     inS.read(temp);
     alignment=this.leftToRight;
     if(temp[0]==1) alignment=this.rightToLeft;
     //set transparent status
     transparentArray=new byte[len];
     inS.read(transparentArray);
    //if constant width,i read width and then Hole of bitArray
    if(widthType==this.constantWidth){
      inS.read(temp);
      byte width=temp[0];
      for(int i=0;i<len;i++){
        int realCol=(width-1)/8+1;
        byte []b=new byte[realCol*height];
        inS.read(b);
        this.array[i]=new BitArray(b,height,width);
       }

      }
     //else for varied length length of any character figure will be readed
     //from file and then characters' figure.
     else
       for(int i=0;i<len;i++){
         inS.read(temp);
         byte width=temp[0];
         int realCol=(width-1)/8+1;
         byte []b=new byte[realCol*height];
         inS.read(b);
         this.array[i]=new BitArray(b,height,width);
        }
      temp=new byte[4];
      inS.read(temp);
      revAfter=bytesToInt(temp);
   }catch(Exception e){System.out.println("Error in BaitArrayCaontainer.OpenFromFile Catched");return false;}
   return true;
 }

  private int bytesToInt(byte[] b) {
    return  (((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16) |
            ((b[1] & 0xff) << 8) | (b[0] & 0xff));
  }
}