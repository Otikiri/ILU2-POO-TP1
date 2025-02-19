package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);
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

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (nbEtalOcc == nbEtalTotal) {
				System.out.println("Marche est rempli");
				return;
			}
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			nbEtalOcc++;
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
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

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}

		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = nbEtalTotal - nbEtalOcc;
			for (int i = 0; i < nbEtalOcc; i++) {
				chaine.append(etals[i].afficherEtal() + "\n");
			}
			chaine.append("Il reste " + nbEtalVide + " etals non utilise dans le marche.\n");
			return chaine.toString();
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

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int i = marche.trouverEtalLibre();
		marche.utiliserEtal(i, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vends des " + produit + " a l'etal " + i + ".");
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etal = marche.trouverEtals(produit);
		for (int i = 0; i < etal.length; i++) {
			if (etal[i].contientProduit(produit)) {
				chaine.append(etal[i].afficherEtal() + "\n");
			}
		}
		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois gaulois) {
		return marche.trouverVendeur(gaulois);
	}

	public String partirVendeur(Gaulois gaulois) {
		Etal etal = rechercherEtal(gaulois);
		return etal.libererEtal();
	}

	public String afficherMarche() {
		return marche.afficherMarche();
	}
}