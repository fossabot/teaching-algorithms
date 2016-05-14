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

package teachingalgorithms.logic.graph.protocol.step;

import teachingalgorithms.logic.graph.util.AdjacencyMatrix;
import teachingalgorithms.ui.i18n.I18n;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * contains information about step done by algorithm.
 * @author Jonathan Lechner
 */
public class Step {

    private AdjacencyMatrix matrix;

    private Map<String, List<String>> descriptionKeysAndReplaceValues;

    private Map<String, Object> additionalInformation;

    public Step(AdjacencyMatrix matrix) {
        descriptionKeysAndReplaceValues = new HashMap<>();
        additionalInformation = new HashMap<>();

        this.matrix = matrix;
    }

    public void addDescription(String key, List<String> replaceValues) {
        descriptionKeysAndReplaceValues.put(key, replaceValues);
    }

    public String getDescriptionText(I18n message) {
        String toReturn = "";
        descriptionKeysAndReplaceValues.entrySet().forEach(entry -> {
            toReturn.concat(message.getPreparedMessage(entry.getKey(), (String[]) entry.getValue().toArray()));
        });
        return toReturn;
    }

    public void addAdditionalInformation(String key, Object o) {
        additionalInformation.put(key, o);
    }

    public Object getAdditionalInformation(String key) {
        return additionalInformation.get(key);
    }

    public AdjacencyMatrix getMatrix() {
        return matrix;
    }
}
