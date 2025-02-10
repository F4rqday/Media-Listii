import java.util.Scanner;

public class MediaManager {
    private static MediaManager instance;
    private DatabaseManager dbManager;

    private MediaManager() {
        dbManager = new DatabaseManager();
    }

    public static MediaManager getInstance() {
        if (instance == null) {
            instance = new MediaManager();
        }
        return instance;
    }

    public void startTerminal() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nSelect an action:");
            System.out.println("1. Add media");
            System.out.println("2. Update status");
            System.out.println("3. Delete media");
            System.out.println("4. Display media list");
            System.out.println("5. Sort by title");
            System.out.println("6. Sort by rating");
            System.out.println("7. Exit");

            String command = scanner.nextLine();

            switch (command) {
                case "1": {
                    System.out.println("Enter title:");
                    String title = scanner.nextLine();

                    String type = getValidMediaType(scanner);
                    String status = getValidStatus(scanner);
                    double rating = getValidRating(scanner);

                    dbManager.addMedia(title, status, rating, type);
                    break;
                }
                case "2": {
                    System.out.println("Enter media title to update:");
                    String title = scanner.nextLine();

                    String newStatus = getValidStatus(scanner);

                    dbManager.updateMediaStatus(title, newStatus);
                    break;
                }
                case "3": {
                    System.out.println("Enter media title to delete:");
                    String title = scanner.nextLine();
                    dbManager.deleteMedia(title);
                    break;
                }
                case "4": {
                    dbManager.displayMedia();
                    break;
                }
                case "5": {
                    dbManager.sortMediaByTitle();
                    break;
                }
                case "6": {
                    dbManager.sortMediaByRating();
                    break;
                }
                case "7": {
                    dbManager.closeConnection();
                    System.out.println("Goodbye!");
                    return;
                }
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private String getValidStatus(Scanner scanner) {
        while (true) {
            System.out.println("Select status:");
            System.out.println("1 - Planned");
            System.out.println("2 - Watching");
            System.out.println("3 - Watched");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    return "Planned";
                case "2":
                    return "Watching";
                case "3":
                    return "Watched";
                default:
                    System.out.println(" Invalid choice! Please enter 1, 2, or 3.");
            }
        }
    }

    private String getValidMediaType(Scanner scanner) {
        while (true) {
            System.out.println("Select media type:");
            System.out.println("1 - Film");
            System.out.println("2 - Series");
            System.out.println("3 - Anime");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    return "Film";
                case "2":
                    return "Series";
                case "3":
                    return "Anime";
                default:
                    System.out.println(" Invalid choice! Please enter 1, 2, or 3.");
            }
        }
    }

    private double getValidRating(Scanner scanner) {
        while (true) {
            System.out.println("Enter rating (0.0 - 10.0):");
            String input = scanner.nextLine();
            try {
                double rating = Double.parseDouble(input);
                if (rating >= 0.0 && rating <= 10.0) {
                    return rating;
                } else {
                    System.out.println(" Rating must be between 0.0 and 10.0.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a number between 0.0 and 10.0.");
            }
        }
    }
}
