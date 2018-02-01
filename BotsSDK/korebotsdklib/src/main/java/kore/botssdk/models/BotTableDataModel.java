package kore.botssdk.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ramachandra Pradeep on 30-Oct-17.
 */

public class BotTableDataModel {

    /*private List<Header> headers = null;
    private List<List<String>> rows = null;
    */

    /*public class Header{
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPercentage() {
            return percentage;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }

        public String getAlignment() {
            return alignment;
        }

        public void setAlignment(String alignment) {
            this.alignment = alignment;
        }

        public String title;
        public double percentage;
        public String alignment;
    }
    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }*/


    private List<List<String>> columns = null;
    private List<Element> elements = null;
    private String tableDesign;

    public class Element {

        private List<String> values = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    }

    public List<List<String>> getColumns() {
        return columns;
    }

    public void setColumns(List<List<String>> columns) {
        this.columns = columns;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public String getTableDesign() {
        return tableDesign;
    }

    public void setTableDesign(String tableDesign) {
        this.tableDesign = tableDesign;
    }
}
