package info.jacoblee.apparchitecture.common.router;

import android.app.Activity;
import android.os.Bundle;

import com.airbnb.deeplinkdispatch.DeepLinkHandler;

@DeepLinkHandler({ RouterModule.class })
public class RouterActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DeepLinkDelegate, LibraryDeepLinkModuleRegistry and AppDeepLinkModuleRegistry
        // are generated at compile-time.
        DeepLinkDelegate deepLinkDelegate =
                new DeepLinkDelegate(new RouterModuleRegistry());
        // Delegate the deep link handling to DeepLinkDispatch.
        // It will start the correct Activity based on the incoming Intent URI
        deepLinkDelegate.dispatchFrom(this);
        // Finish this Activity since the correct one has been just started
        finish();
    }
}
