package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String argv[]) {
		Gaulois g = new Gaulois("g",5);
		Gaulois g1 = new Gaulois("g",6);
		
		Etal etal = new Etal();
		
		etal.occuperEtal(g1, "produit", 2);
		try {
			etal.acheterProduit(1, g);}
		catch (IllegalArgumentException e ){
			System.out.println("illegal arg : quantite acheter est en dessous de 1 \n" );
		}
		catch (IllegalStateException e) {
			System.out.println("Illegal arg : etal vide ");
		}
		System.out.println("Fin de test");
		System.out.println(etal.acheterProduit(1, g));

	}
}
