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
        "auto","break","case","char","const","continue",
        "do","double","else","float","for","if","int",
        "return","switch","void","while","mohamed_ali"
    };

    // Vérifie si t est un séparateur
    public static boolean estSeparateur(String t) {
        for (int i = 0; i < SEPARATEURS.length; i++) {
            if (SEPARATEURS[i].equals(t)) return true;
        }
        return false;
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
