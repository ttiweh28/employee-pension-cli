package com.company.pension.model;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;


public class PensionPlan {
    private String planReferenceNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollmentDate;

    private double monthlyContribution;

    public PensionPlan(String planReferenceNumber, LocalDate enrollmentDate, double monthlyContribution) {
        this.planReferenceNumber = planReferenceNumber;
        this.enrollmentDate = enrollmentDate;
        this.monthlyContribution = monthlyContribution;
    }


    public String getPlanReferenceNumber() { return planReferenceNumber; }
    public void setPlanReferenceNumber(String planReferenceNumber) { this.planReferenceNumber = planReferenceNumber; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public double getMonthlyContribution() { return monthlyContribution; }
    public void setMonthlyContribution(double monthlyContribution) { this.monthlyContribution = monthlyContribution; }

    @Override
    public String toString() {
        return "PensionPlan{" +
                "planReferenceNumber='" + planReferenceNumber + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                ", monthlyContribution=" + monthlyContribution +
                '}';
    }
}
