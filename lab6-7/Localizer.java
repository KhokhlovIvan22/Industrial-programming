import java.util.Locale;
import java.util.ResourceBundle;

public class Localizer {
    private static ResourceBundle bundle;
    private static Locale currentLocale;

    public static void init(String language, String region) {
        currentLocale = new Locale(language, region);
        bundle = ResourceBundle.getBundle("messages", currentLocale);
    }

    public static String t(String key) {
        if (bundle == null)
            init("en", "GB");
        return bundle.getString(key);
    }

    public static Locale locale() {
        if (currentLocale == null)
            init("en", "GB");
        return currentLocale;
    }
}