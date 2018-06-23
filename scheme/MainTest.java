/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheme;

import it.unisa.dia.gas.jpbc.CurveParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.DefaultCurveParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.simple.SimpleBase;


/**
 *
 * @author  
 */
public class MainTest {

     public static void main(String[] args) {
         
 
         String m="HelloWorld";
         String[] str= new String[3];
         str[0]="aa";         
         str[1]="bb";
         str[2]="cc";
       
         MskLS msk= new MskLS();
         PubKeyLS pub= new PubKeyLS();
         int u=3;
         CPABE execute=new CPABE();
         SetupReturn SetResult=new SetupReturn();
         SetResult=execute.setupLS(pub,msk,u);
         SecureKeyLS sk=new SecureKeyLS();
         sk=execute.KeygenLS(pub, msk, str); 
     
       System.out.println("--------------------------------");
         System.out.println(" Public key paremeters :");
         System.out.println("--------------------------------");
     System.out.println(" g  = "+SetResult.pub.g);
         System.out.println();
        System.out.println(" g(e,e)^alpha = "+SetResult.pub.g_hat_alpha);
         System.out.println();
         System.out.println("   "+SetResult.pub.f);
         System.out.println();
          for(int i=1;i<u;i++){
         System.out.println(" h"+i+" = "+SetResult.pub.U.get(i).toString());
         System.out.println();
         System.out.println("--------------------------------");
         System.out.println();
         System.out.println("--------------------------------");
         System.out.println("    Master key paremeters :");
         System.out.println("--------------------------------");
         System.out.println(" MSK = "+SetResult.msk.g_alpha);
         System.out.println("--------------------------------");
        
        System.out.println("--------------------------------");
         System.out.println("    Secure Key parameters");
         System.out.println("--------------------------------");
         System.out.println("    K =  "+sk.K);
         System.out.println();
         System.out.println("    L =  "+sk.L);
         System.out.println();
          for(int j=1;j<u;j++){
              System.out.println("    K"+i+" =  "+sk.comps.get(j).Kx+"       "+sk.comps.get(j).attri);
              System.out.println();
          }
          System.out.println("--------------------------------");
          }
         
         
     
     SimpleBase lambda;
          lambda=new SimpleBase(2, 1) {
             @Override
             protected SimpleBase createMatrix(int i, int i1, MatrixType mt) {
                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }

             @Override
             protected SimpleBase wrapMatrix(Matrix matrix) {
                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }
           
             
             
         };
            System.out.print(lambda);   
    }
    
    
    
    
}
