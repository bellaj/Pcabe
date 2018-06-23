/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheme;

import it.unisa.dia.gas.jpbc.Element;

import org.ejml.simple.SimpleBase;

import org.ejml.data.Matrix;
import org.ejml.simple.SimpleBase;

/**
 *
 * @author 
 */
public class PolicyLs {
    
    //used for encrypt
      Element C;
      Element Cp;
      Element[][] CDTable;
      public int numCol; 
      public int attNumber;
      SimpleBase M;
     
    String[] attsArray=new String[attNumber];
    // how to declare a function ro which map every row of the matrix to an attribute
    
    public LsEntry[] mapFunction(){
       
      LsEntry[] mapTable=new LsEntry[attNumber];
      SimpleBase N=this.M;
      
      for(int i=1;i<=attNumber;i++){
          
        SimpleBase V=N.extractVector(true, i);
        mapTable[i].R=V;
        mapTable[i].Att=attsArray[i];
     
    }
     
    return mapTable;
    }
}
