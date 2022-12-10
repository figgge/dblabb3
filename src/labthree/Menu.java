package labthree;


public class Menu {
    private static boolean showMenu = true;


    public static void displayMainMenu() {
        System.out.println("""
                Huvudmeny
                =========
                1 - (C) Lägg till en ny film
                2 - (R) Visa alla filmer
                3 - (U) Uppdatera en film
                4 - (D) Ta bort en film
                5 - Visa antal filmer i databasen
                6 - Visa medelbudget per genre
                7 - Sök på genre
                8 - Genre meny
                """);
        System.out.print("Menyval: ");
    }

    public static void mainMenu() {
        if (showMenu)
            displayMainMenu();
        String userInput = Main.scanner.nextLine().toLowerCase();
        switch (userInput) {
            case "1", "c" -> Main.insertMovie();
            case "2", "r" -> Main.selectAll();
            case "3", "u" -> Main.updateMovie();
            case "4", "d" -> Main.deleteMovie();
            case "5" -> Main.movieCount();
            case "6" -> Main.averageMovieBudgetByGenre();
            case "7" -> Main.selectGenre();
            case "8" -> genreMenu();
            case "e" -> System.exit(0);
            default -> displayMainMenu();
        }
        showMenu = false;
        System.out.println();
        mainMenu();
    }

    public static void displayGenreMenu() {
        System.out.println("""
                Genremeny
                =========
                1 - Visa alla genrer
                2 - Lägg till genre
                3 - Uppdatera genre
                4 - Ta bort genre
                5 - Tillbaks till huvudmeny
                """);
        System.out.print("Menyval: ");
    }

    public static void genreMenu() {
        displayGenreMenu();
        String userInput = Main.scanner.nextLine().toLowerCase();
        if (userInput.equals("5"))
            showMenu = true;
        switch (userInput) {
            case "1" -> Main.displayGenre();
            case "2" -> Main.insertGenre();
            case "3" -> Main.updateGenre();
            case "4" -> Main.deleteGenre();
            case "5" -> mainMenu();
        }
        genreMenu();
    }


}
