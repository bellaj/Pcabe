/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheme;

import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import it.unisa.dia.gas.jpbc.CurveGenerator;
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
public class CPABE {
    /*
	 * Generate a public key and corresponding master secret key.
	 */

	private static String curveParams = "type a\n"
			+ "q 87807107996633125224377819847540498158068831994142082"
			+ "1102865339926647563088022295707862517942266222142315585"
			+ "8769582317459277713367317481324925129998224791\n"
			+ "h 12016012264891146079388821366740534204802954401251311"
			+ "822919615131047207289359704531102844802183906537786776\n"
			+ "r 730750818665451621361119245571504901405976559617\n"
			+ "exp2 159\n" + "exp1 107\n" + "sign1 1\n" + "sign0 1\n";
        
        
    
     /************************************************************************************
      * 
      *LSSS setup
      */
        
        public SetupReturn setupLS(PubKeyLS pub, MskLS msk,int u) {
		
                SetupReturn setR=new SetupReturn();
                

                /*CurveParameters params = new DefaultCurveParameters()
				.load(new ByteArrayInputStream(curveParams.getBytes()));*/
                CurveGenerator cg = new TypeACurveGenerator(70, 70);
                DefaultCurveParameters params = (DefaultCurveParameters) cg.generate();
                System.out.println(params);

		pub.pairingDesc = curveParams;
		//pub.pairingDesc = params.toString();
		pub.p = PairingFactory.getPairing(params);
		Pairing pairing = pub.p;
		

		pub.g = pairing.getG1().newElement();
		pub.f = pairing.getG1().newElement();
		pub.h = pairing.getG1().newElement();
		pub.g_hat_alpha = pairing.getGT().newElement();
                
	pub.alpha = pairing.getZr().newElement();
	pub.a= pairing.getZr().newElement();
                
                
		msk.g_alpha = pairing.getG2().newElement();
                
                //build
	pub.alpha.setToRandom();
	pub.g.setToRandom();
		pub.a.setToRandom();
                
                //Compute 
                msk.g_alpha=pub.g.duplicate();
                msk.g_alpha.powZn(pub.alpha);
                
                
                pub.g_hat_alpha = pairing.pairing(pub.g, msk.g_alpha);
                pub.f = pub.g.duplicate();
                pub.f.powZn(pub.a);
                System.out.println("pub.U before = "+pub.U);
                for(int i=0;i<u;i++){
                    pub.h.setToRandom();
                    pub.U.add(i, pub.h);
                    
                }
                
                System.out.println("pub.U after  = "+pub.U);
                System.out.println("pub.g  = "+pub.g);

                setR.pub=pub;
                setR.msk=msk;
               
                System.out.println("setR = "+setR.pub.g);
                return setR;
        }

	public SecureKeyLS KeygenLS(PubKeyLS pub,MskLS msk,String[] S){
        SecureKeyLS sk=new SecureKeyLS();
        Pairing pairing;
        
        //initialize
        pairing=pub.p;
        sk.K=pairing.getG1().newElement();
        sk.L=pairing.getG1().newElement();
        sk.t=pairing.getZr().newElement();
        
        //built
        sk.t.setToRandom();
        
        //Compute
        sk.K=msk.g_alpha.duplicate();
        sk.K.mul(pub.f.powZn(sk.t));
        
        sk.L=pub.g.duplicate();
        sk.L.powZn(sk.t);
        
        sk.comps=new ArrayList<SecureCompLS>();
        int len=S.length;
        for(int i=0;i<len;i++){
            
            SecureCompLS comp=new SecureCompLS();
            comp.Kx=pairing.getG1().newElement();
            
            comp.Kx=pub.U.get(i).duplicate();
            comp.Kx.powZn(sk.t);
            comp.attri=S[i];
            sk.comps.add(comp);
            }
        
        return sk;
        }
	
        
        /*************************************************************************************
     * * Encrypting using LSSS policy
     * /
     */
    public CphKey encryptUseLs(PubKeyLS pub,PolicyLs Policy,String message){
        CphKey CphReturn= new CphKey ();
        CipherTextLs cph=new CipherTextLs();
        Element s,m;
        Element r;
        
        
        //Initialize 
        Pairing pairing = pub.p;
        cph.C = pairing.getGT().newElement();
        cph.Cp = pairing.getG1().newElement();
        cph.CDs=new ArrayList<CDCipherText>();
        
        int n=Policy.M.numCols()-1;
        s = pairing.getZr().newElement();
        m = pairing.getGT().newElement();
        r=pairing.getZr().newElement();
        
        SimpleBase v;
        SimpleBase lambda=new SimpleBase(2, 1) {
         
            protected SimpleBase createMatrix(int i, int i1, MatrixType mt) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            protected SimpleBase wrapMatrix(Matrix matrix) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        SimpleBase LambdaVect; 
        LsEntry[] mapTable=Policy.mapFunction();
        
        //build
        s.setToRandom();
        m.setToRandom();
        //v.set(1, 1, s); the problem is initializing the simplebase
        /*
        Create random vector V
        */
        for (int i=2;i<=n;i++){
            r.setToRandom();
            // v.set(1, i, r); same probleme 
        
        }
        /*
        Calculate lamda'i' and stock them as simplebase form in lambdaVect
        */
        for (int i=1;i<=n;i++){
           lambda=mapTable[i].R.mult(Policy.M);
           //LambdaVect.set(1,i,lamda);
           
        }
        
        //Compute
        cph.C=pub.g_hat_alpha.duplicate();
        cph.C.powZn(s);
        cph.C.mul(m);//grand M ou petite m ?????
        
        cph.Cp=pub.g.duplicate();
        cph.Cp.powZn(s);
        
        for(int i=1;i<=Policy.attNumber;i++){
            /*double Powlambda=LambdaVect.get(1,i);
            double powr=v.get(1,i);
            


            */
        }
        
        
        return CphReturn;
    
    }
}
