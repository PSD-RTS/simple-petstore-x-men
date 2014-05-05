package test.unit.org.testinfected.petstore.views;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testinfected.hamcrest.dom.DomMatchers.hasSelector;
import static org.testinfected.hamcrest.dom.DomMatchers.hasSize;
import static org.testinfected.hamcrest.dom.DomMatchers.hasText;
import static org.testinfected.hamcrest.dom.DomMatchers.hasUniqueSelector;
import static test.support.org.testinfected.petstore.web.OfflineRenderer.render;

import org.junit.Test;
import org.w3c.dom.Element;

import test.support.org.testinfected.petstore.web.OfflineRenderer;
import test.support.org.testinfected.petstore.web.WebRoot;

public class AboutTest {
	String ABOUT_TEMPLATE = "about";

	Element page;

	@Test
	public void aboutPageHasTitleAbout() {
		page = renderAboutPage().asDom();

		assertThat("cart page", page, hasUniqueSelector("h1", hasText("About")));

		
	}
	
	@Test
	public void haveListOfDevInPage(){
		page = renderAboutPage().asDom();

		assertThat("cart page", page, hasSelector("#dev ul li", hasSize(6)));

	}

	private OfflineRenderer renderAboutPage() {
		return render(ABOUT_TEMPLATE).from(WebRoot.pages());
	}
}
