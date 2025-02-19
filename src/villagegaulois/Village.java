package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	private static class Marche {

		private int nbEtalTotal;
		private int nbEtalOcc = 0;
		private Etal[] etals;

		public Marche(int nbEtTotal) {
			nbEtalTotal = nbEtTotal;
			this.etals = new Etal[nbEtalTotal];

		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produi, int nbProduit) {
			if (nbEtalOcc == nbEtalTotal) {
				System.out.println("Marche est rempli");
				return;
			}
			etals[indiceEtal].occuperEtal(vendeur, produi, nbProduit);
			nbEtalOcc++;
		}

		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}

		public Etal[] trouverEtals(String produit) {
			Etal[] etalProduit = new Etal[nbEtalOcc];
			for (int i = 0; i < etalProduit.length; i++) {
				for (int j = 0; j < etalProduit.length; j++) {
					if (etals[j].contientProduit(produit)) {
						etalProduit[i] = etals[j];
					}
				}
			}
			return etalProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur().equals(gaulois)) {
					return etals[i]; 
				}
			}
			return null;
		}
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}