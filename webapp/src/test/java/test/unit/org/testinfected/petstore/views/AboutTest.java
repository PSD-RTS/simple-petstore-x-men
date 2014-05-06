package test.unit.org.testinfected.petstore.views;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testinfected.hamcrest.dom.DomMatchers.*;
import static test.support.org.testinfected.petstore.web.OfflineRenderer.render;

import org.junit.Test;
import org.w3c.dom.Element;

import test.support.org.testinfected.petstore.web.OfflineRenderer;
import test.support.org.testinfected.petstore.web.WebRoot;

public class AboutTest {
        final static String ABOUT_TEMPLATE = "about";
        final static int TEAM_SIZE = 6;
        Element page;

        @Test
        public void aboutPageHasImgTitleAbout() {
            // load page html
            page = renderAboutPage().asDom();

            // do assert on h1 tag and check if value is equal to "About"
            assertThat("about page", page, hasUniqueSelector("h1 img", hasAttribute("src", "http://img4.wikia.nocookie.net/__cb20140116014001/cartoonfatness/images/f/ff/X-men-logo.png")));

        }

        @Test
        public void aboutPageHasAboutSectionMeta() {
            // load page html
            page = renderAboutPage().asDom();

            // do assert on h1 tag and check if value is equal to "About"
            assertThat("about page", page, hasUniqueSelector("meta[name='section']", hasAttribute("content", "about")));

        }

        @Test
        public void aboutPageLinkIsHighlighted() {
            // load page html
            page = renderAboutPage().asDom();
            String s = renderAboutPage().asString();

            // do assert on h1 tag and check if value is equal to "About"
            assertThat("about page", page, hasUniqueSelector("meta[name='section']", hasAttribute("content", "about")));

        }

        @Test
        public void haveListOfDevsInPage() {
            // load page html
            page = renderAboutPage().asDom();

            // counter size of items in dom and check if it is equal to the team
            // size
            assertThat("cart page", page,
                    hasSelector("#dev ul li", hasSize(TEAM_SIZE)));

        }
        @Test
        public void haveListOfDevsDescriptionInPage() {
            // load page html
            page = renderAboutPage().asDom();

            // counter size of items in dom and check if it is equal to the team (for description)
            // size
            assertThat("cart page", page,
                    hasSelector("#dev ul .description", hasSize(TEAM_SIZE)));

        }
        @Test
        public void haveListOfDevsImageInPage() {
            // load page html
            page = renderAboutPage().asDom();

            // counter size of items in dom and check if it is equal to the team (for images)
            // size
            assertThat("cart page", page,
                    hasSelector("#dev ul li img", hasSize(TEAM_SIZE)));

        }

        private OfflineRenderer renderAboutPage() {
            return render(ABOUT_TEMPLATE).from(WebRoot.pages());
        }
}
