public class LexicalUtils {

    private static final String[] SEPARATEURS = {
        "(", ")", "{", "}", "[", "]",
        ";", ",", ":", ".", "#"
    };

    private static final String[] OPERATEURS = {
        "+", "-", "*", "/", "%", "++", "--",
        "==", "!=", ">=", "<=", ">", "<",
        "&&", "||", "!", "&", "|", "^", "~",
        "<<", ">>", "=", "+=", "-=", "*=", "/=", "%="
    };

    private static final String[] MOTS_CLES = {
        "main","auto","break","case","char","const","continue",
        "do","double","else","float","for","if","int",
        "return","switch","void","while","mohamed_ali","bool"
    };
private static final char[] lettres = {
    // minuscules
    'a','b','c','d','e','f','g','h','i','j','k','l','m',
    'n','o','p','q','r','s','t','u','v','w','x','y','z',
    // majuscules
    'A','B','C','D','E','F','G','H','I','J','K','L','M',
    'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
};




    // Vérifie si t est un séparateur
    public static boolean estSeparateur(String t) {
        for (int i = 0; i < SEPARATEURS.length; i++) {
            if (SEPARATEURS[i].equals(t)) return true;
        }
        return false;
    }


public static boolean estcaractere(String c) {
    // Doit commencer et finir par '
    if(c == null || c.length() != 3) return false;
    if(c.charAt(0) != '\'' || c.charAt(2) != '\'') return false;

    char ch = c.charAt(1);
    for(int i=0; i<lettres.length; i++) {
        if(lettres[i] == ch) return true;
    }
    return false;
}



/*public static boolean estChaine(String s) {
    // doit commencer et finir par "
    if(s == null || s.length() < 2) return false;
    if(s.charAt(0) != '"' || s.charAt(s.length()-1) != '"') return false;

    // vérifier chaque caractère à l'intérieur
    for(int i=1; i<s.length()-1; i++) {
        char ch = s.charAt(i);
        // lettre maj/min ou chiffre ou espace ou symbole imprimable
        if(!estLettre(ch) && !estChiffre(ch) && ch>=32 && ch<=126) {
            return false;
        }
    }
    return true;
}*/

// Méthodes utilitaires
public static boolean estLettre(char c){
    for(int i=0; i<lettres.length; i++){
        if(lettres[i] == c) return true;
    }
    return false;
}

public static boolean estChiffre(char c){
    return c>='0' && c<='9';
}


    // Vérifie si t est un opérateur
    public static boolean estOperateur(String t) {
        for (int i = 0; i < OPERATEURS.length; i++) {
            if (OPERATEURS[i].equals(t)) return true;
        }
        return false;
    }

    
    // Vérifie si t est un mot-clé
    public static boolean estMotCle(String t) {
        for (int i = 0; i < MOTS_CLES.length; i++) {
            if (MOTS_CLES[i].equals(t)) return true;
        }
        return false;
    }




    
    // Colonne pour la matrice d'identificateur
    private static int col(char c) {
        if (c == '_') return 0;
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) return 1;
        if (c >= '0' && c <= '9') return 2;
        return 3;
    }

    
    // Vérifie si lex est un identificateur C
    public static boolean estIdentificateur(String lex) {
        if (lex == null) return false;
        if (lex.length() == 0) return false;

        int[][] matrice = {
            {1, 1, -1, -1}, // état 0
            {1, 1, 1, -1}   // état 1
        };
        int etat = 0;
        int etatFinal = 1;
        lex = lex + "#"; // sentinelle
        int i = 0;

        while (lex.charAt(i) != '#' && matrice[etat][col(lex.charAt(i))] != -1) {
            etat = matrice[etat][col(lex.charAt(i))];
            i++;
        }

        if (lex.charAt(i) == '#' && etat == etatFinal && i == lex.length() - 1) return true;
        return false;
    }

    
    // Vérifie si lex est un nombre entier
    public static boolean estNombre(String lex) {
        if (lex == null) return false;
        if (lex.length() == 0) return false;
        for (int i = 0; i < lex.length(); i++) {
            char c = lex.charAt(i);
            if (!(c >= '0' && c <= '9')) return false;
        }
        return true;
    }



}
