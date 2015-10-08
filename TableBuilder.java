import java.util.LinkedList;

public class TableBuilder {
    private Row header = null;
    
    private final LinkedList<Row> rows = new LinkedList<>();
    private LinkedList<String> defaultAttributes = new LinkedList<>();

    /**
     *
     * @param s adds a new column to the table with the header text 
     * @return TableBuilder.Cell that corresponds to this header cell
     */
    public Cell newHeaderCell(String s) {
        if (header == null)
            header = new Row("th");
        
        Cell c = header.newCell(s);
        
        for(String a : defaultAttributes)
            c.addAttribute(a);
        
        return c;
    }
    
    /**
     *
     * @return TableBuilder.Cell that is empty of text and all default attributes
     */
    public Cell newEmptyHeaderCell() {
        Cell c = newHeaderCell("");
        c.clearAttributes();
                
        return c;
    }
    
    /**
     *
     * @return TableBuilder.Row that is currently empty
     */
    public Row newRow() {
        Row r = new Row("td");
        rows.add(r);
        
        return r;
    }
    
    /**
     *
     * @param da List of default attributes, such as css class or colors
     */
    public void setDefaultAttributes(LinkedList<String> da) {
        if (da == null)
            throw new IllegalArgumentException("Passed in a null value");
                
        defaultAttributes = da;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<table>\n");
        
        if (header != null) {
            sb.append(header.toString()).append("\n");
        }
        for(Row row : rows) {
            sb.append(row.toString()).append("\n");
        }
        
        sb.append("</table>\n");
        
        return sb.toString();
    }

    public class Cell {
        private final LinkedList<String> attrs = new LinkedList<>();
        private String value = "";
        final private String type;
        
        Cell(String t, String s) {
            if (t == null || !t.equals("th") && !t.equals("td"))
                throw new IllegalArgumentException("Unexpected Cell Type: " + t);
            
            type = t;
            
            if (s != null)
                value = s;
        }
        
        /**
         *
         * @param v the string to be used for this cell in the table
         * @return a reference to this object
         */
        public Cell setValue(String v) {
            value = v;
            return this;
        }
        
        /**
         *
         * @param a an attribute to be added to just this Cell object.
         * @return a reference to this object
         */
        public Cell addAttribute(String a) {
            attrs.add(a);
            return this;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            
            sb.append("<").append(type);
            for(String a : attrs) {
                sb.append(" ").append(a);
            }
            sb.append(">");
            sb.append(value);
            sb.append("</").append(type).append(">");
            
            return sb.toString();
        }

        void clearAttributes() {
            attrs.clear();
        }
        
    }
    public class Row {
        private final LinkedList<Cell> cells = new LinkedList<>();
        private LinkedList<String> defaultAttributes = new LinkedList<>();
        private final String type;
        
        Row(String t) {
            this(t, new LinkedList<String>());
        }
        
        Row(String t, LinkedList<String> da) {
            type = t;
            defaultAttributes = da;
        }
        
        /**
         *
         * @param s the string to be used for this cell in the table
         * @return a reference to a new TableBuilder.Cell object
         */
        public Cell newCell(String s) {
            Cell c = new Cell(type, s);
            cells.add(c);
            
            for(String a : defaultAttributes)
                c.addAttribute(a);
            
            return c;
        }
        
        /**
         * Returns a a new TableBuilder.Cell object without any text or the default attributes
         * 
         * @return a reference to a new empty TableBuilder.Cell object
         */
        public Cell newEmptyCell() {
            Cell c = newCell("");
            c.clearAttributes();
            
            return c;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("<tr>");
            
            for(Cell c : cells) {
                sb.append(c.toString());
            }
            
            sb.append("</tr>");
            
            return sb.toString();
        }
    }    
}
