package com.rise.pages.projects;

import com.rise.pages.BasePage;
import com.rise.pages.components.DataTable;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Trang /projects/all_projects — bảng #project-table. Locator verify trên DOM thật (2026-11-11).
 */
public class ProjectsPage extends BasePage {

    private static final String PATH = "/projects/all_projects";

    private final By addProjectButton = By.cssSelector(".title-button-group a[title='Add project']");

    private final DataTable table;

    public ProjectsPage(WebDriver driver) {
        super(driver);
        this.table = new DataTable(driver, "project-table");
    }

    @Step("Mở trang Projects")
    public ProjectsPage open() {
        openPath(PATH);
        table.waitLoaded();
        return this;
    }

    @Step("Click \"Add project\"")
    public ProjectFormModal clickAddProject() {
        click(addProjectButton);
        return new ProjectFormModal(driver).waitOpen();
    }

    @Step("Tìm project \"{keyword}\"")
    public ProjectsPage search(String keyword) {
        table.search(keyword);
        return this;
    }

    public int countRowsTitled(String title) {
        return table.getRowsWithLink(title).size();
    }

    public String getCellOfProject(String title, String column) {
        return table.getCell(table.getRowWithLink(title), column);
    }
}
