/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.utpl.resource.sbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/* nombre apellido genero ciudad edad ocuapacion
 *
 * @author david
 * 
 */
public class main {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("InputDialog Example #1");
String name;
name = JOptionPane.showInputDialog(frame, "Ingrese palabra clave?");
String endpoint = "http://dbpedia.org/sparql?";
String format = "json";
String query = "select distinct ?s ?on ?cn where {\n"
+ "?s a ?o;\n"
+ " dbo:city ?c.\n"
+ "?o rdfs:label ?on.\n"
+ "?c rdfs:label ?cn.\n"
+ "filter(regex(?c, \"" + name + "\" ))\n"
+ "FILTER (lang(?on) = 'es')\n"
+ "FILTER (lang(?cn) = 'es')\n"
+ "} LIMIT 10";
URL url = new URL(endpoint + "query=" + URLEncoder.encode(query, "UTF-8") + "&format=" + format);
try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
for (String line; (line = reader.readLine()) != null;) {
System.out.println(line);
}
}
}
}