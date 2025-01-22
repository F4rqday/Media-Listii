public class Film extends Media {
    public Film(String title, String status) {
        super(title, status);
    }

    @Override
    public String getType() {
        return "Film";
    }
}
