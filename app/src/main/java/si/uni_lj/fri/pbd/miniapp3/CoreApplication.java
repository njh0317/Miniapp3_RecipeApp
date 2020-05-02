package si.uni_lj.fri.pbd.miniapp3;

import android.app.Application;

import timber.log.Timber;

public class CoreApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        plantTimber();
    }

    private void plantTimber() {
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

}
