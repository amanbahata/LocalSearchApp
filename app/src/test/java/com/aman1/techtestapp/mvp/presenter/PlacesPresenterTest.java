package com.aman1.techtestapp.mvp.presenter;

import com.aman1.techtestapp.constants.Constants;
import com.aman1.techtestapp.mvp.model.Response;
import com.aman1.techtestapp.mvp.model.Result;
import com.aman1.techtestapp.mvp.view.PlacesView;
import com.aman1.techtestapp.services.ApiHelper;
import com.aman1.techtestapp.storage.ISharedPreferencesManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlacesPresenterTest {

    private static final String TEST_PLACE = "big ben";

    @Mock private PlacesView view;
    @Mock private ApiHelper dataManager;
    @Mock private ISharedPreferencesManager preferences;

    private PlacesPresenter presenter;

    @Before
    public void setUp() throws Exception {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });

        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });

        presenter = new PlacesPresenter(view, dataManager, preferences);
    }

    @Test
    public void searchPlace_Success_When_Is_Connected_To_Internet() {

        Observer<Response> observer = mock(Observer.class);

        Response response = new Response();

        Observable<Response> responseObservable = Observable.just(response);
        responseObservable.subscribe(observer);
        when(dataManager.getPlace(anyString(), eq(Constants.GOOGLE_PLACES_API_KEY))).thenReturn(responseObservable);

        presenter.searchPlace(true, TEST_PLACE);

        verify(observer).onNext(response);
        verify(preferences).saveLastSearch(response);
        verify(view).onSearchSuccess(response.getResults());
    }

    @Test
    public void searchPlace_Fail_When_Is_Connected_To_Internet() {

        Observer<Response> observer = mock(Observer.class);

        String message = "api-key limit exceed.";
        Throwable throwable = new Throwable(message);

        Observable<Response> responseObservable = Observable.error(throwable);
        responseObservable.subscribe(observer);
        when(dataManager.getPlace(anyString(), eq(Constants.GOOGLE_PLACES_API_KEY))).thenReturn(responseObservable);

        presenter.searchPlace(true, TEST_PLACE);

        verify(observer).onError(throwable);
        verify(view).onSearchFail(eq(message));
    }

    @Test
    public void searchPlace_When_Is_Not_Connected_To_Internet_And_Have_Stored_Value() {

        Response savedResponse = new Response();

        when(preferences.getLastSearch()).thenReturn(savedResponse);

        presenter.searchPlace(false, TEST_PLACE);

        verify(preferences).getLastSearch();
        verify(view).onSearchSuccess(savedResponse.getResults());
    }


    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}