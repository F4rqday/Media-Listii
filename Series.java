public class Series extends Media {
    public Series(String title, String status) {
        super(title, status);
    }

    @Override
    public String getType() {
        return "Series";
    }
}
