package com.tw.techops.dp.api.eda;

import com.tw.techops.dp.api.eda.controller.ApiController;
import com.tw.techops.dp.api.eda.controller.ApiReviewController;
import com.tw.techops.dp.api.eda.event.ApiReviewStateChangeType;
import com.tw.techops.dp.api.eda.event.ApiStateChangeType;
import com.tw.techops.dp.api.eda.model.Api;
import com.tw.techops.dp.api.eda.model.ApiReview;
import com.tw.techops.dp.api.eda.state.ApiReviewState;
import com.tw.techops.dp.api.eda.state.ApiState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EdaApplicationTests {

    @Autowired
    private ApiController apiController;

    @Autowired
    private ApiReviewController apiReviewController;

    private Api api = new Api();
    private ApiReview review1 = new ApiReview();
    private ApiReview review2 = new ApiReview();

    @Before
    public void init() {
        api.setId(1);
        api.setName("TEST_001");

        review1.setApi(api);
        review1.setComment("API-REVIEW-001");
        review1.setId("API-REVIEW-001");
        review1.setTimestamp(new Timestamp((new Date()).getTime()));

        review2.setApi(api);
        review2.setComment("API-REVIEW-002");
        review2.setId("API-REVIEW-002");
        review2.setTimestamp(new Timestamp((new Date()).getTime()));

        api.getReviewList().add(review1);
        api.getReviewList().add(review2);

        apiController.createApi(api);
    }

    // INITIAL -create-> UNPUBLISHED
    @Test
    public void testCreateIntialApi() {
        Api foundApi = apiController.getApiByName("TEST_001");
        Assert.assertEquals(ApiState.UNPUBLISHED, foundApi.getState());
    }

    // INITIAL -create-> NEW
    @Test
    public void testCreateApiReview() {
        apiReviewController.createApiReview(review1);
        ApiReview foundApiReview = apiReviewController.getApiReviewById("API-REVIEW-001");
        Assert.assertEquals(ApiReviewState.NEW, foundApiReview.getState());
    }

    // UNPUBLISHED -commit-> COMMITTED
    @Test
    public void testCommitApi() {
        apiController.updateApi(api, ApiStateChangeType.COMMIT);

        Api foundApi = apiController.getApiByName("TEST_001");

        Assert.assertEquals(ApiState.COMMITTED, foundApi.getState());

        Assert.assertEquals(ApiReviewState.NEW, review1.getState());
        Assert.assertEquals(ApiReviewState.NEW, review2.getState());
    }

    // UNPUBLISHED -reject-> UNPUBLISHED
    @Test
    public void testRejectUnpublishedApiFailed() {
        apiController.updateApi(api, ApiStateChangeType.REJECT);

        Api foundApi = apiController.getApiByName("TEST_001");

        Assert.assertEquals(ApiState.UNPUBLISHED, foundApi.getState());

        Assert.assertEquals(ApiReviewState.INTIAL, review1.getState());
        Assert.assertEquals(ApiReviewState.INTIAL, review2.getState());
    }

    // COMMIT -reject-> REJECTED
    @Test
    public void testRejectApi() {
        apiController.updateApi(api, ApiStateChangeType.COMMIT);
        Assert.assertEquals(ApiState.COMMITTED, api.getState());

        Assert.assertEquals(ApiReviewState.NEW, review1.getState());
        Assert.assertEquals(ApiReviewState.NEW, review2.getState());

        apiReviewController.updateApiReview(review1, ApiReviewStateChangeType.START);
        Assert.assertEquals(ApiReviewState.REVIEWING, review1.getState());
        Assert.assertEquals(ApiState.COMMITTED, api.getState());
        apiReviewController.updateApiReview(review1, ApiReviewStateChangeType.REJECT);
        Assert.assertEquals(ApiReviewState.REJECTED, review1.getState());
        Assert.assertEquals(ApiState.REJECTED, api.getState());

        Api foundApi = apiController.getApiByName("TEST_001");

        Assert.assertEquals(ApiState.REJECTED, foundApi.getState());
    }

    // COMMIT -approve-> PUBLISHED
    @Test
    public void testPublishApi() {
        apiController.updateApi(api, ApiStateChangeType.COMMIT);
        Assert.assertEquals(ApiState.COMMITTED, api.getState());

        Assert.assertEquals(ApiReviewState.NEW, review1.getState());
        Assert.assertEquals(ApiReviewState.NEW, review2.getState());

        apiReviewController.updateApiReview(review1,ApiReviewStateChangeType.START);
        Assert.assertEquals(ApiReviewState.REVIEWING, review1.getState());
        Assert.assertEquals(ApiState.COMMITTED, api.getState());
        apiReviewController.updateApiReview(review1, ApiReviewStateChangeType.APPROVE);
        Assert.assertEquals(ApiReviewState.APPROVED, review1.getState());
        Assert.assertEquals(ApiState.PUBLISHED, api.getState());

        Api foundApi = apiController.getApiByName("TEST_001");

        Assert.assertEquals(ApiState.PUBLISHED, foundApi.getState());
    }

    // REJECTED -reopen-> UNPUBLISHED
    @Test
    public void testReopenApi() {
        apiController.updateApi(api, ApiStateChangeType.COMMIT);
        apiController.updateApi(api, ApiStateChangeType.REJECT);
        apiController.updateApi(api, ApiStateChangeType.REOPEN);

        Api foundApi = apiController.getApiByName("TEST_001");

        Assert.assertEquals(ApiState.UNPUBLISHED, foundApi.getState());
    }

    // REJECTED -abandon-> ARCHIVED
    @Test
    public void testAbandonApi() {
        apiController.updateApi(api, ApiStateChangeType.COMMIT);
        apiController.updateApi(api, ApiStateChangeType.REJECT);
        apiController.updateApi(api, ApiStateChangeType.ABANDON);

        Api foundApi = apiController.getApiByName("TEST_001");

        Assert.assertEquals(ApiState.ARCHIVED, foundApi.getState());
    }

    // PUBLISHED -deprecate-> DEPRECATED
    @Test
    public void testDeprecateApi() {
        apiController.updateApi(api, ApiStateChangeType.COMMIT);
        apiController.updateApi(api, ApiStateChangeType.PUBLISH);
        apiController.updateApi(api, ApiStateChangeType.DEPRECATE);

        Api foundApi = apiController.getApiByName("TEST_001");

        Assert.assertEquals(ApiState.DEPRECATED, foundApi.getState());
    }

    // DEPRECATED -deactivate-> PUBLISHED
    @Test
    public void testReactivateApi() {
        apiController.updateApi(api, ApiStateChangeType.COMMIT);
        apiController.updateApi(api, ApiStateChangeType.PUBLISH);
        apiController.updateApi(api, ApiStateChangeType.DEPRECATE);
        apiController.updateApi(api, ApiStateChangeType.REACTIVATE);

        Api foundApi = apiController.getApiByName("TEST_001");

        Assert.assertEquals(ApiState.PUBLISHED, foundApi.getState());
    }

    // DEPRECATED -archive-> ARCHIVED
    @Test
    public void testArchiveApi() {
        apiController.updateApi(api, ApiStateChangeType.COMMIT);
        apiController.updateApi(api, ApiStateChangeType.PUBLISH);
        apiController.updateApi(api, ApiStateChangeType.DEPRECATE);
        apiController.updateApi(api, ApiStateChangeType.ARCHIVE);

        Api foundApi = apiController.getApiByName("TEST_001");

        Assert.assertEquals(ApiState.ARCHIVED, foundApi.getState());
    }
}
