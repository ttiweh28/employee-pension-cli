package com.company.pension.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

    public class Employee {
        private long employeeId;
        private String firstName;
        private String lastName;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate employmentDate;

        private double yearlySalary;
        private PensionPlan pensionPlan; // null if not enrolled

        public Employee(long employeeId, String firstName, String lastName,
                        LocalDate employmentDate, double yearlySalary) {
            this.employeeId = employeeId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.employmentDate = employmentDate;
            this.yearlySalary = yearlySalary;
        }


        public long getEmployeeId() { return employeeId; }
        public void setEmployeeId(long employeeId) { this.employeeId = employeeId; }

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }

        public LocalDate getEmploymentDate() { return employmentDate; }
        public void setEmploymentDate(LocalDate employmentDate) { this.employmentDate = employmentDate; }

        public double getYearlySalary() { return yearlySalary; }
        public void setYearlySalary(double yearlySalary) { this.yearlySalary = yearlySalary; }

        public PensionPlan getPensionPlan() { return pensionPlan; }
        public void setPensionPlan(PensionPlan pensionPlan) { this.pensionPlan = pensionPlan; }

        @Override
        public String toString() {
            return "Employee{" +
                    "employeeId=" + employeeId +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", employmentDate=" + employmentDate +
                    ", yearlySalary=" + yearlySalary +
                    ", pensionPlan=" + pensionPlan +
                    '}';
        }
    }

