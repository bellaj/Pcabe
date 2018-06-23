/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheme;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

import java.util.ArrayList;

import org.ejml.simple.SimpleBase;

/**
 *
 * @author  
 */
public class PubKeyLS {
    /*
	 * A public key
	 */
	public String pairingDesc;
	public Pairing p;				
	public Element g;				/* G_1 */
	public Element h;				/* G_1 */
	public Element f;				/* G_1 */
	public Element g_hat_alpha;	/* G_T */
        public Element a;
        Element alpha;
        ArrayList<Element> U=new ArrayList<Element>(10);
}
