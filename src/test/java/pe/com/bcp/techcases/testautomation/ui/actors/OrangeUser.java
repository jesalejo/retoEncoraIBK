package pe.com.bcp.techcases.testautomation.ui.actors;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.steps.ScenarioSteps;

public class OrangeUser extends ScenarioSteps {

    static class PageContainer {
        private static PageObject CURRENT_PAGE;

        public static PageObject getCurrentPage() {
            return CURRENT_PAGE;
        }

        public static PageObject setCurrentPage(PageObject page) {
            CURRENT_PAGE = page;
            return CURRENT_PAGE;
        }
    }

    @Step
    public PageObject goToPage(Class<PageObject> pageClass) {
        PageObject page = getPages().get(pageClass);
        page.open();
        OrangeUser.PageContainer.setCurrentPage(page);
        return page;
    }

}