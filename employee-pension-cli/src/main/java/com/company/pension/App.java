package com.company.pension;

import com.company.pension.model.Employee;
import com.company.pension.model.PensionPlan;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        List<Employee> employees = loadSampleData();

        // Feature 1: All employees
        printAllEmployeesAsJson(employees);

        // Feature 2: Quarterly Upcoming Enrollees
        printQuarterlyUpcomingEnrollees(employees);
    }

    private static List<Employee> loadSampleData() {
        List<Employee> employees = new ArrayList<>();

        // #1 Daniel Agar (with PensionPlan EX1089)
        Employee e1 = new Employee(
                1L,
                "Daniel",
                "Agar",
                LocalDate.parse("2023-01-17"),
                105945.50
        );
        e1.setPensionPlan(new PensionPlan("EX1089", null, 100.00));
        employees.add(e1);

        // #2 Benard Shaw (not enrolled)
        Employee e2 = new Employee(
                2L,
                "Benard",
                "Shaw",
                LocalDate.parse("2022-09-03"),
                197750.00
        );
        employees.add(e2);

        // #3 Carly Agar (with PensionPlan SM2307)
        Employee e3 = new Employee(
                3L,
                "Carly",
                "Agar",
                LocalDate.parse("2014-05-16"),
                842000.75
        );
        e3.setPensionPlan(new PensionPlan("SM2307", LocalDate.parse("2017-05-17"), 1555.50));
        employees.add(e3);

        // #4 Wesley Schneider (not enrolled)
        Employee e4 = new Employee(
                4L,
                "Wesley",
                "Schneider",
                LocalDate.parse("2023-07-21"),
                74500.00
        );
        employees.add(e4);

        // #5 Anna Wiltord (not enrolled)
        Employee e5 = new Employee(
                5L,
                "Anna",
                "Wiltord",
                LocalDate.parse("2023-03-15"),
                85750.00
        );
        employees.add(e5);

        // #6 Yosef Tesfalem (not enrolled)
        Employee e6 = new Employee(
                6L,
                "Yosef",
                "Tesfalem",
                LocalDate.parse("2024-10-03"),
                100000.00
        );
        employees.add(e6);

        return employees;
    }

    private static void printAllEmployeesAsJson(List<Employee> employees) throws Exception {
        employees.sort(
                Comparator.comparingDouble(Employee::getYearlySalary).reversed()
                        .thenComparing(Employee::getLastName)
        );

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String json = mapper.writeValueAsString(employees);
        System.out.println("Printing all Employees (Sorted)");
        System.out.println(json);
    }

    private static void printQuarterlyUpcomingEnrollees(List<Employee> employees) throws Exception {
        LocalDate today = LocalDate.now();
        LocalDate[] nextQuarter = getNextQuarterRange(today);
        LocalDate start = nextQuarter[0];
        LocalDate end = nextQuarter[1];

        List<Employee> upcoming = employees.stream()
                .filter(e -> e.getPensionPlan() == null) // not enrolled
                .filter(e -> {
                    LocalDate qualificationDate = e.getEmploymentDate().plusYears(3);
                    return ( !qualificationDate.isBefore(start) && !qualificationDate.isAfter(end) );
                })
                .sorted(Comparator.comparing(Employee::getEmploymentDate).reversed())
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String json = mapper.writeValueAsString(upcoming);
        System.out.println("Printing quarterly Upcoming Enrollees Report");
        System.out.println(json);
    }

    private static LocalDate[] getNextQuarterRange(LocalDate today) {
        int year = today.getYear();
        int month = today.getMonthValue();

        if (month <= 3) { // current Q1 → next Q2
            return new LocalDate[]{
                    LocalDate.of(year, Month.APRIL, 1),
                    LocalDate.of(year, Month.JUNE, 30)
            };
        } else if (month <= 6) { // current Q2 → next Q3
            return new LocalDate[]{
                    LocalDate.of(year, Month.JULY, 1),
                    LocalDate.of(year, Month.SEPTEMBER, 30)
            };
        } else if (month <= 9) { // current Q3 → next Q4
            return new LocalDate[]{
                    LocalDate.of(year, Month.OCTOBER, 1),
                    LocalDate.of(year, Month.DECEMBER, 31)
            };
        } else { // current Q4 → next Q1 of next year
            return new LocalDate[]{
                    LocalDate.of(year + 1, Month.JANUARY, 1),
                    LocalDate.of(year + 1, Month.MARCH, 31)
            };
        }
    }
}
