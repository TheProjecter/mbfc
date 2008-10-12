package mbfcme.storage;
import javax.microedition.lcdui.Graphics;
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

public class BitArray {
  private byte [][] array;
  private int row,col;
  public static final int rowsScale=2;
  public static final int colsScale=2;
  public BitArray(int aRow,int aCol) {
    row=aRow;
    col=aCol;
    int realCol=(col-1)/8+1;
    array=new byte[row][realCol];
    for(int i=0;i<row;i++)
     for(int j=0;j<realCol;j++)
       array[i][j]=0;
  }
  public BitArray(byte[] byteArray,int aRow,int aCol){
    row=aRow;
    col=aCol;
    setBytes(byteArray);
  }
  public void setBit(int r,int c){
    int tempC=c/8;
    array[r][tempC]=(byte)((array[r][tempC])|(twoPower(7-(c-8*tempC))));
  }
  public void clearBit(int r,int c){
    int tempC=c/8;
    array[r][tempC]=(byte)(array[r][tempC]&(255-twoPower(7-(c-8*tempC))));
  }
  public void complementBit(int r,int c){
    int bit=getBit(r,c);
    if(bit==1) clearBit(r,c);
    else setBit(r,c);
  }
  public int getBit(int r,int c){
    try{
      int temp = array[r][c / 8] & (twoPower(7 - (c - 8 * (c / 8))));
      if (temp == 0)return (0);
    }catch(Exception e){System.out.println("Error in array boundries"+r+" "+c);}
    return(1);
  }
  public static byte twoPower(int e){
    byte temp=1;
    for(int i=0;i<e;i++) temp=(byte)(temp*2);
    return(temp);
  }
  public int getRowsLength(){
    return(row);
  }
  public int getColsLength(){
    return(col);
  }
  public byte[] getBytes(){
    int realCol=(col-1)/8+1;
    byte []temp=new byte[row*realCol];
    for(int i=0;i<row;i++)
      for(int j=0;j<realCol;j++) temp[i*realCol+j]=array[i][j];
    return(temp);
  }
 public void setBytes(byte[] byteArray){
   int realCol=(col-1)/8+1;
   array=new byte[row][realCol];
   for(int i=0;i<row;i++)
    for(int j=0;j<realCol;j++)
     array[i][j]=byteArray[i*realCol+j];

 }
 public void paintIt(Graphics g,int top,int left,int color){
  g.setColor(color);
  for(int i=0;i<getRowsLength();i++)
   for(int j=0;j<getColsLength();j++){
     if (getBit(i, j) == 1)
       g.fillRect(left + j /* *colsScale*/, top + i /* *rowsScale*/, /*colsScale,
                  rowsScale*/1,1);
   }
  }
}