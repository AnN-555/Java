// src/main/java/uit/ie303/demo/model/FeedbackDTO.java
package uit.ie303.demo.model;

import java.util.Date;

public class FeedbackDTO {
    private String customerName;
    private String comment;
    private Date createdAt;

    // Constructor rỗng (Spring cần)
    public FeedbackDTO() {}

    // Getters & Setters
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}