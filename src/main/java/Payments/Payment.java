package Payments;

public class Payment {
    
    private String convention, description, period;
    private float total;

    public Payment (String convention, String description, String period, float total) {
        this.convention = convention;
        this.description = description;
        this.period = period;
        this.total = total;
    }

    public void setConvention(String convention) {
        this.convention = convention;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getConvention() {
        return convention;
    }

    public String getDescription() {
        return description;
    }

    public String getPeriod() {
        return period;
    }

    public float getTotal() {
        return total;
    }
}