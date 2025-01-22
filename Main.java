import java.util.*;

public class Main {
    public static void main(String[] args) {
        MediaManager manager = MediaManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to Media Manager!");

        while (true) {
            System.out.println("\nSelect an action:");
            System.out.println("1. Add media");
            System.out.println("2. Update status");
            System.out.println("3. Delete media");
            System.out.println("4. Rate media");
            System.out.println("5. Search media");
            System.out.println("6. Sort by title");
            System.out.println("7. Sort by rating");
            System.out.println("8. Display media list");
            System.out.println("9. Exit");

            command = scanner.nextLine();

            switch (command) {
                case "1": {
                    String type = "";
                    for (int attempts = 0; attempts < 3; attempts++) {
                        System.out.println("Enter media type (Film/Series/Anime):");
                        type = scanner.nextLine().toLowerCase();
                        if (type.equals("film") || type.equals("series") || type.equals("anime")) {
                            break;
                        }
                        System.out.println("Invalid type. Please enter Film, Series, or Anime.");
                        if (attempts == 2) {
                            System.out.println("Too many invalid attempts. Returning to main menu.");
                            continue;
                        }
                    }

                    System.out.println("Enter title:");
                    String title = scanner.nextLine();

                    String status = "";
                    for (int attempts = 0; attempts < 3; attempts++) {
                        System.out.println("Enter status (Planned/Watching/Watched):");
                        status = scanner.nextLine().toLowerCase();
                        if (status.equals("planned") || status.equals("watching") || status.equals("watched")) {
                            break;
                        }
                        System.out.println("Invalid status. Please enter Planned, Watching, or Watched.");
                        if (attempts == 2) {
                            System.out.println("Too many invalid attempts. Returning to main menu.");
                            continue;
                        }
                    }

                    switch (type) {
                        case "film" -> manager.addMedia(new Film(title, status));
                        case "series" -> manager.addMedia(new Series(title, status));
                        case "anime" -> manager.addMedia(new Anime(title, status));
                    }
                    break;
                }
                case "2": {
                    String updateTitle = "";
                    boolean success = false;
                    for (int attempts = 0; attempts < 3; attempts++) {
                        System.out.println("Enter media title:");
                        updateTitle = scanner.nextLine();
                        if (manager.mediaExists(updateTitle)) {
                            success = true;
                            break;
                        }
                        System.out.println("Media not found. Please enter an existing title.");
                        if (attempts == 2) {
                            System.out.println("Too many invalid attempts. Returning to main menu.");
                            continue;
                        }
                    }

                    if (!success) break;

                    String newStatus = "";
                    for (int attempts = 0; attempts < 3; attempts++) {
                        System.out.println("Enter new status (Planned/Watching/Watched):");
                        newStatus = scanner.nextLine().toLowerCase();
                        if (newStatus.equals("planned") || newStatus.equals("watching") || newStatus.equals("watched")) {
                            break;
                        }
                        System.out.println("Invalid status. Please enter Planned, Watching, or Watched.");
                        if (attempts == 2) {
                            System.out.println("Too many invalid attempts. Returning to main menu.");
                            continue;
                        }
                    }
                    manager.updateMediaStatus(updateTitle, newStatus);
                    break;
                }
                case "3": {
                    System.out.println("Enter media title to delete:");
                    String deleteTitle = scanner.nextLine();
                    manager.deleteMedia(deleteTitle);
                    break;
                }
                case "4": {
                    System.out.println("Enter media title:");
                    String rateTitle = scanner.nextLine();

                    double rating = 0;
                    for (int attempts = 0; attempts < 3; attempts++) {
                        System.out.println("Enter rating (0-10):");
                        try {
                            rating = Double.parseDouble(scanner.nextLine());
                            if (rating >= 0 && rating <= 10) {
                                break;
                            }
                            System.out.println("Rating must be between 0 and 10.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number between 0 and 10.");
                        }
                        if (attempts == 2) {
                            System.out.println("Too many invalid attempts. Returning to main menu.");
                            continue;
                        }
                    }
                    manager.rateMedia(rateTitle, rating);
                    break;
                }
                case "5": {
                    System.out.println("Enter keyword to search:");
                    String keyword = scanner.nextLine();
                    manager.searchMedia(keyword);
                    break;
                }
                case "6": {
                    manager.sortMediaByTitle();
                    break;
                }
                case "7": {
                    manager.sortMediaByRating();
                    break;
                }
                case "8": {
                    manager.displayMedia();
                    break;
                }
                case "9": {
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    return;
                }
                default: {
                    System.out.println("Invalid command. Please try again.");
                    break;
                }
            }
        }
    }
}
