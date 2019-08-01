package org.odk.collect.android.views;

import android.app.Activity;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import org.javarosa.core.reference.InvalidReferenceException;
import org.javarosa.core.reference.Reference;
import org.javarosa.core.reference.ReferenceManager;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.odk.collect.android.audio.AudioHelper;
import org.odk.collect.android.audio.ScreenContext;
import org.odk.collect.android.support.RobolectricHelpers;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class MediaLayoutTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    public ReferenceManager referenceManager;

    @Mock
    public AudioHelper audioHelper;

    @Test
    public void withTextView_andAudio_playingAudio_highlightsText() throws Exception {
        setupAudioFile("file://audio.mp3", referenceManager);

        MutableLiveData<Boolean> isPlaying = new MutableLiveData<>();
        isPlaying.setValue(false);
        when(audioHelper.setAudio(any(), any(), any())).thenReturn(isPlaying);

        Activity activity = RobolectricHelpers.createThemedActivity(ScreenContextFragmentActivity.class);

        MediaLayout mediaLayout = new MediaLayout(activity);
        mediaLayout.setAVT(
                new TextView(activity),
                "file://audio.mp3",
                null,
                null,
                null,
                referenceManager,
                audioHelper);

        int originalTextColor = mediaLayout.getTextView().getCurrentTextColor();

        isPlaying.setValue(true);
        int textColor = mediaLayout.getTextView().getCurrentTextColor();
        assertThat(textColor, not(equalTo(originalTextColor)));

        isPlaying.setValue(false);
        textColor = mediaLayout.getTextView().getCurrentTextColor();
        assertThat(textColor, equalTo(originalTextColor));
    }

    private static void setupAudioFile(String audioURI, ReferenceManager referenceManager) throws InvalidReferenceException {
        Reference reference = mock(Reference.class);
        when(reference.getLocalURI()).thenReturn(audioURI);
        when(referenceManager.deriveReference(audioURI)).thenReturn(reference);
    }

    public static class ScreenContextFragmentActivity extends FragmentActivity implements ScreenContext {

        @Override
        public FragmentActivity getActivity() {
            return this;
        }

        @Override
        public LifecycleOwner getViewLifecycle() {
            return this;
        }
    }
}
