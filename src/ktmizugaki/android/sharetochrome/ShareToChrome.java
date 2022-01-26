package ktmizugaki.android.sharetochrome;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ShareToChrome extends Activity
{
    private static final String TAG = "ShareToChrome";

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Intent intent = getIntent();
        if (intent != null) {
            CharSequence text = intent.getCharSequenceExtra(Intent.EXTRA_TEXT);
            if (text != null) {
                openWithChrome(text.toString());
            }
        }
        finish();
    }

    private void openWithChrome(String url) {
        try {
            Uri uri = Uri.parse(url);
            if (uri == null || !isWebScheme(uri.getScheme())) {
                Toast.makeText(this, R.string.toast_unsupported, Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, R.string.toast_not_installed, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isWebScheme(String scheme) {
        return scheme != null && ("https".equalsIgnoreCase(scheme) || "http".equalsIgnoreCase(scheme));
    }
}
