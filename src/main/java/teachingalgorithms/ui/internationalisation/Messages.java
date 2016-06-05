/*
 *
 *     Copyright (C) 2015-2016  Moritz Flöter
 *     Copyright (C) 2016  Jonathan Lechner
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package teachingalgorithms.ui.internationalisation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * <p>
 *         The Class Messages supports internationalisation.
 * </p>
 * @author Jonathan Lechner
 */
public class Messages {

    /**
     * The ResourceBundle with text for language und country.
     */
    ResourceBundle messages;

    /**
     * The constructor with definition for language and country.
     * @param locale
     */
    public Messages(Locale locale) {
        messages = ResourceBundle.getBundle("Messages", locale, new EncodingControl());
    }

    /**
     * Returns the text for textID in the predefined language.
     * @param property id for text.
     * @return text in selected language.
     */
    public String getMessage(String property) {
        return messages.getString(property);
    }

    /**
     * Returns the textlist for textID in the predefined language.
     * @param property id for textlist.
     * @return textlist in selected language.
     */
    public String[] getMessages(String property) {
        return messages.getStringArray(property);
    }

    /**
     * Constructs the selected string with dynamic Values.
     * insert [ArrayIndex] into your property and pass the array to this method it will replace the placeholders.
     * for example {"foo", "bar"} and "this is a property string with [0] placeholders [0] in it [1]"
     * will be returned as "this is a property string with foo placeholders foo in it bar"
     * @param property id for text.
     * @param toFillIn array with filling values (should be as long as the highest replace index)
     * @return constructed text.
     */
    public String getPreparedMessage(String property, String[] toFillIn) {
        String toReturn = messages.getString(property);
        for (int i = 0; i < toFillIn.length; i++) {
            toReturn = toReturn.replaceAll("\\[" + i + "\\]", toFillIn[i]);
        }
        return toReturn;
    }

    /**
     * used to deal with special character in properties.
     */
    private static class EncodingControl extends ResourceBundle.Control {
        private static final String BUNDLE_EXTENSION = "properties";
        private static final String ENCODING = "UTF-8";

        public ResourceBundle newBundle
                (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException
        {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, BUNDLE_EXTENSION);
            ResourceBundle bundle = null;
            InputStream stream = null;
            if (reload) {
                URL url = loader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            } else {
                stream = loader.getResourceAsStream(resourceName);
            }
            if (stream != null) {
                try {
                    bundle = new PropertyResourceBundle(new InputStreamReader(stream, ENCODING));
                } finally {
                    stream.close();
                }
            }
            return bundle;
        }
    }
}
