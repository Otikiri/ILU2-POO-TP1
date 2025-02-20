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
			etals = new Etal[nbEtalTotal];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
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
			for (int i = 0; i < nbEtalTotal; i++) {
				if (!(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {

			int nbEtals = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtals++;
				}
			}
			int i = 0;
			Etal[] etalProduit = new Etal[nbEtals];
			for (int j = 0; j < etals.length; j++) {
				if (etals[j].contientProduit(produit)) {
					etalProduit[i] = etals[j];
					i++;
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
				chaine.append(etals[i].afficherEtal());
			}
			chaine.append("Il reste " + nbEtalVide + " etals non utilise dans le marche.");
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
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".");
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
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".");
		chaine.append("Le vendeur " + vendeur.getNom() + " vends des " + produit + " a l'etal " + i + ".");
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etal = marche.trouverEtals(produit);
		if (etal.length <= 0) {
			chaine.append("Il n'y a de vendeur qui propose des " + produit + " au marche");
		}
		for (int i = 0; i < etal.length; i++) {
			if (etal[i].contientProduit(produit)) {
				chaine.append(etal[i].afficherEtal());
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