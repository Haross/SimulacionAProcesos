/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APlanificacion;

/**
 *
 * @author Javier
 */
import java.util.Scanner;
public class FCFS {
    float E;
		float R;
		float P;

	public static void main(String[] args) {
		int datos[] ={3,5,2,5,5};
		int llegada[]={0,1,3,9,12};
		char proceso[]={'A','B','C','D','E'};
		FCFS fc= new FCFS();
		fc.calcula(datos, llegada, proceso);
		System.out.println("\nTiempo de espera total: " +fc.getE());
		System.out.println("\nTiempo de Respuesta total: "+fc.getT());
		System.out.println("\nTiempo de Proporción de penalizacion total: "+fc.getP());
	}

	public void calcula(int[] datos, int[] llegada, char[] proceso){
		float[] tE=new float[datos.length];
		float e[]= new float[datos.length];
		float tR[]= new float[datos.length];
		float pP[]= new float[datos.length];
		E=0;
		R=0;
		P=0;
		tE[0]=llegada[0];

		
		//System.out.print("\n\nTiempo de espera gant es: "+tE[0]);
		e[0]=tE[0]-llegada[0];
		System.out.print("\nTiempo de Espera de "+proceso[0]+" : "+e[0]+" ms \t");
		tR[0]=e[0]+datos[0];
		pP[0]=tR[0]/datos[0];
		System.out.println("\nTiempo de Respuesta de "+proceso[0]+" : "+tR[0]+" ms");
		System.out.println("Proporcion de penalizacion de "+proceso[0]+" : "+pP[0]+" ms");
		for (int i=1; i<datos.length ;i++ ) {
			tE[i]=tE[i-1]+datos[i-1];
			
		//	System.out.print("\nTiempo de Espera gant "+(i+1)+" : "+tE[i]+" ms \t");
			e[i]=tE[i]-llegada[i];
			System.out.print("\nTiempo de Espera de "+proceso[0]+" : "+e[i]+" ms \t");
			tR[i]=e[i]+datos[i];
			System.out.println("\nTiempo de Respuesta de "+proceso[0]+" : "+tR[i]+" ms \t");
			pP[i]=(tR[i]/datos[i]);
			System.out.println("Proporción de penalizacion de "+proceso[0]+" : "+pP[i]+" ms \t");
			E=E+e[i];
			R=R+tR[i];
			P=P+pP[i];

		}
		E=E/datos.length;
		R=(R+tR[0])/datos.length;
		P=(P+pP[0])/datos.length;
	}

	public float getE(){
		return E;
	}

	public float getT(){
		return R;
	}

	public float getP(){
		return P;
	}
}
