
import java.io.*;

public class AnalyseurLexical {

    private static boolean erreur = false;

    public static void analyser(String chemin) {
        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {

            int car;
            String lexeme = ""; //le lexeème ou l'espace ou on va stocker les caractères jusqu'a avoir un lexème
            boolean ignoreDirective = false;  
            boolean ignoreCommentaireLigne = false;
            boolean ignoreCommentaireBloc = false;

            while ((car = br.read()) != -1) {  //lire le fichier caarctère par caractère
                char c = (char) car;

                // Ignorer directives #include
                if (!ignoreDirective && c == '#') {
                    ignoreDirective = true;
                    lexeme = "";
                    continue;
                }
                if (ignoreDirective) {
                    if (c == '\n' || c == '\r') ignoreDirective = false;
                    continue;
                }

                // Début commentaires s'il est un commentaire on l'ignore
                if (!ignoreCommentaireLigne && !ignoreCommentaireBloc) {
                    if (c == '/') {
                        br.mark(1);
                        int next = br.read();
                        if (next == '/') {
                            ignoreCommentaireLigne = true;
                            continue;
                        } else if (next == '*') {
                            ignoreCommentaireBloc = true;
                            continue;
                        } else {
                            br.reset();
                        }
                    }
                }

                // Ignorer commentaires
                if (ignoreCommentaireLigne) {
                    if (c == '\n' || c == '\r') ignoreCommentaireLigne = false;
                    continue;
                }

                if (ignoreCommentaireBloc) {
                    if (c == '*') {
                        br.mark(1);
                        int next = br.read();
                        if (next == '/') ignoreCommentaireBloc = false;
                        else br.reset();
                    }
                    continue;
                }

                // Début d'une chaîne exemple (a l'interieur d'un printf).
                if (c == '"') {
                    String chaine = "\"";
                    while ((car = br.read()) != -1) {
                        char next = (char) car;
                        chaine += next;
                        if (next == '"') break; // fin de la chaîne
                    }
                    System.out.println(chaine + " --> Chaîne");
                    continue;
                }

                // Fin lexème : espace ou tab ou retour chariot
                if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                    if (lexeme.length() > 0) {
                        afficherLexeme(lexeme);
                        lexeme = "";
                    }
                    continue;
                }

                // Séparateur
                if (LexicalUtils.estSeparateur("" + c)) {  //"" + c  transforme le caractère en chaîne de caractères (String) en le concaténant avec une chaîne vide ("").
                   
                if (lexeme.length() > 0) {
                        afficherLexeme(lexeme);
                        lexeme = "";

                    }
                    System.out.println(c + " --> Séparateur");
                    continue;
                }


                // Opérateur simple
                if (LexicalUtils.estOperateur("" + c)) {
                    if (lexeme.length() > 0) {
                        afficherLexeme(lexeme);
                        lexeme = "";
                    }
                    System.out.println(c + " --> Opérateur");
                    continue;
                }


               /* if (LexicalUtils.estChaine("" + c)) {
                    
                        afficherLexeme(lexeme);
                        lexeme = "";
                  
                    System.out.println(c + " --> constante chaine de caractère");
                    continue;
                }*/  

                 if (LexicalUtils.estcaractere("" + c)) {
                        afficherLexeme(lexeme);
                        lexeme = "";
                    System.out.println(c + " --> constante caractere");
                    continue;
                }
                // Sinon : construction du lexème
                lexeme = lexeme + c;
            }

            // Dernier lexème
            if (lexeme.length() > 0) {
                afficherLexeme(lexeme);
            }

        } catch (IOException e) {
            System.out.println("Erreur lecture fichier : " + e.getMessage()); //si on rencontre un problème dans la lecture du fichier
        }

        if (erreur)
            System.out.println("\n ERREUR LEXICALE RENCONTRÉE");
        else
            System.out.println("\n ANALYSE LEXICALE BIEN FAITE");
    }

    private static void afficherLexeme(String lex) {
        if (lex == null || lex.length() == 0) return;

        if (LexicalUtils.estMotCle(lex)) System.out.println(lex + " --> Mot-clé ");
        else if (LexicalUtils.estIdentificateur(lex)) System.out.println(lex + " --> Identificateur ");
        else if (LexicalUtils.estNombre(lex)) System.out.println(lex + " --> Nombre ");
        else if (LexicalUtils.estcaractere(lex)) System.out.println(lex + "--> caractrère ");
        else {
            System.out.println(lex + " --> ERREUR LEXICALE");
            erreur = true;
        }
    }

    public static void main(String[] args) {
        analyser("C:\\Users\\victus\\Desktop\\DoWhile\\analyseurdowhile\\code.c"); // mettre le chemin correct vers ton fichier C pour le lire .
    }
}
