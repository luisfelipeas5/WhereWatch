package br.com.luisfelipeas5.givemedetails.details;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import br.com.luisfelipeas5.givemedetails.rules.AppTestComponentTestRule;
import br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity;
import br.com.luisfelipeas5.givemedetails.view.di.modules.model.ModelTestModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class ReviewsInstrumentedTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(DetailActivity.class, true, false);
    @Rule
    public AppTestComponentTestRule appTestComponentTestRule = new AppTestComponentTestRule();

    private Intent intent;
    private List<Review> mReviews;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Movie movie = ModelTestModule.getMovieMocked();
        mReviews = ModelTestModule.getReviewsMocked();

        intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.getId());
    }

    @Test
    public void whenGetReviews_showList_success() {
        mActivityRule.launchActivity(intent);

        onView(allOf(withId(R.id.progress_bar), isDescendantOfA(withId(R.id.fragment_reviews))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.txt_no_reviews))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        for (Review review : mReviews) {
            onView(withText(review.getAuthor()))
                    .perform(ViewActions.scrollTo())
                    .check(
                            matches(allOf(withId(R.id.txt_author), withText(review.getAuthor())))
                    )
                    .check(
                            matches(allOf(withId(R.id.txt_content), withText(review.getContent())))
                    );

        }
    }

}
