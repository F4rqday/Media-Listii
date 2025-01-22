import java.util.*;

public class MediaManager {
    private static MediaManager instance;
    private List<Media> mediaList;

    private MediaManager() {
        mediaList = new ArrayList<>();
    }

    public static MediaManager getInstance() {
        if (instance == null) {
            instance = new MediaManager();
        }
        return instance;
    }

    public void addMedia(Media media) {
        if (mediaList.stream().anyMatch(m -> m.getTitle().equalsIgnoreCase(media.getTitle()))) {
            System.out.println("Media with this title already exists.");
        } else {
            mediaList.add(media);
            System.out.println("Media added successfully.");
        }
    }

    public boolean mediaExists(String title) {
        return mediaList.stream().anyMatch(media -> media.getTitle().equalsIgnoreCase(title));
    }

    public void updateMediaStatus(String title, String newStatus) {
        for (Media media : mediaList) {
            if (media.getTitle().equalsIgnoreCase(title)) {
                media.setStatus(newStatus);
                System.out.println("Status updated!");
                return;
            }
        }
        System.out.println("Media not found. Please check the title and try again.");
    }

    public void deleteMedia(String title) {
        Optional<Media> mediaOptional = mediaList.stream()
                .filter(media -> media.getTitle().equalsIgnoreCase(title))
                .findFirst();

        if (mediaOptional.isPresent()) {
            mediaList.remove(mediaOptional.get());
            System.out.println("Media deleted successfully.");
        } else {
            System.out.println("Media not found. Please check the title and try again.");
        }
    }

    public void rateMedia(String title, double rating) {
        for (Media media : mediaList) {
            if (media.getTitle().equalsIgnoreCase(title)) {
                media.setRating(rating);
                System.out.println("Rating updated!");
                return;
            }
        }
        System.out.println("Media not found.");
    }

    public void searchMedia(String keyword) {
        List<Media> results = mediaList.stream()
                .filter(media -> media.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();

        if (results.isEmpty()) {
            System.out.println("No results found for keyword: " + keyword);
        } else {
            System.out.println("Search results:");
            for (Media media : results) {
                System.out.println(media.getType() + ": " + media.getTitle() + " [" + media.getStatus() + "] Rating: " + media.getRating());
            }
        }
    }

    public void sortMediaByTitle() {
        mediaList.sort(Comparator.comparing(Media::getTitle, String.CASE_INSENSITIVE_ORDER));
        System.out.println("List sorted by title:");
        displayMedia();
    }

    public void sortMediaByRating() {
        mediaList.sort(Comparator.comparingDouble(Media::getRating).reversed());
        System.out.println("List sorted by rating:");
        displayMedia();
    }

    public void displayMedia() {
        if (mediaList.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }

        for (Media media : mediaList) {
            System.out.println(media.getType() + ": " + media.getTitle() + " [" + media.getStatus() + "] Rating: " + media.getRating());
        }
    }
}
