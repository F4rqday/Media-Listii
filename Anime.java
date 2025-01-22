public class Anime extends Media {
    public Anime(String title, String status) {
        super(title, status);
    }

    @Override
    public String getType() {
        return "Anime";
    }
}
