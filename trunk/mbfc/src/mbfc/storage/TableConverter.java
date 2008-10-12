package mbfc.storage;

// <editor-fold defaultstate="collapsed" desc="mbfc license">
/*
 * Created until 26-Nov-2007 at 16:20:01.
 * 
 * Copyright (c) 2007 Majid Asgari Bidhendi / Squirrel Soft�
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
// </editor-fold>
public class TableConverter {

    private BitArray source;
    private byte srow,  scol,  drow,  dcol;

    public TableConverter(BitArray aSource, byte sourceRow, byte sourceCol,
            byte destRow, byte destCol) {
        source = aSource;
        srow = sourceRow;
        scol = sourceCol;
        drow = destRow;
        dcol = destCol;
    }

    public static BitArray convert(BitArray source, byte srow, byte scol, byte drow, byte dcol) {
        BitArray temp = new BitArray((byte) (srow * drow), (byte) (scol * dcol));
        for (int i = 0; i < srow; i++) {
            for (int j = 0; j < scol; j++) {
                if (source.getBit(i, j) == 0) {
                    for (int k = 0; k < drow; k++) {
                        for (int l = 0; l < dcol; l++) {
                            temp.clearBit(i + k, j + l);
                        }
                    }
                } else {
                    for (int k = 0; k < drow; k++) {
                        for (int l = 0; l < dcol; l++) {
                            temp.setBit(i + k, j + l);
                        }
                    }
                }
            }
        }
        int[][] temp2 = new int[drow][dcol];
        for (int i = 0; i < drow * srow; i++) {
            for (int j = 0; j < scol * dcol; j++) {
                if (temp.getBit(i, j) == 1) {
                    temp2[i / srow][j / scol]++;
                }
            }
        }
        BitArray dest = new BitArray(drow, dcol);
        int p = (srow * scol) / 2;
        for (int i = 0; i < drow; i++) {
            for (int j = 0; j < dcol; j++) {
                if (temp2[i][j] >= p) {
                    dest.setBit(i, j);
                //else those cell would be 0(constructor)
                }
            }
        }
        return (dest);
    }

    public BitArray convert() {
        BitArray temp = new BitArray((byte) (srow * drow), (byte) (scol * dcol));
        for (int i = 0; i < srow; i++) {
            for (int j = 0; j < scol; j++) {
                if (source.getBit(i, j) == 0) {
                    for (int k = 0; k < drow; k++) {
                        for (int l = 0; l < dcol; l++) {
                            temp.clearBit(i + k, j + l);
                        }
                    }
                } else {
                    for (int k = 0; k < drow; k++) {
                        for (int l = 0; l < dcol; l++) {
                            temp.setBit(i + k, j + l);
                        }
                    }
                }
            }
        }
        int[][] temp2 = new int[drow][dcol];
        for (int i = 0; i < drow * srow; i++) {
            for (int j = 0; j < scol * dcol; j++) {
                if (temp.getBit(i, j) == 1) {
                    temp2[i / srow][j / scol]++;
                }
            }
        }
        BitArray dest = new BitArray(drow, dcol);
        int p = (srow * scol) / 2;
        for (int i = 0; i < drow; i++) {
            for (int j = 0; j < dcol; j++) {
                if (temp2[i][j] >= p) {
                    dest.setBit(i, j);
                //else those cell would be 0(constructor)
                }
            }
        }
        return (dest);

    }
}