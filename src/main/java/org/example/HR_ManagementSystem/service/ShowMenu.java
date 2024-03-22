package org.example.HR_ManagementSystem.service;

public abstract class ShowMenu {
    public final String ANSI_GREEN = "\u001B[32m";
    public final String ANSI_BLUE = "\u001B[34m";
    public final String ANSI_RED = "\u001B[31m";
    public final String ANSI_YELLOW = "\u001B[33m";
    public final String ANSI_RESET = "\u001B[0m";

    public void display() {
        String horizontalLine = "\u2550".repeat(53);
        System.out.println("\u2554" + horizontalLine + "\u2557");
        System.out.println("\u2551" + " " + ANSI_GREEN + "Добро пожаловать в систему управления сотрудниками!" + ANSI_RESET + " " + "\u2551");
        System.out.println("\u255A" + horizontalLine + "\u255D");
        System.out.println(ANSI_BLUE + "== Выберите действие: \n" + ANSI_RESET);
    }

    abstract void doDisplay();
}
