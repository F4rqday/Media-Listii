public abstract class Media {
    private String title;
    private String status;
    private double rating;

    public Media(String title, String status) {
        this.title = title;
        this.status = status;
        this.rating = 0.0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating >= 0 && rating <= 10) {
            this.rating = rating;
        } else {
            System.out.println("Rating must be between 0 and 10.");
        }
    }

    public abstract String getType();
}
