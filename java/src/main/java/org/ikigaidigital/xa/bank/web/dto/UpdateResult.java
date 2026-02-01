package org.ikigaidigital.xa.bank.web.dto;

public class UpdateResult {
    private int updatedCount;
    private String message;

    public UpdateResult() {}
    public UpdateResult(int updatedCount, String message) {
        this.updatedCount = updatedCount;
        this.message = message;
    }

    // getters / setters
    public int getUpdatedCount() { return updatedCount; }
    public void setUpdatedCount(int updatedCount) { this.updatedCount = updatedCount; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
